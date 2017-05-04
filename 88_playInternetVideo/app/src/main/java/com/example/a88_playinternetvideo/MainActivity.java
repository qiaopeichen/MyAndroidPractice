package com.example.a88_playinternetvideo;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    private MediaPlayer mediaPlayer;
    private int currentPosition; // 当前视频播放的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //0 找到空间用来显示播放视频的内容
        final SurfaceView sfv = (SurfaceView) findViewById(R.id.sfv);
        //1 获取holder对象，用来维护视频播放的内容
        SurfaceHolder holder = sfv.getHolder();
        //0.1 添加holder 生命周期 方法
        holder.addCallback(new SurfaceHolder.Callback() {
            // 这个方法执行了，说明surfaceView准备好了
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //1 初始化mediaPlayer
                Log.d(TAG, "surfaceCreated: ");
                mediaPlayer = new MediaPlayer();
                //2 设置要播放的资源位置 path可以是网络 路径 也可以是本地路径
                try {
                    mediaPlayer.setDataSource("http://192.168.43.144/frog.mp4");
                    //3 准备播放
                    mediaPlayer.prepareAsync();
                    //3.0 设置显示给sfv surfaceholder 是用来维护视频播放的内容
                    mediaPlayer.setDisplay(holder);
                    //3.1 设置一个准备完成的监听
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //4 开始播放
                            mediaPlayer.start();
                            //5 继续上次的位置继续播放
                            mediaPlayer.seekTo(currentPosition);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }
            // 当surfaceView 销毁
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed: ");
                // 停止播放视频
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    // 获取到当前播放视频的位置
                    currentPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                }
            }
        });
    }
}
