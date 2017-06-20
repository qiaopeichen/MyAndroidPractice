package com.example.a19_xml_parse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //1 显示天气信息
            TextView tv_weather = (TextView) findViewById(R.id.tv_weather);
            //1.1 通过上下文获取资产的管理者
            InputStream inputStream = getAssets().open("weather.xml");
            //2 调用我们定义的解析xml业务方法
            List<Channel> weatherLists = WeatherParser.parserXml(inputStream);

            StringBuffer sb = new StringBuffer();
            for (Channel channel : weatherLists) {
                sb.append(channel.toString());
            }
            //3 把数据显示到textview上
            tv_weather.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
