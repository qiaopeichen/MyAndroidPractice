package com.example.a96_fragmentcommunication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
// 定义Fragment 理解为 是Activity的一部分
public class Fragment1 extends Fragment {

    // 当系统第一次画UI的时候调用 通过这个方法可以让Fragment显示自己的布局内容
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 通过打气筒 把一个布局转换成一个View对象
        View view = inflater.inflate(R.layout.fragment_fragment1, null);

        //1 找到按钮 设置点击事件
        Button btn_update = (Button) view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2 修改fragment2里面的内容，通过fragment的公共桥梁--->Activity
                Fragment2 fragment2 = (Fragment2) getActivity().getSupportFragmentManager().findFragmentByTag("f2");
                fragment2.updateText("haha");
            }
        });
        return view;
    }
}
