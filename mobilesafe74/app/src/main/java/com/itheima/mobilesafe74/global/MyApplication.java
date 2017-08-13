package com.itheima.mobilesafe74.global;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Administrator on 2017/8/13.
 */
// 定义了一个自己的MyApplication，需要告知Android系统，运营自己定义的MyApplication
public class MyApplication extends Application{
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.



        super.onCreate();
    }
}
