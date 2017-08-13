package com.itheima.mobilesafe74.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/13.
 */

public class ToastUtil {
    public static void show(Context ctx, String text) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
    }
}
