package com.example.a78_insert_contact;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
    }

    // 点击按钮 把用户输入的数据 插入到联系人数据库中
    public void click(View v) {

        //2 获取数据
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String email = et_email.getText().toString().trim();

        //2.1 定义uri   raw:原始的
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        //2.2 先查询一下raw_contacts表中一共有几条数据 行数+1 就是contact_id的值
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int count = cursor.getCount();
        int contact_id = count + 1; // 代表当前联系人的id

        //3 把数据插入到联系人数据库，由于联系人的数据库也是通过内容提供者暴露出来，所以我们直接通过内容解析者去操作数据库
        ContentValues values = new ContentValues();
        values.put("contact_id", contact_id);
        getContentResolver().insert(uri, values);

        //4 把name,phone,email插入到data表
        ContentValues nameValues = new ContentValues();
        nameValues.put("data1", name); // 把数据插入到data1列
        nameValues.put("raw_contact_id", contact_id); // 告诉数据库我们插入的数据属于哪条联系人
        nameValues.put("mimetype", "vnd.android.cursor.item/name"); // 告诉数据库插入的数据的数据类型
        getContentResolver().insert(dataUri, nameValues);

        //5 把phone 插入到data表
        ContentValues phoneValues = new ContentValues();
        phoneValues.put("data1", phone); // 把数据插入到data1列
        phoneValues.put("raw_contact_id", contact_id); // 告诉数据库我们插入的数据属于哪条联系人
        phoneValues.put("mimetype", "vnd.android.cursor.item/phone_v2"); // 告诉数据库插入的数据的数据类型
        getContentResolver().insert(dataUri, phoneValues);

        //6 把email插入到data表
        ContentValues emailValues = new ContentValues();
        emailValues.put("data1", email); // 把数据插入到data1列
        emailValues.put("raw_contact_id", contact_id); // 告诉数据库我们插入的数据属于哪条联系人
        emailValues.put("mimetype", "vnd.android.cursor.item/email_v2"); // 告诉数据库插入的数据的数据类型
        getContentResolver().insert(dataUri, emailValues);
    }
}
