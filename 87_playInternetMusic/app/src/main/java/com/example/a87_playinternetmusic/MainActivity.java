package com.example.a87_playinternetmusic;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // 点击按钮 播放一个音频文件
    public void click(View v) {
        //1 初始化mediaplayer
        final MediaPlayer mediaPlayer = new MediaPlayer();
        //2 设置到播放的资源位置 path 可以是网络 路径 也可以是本地路径

        try {
            mediaPlayer.setDataSource("http://192.168.43.144/1.mp3");
            //3 准备播放
            mediaPlayer.prepareAsync();
            //3.1 设置一个准备完成的监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 4 开始播放
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
