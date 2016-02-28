package com.red.allrecyclerdemo.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 * 洪三
 */
public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_NORMAL = 0X11;//默认模式

    private final int TYPE_CUSTOM = 0X12;//自定义模式

    protected final List<T> mData;

    protected final Context mContext;

    protected LayoutInflater mInflater;

    private MyItemClickListener myItemClickListener;

    private MyItemLongClickListener myItemLongClickListener;

    private int mMode;

    protected RecyclerBaseAdapter(List<T> mData, Context mContext) {
        this.mData = (mData != null) ? mData : new ArrayList<T>();

        this.mContext = mContext;

        mInflater = LayoutInflater.from(mContext);

        if( 0 != getStartMode()){
            mMode = TYPE_CUSTOM;
        }else{
            mMode = TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                getItemLayoutId(viewType),
                parent,
                false);

        final RecyclerViewHolder holder = new RecyclerViewHolder(binding.getRoot());

        holder.setBinding(binding);

        if( null != myItemClickListener){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }

        if( null != myItemLongClickListener){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myItemLongClickListener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                    return true;
                }
            });
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

        switch (mMode){
            case TYPE_NORMAL:
                bindData(recyclerViewHolder,mData.get(position));
                break;
            case TYPE_CUSTOM:
                bindData(recyclerViewHolder,mData.get(position));
                bindCustomData(recyclerViewHolder,position,mData.get(position));
                break;
        }


    }

    /**
     * 加载图片方法
     * @param view
     * @param url
     */
    @BindingAdapter({"bind:imageUrl","bind:error"})
    public static void loadImage(ImageView view, String url,Drawable error) {
        Picasso.with(view.getContext()).load(url).error(error).into(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(int pos,T item){
        mData.add(pos, item);
        notifyDataSetChanged();
    }

    public void delete(int pos){
        mData.remove(pos);
        notifyDataSetChanged();
    }

    public T getItemObject(int pos){

        return  mData.get(pos);
    }

    public void setMyItemLongClickListener(MyItemLongClickListener myItemLongClickListener) {
        this.myItemLongClickListener = myItemLongClickListener;
    }

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    /**
     * 布局引用
     * @param viewType
     * @return
     */
    abstract public int getItemLayoutId(int viewType);

    /**
     * data bind Variable 引用
     * @return
     */
    abstract public int getVariableId();

    private void bindData(RecyclerViewHolder holder,T item){
        holder.getBinding().setVariable(getVariableId(), item);
        holder.getBinding().executePendingBindings();
    }

    /**
     * 自定义操作
     * @param holder
     * @param position
     * @param item
     */
    abstract public void bindCustomData(RecyclerViewHolder holder,int position,T item);

    /**
     * 加载模式
     * @return
     */
    abstract public int getStartMode();

    public interface MyItemLongClickListener {
        public void onItemLongClick(View view,int position);
    }
    public interface MyItemClickListener {
        public void onItemClick(View view,int position);
    }
}

