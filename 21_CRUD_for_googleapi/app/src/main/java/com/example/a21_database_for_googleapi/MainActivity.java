package com.example.a21_database_for_googleapi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyOpenHelper myOpenHelper;
    private ListView lv;
    private List<Person> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //0 找到lv
        lv = (ListView) findViewById(R.id.lv);

        myOpenHelper = new MyOpenHelper(getApplicationContext());

        //1 定义一个集合用来存listview要显示的数据
        lists = new ArrayList<>();

        //打开或者创建数据库 如果是第一次就创建
//        SQLiteDatabase sqLiteDatabase = myOpenHelper.getWritableDatabase();
        //打开或者创建数据库 如果是第一次就创建 如果磁盘满了 返回只读的
//        SQLiteDatabase readableDatabase = myOpenHelper.getReadableDatabase();
    }
    //点击按钮增加一条记录
    public void click1(View v) {
        //1 获取数据库对象
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        //2 执行增加一条的sql语句
//        db.execSQL("insert into info(name,phone) values(?,?)", new Object[]{"张三","13888"});
        /**
         * table 表名
         * ContentValues 内部封装了一个map key:对应列的名字 value对应的值
         */
        ContentValues values = new ContentValues();
        values.put("name", "王五");
        values.put("phone", "110");
        //返回值代表插入新行的id
        long insert = db.insert("info", null, values); //底层就在组拼sql语句
        //3 数据库用完需要关闭
        db.close();
    }
    //删除
    public void click2(View v) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.execSQL("delete from info where name=?", new Object[]{"张三"});
        db.close();
    }
    //更新
    public void click3(View v) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.execSQL("update info set phone=? where name=?",new Object[]{"13999","张三"});
        db.close();
    }
    //查找
    public void click4(View v) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from info", null);
        if (cursor!= null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                //columnIndex代表列的索引
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                Log.d("Test", "name:"+name+"----"+phone);
            }
        }
    }

    //定义listview的适配器
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
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
            return null;
        }
    }
}





































