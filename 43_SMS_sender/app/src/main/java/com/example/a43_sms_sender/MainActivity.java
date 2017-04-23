package com.example.a43_sms_sender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;
    private EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找到我们关心的控件
        et_number = (EditText) findViewById(R.id.et_number);
        et_content = (EditText) findViewById(R.id.et_content);
    }

    // 点击按钮跳转到发送短信模板页面
    public void insertsms(View v) {
        Intent intent = new Intent(this, SmsTemplateActivity.class);
        startActivityForResult(intent, 2);
    }
    // 点击按钮 跳转到contactActivity页面
    public void add(View v) {
        Intent intent = new Intent(this, ContactActivity.class);
//        startActivity(intent);
        /*   小细节 如果点击按钮 开启了另外一Activity
             并且当开启的这个Activity关闭的时候，
             我想要这个开启Activity的数据,
             用下面这个方法开启Activity
        */
        startActivityForResult(intent, 1);
    }

    //当我开启的Activity页面关闭的时候 这个方法就调用
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // 代表请求ContactActivity这个页面的数据
            String phone = data.getStringExtra("phone");
            et_number.setText(phone);
        } else if (requestCode == 2) {
            // 代表请求 短信模板页面的数据
            String smsContent = data.getStringExtra("smscontent");
            et_content.setText(smsContent);
        }
//        if (resultCode == 10) {
//            // 说明数据是从 ContactActivity返回的
//            String phone = data.getStringExtra("phone");
//            et_number.setText(phone);
//        } else if (resultCode == 20) {
//            // 说明数据是从 SmsTempalte返回的
//            String smsContent = data.getStringExtra("smscontent");
//            et_content.setText(smsContent);
//        }
    }
    // 点击按钮 发送短信
    public void send(View v) {
        //1 获取发送短信的号码 和发送短信的内容
        String number = et_number.getText().toString().trim();
        String content = et_content.getText().toString().trim();
        //2 获取到smsmanager的实例
        SmsManager smsManager = SmsManager.getDefault();
        // 短信内容可能会过长，所以需要分割？
        ArrayList<String> divideMessages = smsManager.divideMessage(content);
        for (String div : divideMessages) {
            /**
             * destinationAddress 发送给谁
             * scAddress 服务中心号码
             * text 要发送的内容
             */
            smsManager.sendTextMessage(number, null, div, null, null);
        }
    }
}
