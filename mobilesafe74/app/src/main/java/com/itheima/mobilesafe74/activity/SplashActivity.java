package com.itheima.mobilesafe74.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.webkit.MimeTypeMap;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;
import com.itheima.mobilesafe74.utils.StreamUtil;
import com.itheima.mobilesafe74.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "jojo";
    /**
     * 进入应用程序主界面状态码
     */
    private static final int UPDATE_VERSION = 100;
    private static final int ENTER_HOME = 101;
    private static final int IO_ERROR = 102;
    private static final int URL_ERROR = 103;
    private static final int JSON_ERROR = 104;

    private RelativeLayout rl_root;
    private TextView tv_version_name;
    private String mVersionDes;
    private String mDownloadUrl;
    private Handler mHandler = new Handler() { //member 成员变量
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    // 提示用户更新APK
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUtil.show(getApplicationContext(), "URL异常");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUtil.show(getApplicationContext(), "IO异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(getApplicationContext(), "JSON解析异常");
                    enterHome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 告知此activity没有头
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        // 初始化控件的操作
        initUI();
        // 初始化数据的方法
        initData();
        // 初始化动画，动画执行方法，淡入淡出
        initAnimation();
        //  初始化数据库
        initDB();

        boolean has_short_cut = SpUtil.getBoolean(this, ConstantValue.HAS_SHORT_CUT, false);
        if (!has_short_cut) {
            // 是否生成快捷键的方法
            initShortCut();
        }
    }

    private void initDB() {
        //1.归属地数据拷贝过程
        initAddressDB("address.db");
    }

    /**
     * 拷贝数据库至files文件夹下
     * @param dbName 数据库名称
     */
    private void initAddressDB(String dbName) {
        //1.在files文件夹下创建名为dbName数据库文件过程
        File files = getFilesDir();
        File file = new File(files, dbName);
        if (file.exists()) {
            return;
        }
        InputStream stream = null;
        FileOutputStream fos = null;
        //2.输入流读取第三方资产目录下的文件
        try {
            stream = getAssets().open(dbName);
            //3.将读取到内容写入到指定文件夹的文件中去
            fos = new FileOutputStream(file);
            //4.每次的读取内容大小
            byte[] bs = new byte[1024];
            int temp = -1;
            while((temp = stream.read(bs))!= -1) {
                fos.write(bs, 0, temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null && fos != null) {
                try{
                    stream.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initShortCut() {
        // 快捷方式只生成一个
        // 图片
        // 文字
        // 点击此快捷方式开启的意图

        // 生成快捷方式，其实就是去发送一条广播，上述的内容，都可以封装到广播的Intent中，权限
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 第二个参数，将资源文件中的一张图片转换成Bitmap，作为数据在发送广播的过程中传递出去
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, R.mipmap.ic_launcher);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "手机卫士");

        // 点开快捷方式的意图
        Intent shortCutIntent = new Intent();
        shortCutIntent.setAction("com.itheima.mobilesafe74");
        shortCutIntent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
        sendBroadcast(intent);
        SpUtil.putBoolean(this, ConstantValue.HAS_SHORT_CUT, true);
    }

    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        rl_root.startAnimation(alphaAnimation);
    }

    private void showUpdateDialog() {
        // 对话框依赖于activity
        // 吐司和activity没有关联
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 屏蔽返回按钮，不建议
//        builder.setCancelable(false);
        builder.setTitle("是否更新");
        builder.setIcon(R.mipmap.ic_launcher);
//        描述信息
        builder.setMessage(mVersionDes);
        // 立即更新（积极）
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                下载逻辑, (多线程断点续传) 第三方框架（Xutils）
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消
                dialog.dismiss();
                //依然需要跳转到应用程序主界面
                enterHome();
            }
        });
        builder.show();
    }

    private void enterHome() {
        //activity跳转
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void downloadApk() {
        RxPermissions rxPermissions = new RxPermissions(this);
        // 动态获取运行时权限
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + "mobilesafe69.apk";
                            RequestParams params = new RequestParams(mDownloadUrl);
                            Log.d(TAG, "downloadApk: " + mDownloadUrl);
                            params.setAutoRename(true);//断点下载
                            params.setSaveFilePath(sdPath);
                            x.http().get(params, new Callback.ProgressCallback<File>() {
                                @Override
                                public void onCancelled(CancelledException arg0) {
                                    Log.d(TAG, "onCancelled: 下载取消");
                                }

                                @Override
                                public void onFinished() {
                                    Log.d(TAG, "onFinished: 下载完成");
                                }

                                @Override
                                public void onError(Throwable arg0, boolean arg1) {
                                    Log.d(TAG, "onError: 下载错误");
                                }

                                @Override
                                public void onSuccess(File arg0) {
                                    // TODO Auto-generated method stub
                                    Log.d(TAG, "onSuccess: 下载成功" + arg0);
                                    installApk(arg0);

                                }

                                @Override
                                public void onLoading(long arg0, long arg1, boolean arg2) {
                                    // TODO Auto-generated method stub
                                    Log.d(TAG, "onLoading: 正在下载");
                                }

                                @Override
                                public void onStarted() {
                                    // TODO Auto-generated method stub
                                    Log.d(TAG, "onStarted: 开始下载");
                                }

                                @Override
                                public void onWaiting() {
                                    // TODO Auto-generated method stub
                                    Log.d(TAG, "onWaiting: 等待下载");
                                }
                            });

                        }
                    } else {
                        // Oups permission denied
                        ToastUtil.show(getApplicationContext(), "未得到相应权限");
                    }
                });



    }

    private void installApk(File file) {
        // 通过隐式意图去开启activity
        Log.d(TAG, "installApk: ");
        if (Build.VERSION.SDK_INT < 23) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(
                    file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } else {
            openFile(file, this);
        }

    }

    /**
     * 安装APK，适配android6.0
     */
    public void openFile(File var0, Context var1) {
        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uriForFile = FileProvider.getUriForFile(var1, var1.getApplicationContext().getPackageName() + ".provider", var0);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
        } else {
            var2.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
        }
        try {
            var1.startActivity(var2);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(var1, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }
    }

    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {

        tv_version_name.setText("版本名称：" + getVersionName());
        // 获取本地的版本号，检测升级（本地版本号<服务器版本号，服务器有一个更高版本的应用）
        // 数据的传递
        // 客户端：发送http请求，http://www.oxx.com/index.jsp?key==value
        // 服务器：在接受到请求以后，给客户端发送数据，（json,xml），json数据从数据库中读取出来，读取数据拼接json,语法规则，结构
        // 服务端返回字段
        // 1 新版本应用描述
        // 2 新版本版本号（本地版本比大小）
        // 3 新版本版本名称
        // 4 新版本apk下载地址
        /*
            {}:json对象，json对象中可以包含多个字段，字段的值，就是在客户端上要去显示的文字以及应用的下载地址
            {
                key:value,
                key:value,
                key:value
            }
         */
        boolean is_Update = SpUtil.getBoolean(this, ConstantValue.UPDATE_VERSION, true);
        if (is_Update) {
            checkVersion();
        } else {
            // 延时跳转到主界面过程，消息机制
//            mHandler.sendEmptyMessage(0);
            // 通过消息机制延时处理消息，在4秒后处理消息的时候，去跳转到主界面
            //1. 延时消息类型状态码， 2.延时处理此消息的时间
            mHandler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
        }

    }

    /**
     * 通过版本号，检测版本更新
     */
    private void checkVersion() {
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    // 1. 获取访问网络Url地址， http://10.0.2.2/update69.json
                    URL url = new URL("http://10.0.2.2/update69.json");
                    // 2.在此url地址上去开启一个连接请求
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 3.请求的配置信息
                    connection.setConnectTimeout(2000);
                    connection.setReadTimeout(2000);
                    // 4.通过服务端返回的响应码，判断是否请求成功
                    if (connection.getResponseCode() == 200) {
                        // 5.读取服务器端返回的数据，流
                        InputStream inputStream = connection.getInputStream();
                        // 6.流转换成字符串，常见，工具类
                        String jsonStr = StreamUtil.stream2String(inputStream);
                        Log.d(TAG, "jsonstr = " + jsonStr);
                        // 7.json解析，看见什么就解析什么，参数就是要去解析的json字符串
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        // 8.逐个解析字段
                        String versionName = jsonObject.getString("versionName");
                        String versionCode = jsonObject.getString("versionCode");
                        mVersionDes = jsonObject.getString("versionDes");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        // 9.本地版本号<服务端版本号，比较大小，提示用户有更新版本的apk可下载
                        int localVersionCode = getVersionCode();
                        int remoteVersionCode = Integer.parseInt(versionCode);
                        if (localVersionCode < remoteVersionCode) {
                            // 提示用户更新 ctrl + shift + u 切换大小写
                            msg.what = UPDATE_VERSION;
                        } else {
                            // 进入应用的主界面
                            msg.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    // 进入应用主界面
                    msg.what = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
                } finally {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime < 4000) {
                        // 睡眠3秒
                        try {
                            Thread.sleep(4000 - (endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * @return 返回版本号，如果返回0表示获取版本号异常
     */
    private int getVersionCode() {
        // 获取版本号，PackageManager->PackageInfo->versionName,versionCode
        // 1.创建PackageManager对象
        PackageManager packageManager = getPackageManager();
        // 2.获取PackageInfo对象（包名，0获取PackageInfo对象）
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    public String getVersionName() {
        // 获取版本号，PackageManager->PackageInfo->versionName,versionCode
        // 1. 创建PackageManager对象
        PackageManager pm = getPackageManager();
        // 2. 获取PackageInfo对象（包名，0获取PackageInfo对象）
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            // 3. 获取版本名称
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}