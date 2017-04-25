package com.example.a56_special_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    //当我们进行屏幕锁屏和解锁时 这个方法执行
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        //获取当前广播的事件类型
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            Log.d("jojo", "onReceive: 屏幕锁屏了");
        } else if ("android.intent.action.SCREEN_ON".equals(action)) {
            Log.d("jojo", "onReceive: 屏幕解锁了");
        }
    }
}
