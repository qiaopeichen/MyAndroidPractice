package com.example.a89_videoview3;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        VideoView mVideoView = (VideoView) findViewById(R.id.mVideoView);

        Vitamio.isInitialized(getApplicationContext());
        MediaController mMediaController = new MediaController(this);
        String path = "http://192.168.1.116/frog.mp4";
        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(mMediaController);

        //这里可选
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
                mediaPlayer.start();
            }
        });
    }



}
