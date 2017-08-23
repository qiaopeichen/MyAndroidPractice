package com.itheima.mobilesafe74.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/14.
 * 能够获取焦点的自定义TextView
 */

public class FocusTextView extends android.support.v7.widget.AppCompatTextView{

    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 重写获取焦点的方法，由系统调用，调用的时候默认就能获取焦点




    @Override
    public boolean isFocused() {
        return true;
    }
}
