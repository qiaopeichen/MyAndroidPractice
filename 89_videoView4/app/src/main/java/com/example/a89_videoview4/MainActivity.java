package com.example.a89_videoview4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView mVideoView = (VideoView) findViewById(R.id.mVideoView);


        Vitamio.isInitialized(getApplicationContext());
        MediaController mMediaController = new io.vov.vitamio.widget.MediaController(this);//实例化控制器


        String path = "http://192.168.1.116/frog.mp4";
        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(mMediaController);

        //这里可选
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });

    }
}
