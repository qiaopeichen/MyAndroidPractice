package com.example.a27_simple_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找到控件
        ListView lv = (ListView) findViewById(R.id.lv);
        //1.1 准备Listview要显示的数据
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "张飞");
        map1.put("phone", "13888");

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "赵云");
        map2.put("phone", "110");

        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("name", "貂蝉");
        map3.put("phone", "123456");

        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("name", "关羽");
        map4.put("phone", "119");

        //1.1 把map加入到集合中
        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);

        //2 设置数据适配器 resource 我们定义的布局文件
        //form map集合的键
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.item,
                new String[]{"name", "phone"}, new int[]{R.id.tv_name, R.id.tv_phone});
        //3 设置数据适配器
        lv.setAdapter(adapter);
    }
}