package com.itheima.mobilesafe74.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;


public class SettingItemView extends RelativeLayout {

//    private static final String TAG = "jojo";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private String mDestitle;
    private String mDesoff;
    private String mDeson;
    private CheckBox cb_box;
    private TextView tv_dex;
    private TextView tv_title;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // xml ->view 将设置界面的一个条目转换成view对象，直接添加到了当前SettingItemView对应的view中
        View.inflate(context, R.layout.setting_item_view, this);
        // 等同于以下代码
        /*View view = View.inflate(context, R.layout.setting_item_view, null);
        * this.addView(view);
        * */
        // 自定义组合控件中的标题描述
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_dex = (TextView) findViewById(R.id.tv_des);
        cb_box = (CheckBox) findViewById(R.id.cb_box);
        // 获取自定义以及原生属性的操作，写在此处，AttributeSet attrs对象中获取
        initAttrs(attrs);

    }

    /**
     * 返回属性集合中自定义属性属性值
     *
     * @param attrs 构造方法中维护好的属性集合
     */
    private void initAttrs(AttributeSet attrs) {
//        // 获取属性的总个数
//        Log.d(TAG, "attrs.getAttributeCount() = " + attrs.getAttributeCount());
//        // 获取属性名称以及属性值
//        for (int i = 0; i < attrs.getAttributeCount(); i++) {
//            Log.d(TAG, "name = " + attrs.getAttributeName(i));
//            Log.d(TAG, "value = " + attrs.getAttributeValue(i));
//            Log.d(TAG, "-------------分割线-------------");
//        }

        //通过命名空间 + 属性名称获取属性值
        mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
        mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
        mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
        tv_title.setText(mDestitle);
//        Log.d(TAG, "initAttrs: " + mDestitle);
//        Log.d(TAG, "initAttrs: " + mDesoff);
//        Log.d(TAG, "initAttrs: " + mDeson);
    }

    /**
     * 判断是否开启的方法
     * @return 返回当前SettingItemView是否选中的状态 true开启（checkbox返回true）
     */
    public boolean isCheck(){
//        由checkbox的选中结果，决定当前条目是否开启
        return cb_box.isChecked();
    }

    public void setCheck(boolean isCheck) {
        // 当前条目在选择的过程中，cb_box选中状态也在跟随(isCheck)变化
        cb_box.setChecked(isCheck);
        if (isCheck) {
            tv_dex.setText(mDeson);
        } else {
            tv_dex.setText(mDesoff);
        }
    }




}
