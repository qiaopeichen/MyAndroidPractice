package com.example.a24_listview_show_complex_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找到我们关心的控件
        ListView lv = (ListView) findViewById(R.id.lv);
        //设置数据适配器
        lv.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 7;
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
            //1 想办法把我们自己定义的布局转换成一个view对象 就可以了
            View view;
            if (convertView == null) {
                /*创建一个新的view对象，可以通过打气筒把一个布局资源转换成一个view对象
                    resource 就是我们定义的布局文件
                    1.获取打气筒服务
                    view = View.inflate(getApplicationContext(), R.layout.item, null);
                    2.获取打气筒服务
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, null);
                    */
                //3.获取打气筒服务
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item, null);
            } else {
                //复用历史缓存对象
                view = convertView;
            }
            return view;
        }
    }
}
















