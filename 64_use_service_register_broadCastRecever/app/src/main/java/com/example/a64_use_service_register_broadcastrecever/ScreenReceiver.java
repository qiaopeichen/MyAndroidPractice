package com.example.a64_use_service_register_broadcastrecever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    private static final String TAG = "jojo";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        //获取当前事件类型
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            Log.d(TAG, "onReceive: 屏幕锁屏了");
        } else if ("android.intent.action.SCREEN_ON".equals(action)) {
            Log.d(TAG, "onReceive: 屏幕解锁了");
        }
    }
}
