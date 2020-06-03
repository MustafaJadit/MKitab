package com.example.mkitab.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mkitab.R;
import com.example.mkitab.util.MLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MLog.log("this is mainActivity", getClass());
    }
}
