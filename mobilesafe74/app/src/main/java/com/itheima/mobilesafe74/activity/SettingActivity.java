package com.itheima.mobilesafe74.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mobilesafe74.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
    }

    /**
     * 版本更新开关
     */
    private void initUpdate() {

    }
}
