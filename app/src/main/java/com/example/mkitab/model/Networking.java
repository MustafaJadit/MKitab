package com.example.mkitab.model;

import com.example.mkitab.model.entity.AllBooks;
import com.example.mkitab.model.entity.Volumes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Networking {

    public static final String BASE_URL = "http://www.awazliqkitap.com";
    public static final String NUMERAL_URL = "http://172.104.143.75:8000";
    MRepositories mRepositories;
    static Networking instance;

    public static Networking getInstance() {
        if (instance == null) {
            instance = new Networking();
        }

        return instance;
    }

    private Networking() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NUMERAL_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        mRepositories = retrofit.create(MRepositories.class);
    }

    public void getAllBooks(Callback<AllBooks> allBooksCallback) {
        mRepositories.getAllBooks().enqueue(allBooksCallback);
    }


    public void getVolumes(String id, Callback<List<Volumes>> volumesCallback) {
        mRepositories.getVolumes(id).enqueue(volumesCallback);
    }
}
