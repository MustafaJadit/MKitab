package com.example.mkitab.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.mkitab.MApplication;
import com.example.mkitab.model.Networking;
import com.example.mkitab.model.entity.AllBooks;
import com.example.mkitab.ui.AllBooksRecyclerAdapter;
import com.example.mkitab.util.MLog;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private AllBooksRecyclerAdapter adapter;

    public void setAdapter(AllBooksRecyclerAdapter adapter) {
        this.adapter = adapter;
    }


    // fetch data using old native way
    public void fetchData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = "http://172.104.143.75:8000/awazliq_kitab/mainlist/";
                try {
                    URL url = new URL(s);
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.connect();

//                      getting inputStream. general approach to get data
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder result = new StringBuilder();
                    String singleLine = "";

                    while ((singleLine = bufferedReader.readLine()) != null) {
                        result.append(singleLine).append("\n");
                    }


                    MLog.log(result.toString() + " from fetchData");


                    //if we knew the returned type, we can concert it to a specific data type
                    // (usually return InputStream)
                    // so getInputStream and getContent are the same
                    InputStream content = (InputStream) urlConnection.getContent();
                    JsonReader jsonReader = new JsonReader(new InputStreamReader(content));
                    MLog.log(content.toString());
                    String contentType = urlConnection.getContentType();
                    MLog.log(contentType);
                    String contentEncoding = urlConnection.getContentEncoding();
                    MLog.log("contentEncoding " + contentEncoding);
                    Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                    MLog.log(headerFields.toString());


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }).start();


    }


    public void loadData() {
        Networking instance = MApplication.getNetworking();
        instance.getAllBooks(new Callback<AllBooks>() {
            @Override
            public void onResponse(Call<AllBooks> call, Response<AllBooks> response) {
                AllBooks body = response.body();
                adapter.update(body);
            }

            @Override
            public void onFailure(Call<AllBooks> call, Throwable t) {

            }
        });
    }

}
