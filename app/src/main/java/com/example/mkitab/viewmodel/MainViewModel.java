package com.example.mkitab.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mkitab.util.MLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainViewModel extends ViewModel {

    private ObservableField<String> bookName;
    private MutableLiveData<String> mBookName;

    public MainViewModel() {
        bookName = new ObservableField<>();
        mBookName = new MutableLiveData<>();
        mBookName.setValue("11111");
    }

    public void addLifecycleToBookName(LifecycleOwner lifecycleOwner) {
        mBookName.observe(lifecycleOwner, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                bookName.set(s);
            }
        });
    }

    public ObservableField<String> getBookName() {
        return bookName;
    }

    public void setBookName(ObservableField<String> bookName) {
        this.bookName = bookName;
    }

    public MutableLiveData<String> getmBookName() {
        return mBookName;
    }

    public void setmBookName(MutableLiveData<String> mBookName) {
        this.mBookName = mBookName;
    }

    public void fetchData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = "https://www.google.com";
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
//                    Object content = urlConnection.getContent();
//                    long date = urlConnection.getDate();
//                    MLog.log(content.toString());
//                    MLog.log(date + "");
//                    String contentType = urlConnection.getContentType();
//                    MLog.log(contentType);
//                    String contentEncoding = urlConnection.getContentEncoding();
//                    MLog.log("contentEncoding " + contentEncoding);
//                    Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
//                    MLog.log(headerFields.toString());


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
