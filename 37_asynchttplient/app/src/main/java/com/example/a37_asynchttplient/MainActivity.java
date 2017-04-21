package com.example.a37_asynchttplient;


import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }
    //点击按钮 进行Get方式提交数据
    public void click1(View v) {
        String name = et_username.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();
        //2.1 定义get方式要提交的路径 小细节 如果提交中文要对name和pwd进行一个urlencode编码
        try {
            path = "http://192.168.11.73:8080/login/LoginServlet?username="+ URLEncoder.encode(name, "utf-8")+"&password="+ URLEncoder.encode(pwd, "utf-8")+"";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //3 使用开源项目进行get请求
        //3.1 创建asynchttpclient
        AsyncHttpClient client = new AsyncHttpClient();
        //3.2 进行get请求
        client.get(path, new AsyncHttpResponseHandler() {
            //请求成功的回调方法
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Toast.makeText(getApplicationContext(), new String(responseBody, "gbk"), Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //请求失败
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "服务器忙", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //1 点击按钮 进行Post方式提交数据
    public void click2(View v) {
        //2 获取用户名和密码
        String name = et_username.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();
        String path = "http://192.168.11.73:8080/login/LoginServlet";
        //3.1 创建asynchttpclient
        AsyncHttpClient client = new AsyncHttpClient();
        //3.1.0 准备请求体的内容
        RequestParams params = new RequestParams();
        params.put("username", name);
        params.put("password", pwd);
        //3.2 进行post请求 params请求的参数封装
        client.post(path, params, new AsyncHttpResponseHandler() {
            // 请求成功 登录成功
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Toast.makeText(getApplicationContext(), new String(responseBody,"gbk"), Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "服务器忙", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
