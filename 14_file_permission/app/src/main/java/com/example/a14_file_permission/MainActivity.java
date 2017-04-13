package com.example.a14_file_permission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //点击按钮生成私有文件
    public void click1(View v)  {
        try {
            FileOutputStream fos = openFileOutput("private.txt", MODE_PRIVATE);
            fos.write("haha".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //点击按钮生成APPEND可添加文件
    public void click2(View v) {
        try {
            FileOutputStream fos = openFileOutput("append.txt", MODE_APPEND);
            fos.write("hehe".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //点击按钮生成全球可读文件
    public void click3(View v) {
        try {
            FileOutputStream fos = openFileOutput("read.txt", MODE_WORLD_READABLE);
            fos.write("read".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //点击按钮生成全球可写文件
    public void click4(View v) {
        try {
            FileOutputStream fos = openFileOutput("write.txt", MODE_WORLD_WRITEABLE);
            fos.write("write".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
