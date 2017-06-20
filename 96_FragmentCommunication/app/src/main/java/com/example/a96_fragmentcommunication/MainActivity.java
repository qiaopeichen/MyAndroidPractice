package com.example.a96_fragmentcommunication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 获取fragment管理者
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2 开启一个事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        //2.1 替换fragment
        beginTransaction.replace(R.id.ll_1, new Fragment1(), "f1");
        beginTransaction.replace(R.id.ll_2, new Fragment2(), "f2");
        //3 开启事务
        beginTransaction.commit();
    }
}
