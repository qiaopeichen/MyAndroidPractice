package com.example.a35_login;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/4/20.
 */

public class StreamTools {

    //把一个inputstream 转换成一个string
    public static String readStream(InputStream in) throws Exception {
        //定义一个内存输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024]; // 1kb
        while ((len=in.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        in.close();
        baos.close();
        String content = new String(baos.toByteArray(), "gbk");
        return content;
    }
}
