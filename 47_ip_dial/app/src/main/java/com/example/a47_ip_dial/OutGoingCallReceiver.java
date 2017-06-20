package com.example.a47_ip_dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class OutGoingCallReceiver extends BroadcastReceiver {
    private static final String TAG = "mylog";
    //当进行外拨电话的时候调用
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "start");
        //0 获取用户输入的ip号码 ip号码在config.xml里面存着
        SharedPreferences sp = context.getSharedPreferences("config", 0);
        Log.d(TAG, "SharedPreferences sp = context.getSharedPreferences(\"config\", 0);");
        String ipNumber = sp.getString("ipnumber", "");
        //1 获取当前拨打的电话号码
        String currentNumber = getResultData();
        Log.d(TAG, "String currentNumber = getResultData();");
        //2 在当前的号码前面加上17951
        //2.1 判断当前拨打的号码是否是长途
        if (currentNumber.startsWith("1")) {
            //2.2 修改拨打电话的号码

            setResultData(ipNumber + currentNumber);
            Log.d(TAG, "setResultData(ipNumber + currentNumber);   ");
        }

    }
}
