package com.red.allrecyclerdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.red.allrecyclerdemo.adapter.UserAdapter;
import com.red.allrecyclerdemo.databinding.ActivityMainBinding;
import com.red.allrecyclerdemo.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserAdapter mAdapter;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mUsers = new ArrayList<User>();
        setData();
        mAdapter = new UserAdapter(mUsers,MainActivity.this);
        //设置布局管理器
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView.setAdapter(mAdapter);
    }

    private void setData(){

        for(int i = 0; i < 10; i++){
            User user = new User();
            user.setAvatar("http://g.hiphotos.baidu.com/image/h%3D200/sign=4d3fabc3cbfc1e17e2bf8b317a91f67c/6c224f4a20a446230761b9b79c22720e0df3d7bf.jpg");
            user.setName("小米" + i);
            user.setAge(String.valueOf(i));
            mUsers.add(user);
        }
    }
}
