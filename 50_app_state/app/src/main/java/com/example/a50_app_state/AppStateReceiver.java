package com.example.a50_app_state;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppStateReceiver extends BroadcastReceiver {
    private static final String TAG = "jojo";
    // 当有新的应有被安装了 或有应用被卸载了 调用此方法
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        // 获取当前广播事件类型
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_INSTALL".equals(action)) {
            Log.d(TAG, "onReceive: 应用安装了11111");
        } else if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            Log.d(TAG, "onReceive: 应用安装了22222");
        } else if("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            Log.d(TAG, "onReceive: 应用卸载了" +intent.getData());
        }

    }
}
