package com.example.a94_fragmentcompatiblelowversion;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 获取手机的分辨率
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        //2 判断横竖屏
        //3 获取Fragment的管理者 通过上下文直接获取
//        FragmentManager fragmentManager = getFragmentManager();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction(); // 开启事务
        if (height > width) {
            // 说明是竖屏 加载第一个Fragment android.R.id.content // 代表当前手机的窗体
            beginTransaction.replace(android.R.id.content, new Fragment1());
        } else {
            // 说明是横屏 加载第二个Fragment
            beginTransaction.replace(android.R.id.content, new Fragment2());
        }
        //4 最后一步 记得commit
        beginTransaction.commit();
    }
}
