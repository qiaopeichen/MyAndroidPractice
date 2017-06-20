package com.example.a77_readcontent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jojo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Contact> readContacts = ReadContactUtils.readContact(getApplicationContext());

        for (Contact contact : readContacts) {
            Log.d(TAG, "onCreate: contact:" + contact);
        }
    }
}
