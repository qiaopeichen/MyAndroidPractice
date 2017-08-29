package com.itheima.mobilesafe74.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itheima.mobilesafe74.R;

public class BlackNumberActivity extends AppCompatActivity {
    //1.复用convertView
    //2.对findViewById次数的优化，使用ViewHolder
    //3.将ViewHolder定义成静态，不会去创建多个对象
    //4.listView如果有多个条目的时候，可以做分页算法，每一次加载20条，逆序返回
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);
        initUI();
        initData();
    }
}
