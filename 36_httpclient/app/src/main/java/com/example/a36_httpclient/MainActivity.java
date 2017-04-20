package com.example.a36_httpclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }
    //点击按钮 进行get方式提交数据
    public void click1(View v) {
        new Thread(){public void run() {
            try {
                String name = et_username.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                //定义get方式要提交的路径  小细节 如果提交中文 要对name和pwd进行一个urlencode编码
                String path = "http://192.168.11.73:8080/login/LoginServlet?username="+URLEncoder.encode(name, "utf-8")+"&password="+ URLEncoder.encode(pwd, "utf-8")+"";
                //获取httpclient实例
                //httpclient的API被谷歌弃用了
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}.start();
    }
}
