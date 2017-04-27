package com.example.a68_baidumusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Iservice iservice; // 这个是我们定义的中间人对象
    private MyConn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 混合方式开启服务
        //1 先调用startService 目的是可以保证服务在后台长期运行
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        //2 调用bingService 目的是为了获取我们定义的中间人对象 就可以间接的调用服务里面的方法了
        conn = new MyConn();
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        //当Activity销毁的时候解绑服务，目的是为了不报红色日志
        unbindService(conn);
        super.onDestroy();
    }
    //点击按钮 播放音乐
    public void click1(View v) {
        //调用播放音乐的方法
        iservice.callPlayMusic();
    }
    //点击按钮 暂停音乐
    public void click2(View v) {
        //调用暂停音乐的方法
        iservice.callPauseMusic();
    }
    //点击按钮 继续播放音乐
    public void click3(View v) {
        //调用继续播放音乐的方法
        iservice.callRePlayMusic();
    }

    //监听服务的状态
    private class MyConn implements ServiceConnection {
        //当服务连接成功时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取我们的中间人对象
            iservice = (Iservice) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
