package com.example.a19_xml_parse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class WeatherParser {
    /**
     * 服务器是以流的形式把数据返回的
     * @return
     */
    public static List<Channel> parserXml(InputStream in) throws Exception {
        //0 声明集合对象
        List<Channel> weatherLists = null;
        Channel channel = null;
        //1 获取XmlPullParser 解析的实例
        XmlPullParser parser = Xml.newPullParser();
        //2 设置XmlPullParser的参数
        parser.setInput(in, "utf-8");
        //3 获取事件类型
        int type = parser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG: //解析开始标签
                   //4 具体判断一下 解析到哪个开始标志
                    if ("weather".equals(parser.getName())) {
                        //5 创建一个集合对象
                        weatherLists = new ArrayList<Channel>();
                    } else if ("channel".equals(parser.getName())) {
                        //6 创建channel对象
                        channel = new Channel();
                        //7 获取id值
                        String id = parser.getAttributeValue(0);
                        channel.setId(id);
                    } else if ("city".equals(parser.getName())) {
                        //8 获取city的数据
                        String city = parser.nextText();
                        channel.setCity(city);
                    } else if ("temp".equals(parser.getName())) {
                        String temp = parser.nextText();
                        channel.setTemp(temp);
                    } else if ("wind".equals(parser.getName())) {
                        String wind = parser.nextText();
                        channel.setWind(wind);
                    } else if ("pm250".equals(parser.getName())) {
                        String pm250 = parser.nextText();
                        channel.setPm250(pm250);
                    }
                    break;

                case XmlPullParser.END_TAG: //解析结束标签
                    //判断要解析的结束标签
                    if ("channel".equals(parser.getName())) {
                        //把javabean对象存到集合中
                        weatherLists.add(channel);
                    }
                    break;
            }
            //不停的向下解析
            type = parser.next();
        }
        return weatherLists;
    }
}
