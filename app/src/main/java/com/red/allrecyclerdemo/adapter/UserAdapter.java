package com.red.allrecyclerdemo.adapter;

import android.content.Context;

import com.red.allrecyclerdemo.BR;
import com.red.allrecyclerdemo.R;
import com.red.allrecyclerdemo.models.User;

import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public class UserAdapter extends RecyclerBaseAdapter<User> {

    public UserAdapter(List<User> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.main_item;
    }

    @Override
    public int getVariableId() {
        return BR.users;
    }

    @Override
    public void bindCustomData(RecyclerViewHolder holder, int position, User item) {

    }

    @Override
    public int getStartMode() {
        return 0;
    }
}
