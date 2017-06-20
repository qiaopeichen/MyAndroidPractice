package com.example.a66_banzheng;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BanZhengService.MyBinder myBinder; // 我定义的中间人对象
    private MyConn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BanZhengService.class);
        //连接服务
        conn = new MyConn();
        bindService(intent, conn, BIND_AUTO_CREATE);
    }
    //点击按钮调用服务里面办证的方法
    public void click(View v) {
        myBinder.callBanZheng(10);
    }

    //监视服务的状态
    private class MyConn implements ServiceConnection {
        //当服务连接成功时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取中间人对象
            myBinder = (BanZhengService.MyBinder) service;
        }
        //失去连接
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        //当activity销毁的时候 解绑服务
        unbindService(conn);
        super.onDestroy();
    }
}
