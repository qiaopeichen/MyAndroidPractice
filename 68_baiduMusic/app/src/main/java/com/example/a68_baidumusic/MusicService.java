package com.example.a68_baidumusic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    private static final String TAG = "jojo";

    public MusicService() {
    }
    // 把我们定义的中间人对象返回
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();
    }
    // 播放音乐的方法
    public void playMusic() {
        // TODO: 2017/4/27 等撸完多媒体 再完善这个案例
        Log.d(TAG, "playMusic: ");
    }
    // 暂停音乐的方法
    public void pauseMusic() {
        Log.d(TAG, "pauseMusic: ");
    }
    //继续播放音乐的方法
    public void rePlayMusic() {
        Log.d(TAG, "rePlayMusic: ");
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
    }
}
