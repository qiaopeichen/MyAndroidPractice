package com.example.a28_web_page_source_viewer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/4/16.
 */

public class StreamTools {

    //把一个InputStream 转换成一个Stream
    public static String readStream(InputStream in) throws Exception{
        //定义一个内存输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024]; // 1kb
        while ((len=in.read(buffer))!=-1) {
            baos.write(buffer, 0, len);
        }
        in.close();
        String content = new String(baos.toByteArray());
        return content;
    }
}
