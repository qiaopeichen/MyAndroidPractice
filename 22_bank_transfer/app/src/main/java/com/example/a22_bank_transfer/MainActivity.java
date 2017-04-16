package com.example.a22_bank_transfer;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyOpenHelper myOpenHelper;

//    private List<Person> lists;
//    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        lists = new ArrayList<>();
//        lv = (ListView) findViewById(R.id.lv);
        myOpenHelper = new MyOpenHelper(getApplicationContext());
    }

    //点击按钮进行转账的逻辑
    public void click(View v) {
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        //使用事务进行转账
        db.beginTransaction(); // 开启事务
        try {
            //实现转账的逻辑，实际上就是写SQL语句
            db.execSQL("update info set money = money - 100 where name = ?", new Object[]{"张三"});
//            int i = 10 / 0;
            db.execSQL("update info set money = money + 100 where name = ?", new Object[]{"李四"});
            //给当前事务设置一个成功的标记
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "服务器忙，请稍后再转", Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction(); //关闭事务
        }
    }

    /*关于setTransactionSuccessful()和endTransaction()
* setTransactionSuccessful()给事务一个成功的标记，
* 如果在执行endTransaction()之前都没有遇到成功标记，那么所有事务都会回滚。
* 如果只有成功标记而没有endTransaction()，就不知道在哪里结束。
* 有时候事务涉及到多线程问题，主线程在执行到endTransaction的时候子线程还没执行完，
* 所以setTransactionSuccessful()和endTransaction()缺一不可。
*
* 在setTransactionSuccessful()和endTransaction()绝对不能写数据库逻辑，尽量避免写其他逻辑。
* If any errors are encountered between this and endTransaction the transaction will still be committed.
* 因为无论任何错误发生在这两个函数之间，事务都会提交。
* */

    /**
     * 以下为个人测试代码
     */
    //点击按钮进行查询的逻辑
//    public void click2(View v) {
//        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
//        Cursor cursor = db.query("info", null, null, null, null, null, null);
//
//        if (cursor != null && cursor.getCount()>0) {
//            while (cursor.moveToNext()) {
//                String name = cursor.getString(1);
//                String money = cursor.getString(3);
//                Person person = new Person();
//                person.setName(name);
//                person.setMoney(money);
//                lists.add(person);
//            }
//            lv.setAdapter(new MyAdapter());
//        }
//    }
    //定义listview的适配器

//    private class MyAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return lists.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view;
//            if (convertView == null) {
//                //创建新的view对象 inflate打气筒
//                view = View.inflate(getApplicationContext(), R.layout.item, null);
//            } else {
//                view = convertView;
//            }
//
//            //找到控件来加载数据
//            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//            TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
//
//            Person person =lists.get(position);
//            tv_name.setText(person.getName());
//            tv_money.setText(person.getMoney());
//            return view;
//        }
//    }
}
