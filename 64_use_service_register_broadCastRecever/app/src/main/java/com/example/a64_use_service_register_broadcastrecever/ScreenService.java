package com.example.a64_use_service_register_broadcastrecever;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ScreenService extends Service {

    private ScreenReceiver receiver;

    public ScreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        //1 创建ScreenReceiver实例
        receiver = new ScreenReceiver();
        //2 获取IntentFilter实例 目的是添加action
        IntentFilter filter = new IntentFilter();
        //3 添加action
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        //4 动态注册广播接收者
        registerReceiver(receiver, filter);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        //当服务销毁的时候 取消注册广播接收者
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
