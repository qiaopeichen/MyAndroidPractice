package com.example.a10_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_userpassword;
    private CheckBox cb_ischeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1 找到我们关心的控件
        et_name = (EditText) findViewById(R.id.et_username);
        et_userpassword = (EditText) findViewById(R.id.et_userpassword);
        cb_ischeck = (CheckBox) findViewById(R.id.cb_ischeck);
        //1.1 读取data/data下 info.txt信息
        Map<String, String> maps = UserInfoUtils.readInfo();
        if (maps != null) {
            //把name和pwd取出来
            String name = maps.get("name");
            String pwd = maps.get("pwd");
            //1.2 把name和pwd显示到edittext控件上
            et_name.setText(name);
            et_userpassword.setText(pwd);
        }
    }

    //2 写按钮的点击事件
    public void login(View v) {
        //2.1 获取用户名和密码
        String name = et_name.getText().toString().trim();
        String pwd = et_userpassword.getText().toString().trim();
        //2.2 判断name和pwd是否为空
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)) {
            Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //2.3 进行登录的逻辑
            System.out.println("连接服务器 进行登录 等第四天 再说");
            if (cb_ischeck.isChecked()) {
                //2.4 把用户名和密码的数据存起来
                boolean result = UserInfoUtils.saveInfo(name, pwd);
                if (result) {
                    Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "请勾选cb", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
