package com.example.a32_common_handler_api;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Timer timer;
    private TimerTask task;
    private static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        //2000毫秒后 执行run方法  delay 延期
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tv.setText("哈哈");
//            }
//        }, 5000);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String ii = i+"";
                        tv.setText(ii);
                        i++;
                    }
                });
            }
        };
        //3秒后 每隔1秒执行一次run方法  period 周期
        timer.schedule(task, 3000, 1000);
    }
    //当Activity销毁的时候 会执行这个方法

    @Override
    protected void onDestroy() {
        timer.cancel();
        task.cancel();
        super.onDestroy();
    }
}
