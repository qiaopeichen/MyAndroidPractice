package com.example.a33_news_client;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class XmlParserUtils {

    //解析xml的业务方法
    public static List<News> parserXml(InputStream in) throws Exception {

        List<News> newsLists = null;
        News news = null;
        //1 获取xml的解析器
        XmlPullParser parser = Xml.newPullParser();
        //2 设置解析器要解析的内容
        parser.setInput(in, "utf-8");
        //3 获取解析器的事件类型
        int type = parser.getEventType();
        //4 不停的向下解析
        while (type != XmlPullParser.END_DOCUMENT) {
            //5 具体判断一下解析的是开始节点 还是结束节点
            switch (type) {
                case XmlPullParser.START_TAG: //解析开始节点
                    //6 具体判断一下解析的是哪个开始标签
                    if ("channel".equals(parser.getName())) {
                        //创建一个List集合
                        newsLists = new ArrayList<News>();
                    } else if ("item".equals(parser.getName())) {
                        news = new News();
                    } else if ("title".equals(parser.getName())) {
                        news.setTitle(parser.nextText());
                    } else if("description".equals(parser.getName())) {
                        news.setDescription(parser.nextText());
                    } else if("image".equals(parser.getName())) {
                        news.setImage(parser.nextText());
                    } else if("type".equals(parser.getName())) {
                        news.setType(parser.nextText());
                    } else if ("comment".equals(parser.getName())) {
                        news.setComment(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG: //解析结束标签
                    if("item".equals(parser.getName())) {
                        //把javabean添加到集合
                        newsLists.add(news);
                    }
                    break;
            }
            //不停的向下解析
            type = parser.next();
        }

        return newsLists;
    }
}
