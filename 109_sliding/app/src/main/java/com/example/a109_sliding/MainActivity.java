package com.example.a109_sliding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.example.a109_sliding.ui.SlideMenu;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private SlideMenu sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        sm = (SlideMenu) findViewById(R.id.sm);
        findViewById(R.id.ib_back).setOnClickListener(this);
    }

    public void onTabClick(View view) {

    }

    @Override
    public void onClick(View v) {

    }
}
