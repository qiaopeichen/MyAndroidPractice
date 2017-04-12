package com.example.a5_button_fourth_event;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 找到我们关心的控件
        et_number = (EditText) findViewById(R.id.editText1);
        //2 找到按钮
        Button btn = (Button) findViewById(R.id.button1);

    }

    // 按钮的第四种点击事件 声明一个方法 方法名和你要点击的这个按钮 在布局中声明的onClick属性一样
    public void click(View v) {
       callPhone();
    }

    private void callPhone() {
        String number = et_number.getText().toString().trim();
        if ("".equals(number)) {
            Toast.makeText(MainActivity.this, "number不能为空", 1).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }
}
