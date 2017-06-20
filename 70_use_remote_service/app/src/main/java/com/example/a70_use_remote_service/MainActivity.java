package com.example.a70_use_remote_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a69_remote_service.IService;

public class MainActivity extends AppCompatActivity {

    private IService iService; // 中间人对象
    private MyConn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 调用bindService获取中间人对象
        Intent intent = new Intent();
        intent.setAction("com.example.a69_remote_service");
        intent.setPackage("com.example.a69_remote_service");
        conn = new MyConn();
        //2 连接服务 目的为了获取我们定义的中间人对象
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
    // 点击按钮调用上个应用 服务里面的方法
    public void click(View v) {
        try {
            iService.callMethodService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //监视服务的状态
    private class MyConn implements ServiceConnection {
        //连接成功
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取中间人对象 注意这里面 获取中间人对象的方式变了 和之前不一样
            iService = IService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
