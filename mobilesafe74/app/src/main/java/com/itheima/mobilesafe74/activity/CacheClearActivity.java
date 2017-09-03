package com.itheima.mobilesafe74.activity;

import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;

import java.lang.reflect.Method;
import java.util.List;

public class CacheClearActivity extends AppCompatActivity {

    protected static final int UPDATE_CACHE_APP = 100;
    protected static final int CHECK_CACHE_APP = 101;
    protected static final int CHECK_FINISH = 102;
    protected static final int CLEAR_CACHE = 103;
    private Button bt_clear;
    private ProgressBar pb_bar;
    private TextView tv_name;
    private LinearLayout ll_add_text;
    private PackageManager mPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clear);
        initUI();
        initData();
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                // 1.获取包管理者对象
                mPm = getPackageManager();
                // 2.获取安装在手机上的所有的应用
                List<PackageInfo> installedPackages = mPm.getInstalledPackages(0);
                // 3.给进度条设置最大值（手机中所有应用的总数）
                pb_bar.setMax(installedPackages.size());
                // 4.遍历每一个应用,获取有缓存的应用信息（应用名称，图标，缓存大小，包名）
                for (PackageInfo packageInfo : installedPackages) {
                    // 包名作为获取缓存信息的条件
                    String packageName = packageInfo.packageName;
                    getPackageCache(packageName);
                }
            }
        }.start();
    }

    private void getPackageCache(String packageName) {
        new IPackageStatsObserver.Stub() {
            @Override
            public void onGetStatsCompleted(PackageStats stats, boolean succeeded) throws RemoteException {
                // 子线程中方法，用到消息机制
                // 4.获取指定包名的缓存大小
                long cacheSize = stats.cacheSize;
                // 5.判断缓存大小是否大于0
                if (cacheSize > 0) {
                    // 6.告知主线程更新UI
                    Message msg = Message.obtain();

                    msg.what = UPDATE_CACHE_APP;
                    //TODO

                }
            }
        }
    }

    private void initUI() {
        bt_clear = (Button) findViewById(R.id.bt_clear);
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        tv_name = (TextView) findViewById(R.id.tv_name);
        ll_add_text = (LinearLayout) findViewById(R.id.ll_add_text);
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.获取指定类的字节码文件
                try {
                    Class<?> clazz = Class.forName("android.content.pm.PackageManager");
                    // 2.获取调用方法对象
                    Method method = clazz.getMethod("freeStorageAndNotify", long.class, IPackageStatsObserver.class);
                    // 3.获取对象调用方法
                    method.invoke(mPm, packageName, mStatsObserer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
