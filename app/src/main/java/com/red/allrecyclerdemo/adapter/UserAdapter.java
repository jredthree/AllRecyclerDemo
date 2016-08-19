package com.red.allrecyclerdemo.adapter;

import com.red.allrecyclerdemo.BR;
import com.red.allrecyclerdemo.R;
import com.red.allrecyclerdemo.models.User;
import com.red.databindingadapterhelp.adapter.RecyclerBaseAdapter;
import com.red.databindingadapterhelp.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public class UserAdapter extends RecyclerBaseAdapter<User> {

    public UserAdapter(List<User> mData) {
        super(mData);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType){
            case 1:
                return R.layout.main_item;
            case 2:
                return R.layout.main_item2;
            default:
                return R.layout.main_item;
        }


    }

    @Override
    public int getVariableId(int viewType) {
        switch (viewType){
            case 1:
                return BR.users;
            case 2:
                return BR.titles;
            default:
                return BR.users;
        }

    }

    @Override
    public int getItemTypePosition(int position) {
        return mData.get(position).getTitle() == null ? 1 : 2;
    }

    @Override
    public void bindCustomData(RecyclerViewHolder holder, int position, User item) {

    }

    @Override
    public int getStartMode() {
        return 0;
    }
}
