package com.example.a73_db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AccountProvider extends ContentProvider {

    //1 定义一个urimacher 定义路径匹配器
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int QUERYSUCCESS = 0; // 大小写转换 ctrl + shift + u
    private static final int INSERTSUCCESS = 1;
    private static final int UPDATESUCCESS = 2;
    private static final int DELETESUCCESS = 3;
    private MyOpenHelper myOpenHelper;

    //2 定义静态代码块 添加匹配规则
    static {
        /**
         * authority 注意 这个参数和你在清单文件里面定义的要一样
         * URL http://baidu.com
         * uri content://com.itheima.provider/query
         *     content://com.itheima.provider/insert
         *     content://com.itheima.provider/update
         *     content://com.itheima.provider/delete
         */
        sURIMatcher.addURI("com.itheima.provider", "query", QUERYSUCCESS);
        sURIMatcher.addURI("com.itheima.provider", "insert", INSERTSUCCESS);
        sURIMatcher.addURI("com.itheima.provider", "update", UPDATESUCCESS );
        sURIMatcher.addURI("com.itheima.provider", "delete", DELETESUCCESS);
    }



    public AccountProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int match = sURIMatcher.match(uri);
        if (match == DELETESUCCESS) {
            // 说明路径匹配成功 对数据库进行删除的操作
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            // 代表影响的函数 clause：子句
            int delete = db.delete("info", selection, selectionArgs);
            db.close(); // 关闭数据库
            if (delete > 0) {
                // 发送一条消息 说明数据库发生了改变
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return delete;
        } else {
            // 路径不匹配
            throw new IllegalArgumentException("您的路径不匹配，请检查路径");
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        int code = sURIMatcher.match(uri);
        if (code == INSERTSUCCESS) {
            // 操作数据库 说明路径匹配成功
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            // 返回值代表新插入行数的id
            long insert = db.insert("info", null, values);
            db.close(); // 关闭数据库
            if (insert > 0) {
                // 发送一条消息 说明数据库发生了改变
                getContext().getContentResolver().notifyChange(uri, null);
            }

            Uri uri2 = uri.parse("com.itheima.insert/" + insert);
            return uri2;
        } else {
            // 路径不匹配
            throw new IllegalArgumentException("您的路径不匹配，请检查路径");
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        myOpenHelper = new MyOpenHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        int code = sURIMatcher.match(uri);
        if (code == QUERYSUCCESS) {
            // 说明路径匹配成功 把query方法给实现 数据库的查询方法，对数据库进行查询的操作，想操作数据库必须得获得sqlitedatabase对象
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
            // cursor 不能关闭 注意
            // 数据库被人操作了 自己发送一条消息
            getContext().getContentResolver().notifyChange(uri, null);
            return cursor;
        } else {
            // 路径不匹配
            throw new IllegalArgumentException("路径不匹配，请检查路径");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int code = sURIMatcher.match(uri);
        if (code == UPDATESUCCESS) {
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            // 影响的行数
            int update = db.update("info", values, selection, selectionArgs);
            db.close(); // 关闭数据库
            if (update > 0) {
                // 发送一条消息 说明数据库发生了改变
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return update;
        } else {
            // 路径不匹配
            throw new  IllegalArgumentException("你的路径不匹配，请检查路径");
        }
    }
}
