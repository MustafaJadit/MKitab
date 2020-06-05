package com.example.mkitab.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.mkitab.MApplication;
import com.example.mkitab.model.Networking;
import com.example.mkitab.model.entity.Volumes;
import com.example.mkitab.ui.VolumesRecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolumesModel extends ViewModel {

    private VolumesRecyclerAdapter adapter;
    private String id;

    public void setAdapter(VolumesRecyclerAdapter adapter, String id) {
        this.adapter = adapter;
        this.id = id;
    }


    public void loadData() {
        Networking instance = MApplication.getNetworking();
        instance.getVolumes(id, new Callback<List<Volumes>>() {
            @Override
            public void onResponse(Call<List<Volumes>> call, Response<List<Volumes>> response) {
                List<Volumes> body = response.body();
                adapter.update(body);
            }

            @Override
            public void onFailure(Call<List<Volumes>> call, Throwable t) {

            }
        });
    }
}
