package com.example.mkitab.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mkitab.R;
import com.example.mkitab.databinding.ActivityVolumesBinding;
import com.example.mkitab.viewmodel.VolumesModel;

public class VolumesActivity extends AppCompatActivity {


    private VolumesModel volumesModel;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        init();
    }

    private void init() {
        ActivityVolumesBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_volumes);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        volumesModel = viewModelProvider.get(VolumesModel.class);
        VolumesRecyclerAdapter volumesRecyclerAdapter = new VolumesRecyclerAdapter(this);
        volumesModel.setAdapter(volumesRecyclerAdapter, id);
        viewDataBinding.volumes.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        viewDataBinding.volumes.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.volumes.setAdapter(volumesRecyclerAdapter);

        volumesModel.loadData();
    }
}
