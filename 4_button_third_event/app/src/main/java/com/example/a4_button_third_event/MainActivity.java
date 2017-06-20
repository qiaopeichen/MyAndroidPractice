package com.example.a4_button_third_event;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载一个布局
        setContentView(R.layout.activity_main);
        //1 找到我们关心的控件
        Button btn_call2 = (Button) findViewById(R.id.button2);
        Button btn_call3 = (Button) findViewById(R.id.button3);
        Button btn_call4 = (Button) findViewById(R.id.button4);

        et_number = (EditText) findViewById(R.id.editText1);
        //2 找到按钮
        Button btn_call = (Button) findViewById(R.id.button1);

        //3 给button按钮设置一个点击事件

        //**** 按钮第三种点击事件
        btn_call.setOnClickListener(this);
        btn_call2.setOnClickListener(this);
        btn_call3.setOnClickListener(this);
        btn_call4.setOnClickListener(this);
    }

    private void callPhone() {
        //4 获取edittext文本内容
        String number = et_number.getText().toString().trim();
        if ("".equals(number)) {
            Toast.makeText(MainActivity.this, "number不能为空", 1).show();
            return;
        }

        //5 进行拨打电话 意图
        Intent intent = new Intent();
        //5.1 设置动作
        intent.setAction(Intent.ACTION_DIAL);
        //5.2 设置要拨打的数据
        intent.setData(Uri.parse("tel:"+number));
        //6 开启意图
        startActivity(intent);
    }
    //当我点击按钮的时候执行
    @Override
    public void onClick(View v) {
        //具体判断点击的是哪个按钮
        switch (v.getId()) {
            case R.id.button1://代表点击了按钮1
                callPhone();
                break;
            case R.id.button2://代表点击了按钮2
                callPhone();
                break;
            case R.id.button3://代表点击了按钮3
                callPhone();
                break;
            case R.id.button4://代表点击了按钮4
                System.out.println("我被点击了");
                break;
            default:
                break;
        }
    }
}
