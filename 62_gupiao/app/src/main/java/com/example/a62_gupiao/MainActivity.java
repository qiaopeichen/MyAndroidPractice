package com.example.a62_gupiao;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(1000);
                    Log.d("jojo", "run: 去服务器取数据");
                }
            }
        }.start();
    }
}
