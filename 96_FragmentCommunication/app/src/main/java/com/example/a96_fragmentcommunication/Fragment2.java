package com.example.a96_fragmentcommunication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private TextView tv_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, null);
        //1 找到tv
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }
    // 修改TextView 的内容
    public void updateText(String content) {
        tv_content.setText(content);
    }
}
