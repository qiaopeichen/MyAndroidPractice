package com.itheima.mobilesafe74.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private static final String TAG = "jojojo";
    private List<HashMap<String, String>> contactList = new ArrayList<>();
    private MyAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 填充数据适配器
            mAdapter = new MyAdapter();
            lv_contact.setAdapter(mAdapter);
        }
    };
    private ListView lv_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initUI();
        initData();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_contact_item, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.phone = (TextView) convertView.findViewById(R.id.tv_phone);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(getItem(position).get("name"));
            holder.phone.setText(getItem(position).get("phone"));
            return convertView;
        }
    }

    static class ViewHolder {
        TextView name;
        TextView phone;
    }

    /**
     * 获取系统联系人数据方法
     */
    private void initData() {
        RxPermissions rxPermissions = new RxPermissions(ContactListActivity.this);
        // 因为读取系统联系人，可能是一个耗时操作，放置到子线程中处理
        new Thread() {
            public void run() {
//                //1. 获取内容解析器对象
                ContentResolver contentResolver = getContentResolver();
//                //2. 做查询系统联系人数据库表过程（读取联系人权限）
                rxPermissions
                        .request(Manifest.permission.READ_CONTACTS)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the camera now
                                Cursor cursor = contentResolver.query(
                                        Uri.parse("content://com.android.contacts/raw_contacts"),
                                        new String[]{"contact_id"},
                                        null, null, null);
                                contactList.clear();
                                //3. 循环游标，直到没有数据位置
                                while (cursor.moveToNext()) {
                                    String id = cursor.getString(0);
                                    Log.d(TAG, "id = " + id);
                                    //4. 根据用户唯一性id值，查询data表和mimetype表生成的视图
                                    Cursor indexCursor = contentResolver.query(
                                            Uri.parse("content://com.android.contacts/data"),
                                            new String[]{"data1", "mimetype"},
                                            "raw_contact_id = ?", new String[]{id}, null);
                                    //5.循环获取每一个联系人的电话号码以及姓名，数据类型
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    while (indexCursor.moveToNext()) {
                                        String data = indexCursor.getString(0);
                                        String type = indexCursor.getString(1);
                                        //6.区分类型去给hashMap填充数据
                                        if (type.equals("vnd.android.cursor.item/phone_v2")) {
                                            // 数据非空判断
                                            if (!TextUtils.isEmpty(data)) {
                                                hashMap.put("phone", data);
                                            }
                                        } else if (type.equals("vnd.android.cursor.item/name")) {
                                            if (!TextUtils.isEmpty(data)) {
                                                hashMap.put("name", data);
                                            }
                                        }
                                    }
                                    indexCursor.close();
                                    contactList.add(hashMap);
                                }
                                cursor.close();
                                //7.消息机制，发送一个空的消息，告知主线程可以去使用子线程已经填充好的数据集合
                                mHandler.sendEmptyMessage(0);
                            } else {
                                // Oups permission denied
                                ToastUtil.show(getApplicationContext(), "未获取到相应权限");
                            }
                        });

            }
        }.start();


    }

    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.获取点中条目的索引指向集合的对象
                if (mAdapter!=null) {
                    HashMap<String, String> hashMap = mAdapter.getItem(position);
                    //2.获取当前条目指向集合对应的电话号码
                    String phone = hashMap.get("phone");
                    //3.此电话号码需要给第三个导航界面使用

                    //4.在结束此页面回到前一个导航界面的时候，需要将数据返回过去
                    Intent intent = new Intent();
                    intent.putExtra("phone", phone);
                    setResult(0, intent);
                    finish();
                }
            }
        });
    }


}
