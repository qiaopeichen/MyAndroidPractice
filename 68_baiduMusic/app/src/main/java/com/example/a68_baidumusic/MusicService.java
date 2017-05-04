package com.example.a68_baidumusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    private static final String TAG = "jojo";
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        //1 初始化mediaPlayer
        mediaPlayer = new MediaPlayer();
        super.onCreate();
    }

    // 把我们定义的中间人对象返回
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    // 播放音乐的方法
    public void playMusic() {
        // TODO: 2017/4/27 等撸完多媒体 再完善这个案例
        //2 设置要播放的资源位置 path 可以是网络 路径 也可以是本地路径
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/1.mp3");
            //3 准备播放
            mediaPlayer.prepare();
            //4 开始播放
            mediaPlayer.start();
            //5 更新进度条
            updateSeekBar();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //更新进度条的方法
    private void updateSeekBar() {
        //1 获取当前播放的总长度
        final int duration = mediaPlayer.getDuration();
        //2 使用Timer 定时器去定时截取当前进度
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //3 一秒钟获取一次当前进度
                int currentPosition = mediaPlayer.getCurrentPosition();
                //4 拿着我们在MainActivity创建的handler发消息 消息就可以携带数据
                Message msg = Message.obtain();
                Bundle bundle = new Bundle(); // map
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                msg.setData(bundle);
                // 发送一条消息 mainActivity里面的handleMessage方法就会执行
                MainActivity.handler.sendMessage(msg);
            }
        };
        // 100 毫秒后 每隔1秒执行一次run方法
        timer.schedule(task, 100, 1000);
        // 当歌曲执行完毕后 把timer和timertask取消
        // 设置播放完成的监听
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion:  歌曲播放完成了");
                // 把 timer 和 timertask 取消
                timer.cancel();
                task.cancel();
            }
        });
    }
    // 暂停音乐的方法
    public void pauseMusic() {
        mediaPlayer.pause();
    }
    //继续播放音乐的方法
    public void rePlayMusic() {
        mediaPlayer.start();
    }
    //实现指定播放的位置
    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    //1 在服务的内部定义一个中间人对象(IBinder)
    private class MyBinder extends Binder implements Iservice{
        //调用播放音乐的方法
        @Override
        public void callPlayMusic() {
            playMusic();
        }
        //调用暂停音乐的方法
        @Override
        public void callPauseMusic() {
            pauseMusic();
        }
        //调用继续播放音乐的方法
        @Override
        public void callRePlayMusic() {
            rePlayMusic();
        }
        @Override
        public void callSeekTo(int position) {
            seekTo(position);
        }
    }
}
