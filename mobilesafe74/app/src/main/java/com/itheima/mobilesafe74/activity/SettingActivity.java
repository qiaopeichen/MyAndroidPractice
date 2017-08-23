package com.itheima.mobilesafe74.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;
import com.itheima.mobilesafe74.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
    }

    /**
     * 版本更新开关
     */
    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        // 获取已有的开关状态，用作显示
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        // 是否选中，根据上一次存储的结果去做决定
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果之前是选中的，点击过后变成未选中
                // 如果之前是未选中的，点击过后变成选中
                //获取之前的选中状态
                boolean isCheck = siv_update.isCheck();
                // 将原有状态取反，等同上述的两步操作
                siv_update.setCheck(!isCheck);
//                 将取反后的状态存储到相应sp中
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !isCheck);
            }
        });
    }
}
