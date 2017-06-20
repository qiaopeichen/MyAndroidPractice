package com.example.a30_img_viewer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/4/17.
 */

public class StreamTools {
    //把一个ImputStream 转换成一个String
    public static String readStream(InputStream in) throws IOException {

        //定义一个内存输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024]; // 1kb
        while((len=in.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        in.close();
        String content = new String(baos.toByteArray());
        return content;
    }
}
