package com.example.a85_syf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 用来显示操作后的图片
        final ImageView iv = (ImageView) findViewById(R.id.iv);

        //1 获取要操作图片的原图
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pre19);

        //2 创建一个副本 相当于有一个和原图大小的白纸
        final Bitmap alterBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        // 创建画笔
        Paint paint = new Paint();
        // 创建画布 把白纸铺到画布上
        Canvas canvas = new Canvas(alterBitmap);
        // 开始作画
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);

        iv.setImageBitmap(alterBitmap);

        //3 给vi设置一个触摸事件
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //4 具体判断一下触摸事件
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: // 移动事件
                        for (int i = -20; i < 20; i++) { // 增加x轴坐标
                            for (int j = -20; j < 20; j++) { // 增加y轴坐标
                                //为了良好的用户体验 撕一个圆
                                if (Math.sqrt(i*i + j*j) < 20 &&
                                        0 <= event.getY()+j && 0 <= event.getX()+i &&
                                        event.getX()+i < alterBitmap.getWidth() &&
                                        event.getY()+j < alterBitmap.getHeight()) {
                                    alterBitmap.setPixel((int)event.getX()+i, (int)event.getY()+j, Color.TRANSPARENT);
                                }
                            }
                        }
                        //更新一下iv
                        iv.setImageBitmap(alterBitmap);
                        break;
                }
                return true;
            }
        });
    }
}
