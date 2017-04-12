package com.example.a3_button_second_event;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a3_button_second_event.R;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载一个布局
        setContentView(R.layout.activity_main);
        //1 找到我们关心的控件 edittext button
        et_number = (EditText) findViewById(R.id.editText1);
        //2 找到按钮
        Button btn_call = (Button) findViewById(R.id.button1);
        //3 给button按钮设置一个点击事件
        //**** 按钮的第二种点击事件 匿名内部类的方式
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //4 实现拨打电话的逻辑
                callPhone();
            }
            
        });

    }

    private void callPhone() {
        //4 获取edittext文本内容
        String number = et_number.getText().toString().trim();
        if ("".equals(number)) {
            /**
             * context 上下文
             */
            Toast.makeText(MainActivity.this, "number不能为空", 1).show();
            return;
        }
        //5 进行拨打电话 意图 Intent
        Intent intent = new Intent();
        //5.1 设置动作
        intent.setAction(Intent.ACTION_DIAL);
        //5.2 设置要拨打的数据
        intent.setData(Uri.parse("tel:"+number));
        //6 开启意图
        startActivity(intent);
    }


}
