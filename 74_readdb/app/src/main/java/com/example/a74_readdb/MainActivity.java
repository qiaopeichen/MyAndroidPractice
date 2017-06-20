package com.example.a74_readdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 对数据库进行增加一条记录由于数据库的内容已经通过内容提供者暴露出来，所以我们只需要使用内容解析者去操作就可以了
    public void click1(View v) {
        Uri uri = Uri.parse("content://com.itheima.provider/insert");
        ContentValues values = new ContentValues();
        // key 对应表的字段 value 对应值
        values.put("name", "赵六");
        values.put("money", 11111);
        Uri insert = getContentResolver().insert(uri, values);
        Log.d(TAG, "click1: insert");
    }

    // 对数据库进行删除一条记录
    public void click2(View v) {
        Uri uri = Uri.parse("content://com.itheima.provider/delete");
        int delete = getContentResolver().delete(uri, "name=?", new String[] {"赵六"});
        Toast.makeText(getApplicationContext(), "删除了" + delete + "行", Toast.LENGTH_SHORT).show();
    }

    // 对数据库进行修改一条记录
    public void click3(View v) {
        Uri uri = Uri.parse("content://com.itheima.provider/update");
        ContentValues values = new ContentValues();
        values.put("money", 0.000);
        int update = getContentResolver().update(uri, values, "name=?", new String[] {"赵六"});
        Toast.makeText(getApplicationContext(), "更新了" + update + "行", Toast.LENGTH_SHORT).show();
    }

    // 对数据库进行一次查找记录
    public void click4(View v) {
        // 第二种方式 读取数据库
        // 由于第一个应用里面的私有数据库已经通过内容提供者给暴露出来了，所以可以直接通过内容解析者进行访问
        //1 拿到内容解析者 直接通过上下文获取
        Uri uri = Uri.parse("content://com.itheima.provider/query"); // 路径和你定义的路径一样
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String money = cursor.getString(2);
                Log.d(TAG, "click4: name:" + name + "money" + money);
            }
        }
    }
}
