package com.example.a22_bank_transfer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/16.
 */

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "Account.db", null, 1);
    }


    //当数据库第一次创建的时候调用，特别适合做表结构的初始化
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info (_id integer primary key autoincrement, name varchar(20), phone varchar(20), money varchar(20))");
        db.execSQL("insert into info ('name', 'phone', 'money') values ('张三', '13888','2000')");
        db.execSQL("insert into info ('name', 'phone', 'money') values ('李四', '13999', '5000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
