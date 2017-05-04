package com.example.a90_carmea;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

import static android.support.v4.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//    // 点击按钮进行照相
//    public void click1(View v) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = new File(getCacheDir() + "haha.png");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)); // 保存图片的位置
//        // start the image capture Intent
//        startActivityForResult(intent, 1);
//    }

     // 点击按钮进行录像
    public void click2(View v) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File imagePath = new File(getFilesDir(), "images");
        File newFile = new File(imagePath, "default_image.jpg");
        Uri contentUri = getUriForFile(MainActivity.this, "com.example.a90_carmea.provider", newFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri); // 保存视频的位置
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // start the video capture Intent
        startActivityForResult(intent, 2);
    }

     // 当开启的Activity关闭的时候调用

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: hehe");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
