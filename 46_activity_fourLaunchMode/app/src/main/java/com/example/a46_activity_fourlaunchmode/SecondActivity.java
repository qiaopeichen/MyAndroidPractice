package com.example.a46_activity_fourlaunchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
    // 打开第一个页面
    public void open1(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    // 打开第二个页面
    public void open2(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
