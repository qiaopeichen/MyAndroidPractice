package com.example.a47_ip_dial;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_number = (EditText) findViewById(R.id.et_ipnumber);
    }

    // 点击按钮 保存用户输入的ip号码
    public void click(View v) {
        //1 获取用户输入的ip号码
        String ipnumber = et_number.getText().toString().trim();
        //2 把当前ipnumber存起来 存到sp里面
        SharedPreferences sp = getSharedPreferences("config", 0);
        //3 获取 sp 的编译器
        sp.edit().putString("ipnumber", ipnumber).commit();
        //4 保存成功
        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
    }
}
