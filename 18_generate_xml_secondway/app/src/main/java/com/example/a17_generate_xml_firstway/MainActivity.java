package com.example.a17_generate_xml_firstway;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    //点击按钮 通过StringBuffer的方式生成一个xml文件
    public void click(View v) {

        //1 创建sb对象
        StringBuffer sb = new StringBuffer();
        //2 开始组拼xml文件头
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        //3 开始组拼xml根节点
        sb.append("<smss>");
        //4 开始组拼sms节点
        for (Sms sms:smsLists) {
            sb.append("<sms>");
            //5 开始组拼address节点
            sb.append("<address>");
            sb.append(sms.getAddress());
            sb.append("</address>");
            //6 开始组拼body节点
            sb.append("<body>");
            sb.append(sms.getBody());
            sb.append("</body>");
            //7 开始组拼date节点
            sb.append("<date>");
            sb.append(sms.getDate());
            sb.append("</date>");
            sb.append("</sms>");
        }
        sb.append("</smss>");
        //8 把数据保存到sb卡中

        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "smsbackup.xml");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            fos.close();//关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}





























