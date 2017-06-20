package com.example.a104_youku_menu.util;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/6/20.
 */

public class AnimationUtils {
    // 正在运行的动画个数
    public static int runningAnimationCount = 0;
    // 旋转出去的动画
    public static void rotateOutAnim(RelativeLayout layout, long delay) {
        int childCount = layout.getChildCount();
        // 如果隐藏，则找到所有的子view，禁用
        for (int i = 0; i < childCount; i++) {
            layout.getChildAt(i).setEnabled(false);
        }

        RotateAnimation ra = new RotateAnimation(0f, -180f, // 开始，结束的角度，逆时针
                Animation.RELATIVE_TO_SELF, 0.5f, // 相对的x坐标点，指定旋转中心x值
                Animation.RELATIVE_TO_SELF, 1.0f); // 相对的y坐标点，指定旋转中心y值
        ra.setDuration(500);
        ra.setFillAfter(true); // 设置动画停留在结束位置
        ra.setStartOffset(delay); // 设置动画开始延时
        ra.setAnimationListener(new MyAnimationListener()); // 添加监听
        layout.startAnimation(ra);
    }

    // 旋转进来的动画
    public static void rotateInAnim(RelativeLayout layout, long delay) {
        int childCount = layout.getChildCount();
        //如果隐藏，则找到所有的子view，启用
        for (int i = 0; i < childCount; i++) {
            layout.getChildAt(i).setEnabled(true);
        }

        RotateAnimation ra = new RotateAnimation(
                -180f, 0f, // 开始，结束的角度，顺时针
                Animation.RELATIVE_TO_SELF, 0.5f, // 相对的x坐标点，指定旋转中心x值
                Animation.RELATIVE_TO_SELF, 1.0f); // 相对的y坐标点，指定旋转中心y值
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setStartOffset(delay); // 设置动画开始延时
        ra.setAnimationListener(new MyAnimationListener()); // 添加监听
        layout.startAnimation(ra);
    }
    private static class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            runningAnimationCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            runningAnimationCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
