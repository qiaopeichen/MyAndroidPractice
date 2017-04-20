package com.example.a34_custom_smartimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySmartImageView iv = (MySmartImageView) findViewById(R.id.iv);
        iv.setImageUrl("https://www.baidu.com/img/270_36f64e1b77eb344d543d6e20029dbb92.png", R.mipmap.ic_launcher);
    }
}
