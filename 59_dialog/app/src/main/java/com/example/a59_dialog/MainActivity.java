package com.example.a59_dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "jojo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //点击按钮 弹出一个普通对话框
    public void click1(View v) {
        //通过builder 构建器来构造
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("世界上最遥远的距离是没有网络");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: 点击了确定按钮");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: 点击了取消按钮");
            }
        });
        // 最后一步 一定要记得和Toast一样 show出来
        builder.show();
    }

    //点击按钮 弹出一个单选对话框
    public void click2(View v) {
        //通过builder 构建器来构造
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择您喜欢的课程");
        final String items[] = {"Android","ios","c","c++","html","c#"};
        // -1代表没有条目被选中
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //1 把选中的条目给取出来
                String item = items[which];
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                //2 把对话框关闭   dismiss解雇
                dialog.dismiss();
            }
        });
        //最后一步 一定要记得 和Toast一样 show出来
        builder.show();
    }

    //点击按钮 弹出一个多选对话框
    public void click3(View v) {
        //通过builder 构建器来构造
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择您喜欢吃的水果");
        final String items[] = {"香蕉","黄瓜","哈密瓜","西瓜","梨","柚子","榴莲"};
        final boolean[] checkedItems = {true,false,false,false,false,false,true};
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //把选中的条目取出来
                StringBuffer  sb= new StringBuffer();
                for (int i = 0; i < checkedItems.length; i++) {
                    //判断一下 选中的
                    if (checkedItems[i]) {
                        String fruit = items[i];
                        sb.append(fruit + " ");
                    }
                }
                Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                //关闭对话框
                dialog.dismiss();
            }
        });
        //最后一步 一定要记得 和Toast一样 show出来
        builder.show();
    }
    // 点击按钮 弹出一个进度条对话框
    public void click4(View v) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("正在玩命加载ing");
        //设置一下进度条的样式
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //最后一步一定要记得show出来
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                //设置进度条的最大值
                dialog.setMax(100);
                //设置当前进度
                for (int i = 0; i <= 100; i++) {
                    dialog.setProgress(i);
                    //睡眠一会
                    SystemClock.sleep(50);
                }
            }
        }.start();

    }

}
