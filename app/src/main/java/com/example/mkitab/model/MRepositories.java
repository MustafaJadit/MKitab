package com.example.mkitab.model;

import com.example.mkitab.model.entity.AllBooks;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MRepositories {

    @GET("http://172.104.143.75:8000/awazliq_kitab/mainlist/")
    Call<AllBooks> getAllBooks();
}
