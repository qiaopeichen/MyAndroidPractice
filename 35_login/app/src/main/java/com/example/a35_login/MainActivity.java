package com.example.a35_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

    //点击按钮 进行Get方式提交数据
    public void click1(View v) {
        new Thread(){
            @Override
            public void run() {
                try {
                    //2 获取用户名和密码
                    String name = et_username.getText().toString().trim();
                    String pwd = et_password.getText().toString().trim();
                    //2.1 定义Get方式要提交的路径 小细节，如果提交中文，要对name和pwd进行一个Urlencode编码
                    String path = "http://192.168.11.73:8080/login/LoginServlet?username="+URLEncoder.encode(name, "utf-8")+"&password="+ URLEncoder.encode(pwd, "utf-8")+"";

                    //1 创建一个url对象 参数就是网址
                    URL url = new URL(path);
                    //2 获取HttpURLConnection 链接对象
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //3 设置参数 发送get请求
                    conn.setRequestMethod("GET");
                    //4 设置连接网络的超时时间
                    conn.setConnectTimeout(5000);
                    //5 获取服务器返回的状态码
                    int code = conn.getResponseCode(); // 200 代表获取服务器资源全部成功 206 获取请求部分资源
                    if (code == 200) {
                        //获取服务器返回的数据 以流的形式返回
                        InputStream in = conn.getInputStream();
                        //6.1 把inputstream转换成string
                        String conntent = StreamTools.readStream(in);
                        //7 把服务器返回的数据展示到toast上 不能在子线程展示toast
                        showToast(conntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //1 点击按钮 进行post方法提交数据
    public void click2(View v) {
        new Thread(){
            @Override
            public void run() {

                try {
                    //2 获取用户名和密码
                    String name = et_username.getText().toString().trim();
                    String pwd = et_password.getText().toString().trim();
                    //2.1 定义Get方式要提交的路径
                    String data =  "username="+URLEncoder.encode(name, "utf-8")+"&password="+URLEncoder.encode(pwd,"utf-8")+""; //请求体的内容

                    //1 ***和get方式提交数据 区别 路径不同
                    String path = "http://192.168.11.73:8080/login/LoginServlet";

                    //1 创建一个Url对象 参数就是网址
                    URL url = new URL(path);
                    //2 获取HttpURLConnection 连接对象
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //2 ***和get方式提交数据区别 设置请求方式是post
                    connection.setRequestMethod("POST"); // 默认请求是get 要大写
                    // 设置连接网络的超时时间
                    connection.setConnectTimeout(5000);
                    //3 ***和get方式提交数据区别 要多设置两个请求头信息
                    //设置头信息
                    connection.setRequestProperty("content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("content-Length",data.length()+"");
                    //4 *** 把我们组拼好的数据提交给服务器 以流的形式提交
                    connection.setDoOutput(true); //设置一个标记 允许输出
                    connection.getOutputStream().write(data.getBytes());

                    //5 获取服务器返回的状态码
                    int code = connection.getResponseCode(); //200 代表获取服务器资源全部成功 206 请求部分资源成功
                    if (code == 200) {
                        //6 获取服务器返回的数据 以流的形式返回
                        InputStream inputStream = connection.getInputStream();
                        //6.1 把inputstream 转换成 string
                        String content = StreamTools.readStream(inputStream);
                        //7 把服务器返回的数据展示到Toast上 不能在子线程展示Toast
                        showToast(content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //封装toast方法 该toast方法执行在主线程
    public void showToast(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 该方法一定是执行主线程
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
