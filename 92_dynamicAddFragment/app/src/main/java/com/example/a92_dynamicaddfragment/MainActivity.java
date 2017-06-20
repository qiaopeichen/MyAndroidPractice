package com.example.a92_dynamicaddfragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        int heigth = wm.getDefaultDisplay().getHeight();
        //2 判断横竖屏
        //3 获取Fragment的管理者 通过上下文直接获取
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction(); // 开启事务
        if (heigth > width) {
            // 说明是竖屏 加载第一个Fragment android.R.id.content代表当前手机的窗体

            beginTransaction.replace(android.R.id.content, new Fragment1());
        } else {
            // 说明是横屏 加载第二个Fragment
            beginTransaction.replace(android.R.id.content, new Fragment2());
        }
        //4 最后一步 记得commit
        beginTransaction.commit();
    }
}
