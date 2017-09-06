package com.itheima.mobilesafe74.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.db.dao.AppLockDao;
import com.itheima.mobilesafe74.db.domain.AppInfo;
import com.itheima.mobilesafe74.engine.AppInfoProvider;

import java.util.ArrayList;
import java.util.List;

public class AppLockActivity extends AppCompatActivity {

    private List<AppInfo> mAppInfoList;
    private List<AppInfo> mUnLockList;
    private List<AppInfo> mLockList;
    private AppLockDao mDao;
    private TranslateAnimation mTranslateAnimation;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 6.接收到消息，填充已加锁和未加锁的数据适配器
            mLockAdapter = new MyAdapter(true);
            lv_lock.setAdapter(mLockAdapter);

            mUnLockAdapter = new MyAdapter(false);
            lv_unlock.setAdapter(mUnLockAdapter);
        }
    };
    private Button bt_unlock;
    private Button bt_lock;
    private LinearLayout ll_unlock;
    private LinearLayout ll_lock;
    private TextView tv_unlock;
    private TextView tv_lock;
    private ListView lv_unlock;
    private ListView lv_lock;
    private MyAdapter mLockAdapter;
    private MyAdapter mUnLockAdapter;

    class MyAdapter extends BaseAdapter {
        private boolean isLock;

        /**
         * @param isLock 用于区分已加锁和未加锁应用的标志，true已加锁数据适配器，false未加锁数据适配器
         */
        public MyAdapter(boolean isLock) {
            this.isLock = isLock;
        }
        @Override
        public int getCount() {
            if (isLock) {
                tv_lock.setText("已加锁应用：" + mLockList.size());
                return mLockList.size();
            } else {
                tv_unlock.setText("未加锁应用：" + mUnLockList.size());
                return mUnLockList.size();
            }
        }

        @Override
        public AppInfo getItem(int position) {
            if (isLock) {
                return mLockList.get(position);
            } else {
                return mUnLockList.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.listview_islock_item, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.iv_lock = (ImageView) convertView.findViewById(R.id.iv_lock);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final AppInfo appInfo = getItem(position);
            final View animationView = convertView;
            holder.iv_icon.setBackgroundDrawable(appInfo.icon);
            holder.tv_name.setText(appInfo.name);
            if (isLock) {
                holder.iv_lock.setBackgroundResource(R.drawable.lock);
            } else {
                holder.iv_lock.setBackgroundResource(R.drawable.unlock);
            }
            holder.iv_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 添加动画效果，动画默认是非阻塞的，所以执行动画的同时，动画一下的代码也会执行
                    animationView.startAnimation(mTranslateAnimation); // 500毫秒
                    // 对动画执行过程做事件监听，监听到动画执行完成后，再去移除集合中的数据，操作数据库，刷新界面
                    mTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (isLock) {
                                // 已加锁---->未加锁过程
                                // 1.已加锁集合删除一个，未加锁集合添加一个，对象就是getItem方法获取的对象
                                mLockList.remove(appInfo);
                                mUnLockList.add(appInfo);
                                // 2.从已加锁的数据库中删除一条数据
                                mDao.delete(appInfo.packagename);
                            } else {
                                // 未加锁---->已加锁过程
                                // 1.已加锁集合添加一个，未加锁集合删除一个，对象就是getItem方法获取的对象
                                mLockList.add(appInfo);
                                mUnLockList.remove(appInfo);
                                // 2.从已加锁的数据库中插入一条数据
                                mDao.insert(appInfo.packagename);
                            }
                            // 3.刷新数据适配器
                            mLockAdapter.notifyDataSetChanged();
                            // 3.刷新数据适配器
                            mUnLockAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            return convertView;
        }
    }
    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        ImageView iv_lock;
    }
    private void initAnimation() {
       mTranslateAnimation = new TranslateAnimation(
               Animation.RELATIVE_TO_SELF, 0,
               Animation.RELATIVE_TO_SELF, 1,
               Animation.RELATIVE_TO_SELF, 0,
               Animation.RELATIVE_TO_SELF, 0);
        mTranslateAnimation.setDuration(500);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);
        initUI();
        initData();
        initAnimation();
    }

    /**
     * 区分已加锁和未加锁应用的集合
     */
    private void initData() {
        new Thread() {
            @Override
            public void run() {
                // 1.获取所有手机的应用
                mAppInfoList = AppInfoProvider.getAppInfoList(getApplicationContext());
                // 2.区分已加锁应用和未加锁应用
                mLockList = new ArrayList<>();
                mUnLockList = new ArrayList<>();
                // 3.获取数据库中已加锁应用包名的集合
                mDao = AppLockDao.getInstance(getApplicationContext());
                List<String> lockPackageList = mDao.findAll();
                for (AppInfo appInfo : mAppInfoList) {
                    // 4.如果循环到的应用的包名，在数据库中，则说明是已加锁应用
                    if (lockPackageList.contains(appInfo.packagename)) {
                        mLockList.add(appInfo);
                    } else {
                        mUnLockList.add(appInfo);
                    }
                }
                // 5.告知主线程，可以使用维护的数据
                mhandler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void initUI() {
        bt_unlock = (Button) findViewById(R.id.bt_unlock);
        bt_lock = (Button) findViewById(R.id.bt_lock);
        ll_unlock = (LinearLayout) findViewById(R.id.ll_unlock);
        ll_lock = (LinearLayout) findViewById(R.id.ll_lock);
        tv_unlock = (TextView) findViewById(R.id.tv_unlock);
        tv_lock = (TextView) findViewById(R.id.tv_lock);
        lv_unlock = (ListView) findViewById(R.id.lv_unlock);
        lv_lock = (ListView) findViewById(R.id.lv_lock);

        bt_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.已加锁列表隐藏，未加锁列表显示
                ll_lock.setVisibility(View.GONE);
                ll_unlock.setVisibility(View.VISIBLE);
                // 2.未加锁变成深色图片，已加锁变成浅色图片
                bt_unlock.setBackgroundResource(R.drawable.tab_left_pressed);
                bt_lock.setBackgroundResource(R.drawable.tab_right_default);
            }
        });
        bt_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.未加锁列表隐藏，已加锁列表显示
                ll_unlock.setVisibility(View.GONE);
                ll_lock.setVisibility(View.VISIBLE);
                // 2.已加锁变成深色图片，未加锁变成浅色图片
                bt_lock.setBackgroundResource(R.drawable.tab_right_pressed);
                bt_unlock.setBackgroundResource(R.drawable.tab_left_default);
            }
        });

    }
}
