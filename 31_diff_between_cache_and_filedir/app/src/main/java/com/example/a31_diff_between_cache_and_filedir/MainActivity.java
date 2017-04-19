package com.example.a31_diff_between_cache_and_filedir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //点击按钮生成一个cache目录
    public void click1(View v) {
        try {
            File file = new File(getCacheDir(), "info.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("hehe".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //点击按钮生成一个files目录
    public void click2(View v) {
        try {
            FileOutputStream fos = openFileOutput("info.txt", 0);
            fos.write("haha".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
