package com.red.databindingadapterhelp.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.red.databindingadapterhelp.models.RecyclerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * email: redthree3333@gmail.com
 * author: jredthree
 * time: 2016/5/3
 */
public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Default Mode
    private final int TYPE_NORMAL = 0X11;

    //Custom Mode
    private final int TYPE_CUSTOM = 0X12;

    //header type
    private final int VIEW_HEADER = 0X20;

    //bodyer type
    private final int VIEW_BODYER = 0X00;

    //footer type
    private final int VIEW_FOOTER = 0X22;

    //head data
    private final List<RecyclerModel<T>> headViews;

    //body data
    protected final List<T> mData;

    //foot data
    private final List<RecyclerModel<T>> footViews;

    //header count
    private int headerCount;

    //footer count
    private int footerCount;

    //bodyer click listener
    private MyItemClickListener myItemClickListener;

    //bodyer long click listener
    private MyItemLongClickListener myItemLongClickListener;

    //header click listener
    private OnHeaderClickListener headerClickListener;

    //footer click listener
    private OnFooterClickListener footerClickListener;

    private int mMode;

    protected RecyclerBaseAdapter(List<T> mData) {

        this.mData = (mData != null) ? mData : new ArrayList<T>();

        headViews = new ArrayList<>();

        footViews = new ArrayList<>();

        headerCount = 0;

        if( 0 != getStartMode()){
            mMode = TYPE_CUSTOM;
        }else{
            mMode = TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = null;

        if(null != headViews && viewType == VIEW_HEADER && getHeaderVisible()) {

            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    getHeaderId(headerCount),
                    parent,
                    false);
            headerCount++;

        }else if(null != footViews && viewType == VIEW_FOOTER && getFooterVisible()) {

            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    getFooterId(footerCount),
                    parent,
                    false);
            footerCount++;
        }else{

            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    getItemLayoutId(viewType),
                    parent,
                    false);

        }

        RecyclerViewHolder holder = new RecyclerViewHolder(binding.getRoot());

        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;


        switch (getItemViewType(position)){
            case VIEW_HEADER:

                bindData(recyclerViewHolder,headViews.get(position).getVariableId(),headViews.get(position).getData());
                recyclerViewHolder.itemView.setOnClickListener(getHeaderClickListener(recyclerViewHolder.itemView, position));

                break;

            case VIEW_FOOTER:
                bindData(recyclerViewHolder,footViews.get(getFooterPosition(recyclerViewHolder)).getVariableId(),footViews.get(getFooterPosition(recyclerViewHolder)).getData());
                holder.itemView.setOnClickListener(getFooterClickListener(recyclerViewHolder.itemView,getFooterPosition(recyclerViewHolder)));

                break;
            default:
                recyclerViewHolder.itemView.setOnClickListener(getClickListener(recyclerViewHolder.itemView,getRealPosition(holder)));

                recyclerViewHolder.itemView.setOnLongClickListener(getLongClickListener(recyclerViewHolder.itemView,getRealPosition(holder)));

                switch (mMode){
                    case TYPE_NORMAL:
                        bindData(recyclerViewHolder,getVariableId(getItemViewType(position)),mData.get(getRealPosition(recyclerViewHolder)));
                        break;
                    case TYPE_CUSTOM:
                        bindData(recyclerViewHolder,getVariableId(getItemViewType(position)),mData.get(getRealPosition(recyclerViewHolder)));
                        bindCustomData(recyclerViewHolder,position,mData.get(getRealPosition(recyclerViewHolder)));
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == headViews) return getItemTypePosition(position);

        if (position < headViews.size()) return VIEW_HEADER;

        if (position < (getRealListSize() + headViews.size())) return getItemTypePosition(position - headViews.size());

        if (null != footViews && position < getItemCount()) return VIEW_FOOTER;

        return VIEW_BODYER;
    }

    /**
     * all data
     * @return header + bodyer + footer
     */
    @Override
    synchronized public int getItemCount() {

        int size = 0;

        if(null != headViews){
            size += headViews.size();
        }
        if(null != footViews){
            size += footViews.size();
        }

        size += mData.size();

        return size;
    }

    /**
     * bodyer data
     * @return body data size
     */
    public int getRealListSize(){
        return mData.size();
    }

    private int getFooterPosition(RecyclerView.ViewHolder holder){

        int position = holder.getLayoutPosition();

        return footViews == null ? position : position - (headViews.size() + getRealListSize()) ;
    }

    /**
     *  add bodyer data
     * @param pos position
     * @param item content
     */
    public void add(int pos,T item){
        mData.add(pos, item);
        notifyDataSetChanged();
    }
    /**
     * add bodyer data list
     * @param items content
     */
    public void addBodyerList(List<T> items){
        mData.addAll(items);
        notifyDataSetChanged();
    }
    /**
     * remove bodyer data
     * @param pos position
     */
    public void delete(int pos){
        mData.remove(pos);
        notifyDataSetChanged();
    }
    /**
     * set bodyer data list
     * @param items content
     */
    public void setBodyerListData(List<T> items){
        clearData();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * clear bodyer all data
     */
    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }
    /**
     * bodyer data
     * @param pos position
     * @return T content
     */
    public T getItemObject(int pos){

        return  mData.get(pos);
    }

    /**
     * add header data
     * @param viewLayoutId view layoutid
     */
    public void addHeadView(RecyclerModel viewLayoutId){

        headViews.add(viewLayoutId);
        notifyDataSetChanged();
    }

    /**
     * remove header
     * @param pos position
     */
    public void removeHeadView(int pos){
        if( 0 == headViews.size() ){
            return;
        }
        headerCount = 0;
        headViews.remove(pos);
        notifyDataSetChanged();
    }

    /**
     * get header layoutid
     * @param position position
     * @return layoutid
     */
    private int getHeaderId(int position){
        return headViews.get(position).getLayoutId();
    }

    private boolean getHeaderVisible(){
        return headerCount < headViews.size();
    }

    /**
     * add footer data
     * @param recyclerModel recyclermodel model
     */
    public void addFootView(RecyclerModel recyclerModel){

        footViews.add(recyclerModel);
        notifyDataSetChanged();
    }

    /**
     * remove footer data
     * @param pos position
     */
    public void removeFootView(int pos){
        if(0 == footViews.size()){
            return;
        }
        footerCount = 0;
        footViews.remove(pos);
        notifyDataSetChanged();
    }

    /**
     * get layoutid
     * @param position position
     * @return layoutid
     */
    private int getFooterId(int position){

        return footViews.get(position).getLayoutId();
    }

    private boolean getFooterVisible(){
        return footerCount < footViews.size();
    }

    /**
     * bodyer true position
     * @param holder holder obj
     * @return true position
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {

        int position = holder.getLayoutPosition();

        return headViews == null ? position : position - headViews.size();
    }

    private View.OnClickListener getClickListener(final View view, final int pos){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null != myItemClickListener) {
                    myItemClickListener.onItemClick(view, pos);
                }
            }
        };
    }

    private View.OnLongClickListener getLongClickListener(final View view, final int pos){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if( null != myItemLongClickListener) {
                    myItemLongClickListener.onItemLongClick(view, pos);
                }
                return true;
            }
        };
    }

    private View.OnClickListener getHeaderClickListener(final View view, final int pos){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != headerClickListener) {
                    headerClickListener.headerClick(view, pos);
                }
            }
        };
    }

    private View.OnClickListener getFooterClickListener(final View view, final int pos){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != footerClickListener) {
                    footerClickListener.footerClick(view, pos);
                }
            }
        };
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == VIEW_HEADER ? gridManager.getSpanCount() : getItemViewType(position) == VIEW_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public void setMyItemLongClickListener(MyItemLongClickListener myItemLongClickListener) {
        this.myItemLongClickListener = myItemLongClickListener;
    }

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public void setHeaderClickListener(OnHeaderClickListener headerClickListener) {
        this.headerClickListener = headerClickListener;
    }

    public void setFooterClickListener(OnFooterClickListener footerClickListener) {
        this.footerClickListener = footerClickListener;
    }

    /**
     * bodyer layoutid reference
     * @param viewType type
     * @return layoutid
     */
    abstract public int getItemLayoutId(int viewType);

    /**
     * bind  reference
     * @return BR id
     */
    abstract public int getVariableId(int viewType);

    abstract public int getItemTypePosition(int position);

    private void bindData(RecyclerViewHolder holder,int variableId ,T item){
        holder.getBinding().setVariable(variableId, item);
        holder.getBinding().executePendingBindings();
    }

    /**
     * custom
     * @param holder viewholder
     * @param position position
     * @param item item
     */
    abstract public void bindCustomData(RecyclerViewHolder holder,int position,T item);

    /**
     * load mode
     * @return 0 default
     */
    abstract public int getStartMode();

    /**
     * header click interface
     */
    public interface OnHeaderClickListener{
        void headerClick(View view, int position);
    }

    /**
     * footer click interface
     */
    public interface OnFooterClickListener{
        void footerClick(View view, int position);
    }

    /**
     * bodyer long click interface
     */
    public interface MyItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }

    /**
     * bodyer click interface
     */
    public interface MyItemClickListener {
        public void onItemClick(View view, int position);
    }
}

