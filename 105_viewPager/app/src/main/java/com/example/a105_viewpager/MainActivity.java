package com.example.a105_viewpager;

import android.animation.ObjectAnimator;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private static final String TAG = "jojo";

    boolean isRunning = false;
    private ViewPager viewPager;
    private LinearLayout ll_point_cotainer;
    private TextView tv_desc;
    private String[] contentDescs;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private int previousSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化布局 View视图
        initViews();

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();

        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: 设置当前位置：" + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }


    private void initAdapter() {
        ll_point_cotainer.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 =  1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3  指定复用的判断逻辑，固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//            Log.d(TAG, "isViewFromObject: " + (view == object));
            // 当滑到新的条目，又返回来，view是否可以被复用
            // 返回判断规则
            return view == object;
        }

        // 1 返回要显示的条目内容，创建条目  instantiate实例化
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d(TAG, "instantiateItem: 初始化" + position);
            // container 容器 viewpager
            // position 当前要显示条目的位置 0 -> 4

//            new Position = position % 5;
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架，适配器
            return imageView; // 必须重写，否则报异常
        }

        // 2 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            Log.d(TAG, "destroyItem: 销毁" + position);
            container.removeView((View)object);
        }
    }

    private void initData() {

        // 舒适化要显示的数据

        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述

        contentDescs = new String[] {
                "巩俐不低俗，我就不能低俗",
                "朴树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<>();
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点，指示器
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(5,5);
            if (i != 0)
                layoutParams.leftMargin = 10;

            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_cotainer.addView(pointView, layoutParams);
        }
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this); // 设置页面更新监听
        viewPager.setOffscreenPageLimit(1); // 左右各保留几个对象
        ll_point_cotainer = (LinearLayout) findViewById(R.id.ll_point_container);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 滚动时调用

    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        Log.d(TAG, "onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        // 设置文本
        tv_desc.setText(contentDescs[newPosition]);

//        for (int i = 0; i < ll_point_cotainer.getChildCount(); i++) {
//            View childAt = ll_point_cotainer.getChildAt(position);
//            childAt.setEnabled(position == i);
//        }
        // 把之前的禁用，把最新的启用，更新指示器
        ll_point_cotainer.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_cotainer.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }


}
