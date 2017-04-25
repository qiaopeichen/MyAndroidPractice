package com.example.a53_receive_noorder_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiveCustomReceiver extends BroadcastReceiver {
    // 当接收到我们发送的自定义广播
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        //0 终止广播
        abortBroadcast();
        //1 获取发送广播携带的数据
        String content = intent.getStringExtra("name");
        Toast.makeText(context,content, Toast.LENGTH_SHORT).show();
    }
}
