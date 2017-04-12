package com.example.a2_phonedial;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        btn_call.setOnClickListener(new MyClickListener());

    }

    //定义一个类去实现按钮需要的接口类型
    private class MyClickListener implements View.OnClickListener {
        //当按钮被点击的时候调用
        @Override
        public void onClick(View v) {
            //4 获取edittext的内容
            String number = et_number.getText().toString().trim();
            if ("".equals(number)) {
                /**
                 * context 上下文
                 */
                Toast.makeText(MainActivity.this, "number不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            //5 进行拨打电话 意图 intent
            Intent intent = new Intent();//创建一个意图对象 打电话
            //5.1 设置动作
            intent.setAction(Intent.ACTION_DIAL);
            //5.2 设置要拨打的数据
            /**
             * uri:统一资源标识符 自己定义的路径 想代表什么就代表什么
             * url:统一资源定位符 www.baidu.com
             */
            intent.setData(Uri.parse("tel:"+number));
            //开启意图
            startActivity(intent);
        }
    }
}
