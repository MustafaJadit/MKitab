package com.example.mkitab.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mkitab.R;
import com.example.mkitab.databinding.ActivityMainBinding;
import com.example.mkitab.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        viewModel.loadData();

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
