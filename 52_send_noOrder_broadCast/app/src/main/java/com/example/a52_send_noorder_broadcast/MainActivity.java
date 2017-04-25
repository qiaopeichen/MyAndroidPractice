package com.example.a52_send_noorder_broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // 点击按钮 发送一条无序广播
    public void click(View v) {
        Intent intent = new Intent();
        intent.setAction("com.itheima.custom");
        intent.putExtra("name", "新闻联播每天晚上7点准时开整！！！");
        sendBroadcast(intent); // 发送无序广播
    }
}
