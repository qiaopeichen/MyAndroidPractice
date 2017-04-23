package com.example.a42_sms_collection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String objects[] = {"今天的风儿轻柔无比，今天的花儿香飘万里；今天的鸟儿十分欢喜，今天的云儿满载笑意；今天的事儿万分顺利，今天的人儿如此甜蜜。所有美...",
            "丫头，生活是你自己的，你哭它就对你哭，你笑它就对你笑。转眼，又是一年，你的生日即将来到。今年，还是少不了我对你的祝福，我忍不住...",
            "世界上最动听的声音，是妈妈声声的呼唤；世界上最温暖的笑容，是妈妈温暖的笑脸。妈妈，原谅生日时我不能陪在您身边，在这个日子里，我...",
            "今天是你的生日，祝你：发财势头如快马加鞭，一日千里；发展速度如滔滔江水，势不可挡；好事发生如雨后春笋，络绎不绝；祝福发送如比赛..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到lv
        ListView lv = (ListView) findViewById(R.id.lv);
        //2 设置数据 先有数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, objects);
        //3 设置数据适配器
        lv.setAdapter(adapter);
        //4 给listview设置 点击事件 小技巧：
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 当listview的一个条目被点击的时候调用
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //5 把点击条目的数据取出来 掌握一条原则：数据在哪里存着呢，就去哪里取
                String content = objects[position];
                //6 跳转到发送短信页面
                 /*<intent-filter>
	               <action android:name="android.intent.action.SEND" />
	               <category android:name="android.intent.category.DEFAULT" />
	               <data android:mimeType="text/plain" />
	           </intent-filter>*/
                Intent intent = new Intent();
                //6.1 设置action
                intent.setAction("android.intent.action.SEND");
                //6.2 添加category
                intent.addCategory("android.intent.category.DEFAULT");
                //6.3 设置type
                intent.setType("text/plain");
                //6.4 传递数据
                intent.putExtra("sms_body", content);
                //7 跳转到发送页面
                startActivity(intent);
            }
        });
    }
}
