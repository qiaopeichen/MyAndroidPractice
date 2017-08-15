package com.itheima.mobilesafe74.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.Md5Util;
import com.itheima.mobilesafe74.utils.PrefUtils;
import com.itheima.mobilesafe74.utils.ToastUtil;

import org.xutils.common.util.MD5;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_home;
    private int[] mDrawableIds;
    private String[] mDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        initUI();
        initData();
    }

    private void initData() {
        //gv_home在内部需要去填充数据，形成多个方格
        // 1.拿到GridView对象
        // 2.给数据适配器去准备数据，集合，数组去做填充
        // 3.把数据填充到GridView数据适配器中，用作展示
        // 初始化图片数据
        mDrawableIds = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan,
                R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};
        mDes = new String[]{"手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置"};
        gv_home.setAdapter(new MyAdapter());
        // 设置一个条目的点击事件
        gv_home.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点中条目的索引值
                switch (position) {
                    case 0:
                        showDialog();
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), BlackNumberActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), AppManagerActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), ProgressManagerActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(), TrafficActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getApplicationContext(), AntiVirusActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getApplicationContext(), BaseClearCacheActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getApplicationContext(), AToolActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;
                }
            }
        });
    }

    private void showDialog() {
        String psd = PrefUtils.getString(this, ConstantValue.SET_PSD, "");
        if (TextUtils.isEmpty(psd)) {
            // 1.初次进入设置密码对话框
            showSetPsdDialog();
        } else {
            showConfirmPsdDialog();
        }
    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog() {
        // 对话框和activity绑定，所以必须传递activity对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        //获取对话框对象
        final AlertDialog dialog = builder.create();
        // 修改对话框的样式（布局结构）
        View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
        dialog.setView(view, 0, 0, 0, 0);
        // 找到对话框中所有控件
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        final EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmPsd = Md5Util.encoder(et_confirm_psd.getText().toString().trim());
                String spPsd = PrefUtils.getString(getApplicationContext(), ConstantValue.SET_PSD, "");
                if (!TextUtils.isEmpty(confirmPsd)) {
                    if (spPsd.equals(confirmPsd)) {
                        // 当前的对话框隐藏
                        dialog.dismiss();
                        // 跳转到新的activity界面
                        startActivity(new Intent(getApplicationContext(), MobileSafeActivity.class));
                    } else {
                        ToastUtil.show(getApplicationContext(), "两次密码输入不一致，请重新输入");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(), "请输入密码");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 自定义一个设置密码对话框
     * */
    private void showSetPsdDialog() {
        // 对话框和activity绑定，所以必须传递activity对象
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        // 获取对话框对象
        final AlertDialog dialog = builder.create();
        // 修改对话框的样式（布局结构）
        View view = View.inflate(this, R.layout.dialog_set_psd, null);
        // 因为在2.3.3版本上，系统默认设置内间距，所以需要去除此内间距
        dialog.setView(view, 0, 0, 0, 0);
        // 找到对话框中所有控件
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        final EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
        final EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果用户没有输入两次密码，告知用户输入密码
                String psd = et_set_psd.getText().toString().trim();
                String confirmPsd = et_confirm_psd.getText().toString().trim();
                if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)) {
                    if (psd.equals(confirmPsd)) {
                        //将确认正确的密码存储

                        PrefUtils.putString(getApplicationContext(), ConstantValue.SET_PSD, Md5Util.encoder(psd));
                        // 当前的对话框隐藏
                        dialog.dismiss();
                        // 跳转到新的activity界面
                        startActivity(new Intent(getApplicationContext(), MobileSafeActivity.class));
                    } else {
                        ToastUtil.show(getApplicationContext(), "两次密码输入不一致，请重新输入");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(), "请输入密码");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    private class MyAdapter extends BaseAdapter {
        // 返回ListView中数据条目个数，girdview返回条目（方格）个数
        @Override
        public int getCount() {
            return mDes.length;
        }
        /// 返回position指向当前条目使用对象
        @Override
        public Object getItem(int position) {
            return null;
        }
        // 返回条目索引position
        @Override
        public long getItemId(int position) {
            return position;
        }
        // 返回一个条目对应的view,给其填充上数据
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1.构建一个条目相应布局文件
            View view = View.inflate(getApplicationContext(), R.layout.gridview_item_home, null);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            iv_icon.setBackgroundResource(mDrawableIds[position]);

            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText(mDes[position]);
            // 返回view，代表此view需要去做展示
            return view;
        }
    }
}
