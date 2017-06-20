package com.example.a97_menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import junit.framework.Test;

import static android.content.DialogInterface.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //不带菜单的TestActivity 和 MainActivity作比较
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //1 添加菜单的第一种方式
//        getMenuInflater().inflate(R.menu.main, menu);
        //2 通过代码的方式添加
        menu.add(0, 1, 0, "前进");
        menu.add(0, 2, 0, "后退");
        menu.add(0, 3, 0, "首页");
        return true;
    }
    // 当我们想知道具体点击的是哪个菜单条目，重写下面这个方法（固定流程）

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 具体点击的是哪个菜单条目
        switch (item.getItemId()) {
            case 1:
                Log.d(TAG, "onOptionsItemSelected: 前进");
                break;
            case 2:
                Log.d(TAG, "onOptionsItemSelected: 后退");
                break;
            case 3:
                Log.d(TAG, "onOptionsItemSelected: 首页");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 当菜单打开之前调用这个方法

    @Override
    public boolean onMenuOpened(int featureId,Menu menu) {
        Log.d(TAG, "onMenuOpened: ");
        //弹出一个对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // 最后一步 记得show出来，和Toast一样
        builder.show();

        return super.onPrepareOptionsMenu(menu);
    }
}
