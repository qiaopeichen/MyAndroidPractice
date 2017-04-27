package com.example.a67_banzheng2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DemoService extends Service {
    private static final String TAG = "jojp";
    public DemoService() {
    }
    // 把我定义的中间人对象返回
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();
    }

    //banzheng的方法
    public void banZheng(int money) {
        if (money > 1000) {
            Toast.makeText(getApplicationContext(), "banZheng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "banZhengFail", Toast.LENGTH_SHORT).show();
        }
    }

    //打麻将的方法
    public void playMaJiang() {
        Log.d(TAG, "playMaJiang: ");
    }
    //洗桑拿的方法
    public void sangNa() {
        Log.d(TAG, "sangNa: ");
    }

    private class MyBinder extends Binder implements Iservice{
        @Override
        public void callBanZheng(int money) {
            //调用banzheng的方法
            banZheng(money);
        }
        public void callPlayMaJiang() {
            //调用playMaJiang的方法
            playMaJiang();
        }
        public void callSangNa() {
            //调用洗桑拿的方法
            sangNa();
        }
    }
}
