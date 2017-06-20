package com.example.a83_copy_bitmap1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

        // 显示副本
        ImageView iv_copy = (ImageView) findViewById(R.id.iv_copy);

        //1 先把tomcat.png 图片转换成bitmap 显示到iv_src
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tomcat);

        //1.1 操作图片
        iv_src.setImageBitmap(srcBitmap);

        //2 创建原图的副本
        //2.1 创建一个模板，相当于创建了一个大小和原图一样的空白的白纸
        Bitmap copyBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        //2.2 想作画需要一个画笔
        Paint paint = new Paint();
        //2.3 创建一个画布，把白纸铺到画布上
        Canvas canvas = new Canvas(copyBitmap);
        //2.4 开始作画
        Matrix matrix = new Matrix();
        //2.5 对图片进行旋转
//        matrix.setRotate(20, srcBitmap.getWidth()/2, srcBitmap.getHeight()/2);
        //2.5 对图片进行缩放
//        matrix.setScale(0.5f, 0.5f);
        //2.6 对图片进行平移
//        matrix.setTranslate(30, 0);
        //2.7 以下两个方法一起用为镜面效果。
//        matrix.setScale(-1.0f, 1);
//        matrix.postTranslate(srcBitmap.getWidth(), 0);
        //post是在上一次修改的基础上进行再次修改，set每次操作都是最新的，会覆盖上次的操作
        //右移50，下移50。android坐标：右下为正。
//        matrix.postTranslate(50, 50);
        //2.7 倒影效果
        matrix.setScale(1.0f, -1);
        matrix.postTranslate(0, srcBitmap.getHeight());

        //一顿组装
        canvas.drawBitmap(srcBitmap, matrix, paint);
        //3 把copyBitmap显示到iv_copy上
        iv_copy.setImageBitmap(copyBitmap);
    }
}
