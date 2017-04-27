package com.example.a65_whyusebindservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class TestService extends Service {
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
    return null;
    }
    //在服务里面定义的方法
    public void methodService() {
        Toast.makeText(getApplicationContext(), "haha", Toast.LENGTH_SHORT).show();
    }
}
