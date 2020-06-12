package com.kodyuzz.kitabim.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kodyuzz.kitabim.R;
import com.kodyuzz.kitabim.databinding.ActivityMainBinding;
import com.kodyuzz.kitabim.viewmodel.MainViewModel;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        viewModel.loadData();

        test();
    }

    private void test() {
        System.out.println(getPackageName());
        System.out.println(getPackageCodePath());
        System.out.println(getPackageResourcePath());

        System.out.println(getApplicationInfo().dataDir);
        System.out.println(getFilesDir().getAbsolutePath());


    }


    private void init() {
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        viewModel = viewModelProvider.get(MainViewModel.class);
        AllBooksRecyclerAdapter allBooksRecyclerAdapter = new AllBooksRecyclerAdapter(this);
        viewModel.setAdapter(allBooksRecyclerAdapter);
        viewDataBinding.allbooks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        viewDataBinding.allbooks.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.allbooks.setAdapter(allBooksRecyclerAdapter);


    }

}
