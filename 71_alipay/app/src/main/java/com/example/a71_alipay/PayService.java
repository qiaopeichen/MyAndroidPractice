package com.example.a71_alipay;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class PayService extends Service {

    private static final String TAG = "jojo";

    public PayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();
    }

    //支付的方法
    public boolean pay(String username, String pwd, int money) {
        Log.d(TAG, "pay: 检测用户名密码是否正确");
        Log.d(TAG, "pay: 检测密码通过C去实现");
        Log.d(TAG, "pay: 检测斗地主应用是否携带病毒");
        Log.d(TAG, "pay: ......");
        if ("abc".equals(username) && "123".equals(pwd) && money <= 5000) {
            return true;
        } else {
            return false;
        }
    }
    //1 定义中间人对象
    private class MyBinder extends IService.Stub{

        @Override
        public boolean callPay(String username, String pwd, int money) throws RemoteException {
            //调用支付的方法
            return pay(username, pwd, money);
        }
    }
}
