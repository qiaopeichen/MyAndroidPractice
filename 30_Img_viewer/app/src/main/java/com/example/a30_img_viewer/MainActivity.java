package com.example.a30_img_viewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.cardemulation.HostNfcFService;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText et_path;
    private ImageView iv;

    //创建handle对象
//    private Handler handler = new Handler() {
//        //处理消息
//        public void handdleMessge(Message.msg) {
//            Bitmap bitmap = (Bitmap) msg.obj;
//            iv.setImageBitmap(bitmap);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        et_path = (EditText) findViewById(R.id.et_path);
        iv = (ImageView) findViewById(R.id.iv);

//        StrictMode.setThreadPolicy(new
//                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
//        StrictMode.setVmPolicy(
//                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    //2 点击按钮进行查看 指定路径的源码
    public void click(View v) {
        new Thread() {
            public void run() {

                try {
                    String path = et_path.getText().toString().trim();
                    File file = new File(getCacheDir(), Base64.encodeToString(path.getBytes(), Base64.DEFAULT));
                    if (file.exists() && file.length()>0) {
                        //使用缓存的图片
                        Log.d("mylog", "使用缓存图片");
                        final Bitmap cacheBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        // 把cacheBitmap 显示到iv上
    //                    Message msg = new Message().obtain();
    //                    msg.obj = cacheBitmap;
    //                    handler.sendMessage(msg); // 发消息
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageBitmap(cacheBitmap);
                            }
                        });
                    } else {
                        //第一次访问 联网获取数据
                        Log.d("mylog", "第一次访问连接网络");
                        //2.2 创建url对象
                        URL url = new URL(path);
                        //2.3 获取httpurlconnection
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        //2.4 设置请求的方式
                        conn.setRequestMethod("GET");
                        //2.5 设置超时时间
                        Log.d("mylog", "超时之前");
                        conn.setConnectTimeout(5000);
                        Log.d("mylog", "超时之后");
                        //2.6 获取服务器返回的状态码
                        int code = conn.getResponseCode();
                        Log.d("mylog", "得到状态码");
                        if (code == 200) {
                            Log.d("mylog", "code=200");
                            //2.7 获取图片的数据 不管是什么数据 txt、image 都是以流的形式返回
                            InputStream in = conn.getInputStream();
                            //2.7 缓存图片 谷歌给我们提供了一个缓存目录
                            FileOutputStream fos = new FileOutputStream(file);
                            int len = -1;
                            byte[] buffer = new byte[1024]; // 1kb
                            while ((len = in.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.close();
                            in.close();

                            //2.8 通过位图工厂 获取bitmap (bitmap)
                            final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            //这句api 不管你在什么位置上调用 action都运行在ui线程里
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // run方法一定执行在ui线程里
                                    //2.9 把bitmap显示到tv上
                                    iv.setImageBitmap(bitmap);
                                }
                            });

//                            Message msg = Message.obtain(); //使用msg的静态方法 可以减少对象的创建
//                            msg.obj = bitmap;
//                            handler.setMessage(msg); // 发消息
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
