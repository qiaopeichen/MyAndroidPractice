package com.example.a54_send_rice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //点击按钮 发送有序广播 发大米
    public void click(View v) {
        Intent intent = new Intent();
        intent.setAction("com.itheima.sendrice");
        /**
         * intent 意图
         * receiverPermission 接收的权限
         * resultReceiver 最终的receiver 定义一个类继承BroadReceiver，
         * 实例化类对象传进去，那么这个对象就一定会接受到该广播。
         * scheduler handler
         * initialCode 初始化
         * initialData 初始化数据
         */
        sendOrderedBroadcast(intent, null, new FinalReceiver(), null, 1, "习大大给每个村民发了1000斤大米", null);
    }
}
