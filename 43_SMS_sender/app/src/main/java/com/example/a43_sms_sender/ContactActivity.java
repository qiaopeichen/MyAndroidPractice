package com.example.a43_sms_sender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private List<Person> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);         //加载布局
        //1 找到控件
        ListView lv = (ListView) findViewById(R.id.lv);
        //2 准备listview 要显示的数据 模拟点假数据
        lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person p = new Person();
            p.setName("张三" + i);
            p.setPhone("11" + i);
            lists.add(p);
        }
        //3 展示数据
        lv.setAdapter(new MyAdapter());
        //4 给listview 设置点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //5 获取我点中条目的数据 数据在哪里存着呢 就去哪里取
                String phone = lists.get(position).getPhone();
                //5.0 把数据返回给调用者
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                // 把结果返回给调用者
                setResult(10, intent);
                // 关闭当前页面
                finish();
            }
        });
    }
    // 后退键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.contact_item, null);
            } else {
                view = convertView;
            }
            //1 找到我们在item中定义的控件 用来显示数据
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            //2 展示数据
            tv_name.setText(lists.get(position).getName());
            tv_phone.setText(lists.get(position).getPhone());
            return view;
        }
    }
}
