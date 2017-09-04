package com.itheima.mobilesafe74.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.engine.SmsBackUp;
import com.itheima.mobilesafe74.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

public class AToolActivity extends AppCompatActivity {

    private TextView tv_query_phone_address;
    private TextView tv_sms_backup;
    private ProgressBar pb_bar;
    private TextView tv_commonnumber_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);
        // 电话归属地查询方法
        initPhoneAddress();
        // 短信备份方法
        initSmsBackUp();
        // 常用号码查询
        initCommonNumberQuery();
    }

    private void initCommonNumberQuery() {
        tv_commonnumber_query = (TextView) findViewById(R.id.tv_commonnumber_query);
        tv_commonnumber_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    private void initSmsBackUp() {
        tv_sms_backup = (TextView) findViewById(R.id.tv_sms_backup);
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        tv_sms_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(AToolActivity.this);
                rxPermissions
                        .request(Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                showSmsBackUpDialog();
                            } else {
                                ToastUtil.show(getApplicationContext(), "需要开启相应权限");
                            }
                        });


            }
        });
    }

    private void showSmsBackUpDialog() {
        // 1.创建一个带进度条的对话框
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("短信备份");
        // 2.指定进度条的样式为水平
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 3.展示进度条
        progressDialog.show();
        // 4.直接调用备份短信方法即可
        new Thread() {
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "sms74.xml";
                SmsBackUp.backup(getApplicationContext(), path, new SmsBackUp.CallBack() {
                    @Override
                    public void setMax(int max) {
                        // 由开发者自己决定，使用对话框还是进度条
                        progressDialog.setMax(max);
                        pb_bar.setMax(max);
                    }

                    @Override
                    public void setProgress(int index) {
                        progressDialog.setProgress(index);
                        pb_bar.setProgress(index);
                    }
                });
                progressDialog.show();
            }
        }.start();
    }

    private void initPhoneAddress() {
        tv_query_phone_address = (TextView) findViewById(R.id.tv_query_phone_address);
        tv_query_phone_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QueryAddressActivity.class));
            }
        });
    }
}
