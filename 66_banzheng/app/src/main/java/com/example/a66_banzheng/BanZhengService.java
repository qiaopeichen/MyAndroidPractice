
package com.example.a66_banzheng;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BanZhengService extends Service {
    //把我定义的中间人对象返回
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();
    }

    public void banZheng(int money) {
        if (money>1000) {
            Toast.makeText(getApplicationContext(), "我是领导，把证给你办了", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "这点钱还想办事", Toast.LENGTH_SHORT).show();
        }
    }

    //定义中间人对象（IBinder）
    public class MyBinder extends Binder {
        public void callBanZheng(int money) {
            //调用办证的方法
            banZheng(money);
        }
    }
}
