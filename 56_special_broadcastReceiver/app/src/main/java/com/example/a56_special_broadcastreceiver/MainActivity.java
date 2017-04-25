package com.example.a56_special_broadcastreceiver;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ScreenReceiver screenReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态的去注册广播接收者
        screenReceiver = new ScreenReceiver();
        //创建IntentFilter对象
        IntentFilter filter = new IntentFilter();
        //添加要注册的action
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        //动态注册广播接收者
        registerReceiver(screenReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        //当activity销毁的时候要取消注册广播接收者
        unregisterReceiver(screenReceiver);
        super.onDestroy();
    }
}
