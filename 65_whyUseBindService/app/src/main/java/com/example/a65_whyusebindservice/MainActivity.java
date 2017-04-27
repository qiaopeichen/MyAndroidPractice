package com.example.a65_whyusebindservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }
    //点击按钮 调用服务里面的方法
    public void click(View v) {
        //自己new 对象 脱离了谷歌框架 脱离了环境 没有上下文
        TestService testService = new TestService();
        testService.methodService();
    }
}
