package com.example.a93_wx;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找到按钮
        Button btn_wx = (Button) findViewById(R.id.btn_wx);
        Button btn_contact = (Button) findViewById(R.id.btn_contact);
        Button btn_discover = (Button) findViewById(R.id.btn_discover);
        Button btn_me = (Button) findViewById(R.id.btn_me);
        //2 设置点击事件
        btn_wx.setOnClickListener(this);
        btn_discover.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_me.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //4 获取Fragment的管理者
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        // 具体判断点击的是哪个按钮
        switch (v.getId()) {
            case R.id.btn_wx: // 点击的是微信
                beginTransaction.replace(R.id.ll_layout, new WxFragment());
                break;
            case R.id.btn_contact: // 点击的是联系人
                beginTransaction.replace(R.id.ll_layout, new ContactFragment());
                break;
            case R.id.btn_discover: // 点击的是发现
                beginTransaction.replace(R.id.ll_layout, new DiscoverFragment());
                break;
            case R.id.btn_me: // 点击的是我
                beginTransaction.replace(R.id.ll_layout, new MeFragment());
                break;
        }
        //记得事务要提交
        beginTransaction.commit();
    }
}
