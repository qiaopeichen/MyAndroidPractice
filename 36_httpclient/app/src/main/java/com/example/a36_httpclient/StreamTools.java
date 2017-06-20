package com.example.a36_httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/4/20.
 */

public class StreamTools {

    //把一个inputstream转换成string
    public String readStream(InputStream in) throws IOException {

            int len = -1;
            byte[] buffer = new byte[1024]; //1kb
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len=in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            String content = new String(baos.toByteArray(), "gbk");
            return content;
    }
}
