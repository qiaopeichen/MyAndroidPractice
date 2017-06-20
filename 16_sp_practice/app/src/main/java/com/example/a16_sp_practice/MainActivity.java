package com.example.a16_sp_practice;

import android.content.SharedPreferences;
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
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //0 先初始化sp的实例
        /**
         * name 会帮助我们生成一个xml文件
         * mode 模式 默认就是0 pravtive
         */
        sp = getSharedPreferences("config", 0);

        //1 找到我们关心的控件
        et_name = (EditText) findViewById(R.id.et_username);
        et_userpassword = (EditText) findViewById(R.id.et_userpassword);
        cb_ischeck = (CheckBox) findViewById(R.id.cb_ischeck);
        //2 在config.xml文件中把我们关心的数据取出来，然后显示到edittext控件上
        String name = sp.getString("name", "");
        String pwd = sp.getString("pwd", "");
        //3 把 name 和 pwd 设置到edittext上
        et_name.setText(name);
        et_userpassword.setText(pwd);

        // checkbox状态逻辑
        int flag = sp.getInt("flag", 0);
        if (flag==1) {
            cb_ischeck.setChecked(true);
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
                //2.4 使用SharedPreferences去保存数据 拿到sp的实例

                //2.5 获取sp的编辑器
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("name", name);
                edit.putString("pwd", pwd);
                edit.putInt("flag", 1);
                //2.6 记得把edit进行提交
                edit.commit();
            } else {
                Toast.makeText(MainActivity.this, "请勾选cb", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
