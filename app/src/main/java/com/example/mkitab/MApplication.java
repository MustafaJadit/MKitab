package com.example.mkitab;

import android.app.Application;

import com.example.mkitab.model.Networking;

public class MApplication extends Application {
    private static final String TAG = "MApplication";
    private static Networking networking;

    @Override
    public void onCreate() {
        super.onCreate();

        networking = Networking.getInstance();
    }


    public static Networking getNetworking() {
        return networking;
    }
}
