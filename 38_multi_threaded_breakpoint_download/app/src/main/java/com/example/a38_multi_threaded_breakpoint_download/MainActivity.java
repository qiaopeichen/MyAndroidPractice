package com.example.a38_multi_threaded_breakpoint_download;

import android.app.DownloadManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_path;
    private EditText et_threadCount;
    private LinearLayout ll_pb_layout;
    private List<ProgressBar> pbLists; // 用来存进度条的引用
    private String path;
    private int threadCount;
    private static int runningThread; // 代表当前正在运行的线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        et_path = (EditText) findViewById(R.id.et_path);
        et_threadCount = (EditText) findViewById(R.id.et_threadCount);
        ll_pb_layout = (LinearLayout) findViewById(R.id.ll_pb);

        //2 添加一个集合 用来存进度条的引用
        pbLists = new ArrayList<>();
    }

    //点击按钮实现下载的逻辑
    public void click(View v) {
        if("".equals(et_path.getText().toString().trim())|| "".equals(et_threadCount.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "下载地址或线程数不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //2 获取下载的途径
        path = et_path.getText().toString().trim();
        //3 获取线程的数量
        String threadCountt = et_threadCount.getText().toString().trim();
        //3.0 先移除进度条 再添加
        ll_pb_layout.removeAllViews();

        threadCount = Integer.parseInt(threadCountt);
        pbLists.clear();
        for (int i = 0; i < threadCount; i++) {
            //3.1 把我定义的Item布局转换成一个view对象
            ProgressBar pbView = (ProgressBar) View.inflate(getApplicationContext(), R.layout.item, null);
            //3.2 把pbView添加到集合中
            pbLists.add(pbView);
            //4 动态的添加进度条
            ll_pb_layout.addView(pbView);
        }
        //5 开始移植 联网 获取文件长度
        new Thread(){public void run() {
            //1*** 获取服务器文件的大小 要计算每个线程下载的开始位置和结束位置
            try {
                //1 创建一个url对象 参数就是网址
                URL url = new URL(path);
                //2 获取HttpURLConnection 连接对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //3 设置参数 发送get请求
                connection.setRequestMethod("GET"); // 默认请求就是get 要大写
                //4 设置连接网络的超时时间
                connection.setConnectTimeout(30000);
                //5 获取服务器返回的状态码
                int code = connection.getResponseCode(); // 200 代表获取服务器资源全部成功，206请求部分资源成功
                if (code == 200) {
                    //6 获取服务器文件的大小
                    int length = connection.getContentLength();
                    //6.1 把线程的数量赋值给正在运行的线程
                    runningThread = threadCount;

                    Log.d("mylog", "length:" + length);

                    //2*** 创建一个大小和服务器返回的一模一样的文件 目的是提前把空间申请出来
                    RandomAccessFile randomAccessFile = new RandomAccessFile(getFilename(path), "rw");
                    randomAccessFile.setLength(length);

                    //7 算出每个线程的大小
                    int blockSize = length/threadCount;

                    //3*** 计算每个线程下载的开始位置和结束位置
                    for (int i = 0; i <threadCount; i++) {
                        int startIndex = i * blockSize; // 每个线程下载的开始位置i
                        int endIndex = (i + 1) * blockSize -1;
                        // 特殊情况 就是最后一个线程
                        if (i == threadCount - 1) {
                            // 说明是最后一个线程
                            endIndex = length - 1;
                        }
                        Log.d("mylog", "线程id: " + i + "理论下载的位置：" + startIndex + "-----" + endIndex);
                        //4*** 开启线程去服务器下载文件
                        DownloadThread downLoadThread = new DownloadThread(startIndex, endIndex, i);
                        downLoadThread.start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}.start();
    }

    // 定义线程去服务器下载文件
    private class DownloadThread extends Thread {
        //通过构造方法把每个线程下载的开始位置和结束位置传递进来
        private int startIndex;
        private int endIndex;
        private int threadId;
        private int pbMaxSize; // 代表当前线程下载的最大值
        private int pblastPosition; // 如果中断过 获取上次下载的位置

        private DownloadThread(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            //4*** 实现去服务器下载文件的逻辑

            try {
                //0 计算当前进度条的最大值
                pbMaxSize = endIndex - startIndex;
                //1 创建一个Url对象 参数就是网址
                URL url = new URL(path);
                //2 获取HttpURLConnection 连接对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //3 设置参数 发送get请求
                connection.setRequestMethod("GET"); //默认请求就是get 要大写
                //4 设置连接网络超时的时间
                connection.setConnectTimeout(5000);
                //4.0 如果中间断过 继续上次的位置继续下载 从文件中读取上次下载的位置
                File file = new File(getFilename(path)+threadId+".txt");
                if (file.exists() && file.length()>0) {
                    FileInputStream fis = new FileInputStream(file);
                    BufferedReader bufr = new BufferedReader(new InputStreamReader(fis));
                    String lastPositionn = bufr.readLine(); // 读出来的内容就是上一次下载的位置
                    int lastPosition = Integer.parseInt(lastPositionn);
                    //4.0 给我们定义的进度条位置赋值
                    pblastPosition = lastPosition - startIndex;
                    //4.0.1 要改变一下startIndex的位置
                    startIndex = lastPosition + 1;

                    Log.d("mylog", "线程id: "+ threadId + "真实下载的位置: " + startIndex + "-----" + endIndex);
                    fis.close(); // 关闭流
                }
                //4.1 设置一个请求头Range （作用告诉服务器每个线程下载的开始位置和结束位置）
                connection.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
                //5 获取服务器返回的状态码
                int code = connection.getResponseCode(); // 200 代表获取服务器资源全部成功， 206 请求部分资源成功
                if (code == 206) {
                    //6 创建随机读写文件对象
                    RandomAccessFile randomAccessFile = new RandomAccessFile(getFilename(path), "rw");
                    //6 每个线程要从自己的位置开始写
                    randomAccessFile.seek(startIndex);
                    InputStream inputStream = connection.getInputStream(); // 存的是npp.exe
                    //7 把数据写到文件中
                    int len = -1;
                    byte[] buffer = new byte[1024*1024]; // 1MB
                    int total = 0; // 代表当前线程下载的大小
                    while ((len = inputStream.read(buffer)) != -1) {
                        randomAccessFile.write(buffer, 0, len);
                        total += len;
                        //8 实现断点续传 就是把当前线程下载的位置给存起来 下次再下载的时候 就按照上次下载的位置继续下载
                        int currentThreadPosition = startIndex + total; // 比如就存到一个普通的.txt文本中
                        //9 用来存当前线程下载的位置
                        RandomAccessFile raff = new RandomAccessFile(getFilename(path)+threadId+".txt", "rwd");
                        raff.write(String.valueOf(currentThreadPosition).getBytes());
                        raff.close();
                        //10 设置一下当前进度条的最大值和当前进度
                        pbLists.get(threadId).setMax(pbMaxSize); // 设置进度条的最大值
                        pbLists.get(threadId).setProgress(pblastPosition+total); // 设置当前进度条的当前进度
                    }
                    randomAccessFile.close(); // 关闭流 释放资源
                    Log.d("mylog", "线程id" + threadId + "  下载完毕");
                    //10 把.txt文件删除 每个线程具体什么时候下载完毕了 我们不知道
                    synchronized (DownloadThread.class) {
                        runningThread--;
                        if (runningThread == 0) {
                            // 说明所有的线程都执行完毕了 把.txt文件删除
                            for (int i = 0; i < threadCount; i++) {
                                File deleteFile = new File(getFilename(path) + i + ".txt");
                                deleteFile.delete();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取文件的名字 "http://192.168.1.116/npp.exe"
    public String getFilename(String path) {
        int start = path.lastIndexOf("/") + 1;
        String subString = path.substring(start); //取字串，位置顾前不顾后
        String fileName = Environment.getExternalStorageDirectory().getPath()+"/"+subString;
        return fileName;
    }
}
































