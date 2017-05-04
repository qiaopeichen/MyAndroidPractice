package com.example.a98_auto_complete_textview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    // 模拟actv这个控件要显示的数据
    private String[] COUNTRIES = new String[] { "laofang", "laoli", "laozhang", "laobi", "laowang","aa","abb","ccc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找到控件
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.actv);
        //2 actv这个控件显示数据的原理和listview一样，需要一个数据适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        //3 显示数据
        actv.setAdapter(adapter);
    }
}
