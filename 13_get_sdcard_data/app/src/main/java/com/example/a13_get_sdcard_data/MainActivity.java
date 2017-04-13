package com.example.a13_get_sdcard_data;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;

import android.widget.TextView;

import java.io.File;


import static java.util.Formatter.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找到我们关心的空间
        TextView tv_total_size = (TextView) findViewById(R.id.textview1);
        TextView tv_useable_size = (TextView) findViewById(R.id.textview2);

        //2 获取sd卡总大小和可用空间
        File file = Environment.getExternalStorageDirectory();
        long totalSpace = file.getTotalSpace(); // 总大小
        long usableSpace = file.getUsableSpace(); // 可用空间

        //3 转换数据格式
        String formatTotalSpace = Formatter.formatFileSize(this, totalSpace);
        String formatusableSpace = Formatter.formatFileSize(this, usableSpace);

        // 4 展示到textview上
        tv_total_size.setText("总大小:"+formatTotalSpace);
        tv_useable_size.setText("可用空间:"+formatusableSpace);
    }
}
