package com.example.a9_logcat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: debug");
        Log.e(TAG, "onCreate: error");
        Log.i(TAG, "onCreate: information");
        Log.w(TAG, "onCreate: warning");
    }
}
