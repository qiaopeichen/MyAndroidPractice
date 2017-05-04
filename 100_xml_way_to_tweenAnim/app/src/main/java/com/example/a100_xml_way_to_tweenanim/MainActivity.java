package com.example.a100_xml_way_to_tweenanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    public void click1(View v) {
        // 创建透明动画 1.0意味着完全不透明 0.0意味着完全透明
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        // 开始执行动画
        iv.startAnimation(animation);
    }

    // 点击按钮 实现旋转效果
    public void click2(View v) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        // 开始执行动画
        iv.startAnimation(animation);
    }

    // 点击按钮 实现缩放效果
    public void click3(View v) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        // 开始执行动画
        iv.startAnimation(animation);
    }

    // 点击按钮 实现平移效果
    public void click4(View v) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        // 开始执行动画
        iv.startAnimation(animation);
    }

    // 点击按钮 让动画一起执行
    public void click5(View v) {
        // 创建动画的合集
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.set);
        // 执行动画
        iv.startAnimation(animation);
    }
}
