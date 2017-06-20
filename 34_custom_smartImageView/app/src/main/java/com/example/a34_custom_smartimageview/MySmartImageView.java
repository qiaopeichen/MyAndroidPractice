package com.example.a34_custom_smartimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/4/19.
 */

public class MySmartImageView extends android.support.v7.widget.AppCompatImageView{

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://代表请求成功
                    Bitmap bitmap = (Bitmap) msg.obj;
                    //显示数据
                    MySmartImageView.this.setImageBitmap(bitmap);
                    break;
                case 2://请求失败 显示一个默认图片
                    int resource = (int) msg.obj;
                    MySmartImageView.this.setBackgroundResource(resource);
                    break;
                case 3:
                    int resource1 = (int) msg.obj;
                    MySmartImageView.this.setBackgroundResource(resource1);
                    break;
                default:
                    break;
            }
        }
    };





    //一个参数的构造方法 在new 代码初始化的时候调用
    public MySmartImageView(Context context) {
        super(context);
    }

    //这个类 在布局文件中使用的时候 调用2个参数构造方法
    public MySmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 显示图片的方法 path 是传过来的url地址
    public void setImageUrl(final String path, final int resource) {
        new Thread() {
            public void run() {
                try {
                    //2.2 创建URL 对象指定我们要访问的网址（路径）
                    URL url = new URL(path);
                    //2.3 拿到httpurlconnection对象 用于发送或者接收数据
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.4 设置发送get请求
                    conn.setRequestMethod("GET");
                    //2.5 设置请求超时时间
                    conn.setConnectTimeout(5000);
                    //2.6 获取服务器返回的状态码
                    int code = conn.getResponseCode();
                    //2.7 如果code == 200 说明请求成功
                    if (code == 200) {
                        //2.8 获取服务器返回的数据 是以流的形式返回的，由于把流转换成字符串是一个非常常见的操作
                        //所以我抽出一个工具类utils
                        InputStream in = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        //创建message对象
                        Message msg = Message.obtain();
                        msg.what =1;
                        msg.obj = bitmap;
                        handler.sendMessage(msg); // 发送消息
                    } else {
                        //请求失败
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = resource;
                        handler.sendMessage(msg); // 发送消息
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = 3;
                    msg.obj = resource;
                    handler.sendMessage(msg); // 发送消息
                }
            }
        }.start();
    }
}
