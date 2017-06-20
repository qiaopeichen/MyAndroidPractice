package com.example.a84_paint_board;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    private ImageView iv;
    private Bitmap srcBitmap;
    private Bitmap copyBitmap;
    private Paint paint;
    private Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 用来显示我们画的内容

        iv = (ImageView) findViewById(R.id.iv);
        //1 获取原图 bg
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        //2 获取原图的副本 相当于是一个空白的白纸
        copyBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        // 创建画笔
        paint = new Paint();
        // 创建一个画布
        canvas = new Canvas(copyBitmap);
        // 开始作画
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);
//        canvas.drawLine(20, 30, 50, 80, paint);
        iv.setImageBitmap(copyBitmap);
        //3 给iv设置一个触摸事件
        iv.setOnTouchListener(new View.OnTouchListener() {
            int startX = 0;
            int startY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取当前事件类型
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: // 按下
                        Log.d(TAG, "onTouch: 摸View");
                        // 获取开始位置（划线）
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE: // 移动
                        Log.d(TAG, "onTouch: 移动");
                        // 获取结束位置
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        // 不停的划线
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        // 再次显示到iv上
                        iv.setImageBitmap(copyBitmap);
                        // 更新一下开始坐标和结束坐标
                        startX = stopX;
                        startY = stopY;
                        break;
                    case MotionEvent.ACTION_UP: // 抬起
                        Log.d(TAG, "onTouch: 抬起");
                        break;
                }
                // True if the listener has consumed the event, false otherwise
                return true;
            }
        });
    }
    // 点击按钮 改变画笔的颜色
    public void click1(View v) {
        paint.setColor(Color.RED);
    }
    // 点击按钮 对画笔加粗
    public void click2(View v) {
        paint.setStrokeWidth(15);
    }
    // 点击按钮 保存图片
    public void click3(View v) {
        /**
         * format 保存图片的格式
         * quality 保存图片的质量
         * SystemClock.uptimeMillis() 这个是当前手机的开机时间
         */
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath(), SystemClock.uptimeMillis() + ".png");
            FileOutputStream fos = new FileOutputStream(file);
            copyBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            // 发送一条广播 欺骗系统图库的应用
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file); //out is your output file
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
