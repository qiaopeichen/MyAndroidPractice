package com.example.a25_use_arrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String[] objects ={"A", "B", "C", "D", "E", "F", "G", "H"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        ListView lv = (ListView) findViewById(R.id.lv);
        //2 创建一个arrayAdapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, objects);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item1, R.id.tv_name, objects);

        //设置数据适配器 要会触类旁通
        lv.setAdapter(adapter);
    }
}
