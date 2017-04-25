package com.example.a60_drawable_anim;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到控件 显示动画效果
        final ImageView rocketImage = (ImageView) findViewById(R.id.iv);
        //2 设置背景资源
        rocketImage.setBackgroundResource(R.drawable.anim);
        //2.1 兼容低版本写法
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(20);
                //3 获取AnimationDrawable 类型
                AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
                //4 开启动画
                rocketAnimation.start();
            }
        }.start();
    }
}
