package com.example.a91_fragmentdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
//定义Fragment 理解为是Activity的一部分
public class Fragment1 extends Fragment {

    //当系统第一次画ui的时候调用，通过这个方法可以让Fragment显示自己的布局内容
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment 通过打气筒把一个布局转换成一个view对象
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

}
