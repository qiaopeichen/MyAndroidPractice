package com.example.a11_use_context;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/13.
 */

public class UserInfoUtils {
    // 保存用户名和密码的业务方法
    public static boolean saveInfo(Context context, String username, String pwd) {

        try {
            String result = username + "##" + pwd;

            //2 通过上下文获取FileOutputStream
            FileOutputStream fos = context.openFileOutput("infoo.txt", Context.MODE_PRIVATE);

            fos.write(result.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 读取用户的信息
    public static Map<String ,String> readInfo(Context context) {
        try {
            //1 定义Map
            Map<String, String> maps = new HashMap<String, String>();
            File file = new File("data/data/com.example.a10_login/info.txt");
            FileInputStream fis = context.openFileInput("infoo.txt");
            BufferedReader bufr = new BufferedReader(new InputStreamReader(fis));
            String content = bufr.readLine(); // 读取数据

            // 切割字符串 封装到map集合中
            String[] splits = content.split("##");
            String name = splits[0];
            String pwd = splits[1];
            //　把name 和 pwd 放入map中
            maps.put("name", name);
            maps.put("pwd", pwd);
            fis.close();
            return maps;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

