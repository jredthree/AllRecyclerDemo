package com.red.allrecyclerdemo;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.red.allrecyclerdemo.adapter.UserAdapter;
import com.red.allrecyclerdemo.databinding.ActivityMainBinding;
import com.red.allrecyclerdemo.models.Header;
import com.red.allrecyclerdemo.models.User;
import com.red.databindingadapterhelp.adapter.RecyclerBaseAdapter;
import com.red.databindingadapterhelp.models.RecyclerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerBaseAdapter.OnHeaderClickListener,RecyclerBaseAdapter.OnFooterClickListener {

    private UserAdapter mAdapter;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mUsers = new ArrayList<>();
        setData();
        mAdapter = new UserAdapter(mUsers);
        RecyclerModel<Header> recyclerModel1 = new RecyclerModel<>(R.layout.header_item_1, com.red.allrecyclerdemo.BR.header1,new Header("我是头部1"));
        RecyclerModel<Header> recyclerModel2 = new RecyclerModel<>(R.layout.header_item_1, com.red.allrecyclerdemo.BR.header1,new Header("我是头部2"));

        mAdapter.addHeadView(recyclerModel1);
        mAdapter.addHeadView(recyclerModel2);

        RecyclerModel<Header> recyclerModel3 = new RecyclerModel<>(R.layout.header_item_1, com.red.allrecyclerdemo.BR.header1,new Header("我是尾部1"));
        RecyclerModel<Header> recyclerModel4 = new RecyclerModel<>(R.layout.header_item_1, com.red.allrecyclerdemo.BR.header1,new Header("我是尾部2"));
        mAdapter.addFootView(recyclerModel3);
        mAdapter.addFootView(recyclerModel4);

        mAdapter.setHeaderClickListener(this);
        mAdapter.setFooterClickListener(this);
        //设置布局管理器
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView.setAdapter(mAdapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeHeadView(0);
            }
        });

        mAdapter.setMyItemClickListener(new RecyclerBaseAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("TAG","---"+position);
            }
        });

    }

    private void setData(){

        for(int i = 0; i < 20; i++){
            User user = new User();
            if(i==5){
                user.setTitle("标题");
            }
            user.setAvatar("http://g.hiphotos.baidu.com/image/h%3D200/sign=4d3fabc3cbfc1e17e2bf8b317a91f67c/6c224f4a20a446230761b9b79c22720e0df3d7bf.jpg");
            user.setName("小米" + i);
            user.setAge(String.valueOf(i));
            mUsers.add(user);
        }
    }

    @Override
    public void headerClick(View view, int position) {
        switch (position){
            case 0:
                Toast.makeText(this,"我是头部"+position,Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this,"我是头部"+position,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void footerClick(View view, int position) {
        switch (position){
            case 0:
                Toast.makeText(this,"我是尾部"+position,Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this,"我是尾部"+position,Toast.LENGTH_SHORT).show();
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
}
