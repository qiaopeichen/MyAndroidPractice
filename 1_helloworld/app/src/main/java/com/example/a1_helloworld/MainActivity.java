package com.example.a1_helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /**
     *当activity启动就会执行onCreate方法
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 设置activity的内容 内容来源于布局
         */
        setContentView(R.layout.activity_main);

    }
}
