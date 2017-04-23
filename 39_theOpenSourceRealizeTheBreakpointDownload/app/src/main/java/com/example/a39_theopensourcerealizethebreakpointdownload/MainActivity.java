package com.example.a39_theopensourcerealizethebreakpointdownload;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private EditText et_path;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //0 获取我们关心的控件
        et_path = (EditText) findViewById(R.id.et_path);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
    }
    //点击按钮实现断点续传下载逻辑
    public void click(View v) {
        //1 获取下载路径
        String path = et_path.getText().toString().trim();
        //2 创建RequestParams
        RequestParams params = new RequestParams(path);
        //自定义保存路径 Environment.getExternalStorageDirectory(); SD卡的根目录
        File file = new File(Environment.getExternalStorageDirectory()+"/Judy2.mov");
        params.setSaveFilePath(file+"");
        // 自动为文件命名
        params.setAutoRename(false);
        x.http().post(params, new Callback.ProgressCallback<File>() {


            @Override
            public void onWaiting() {
                Toast.makeText(getApplicationContext(), "下载waiting", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStarted() {
                Toast.makeText(getApplicationContext(), "下载开始", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pb.setMax((int) total);
                pb.setProgress((int) current);
            }

            @Override
            public void onSuccess(File result) {
                Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "下载Error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(getApplicationContext(), "下载cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinished() {
                Toast.makeText(getApplicationContext(), "下载Finish", Toast.LENGTH_SHORT).show();

            }


        });
    }
}
