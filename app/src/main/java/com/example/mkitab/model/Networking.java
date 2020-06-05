package com.example.mkitab.model;

import com.example.mkitab.model.entity.AllBooks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Networking {

    public static final String BASE_URL = "http://www.awazliqkitap.com";
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        mRepositories = retrofit.create(MRepositories.class);
    }

    public void getAllBooks(Callback<AllBooks> allBooksCallback) {
        mRepositories.getAllBooks().enqueue(allBooksCallback);
    }
}
