package com.example.a99_tween_anim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //这个控件用来执行动画
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "你点不到我", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 点击按钮 实现透明效果
    public void click1(View v){
        // 创建透明动画 1.0意味着完全不透明 0.0意味着完全透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000); // 设置动画执行的时间
        alphaAnimation.setRepeatCount(1); // 设置动画重复的次数
        alphaAnimation.setRepeatMode(Animation.REVERSE); // 设置重复的模式
        // 开始执行动画
        iv.startAnimation(alphaAnimation);
    }

    // 点击按钮 实现旋转效果
    public void click2(View v) {
        //fromDegrees 开始角度 toDegrees 结束角度
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000); // 设置动画执行的时间
        rotateAnimation.setRepeatCount(1); // 设置动画重复的次数
        rotateAnimation.setRepeatMode(Animation.REVERSE); // 设置重复的模式
        // 开始执行动画
        iv.startAnimation(rotateAnimation);
    }

    // 点击按钮 实现缩放效果
    public void click3(View v) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000); // 设置动画执行的时间
        scaleAnimation.setRepeatCount(1); // 设置动画重复的次数
        scaleAnimation.setRepeatMode(Animation.REVERSE); // 设置重复的模式
        // 开始执行动画
        iv.startAnimation(scaleAnimation);
    }

    // 点击按钮 实现平移效果 但按钮真实的位置还在原位没动
    public void click4(View v) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.2f);
        translateAnimation.setDuration(2000); // 设置动画执行的时间
        translateAnimation.setFillAfter(true); // 当动画结束后，停留在结束的位置上
        // 开始执行动画
        iv.startAnimation(translateAnimation);
    }

    // 点击按钮 让动画一起执行
    public void click5(View v) {
        // 从创建动画的合集
        AnimationSet set = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000); // 设置动画执行的时间
        alphaAnimation.setRepeatCount(1); // 设置动画重复的次数
        alphaAnimation.setRepeatMode(Animation.REVERSE); // 设置重复的模式

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000); // 设置动画执行的时间
        rotateAnimation.setRepeatCount(1); // 设置动画重复的次数
        rotateAnimation.setRepeatMode(Animation.REVERSE); // 设置重复的模式

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000); // 设置动画执行的时间
        scaleAnimation.setRepeatCount(1); // 设置动画重复的次数
        scaleAnimation.setRepeatMode(Animation.REVERSE); // 设置重复的模式


        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.2f);
        translateAnimation.setDuration(2000); // 设置动画执行的时间
        translateAnimation.setFillAfter(true); // 当动画结束后，停留在结束的位置上

        // 添加动画
        set.addAnimation(alphaAnimation);
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(translateAnimation);

        // 执行动画
        iv.startAnimation(set);
    }
}

