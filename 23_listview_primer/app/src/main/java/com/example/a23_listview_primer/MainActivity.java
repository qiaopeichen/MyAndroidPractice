package com.example.a23_listview_primer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        ListView lv = (ListView) findViewById(R.id.lv);

        //2 显示数据和其他普通控件（textview）有点区别 数据来源于数据适配器
        lv.setAdapter(new MyListAdapter());
    }

    //3 定义listview的数据适配器
    private class MyListAdapter extends BaseAdapter {
        //一共有多少数据需要显示
        @Override
        public int getCount() {
            return 6;
        }
        //返回指定posotion位置对应的对象
        @Override
        public Object getItem(int position) {
            return null;
        }
        //返回position位置对应的id
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         *
         * 获取一个view 用来显示Listview的数据 会作为一个Listview的一个条目出现
         * convertView 历史缓存对象
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                tv=new TextView(getApplicationContext());
                Log.d(TAG, "getView: 创建新的view对象" + position);
            } else {
                Log.d(TAG, "复用历史缓存对象" + position);
                tv = (TextView) convertView;
            }
            tv.setText("哈哈哈"+position);
            return tv;
        }
    }
}
