package com.itheima.mobilesafe74.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.itheima.mobilesafe74.R;

public class BaseClearCacheActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_clear_cache);

        // 2.生成选项卡1   tabspec选项卡 indicator指示器
        TabHost.TabSpec tab1 = getTabHost().newTabSpec("clear_cache").setIndicator("缓存清理");
        // 2.生成选项卡2
        TabHost.TabSpec tab2 = getTabHost().newTabSpec("sd_cache_clear").setIndicator("sd卡清理");
        // 3.告知点中选项卡后续操作
        tab1.setContent(new Intent(this, CacheClearActivity.class));
        tab2.setContent(new Intent(this, SDCacheClearActivity.class));
        // 4.将此两个选项卡维护Host（选项卡宿主）中去
        getTabHost().addTab(tab1);
        getTabHost().addTab(tab2);
    }
}
