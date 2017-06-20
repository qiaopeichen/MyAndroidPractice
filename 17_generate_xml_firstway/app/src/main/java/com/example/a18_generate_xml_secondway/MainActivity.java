package com.example.a18_generate_xml_secondway;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Sms> smsLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 初始化我们要备份的数据
        smsLists = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Sms sms = new Sms();
            sms.setAddress("10008" + i);
            sms.setBody("nihao" + i);
            sms.setDate("201" + i);
            //2 把sms对象加入到集合中
            smsLists.add(sms);

        }
    }

    //点击按钮 通过XmlSerializer的方式生成一个xml文件
    public void click(View v) {
        try {
            //1 获取XmlSerializer类的实例 通过Xml这个工具类去获取
            XmlSerializer serializer = Xml.newSerializer();
            //2 设置 xmlserializer序列化器参数
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "smsbackup2.xml");
            FileOutputStream fos = new FileOutputStream(file);
            serializer.setOutput(fos, "utf-8");
            //3 开始写xml文档开头
            serializer.startDocument("utf-8", true);
            //4 写xml的根节点 namespace 命名空间
            serializer.startTag(null, "smss");
            //5 循环写sms节点
            for (Sms sms : smsLists) {
                serializer.startTag(null, "sms");
                //6 开始写address节点
                serializer.startTag(null, "address");
                serializer.text(sms.getAddress());
                serializer.endTag(null, "address");
                //7 开始body节点
                serializer.startTag(null, "body");
                serializer.text(sms.getBody());
                serializer.endTag(null, "body");
                //8 开始date节点
                serializer.startTag(null, "date");
                serializer.text(sms.getDate());
                serializer.endTag(null, "date");
                //sms 节点结束
                serializer.endTag(null, "sms");
            }
            serializer.endTag(null, "smss");
            //写文档末尾
            serializer.endDocument();// 功能类似于fos.close();
            fos.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





























