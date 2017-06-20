package com.example.a81_load_big_img;

import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    private ImageView iv;
    private int height;
    private int width;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //用来显示小狗的图片
        iv = (ImageView) findViewById(R.id.iv);
        //1 获取手机的分辨率 windowmanager
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
        width = wm.getDefaultDisplay().getWidth();
        Log.d(TAG, "onCreate: width:" + width + "height:" + height);
    }

    // 点击按钮 加载一张大图片
    @SuppressLint("SdCardPath") // 告诉编译器忽略SdCardPath警告
    public void click(View v) {
        // 创建一个位图工厂的配置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 解码器不去真正的解析位图 但是还能够获取图片的宽和高信息 Bounds 边界  graphics图像
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/dog.jpg", options);
        //2 获取图片的宽和高信息
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        Log.d(TAG, "click: 图片的宽和高：" + imgWidth + "-----" + imgHeight);
        //3 计算缩放缩放比,由于是int类型，不管一点几倍都是整数1                 scale 比例
        int scale = 1;
        int scaleX = imgWidth/width;
        int scaleY = imgHeight/height;
        if (scaleX >= scaleY && scaleX > scale) {
            scale = scaleX;
        }
        if (scaleY > scaleX && scaleY > scale) {
            scale = scaleY;
        }
        Log.d(TAG, "click: 缩放比：" + scale);
        //4 安装缩放比进行显示 生物学 丁磊？
        options.inSampleSize = scale;
        //5 安装缩放比 进行解析位图
        options.inJustDecodeBounds =false;
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/dog.jpg", options);
        //6 把bitmap显示iv上
        iv.setImageBitmap(bitmap);
    }
}
