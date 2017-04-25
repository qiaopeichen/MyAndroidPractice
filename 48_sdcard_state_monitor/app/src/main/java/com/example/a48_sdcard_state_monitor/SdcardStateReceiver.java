package com.example.a48_sdcard_state_monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SdcardStateReceiver extends BroadcastReceiver {
    private static final String TAG = "mylog";
    
    // 当sd状态发生改变的时候执行
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        //获取到当前广播的事件类型
        String action = intent.getAction();
        if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
            Log.d(TAG, "onReceive: SD卡挂载了"); 
        } else if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
            Log.d(TAG, "onReceive: SD卡卸载了");
        }
    }
}
