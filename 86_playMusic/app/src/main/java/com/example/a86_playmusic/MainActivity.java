package com.example.a86_playmusic;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //点击按钮 播放一个音频文件
    public void click(View v) {
        //1 初始化Mediaplayer
        MediaPlayer mediaPlayer = new MediaPlayer();
        //2 设置要播放的资源位置 path 可以是网络，路径，也可以是本地路径
        try {
            Log.d(TAG, "click: "+ Environment.getExternalStorageDirectory().getPath() + "/1.mp3");
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/1.mp3");

            //3 准备播放
            mediaPlayer.prepare();
            //4 开始播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
