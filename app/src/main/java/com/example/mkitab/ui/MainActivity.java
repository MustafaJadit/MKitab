package com.example.mkitab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mkitab.R;
import com.example.mkitab.databinding.ActivityMainBinding;
import com.example.mkitab.util.MLog;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = new MainViewModel(this);
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        viewModel = viewModelProvider.get(MainViewModel.class);
        viewModel.addLifecycleToBookName(this);
        viewDataBinding.setVariable1(viewModel);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                viewModel.getmBookName().setValue("2222222");
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, 1000);

        MLog.log("this is mainActivity", getClass());

        test();
    }

    private void test() {

    }
}
