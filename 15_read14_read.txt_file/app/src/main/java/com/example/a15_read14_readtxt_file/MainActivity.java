package com.example.a15_read14_readtxt_file;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            File file = new File("data/data/com.example.a14_file_permission/files/write.txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bufr = new BufferedWriter(new OutputStreamWriter(fos));

            String content = "content";
            bufr.write(content);
            bufr.close();

            Log.w("JustTest", content);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
