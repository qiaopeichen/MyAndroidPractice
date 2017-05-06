package com.example.a102_notifationbar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 获取NotifycationManager的实例
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    // 点击按钮发送一条通知
    public void click1(View v) {
        // 链式调用
        //创建意图对象
        Intent intent = new Intent();
        //实现拨打电话的功能
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+119));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new Notification.Builder(this)
                    .setContentTitle("大标题")
                    .setContentText("标题的内容")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .build();
        nm.notify(10, notification);
    }
    // 点击按钮 取消发送一条通知
    public void click2(View v) {
        // 取消通知
        nm.cancel(10);
    }
}
