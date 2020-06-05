package com.example.mkitab.model;

import com.example.mkitab.model.entity.AllBooks;
import com.example.mkitab.model.entity.Volumes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MRepositories {

    @GET("/awazliq_kitab/mainlist/")
    Call<AllBooks> getAllBooks();

    @GET("/awazliq_kitab/awazlar/{id}")
    Call<List<Volumes>> getVolumes(@Path("id") String id);
}
