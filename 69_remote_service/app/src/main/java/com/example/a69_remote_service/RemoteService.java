package com.example.a69_remote_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import android.util.Log;

public class RemoteService extends Service {
    private static final String TAG = "jojo";
    public RemoteService() {
    }
    //把我们定义的中间人对象返回
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();
    }

    //定义一个方法
    public void methodService() {
        Log.d(TAG, "methodService: ");
    }


    private class MyBinder extends IService.Stub {

        @Override
        public void callMethodService() throws RemoteException {
            //调用方法
            methodService();
        }
    }
}
