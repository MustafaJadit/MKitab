package com.example.mkitab.ui;

import android.util.MutableFloat;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel {

    private ObservableField<String> bookName;
    LiveData<String> mBookName;
//    private String bookName;


    public MainViewModel() {
        bookName = new ObservableField<>("1111");
//        bookName = "1111";
    }

    public ObservableField<String> getBookName() {
        return bookName;
    }

    public void setBookName(ObservableField<String> bookName) {
        this.bookName = bookName;
    }


//    public String getBookName() {
//        return bookName;
//    }
//
//    public void setBookName(String bookName) {
//        this.bookName = bookName;
//    }
}
