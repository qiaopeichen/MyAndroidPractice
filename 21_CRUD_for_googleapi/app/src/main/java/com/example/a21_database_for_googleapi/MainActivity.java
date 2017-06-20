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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        if (insert>0) {
            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
        }
    }
    //删除
    public void click2(View v) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
//        db.execSQL("delete from info where name=?", new Object[]{"张三"});

        //返回值代表影响的行数
        int delete = db.delete("info", "name=?", new String[]{"王五"});
        db.close();
        Toast.makeText(getApplicationContext(), "删除了"+delete+"行", Toast.LENGTH_SHORT).show();
    }
    //更新
    public void click3(View v) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
//        db.execSQL("update info set phone=? where name=?",new Object[]{"13999","张三"});
        ContentValues values = new ContentValues();
        values.put("phone", "114");
        //代表更新多少行
        int update = db.update("info", values, "name=?", new String[]{"王五"});
        db.close();
        Toast.makeText(getApplicationContext(), "更新了"+update+"行", Toast.LENGTH_SHORT).show();
    }
    //查找
    public void click4(View v) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        //columns 代表你要查询的列
        //selection 根据什么查询phone
//        Cursor cursor =db.query("info", new String[]{"name","phone"}, "name=?", new String[]{"王五"}, null, null, null);
        Cursor cursor = db.query("info", null, null, null, null, null, null);

//        Cursor cursor = db.rawQuery("select * from info", null);
        if (cursor!= null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                //columnIndex代表列的索引
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
//                Log.d("Test", "name:"+name+"----"+phone);
                //把数据封装到javabean
                Person person = new Person();
                person.setName(name);
                person.setPhone(phone);

                //把javabean对象加入到集合
                lists.add(person);
            }
            lv.setAdapter(new MyAdapter());
        }
    }

    // TODO: 2017/4/15  
    //定义listview的适配器
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
                //创建新的view对象 inflate打气筒
                view = View.inflate(getApplicationContext(), R.layout.item, null);
            } else {
                view = convertView;
            }
            //找到控件用来显示数据
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            //如何显示数据
            Person person = lists.get(position);
            tv_name.setText(person.getName());
            tv_phone.setText(person.getPhone());

            return view;
        }
    }
}





































