package com.example.mkitab.ui;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.mkitab.R;
import com.example.mkitab.databinding.ActivityMainBinding;
import com.example.mkitab.util.MLog;
import com.example.mkitab.viewmodel.MainViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        viewModel.fetchData();
        MLog.log("this is mainActivity", getClass());

        test();
    }


    private void init() {
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        viewModel = viewModelProvider.get(MainViewModel.class);
        viewModel.addLifecycleToBookName(this);
        viewDataBinding.setVariable1(viewModel);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getmBookName().setValue("2222222");
            }
        }, 1000);
    }

    private void test() {

    }
}
