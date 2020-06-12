package com.kodyuzz.kitabim;

import android.app.Application;

import com.kodyuzz.kitabim.model.Networking;

public class MyApplication extends Application {
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
