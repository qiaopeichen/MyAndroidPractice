package com.example.a44_activity_life_cycle;

import android.app.Dialog;
import android.content.Intent;
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
        Log.d(TAG, "onCreate: ");
    }
    // 点击按钮跳转到testActivity
    public void click(View v) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
    // 当Activity销毁的时候调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
    // 当Activity恢复可见 大概基本不用这个方法，都一起写进onStart里了。
    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }
    // 当Activity可见
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }
    // 界面不可见
    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }
    // 界面上的按钮可以被点击 获取焦点
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }
    //界面上的按钮不可以被点击 失去焦点
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }
}
