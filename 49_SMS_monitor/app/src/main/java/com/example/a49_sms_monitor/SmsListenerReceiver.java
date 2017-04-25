package com.example.a49_sms_monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class SmsListenerReceiver extends BroadcastReceiver {
    // 当短信到来的时候执行
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        // 获取发送者的号码 和发送内容
        Log.d("mylog", "start");
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        Log.d("mylog", "Object[] objects = (Object[]) intent.getExtras().get(\"pdus\");");
        for (Object obj : objects) {
            //1 获取 smsmessage的实例
//            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj); 已弃用。

            /**
             * 第二个参数应该是短信的类型, 大概是GSM与CDMA短信的解码方式不同
             */
//            String format = intent.getStringExtra("format");
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            //2 获取发送短信的内容
            String messageBody = smsMessage.getMessageBody();
            String address = smsMessage.getOriginatingAddress();

            Toast.makeText(context, messageBody +"  "+ address, Toast.LENGTH_SHORT).show();
            System.out.println("jojo" + messageBody + address);

        }
    }
}
