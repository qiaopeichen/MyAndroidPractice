package com.itheima.mobilesafe74.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.itheima.mobilesafe74.activity.EnterPsdActivity;
import com.itheima.mobilesafe74.db.dao.AppLockDao;
import com.itheima.mobilesafe74.engine.ProcessManagerEngine;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class WatchDogService extends Service {

    private AppLockDao mDao;
    private boolean isWatch;
    private String mSkipPackagename;
    private List<String> mPacknameList;
    private InnerReceiver mInnerReceiver;
    private MyContentObserver mContentObserver;
    private static final String TAG = "jojo";
    @Override
    public void onCreate() {
        // 维护一个看门狗的死循环，让其时刻检测现在开启的应用，是否为程序锁中要去拦截的应用
        mDao = AppLockDao.getInstance(this);
        isWatch = true;
        watch();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SKIP");

        mInnerReceiver = new InnerReceiver();
        registerReceiver(mInnerReceiver, intentFilter);

        // 注册一个内容观察者，观察数据库的变化，一旦数据有删除或者添加，则需要让mPacknameList重新获取一次数据
        mContentObserver = new MyContentObserver(new Handler());
        getContentResolver().registerContentObserver(Uri.parse("content://applock/change"), true, mContentObserver);
        Log.d(TAG, "onCreate: 看门狗已开启");
        super.onCreate();
    }

    class MyContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            new Thread() {
                @Override
                public void run() {
                    mPacknameList = mDao.findAll();
                }
            }.start();
            super.onChange(selfChange);
        }
    }

    class InnerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取发送广播过程中传递过来的包名，跳过此包名检测过程
            mSkipPackagename = intent.getStringExtra("packagename");
        }
    }

    private void watch() {
        //1,子线程中,开启一个可控死循环
        new Thread(){

            private String packagename;

            public void run() {
                mPacknameList = mDao.findAll();
                while(isWatch){
                    SystemClock.sleep(50);
                    //2.监测现在正在开启的应用,任务栈
                    //3.获取activity管理者对象

                    // if the sdk >= 21. It can only use getRunningAppProcesses to get task top package name
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        UsageStatsManager usage = (UsageStatsManager)getSystemService(Context.USAGE_STATS_SERVICE);
                        long time = System.currentTimeMillis();
                        List<UsageStats> stats = usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
                        if (stats != null) {
                            SortedMap<Long, UsageStats> runningTask = new TreeMap<Long,UsageStats>();
                            for (UsageStats usageStats : stats) {
                                runningTask.put(usageStats.getLastTimeUsed(), usageStats);
                            }
                            if (runningTask.isEmpty()) {
                                continue;
                            }
                            packagename =  runningTask.get(runningTask.lastKey()).getPackageName();
                        }
                    } else {// if sdk <= 20, can use getRunningTasks
                        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                        //4.获取正在开启应用的任务栈
                        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
                        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                        //5.获取栈顶的activity,然后在获取此activity所在应用的包名
                        packagename = runningTaskInfo.topActivity.getPackageName();
                    }

                    //如果任务栈指向应用有切换,将mSkipPackagename空字符串
                    Log.d(TAG, "run: 66666");
                    //6.拿此包名在已加锁的包名集合中去做比对,如果包含此包名,则需要弹出拦截界面
                    if(mPacknameList.contains(packagename)){
                        Log.d(TAG, "run: 777777");
                        //如果现在检测的程序，已经解锁了,则不需要去弹出拦截界面
                        if(!packagename.equals(mSkipPackagename)){
                            //7,弹出拦截界面
                            Intent intent = new Intent(WatchDogService.this, EnterPsdActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("packagename", packagename);
                            startActivity(intent);
                        }
                    }
                }
            };
        }.start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onDestroy() {
        //  停止看门狗循环
        isWatch = false;
        // 注销广播接收者
        if (mInnerReceiver != null) {
            unregisterReceiver(mInnerReceiver);
        }
        // 注销内容观察者
        if (mContentObserver != null) {
            getContentResolver().unregisterContentObserver(mContentObserver);
        }
        Log.d(TAG, "onDestroy: 看门狗已关闭");
        super.onDestroy();
    }
}
