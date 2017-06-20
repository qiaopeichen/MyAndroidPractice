package com.example.a40_createnewactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {
    //当Activity启动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1 加载布局
        setContentView(R.layout.activity_test);

        //2 获取MainActivity传递过来的数据
        Intent intent = getIntent();
        //3 获取数据
        Uri data = intent.getData();


    }
}
