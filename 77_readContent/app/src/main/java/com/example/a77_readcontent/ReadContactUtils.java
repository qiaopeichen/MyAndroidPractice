package com.example.a77_readcontent;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/29.
 */

public class ReadContactUtils {

    private static final String TAG = "jojo";

    public static List<Contact> readContact(Context context) {
        //0 创建集合对象
        List<Contact> contactsLists = new ArrayList<>();
        //1 由于联系人的数据库也是通过内容提供者暴露出来了，所以我想直接使用内容解析者操作数据库

        //2 先查询raw_contacts表 contact_id列
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        Cursor cursor = context.getContentResolver().query(uri, new String[] {"contact_id"}, null, null, null);
        while (cursor.moveToNext()) {
            String contact_id = cursor.getString(0);
            if (contact_id != null) {
                Log.d(TAG, "contact_id" + contact_id);
                // 创建javabean对象
                Contact contact = new Contact();
                contact.setId(contact_id);
                //3 根据rew_contact_id去查询data表，data1列和mimetype_id列
                //小细节 查询的不是data表，而是view_data的视图
                Cursor dataCursor = context.getContentResolver().query(dataUri, new String[] {"data1", "mimetype"}, "raw_contact_id = ?", new String[] {contact_id}, null);

                while (dataCursor.moveToNext()) {
                    String data1 = dataCursor.getString(0);
                    String mimetype = dataCursor.getString(1);

                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        Log.d(TAG, "姓名：" + data1);
                        contact.setName(data1);
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                        Log.d(TAG, "电话号码：" + data1);
                        contact.setPhone(data1);
                    } else if ("vnd.android.cursor.item/email_v2".equals(mimetype)) {
                        Log.d(TAG, "邮箱：" + data1);
                        contact.setEmail(data1);
                    }
                }
                // 把javabean对象加入到结合中
                contactsLists.add(contact);
            }
        }
        return contactsLists;
    }
}
