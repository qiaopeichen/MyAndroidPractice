package com.example.a75_sms_backup;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //点击按钮 查询短信数据内容 然后进行备份
    public void click(View v) {
        try {
            Log.d(TAG, "click: 进入到click中");
            //1 获取xml序列化实例
            XmlSerializer serializer = Xml.newSerializer();
            //2 设置序列化参数
            Log.d(TAG, "click: 设置序列化参数");
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "smsBackUp.xml");
            Log.d(TAG, "click: 2");
            FileOutputStream fos = new FileOutputStream(file);
            Log.d(TAG, "click: 3");
            serializer.setOutput(fos, "utf-8");
            Log.d(TAG, "click:serializer.setOutput(fos, \"utf-8\"); ");
            //3 开始写xml文档开头
            serializer.startDocument("utf-8", true);
            //4 开始写根节点
            serializer.startTag(null, "smss");
            //5 由于短信数据库 系统也通过内容提供者给暴露出来了 所以我们只需要通过内容解析者去操作数据库
            Uri uri = Uri.parse("content://sms/");
            Log.d(TAG, "click: Uri uri = Uri.parse(\"content://sms/\");");
            Cursor cursor = getContentResolver().query(uri, new String[] {"address", "date", "body"}, null, null, null);
            Log.d(TAG, "click: 6");
            while (cursor.moveToNext()) {
                Log.d(TAG, "click: 7");
                String address = cursor.getString(0);
                String date = cursor.getString(1);
                String body = cursor.getString(2);
                //6 写sms节点
                serializer.startTag(null, "sms");
                //7 写address节点
                serializer.startTag(null, "address");
                serializer.text(address);
                serializer.endTag(null, "address");
                //8 写body节点
                serializer.startTag(null, "body");
                serializer.text(body);
                serializer.endTag(null, "body");
                //9 写date节点
                serializer.startTag(null, "date");
                serializer.text(date);
                serializer.endTag(null, "date");

                serializer.endTag(null, "sms");
            }
            serializer.endTag(null, "smss");
            serializer.endDocument();

            Log.d(TAG, "click: 备份完成");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
