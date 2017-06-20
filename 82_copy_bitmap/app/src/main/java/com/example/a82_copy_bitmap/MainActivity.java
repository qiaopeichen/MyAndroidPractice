package com.example.a82_copy_bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 显示原图
        ImageView iv_src = (ImageView) findViewById(R.id.iv_src);

        //显示副本
        ImageView iv_copy = (ImageView) findViewById(R.id.iv_copy);

        //1 先把tomcat.png 图片转换成bitmap 显示到iv_src
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tomcat);

        //1.1 操作图片
//        srcBitmap.setPixel(20, 30, Color.BLACK);
        iv_src.setImageBitmap(srcBitmap);

        //2 创建原图的副本
        //2.1 创建一个模板，相当于创建了一个大小和原图一样的空白的白纸
        Bitmap copyBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        //2.2 想作画需要一个画笔
        Paint paint = new Paint();
        //2.3 创建一个画布，把白纸铺到画布上
        Canvas canvas = new Canvas(copyBitmap);
        //2.4 开始作画
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);

        //2.5 操作画出来的小猫图片
        for (int i = 0; i < 20; i++) {
            copyBitmap.setPixel(20 + i, 30, Color.RED);
        }

        //3 把copyBitmap显示到iv_copy上
        iv_copy.setImageBitmap(copyBitmap);

    }
}
