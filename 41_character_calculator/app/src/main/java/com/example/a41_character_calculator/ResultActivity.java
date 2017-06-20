package com.example.a41_character_calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载布局
        setContentView(R.layout.activity_result);

        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_sex = (TextView) findViewById(R.id.tv_sex);
        TextView tv_result = (TextView) findViewById(R.id.tv_result);
        //2 获取MainActivity 传递过来的数据
        Intent intent = getIntent(); //获取开启此Activity的意图对象
        //3 获取name 和 sex 的值 小技巧：传递的是什么数据类型，这边就按照传递的数据类型取
        String name = intent.getStringExtra("name");
        int sex = intent.getIntExtra("sex", 0);
        //4 根据name和sex 显示数据
        tv_name.setText(name);
        byte[] bytes = null;
        //5 显示性别
        try {
            switch (sex) {
                case 1:
                    tv_sex.setText("男");
                    bytes = name.getBytes("gbk");
                    break;
                case 2:
                    tv_sex.setText("女");
                    bytes = name.getBytes("utf-8");
                    break;
                case 3:
                    tv_sex.setText("人妖");
                    bytes = name.getBytes("iso-8859-1");
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //6 计算人品结果 市面上大多数应用采用的是随机数
        int total = 0;
        for (byte b : bytes) {
            int number = b&0xff; // 1111 1111 0x是十六进制的标志 ff是 255
            total+=number;
            Log.d("mylog", "onCreate: total= " + total);
        }
        //获取得分
        int score = Math.abs(total)%100;
        if (score > 90) {
            tv_result.setText("您的人品非常好");
        } else if (score > 80) {
            tv_result.setText("您的人品还可以");
        } else if (score > 60) {
            tv_result.setText("您的人品刚及格");
        } else {
            tv_result.setText("您的人品太次了");
        }
    }
}
