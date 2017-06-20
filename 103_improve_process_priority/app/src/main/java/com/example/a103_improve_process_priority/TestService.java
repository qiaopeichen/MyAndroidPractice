package com.example.a103_improve_process_priority;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import static android.R.attr.id;

public class TestService extends Service {
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    // 当服务第一次启动的时候调用
    @Override
    public void onCreate() {
        // Make this service run in the foreground
        startForeground(id, notification);
        super.onCreate();
    }
}
