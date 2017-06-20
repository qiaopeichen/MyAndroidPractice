package com.example.a64_use_service_register_broadcastrecever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启服务
        Intent intent = new Intent(this, ScreenService.class);
        startService(intent);
    }
}
