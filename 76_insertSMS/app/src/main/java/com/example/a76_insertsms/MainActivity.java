package com.example.a76_insertsms;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String defaultSmsPkg;
    private String mySmsPkg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取默认App包名并保存
        defaultSmsPkg = Telephony.Sms.getDefaultSmsPackage(this);
        mySmsPkg = this.getPackageName();
        if (!defaultSmsPkg.equals(mySmsPkg)) {
            //            如果这个App不是默认的Sms App，则修改成默认的SMS APP
            //            因为从Android 4.4开始，只有默认的SMS APP才能对SMS数据库进行处理
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, mySmsPkg);
            startActivity(intent);
        }
    }


    // 点击按钮 往短信数据库里面插入一条数据
    public void click1(View v) {

        // 由于短信数据库已经通过内容提供者暴露出来了 所以我们可以直接通过内容解析者操作数据库
        Uri uri = Uri.parse("content://sms/inbox");
        ContentValues values = new ContentValues();
        values.put("address", "110");
        values.put("body", "王先生，请您马上来一趟，否则后果自负");
        values.put("date", System.currentTimeMillis());
        getContentResolver().insert(uri, values);

        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, defaultSmsPkg);
        startActivity(intent);
    }
}
