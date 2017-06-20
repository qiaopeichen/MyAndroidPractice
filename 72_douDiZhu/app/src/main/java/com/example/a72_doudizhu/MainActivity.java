package com.example.a72_doudizhu;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a71_alipay.IService;

public class MainActivity extends AppCompatActivity {

    private IService iService; // 中间人对象
    MyConn conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("com.itheima.alipay");
        intent.setPackage("com.example.a71_alipay");
        //1 调用bindService 获取中间人对象
        conn = new MyConn();
        //2 绑定服务 获取服务里面定义的中间人对象
        bindService(intent, conn, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        // 当Activity销毁的时候解绑服务
        unbindService(conn);
        super.onDestroy();
    }
    // 点击按钮 进行买豆的逻辑
    public void click(View v) {
        try {
            boolean result = iService.callPay("abc", "123", 999);
            if (result) {
                Toast.makeText(getApplicationContext(), "买豆成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "买豆失败", Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    //监视服务的状态
    private class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取中间人对象
            iService = IService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
