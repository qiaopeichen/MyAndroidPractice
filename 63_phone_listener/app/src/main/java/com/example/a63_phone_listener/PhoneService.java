package com.example.a63_phone_listener;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class PhoneService extends Service {
    private static final String TAG = "jojo";
    private MediaRecorder recorder; // 录音机实例

    public PhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        //1 获取telephonemanager的实例
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //2 注册电话的监听
        tm.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: 服务");
        super.onDestroy();
    }

    //定义一个类用来监听电话的状态
    private class MyPhoneStateListener extends PhoneStateListener{
        //当电话设置状态发生改变的时候调用

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            //3 具体判断一下电话的状态
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: //空闲状态
                    if (recorder != null) {
                        Log.d(TAG, "onCallStateChanged: 停止录音");
                        recorder.stop(); // 停止录音
                        recorder.reset(); // Restarts the MediaRecorder to its idle state.
                        recorder.release();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: // 接听状态
                    Log.d("jojo", "onCallStateChanged:开始录音 ");
                    recorder.start();
                    break;
                case TelephonyManager.CALL_STATE_RINGING: // 电话响铃状态
                    Log.d("jojo", "onCallStateChanged:准备一个录音机 ");
                    //1 创建MediaRecorder实例
                    recorder = new MediaRecorder();
                    //2 设置音频的来源
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //3 设置输出的格式
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //4 设置音频的编码方式
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //5 设置存放的路径
                    Date date = new Date();
                    DateFormat format = DateFormat.getDateInstance();
                    String time = format.format(date);
//                    recorder.setOutputFile(Environment.getExternalStorageDirectory().getPath()+ time +".3gp");
                    recorder.setOutputFile("/mnt/sdcard/luyin.3gp");
                    //6 准备录
                    try {
                        recorder.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }
}
