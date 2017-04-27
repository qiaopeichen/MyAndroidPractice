package com.example.a63_phone_listener;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Myconn conn;
    private static final String TAG = "jojo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d(TAG, "onCreate: fuckit");
    }
    //点击按钮 开启服务 通过startservice
    public void click1(View v) {
        Intent intent = new Intent(this, PhoneService.class);
        startService(intent); // 开启服务
    }
    public void click2(View v) {
        Intent intent = new Intent(this, PhoneService.class);
        stopService(intent);
    }
    //点击按钮 绑定服务 开启服务的第二种方式
    public void click3(View v) {
        Intent intent = new Intent(this,PhoneService.class);
        //连接到DemoService这个服务
        conn = new Myconn();
        bindService(intent, conn, BIND_AUTO_CREATE);
    }
    //点击按钮手动解绑服务
    public void click4(View v) {
        unbindService(conn);
    }
    //定义一个类 用来监视服务的状态
    private class Myconn implements ServiceConnection {
        //当服务连接成功调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("jojo", "onServiceConnected: ");
        }
        //失去连接时调用
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
