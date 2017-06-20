package com.example.a101_property_animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static android.R.attr.alpha;
import static android.R.attr.scaleX;
import static android.R.attr.scaleY;
import static android.R.attr.translateX;
import static android.R.attr.translationX;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载布局
        setContentView(R.layout.activity_main);
        // 作用：执行动画
        iv = (ImageView) findViewById(R.id.iv);
        //给iv设置一个监听事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "你点不到我", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 位移动画
    public void translate(View v) {
        // 创建属性动画
        /**
         * target 执行的目标，谁执行动画
         * propertyName 属性名字，The name of the property being animated.
         * float...values 可变参数 第一个参数为图标瞬移到哪个位置，但源位置不变，剩下的参数就是在源坐标的基础上平移。
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationX", 200, 200, 100, 200, 100);
        objectAnimator.setDuration(2000);
        objectAnimator.start(); // 开始动画
    }
    // 缩放动画
    public void scale(View v) {
        /**
         * target 执行的目标，谁知性动画
         * propertyName 属性名字，scaleX就是X的尺寸，横着改。如果是scaleY,就竖着操作。
         * float...values 可变参数 从0.1f的大小开始，X轴变2,1,2倍
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0.1f, 2, 1, 2);
        objectAnimator.setDuration(2000);
        objectAnimator.start(); // 开始动画
    }
    // 实现透明的效果
    public void alpha(View v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0, 0.5f, 0, 1, 0, 1);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }
    // 实现旋转的效果

    /**
     *
     * rotation 旋转，rotationY 沿着Y轴旋转
     */
    public void rotate(View v) {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0, 180, 90, 360);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "rotationY", 0, 180, 90, 360);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }
    // 一起飞
    public void fly(View v) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationX", 200, 200, 100, 200, 100);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(iv, "scaleX", 0.1f, 2, 1, 2);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(iv, "alpha", 0, 0.5f, 0, 1, 0, 1);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(iv, "rotationY", 0, 180, 90, 360);
        animatorSet.setDuration(2000);
        animatorSet.setTarget(iv);
        // 往集合中添加动画 挨个飞
//        animatorSet.playSequentially(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4);
        // 一起飞
        animatorSet.playTogether(objectAnimator, objectAnimator2 , objectAnimator3, objectAnimator4);
        animatorSet.start();
    }
    // 使用xml的方式创建属性动画
    public void playXml(View v) {
        ObjectAnimator oa = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator);
        // 设置执行目标
        oa.setTarget(iv);
        oa.start(); // 开始执行
    }
}
