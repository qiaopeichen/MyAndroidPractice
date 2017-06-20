package com.example.a79_regist_contentobserver;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 注册内容观察者
        Uri uri = Uri.parse("content://com.itheima.provider");
        getContentResolver().registerContentObserver(uri, true, new MyContentObserver(new Handler()));
    }

    //定义一个内容观察者
    private class MyContentObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        // 当内容发生改变的时候调用
        @Override
        public void onChange(boolean selfChange) {
            Log.d(TAG, "onChange: 数据库的内容发生了改变");
            super.onChange(selfChange);
        }
    }
}
