package com.example.mkitab.ui;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

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
}
