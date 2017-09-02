package com.itheima.mobilesafe74.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "jojo";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "onReceive: 监听手机启动广播");
        // 1.获取开机后手机的sim卡的序列号
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = tm.getSimSerialNumber();
        // 2.sp中存储的序列卡号
        String sim_number = SpUtil.getString(context, ConstantValue.SIM_NUMBER, "");
        // 3.比对不一致
        if (!simSerialNumber.equals(sim_number)) {
            //4. 发短信给选中联系人号码
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("5556", null, "sim change!!!", null, null);
        }
    }
}
