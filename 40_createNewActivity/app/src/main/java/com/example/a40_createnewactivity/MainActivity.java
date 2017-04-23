package com.example.a40_createnewactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mylog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // 点击按钮 实现拨打电话的功能
    public void click1(View v) {
        //1 创建意图对象
        Intent intent = new Intent();
        //2 设置拨打的动作
        intent.setAction(intent.ACTION_DIAL);
        //3 设置拨打的数据
        intent.setData(Uri.parse("tel:"+119));
        //4 开启Activity 记得加上权限
        startActivity(intent);
    }

    // 点击按钮 跳转到TestActivity
    public void click2(View v) {
        //1 创建意图对象 意图就是我要完成一件事
        Intent intent = new Intent();
        //2 设置跳转的动作
        intent.setAction("com.example.testactivity");
        //3 设置category
        intent.addCategory("android.intent.category.DEFAULT");
        //4 设置数据
        intent.setData(Uri.parse("itheima:"));
        //5 设置数据类型
//        intent.setType("aa/bb");
        //6 注意小细节* 如果setdata方法和settype方法一起使用的时候，应该使用下面这个方法
//        intent.setDataAndType(Uri.parse("itheima1:"+110), "aa/bb1");
        // 开启Activity
        startActivity(intent);
    }
    //点击按钮跳转到 TestActivity
    public void click3(View v) {
        //1 创建意图对象 意图就是我要完成一件事
        Intent intent = new Intent(this, Test3Activity.class);
        //2 设置包名和类名 packageName：当前应用的包名
        intent.setClassName("com.example.a40_createnewactivity", "com.example.a40_createnewactivity.Test3Activity");
        //3 开启Activity
        startActivity(intent);
    }
}
