package com.example.a20_create_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/15.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    /**
     *
     * @param context 上下文
     * @param name 数据库的名字
     * @param factory 目的创建cursor对象
     * @param version 数据库的版本 从i开始
     */
    public MyOpenHelper(Context context) {
        super(context, "itheima.db", null, 1);
    }

    /**
     *当数据库第一次创建的时候调用
     * 那么这个方法特别适合做表结构的初始化 创建表就是写sql语句
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //id 一般为 _id
        db.execSQL("create table info(_id integer primary key autoincrement, name varchar(20), phone varchar(20))");
    }

    /**
     * Called when the database needs to be upgraded
     * 当数据库版本升级的时候调用
     * 这个方法适合做表结构的更新
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table info add phone varchar(20)");
    }
}
