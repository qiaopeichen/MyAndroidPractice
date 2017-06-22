package com.example.a107_toggleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a107_toggleview.ui.ToggleView;

public class MainActivity extends AppCompatActivity {

    private ToggleView toggleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleView = (ToggleView) findViewById(R.id.toggleView);
            toggleView.setSwitchBackgroundResource(R.drawable.switch_background);
            toggleView.setSlideButtonResource(R.drawable.slide_button);
            toggleView.setSwitchState(false);

        // 设置开关更新监听
        toggleView.setOnSwitchStateUpdateListener(new ToggleView.OnSwitchStateUpdateListener() {
            @Override
            public void onStateUpdate(boolean state) {
                Toast.makeText(getApplicationContext(), "state:" +state, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
