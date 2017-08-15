package com.itheima.mobilesafe74.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/8/12.
 */

public class PrefUtils {
    private static SharedPreferences sp;

    // 1. 存储boolean变量方法
    public static void putBoolean(Context ctx, String key, boolean value) {
        // name 存储文件名称
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }
    // 2. 读取boolean变量方法
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        // name 存储文件名称
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }
    // 3. 存储String变量方法
    public static void putString(Context ctx, String key, String value) {
        // name 存储文件名称
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }
    // 4. 读取String变量方法
    public static String getString(Context ctx, String key, String defValue) {
        // name 存储文件名称
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }
}
