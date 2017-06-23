package com.example.a108_refreshlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RefreshListView listView;
    private ArrayList<String> listDatas;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
        setContentView(R.layout.activity_main);

        listView = (RefreshListView) findViewById(R.id.listview);
        listView.setRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        listDatas.add(0, "我是下拉刷新出来的数据");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                listView.onRefreshComplete();
                            }
                        });
                    }
                }.start();
            }

            @Override
            public void onLoadMore() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        listDatas.add("我是加载更多出来的数据1");
                        listDatas.add("我是加载更多出来的数据2");
                        listDatas.add("我是加载更多出来的数据3");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                listView.onRefreshComplete();
                            }
                        });
                    }
                }.start();
            }
        });

        listDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            listDatas.add("这是一条ListView数据：" + i);
        }
        // 设置数据适配器
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return listDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(18f);
            textView.setText(listDatas.get(position));
            return textView;
        }
    }
}
