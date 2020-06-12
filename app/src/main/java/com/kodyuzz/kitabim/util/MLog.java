package com.kodyuzz.kitabim.util;

import android.util.Log;

import com.kodyuzz.kitabim.BuildConfig;

public class MLog {
    private static final String TAG = "MLog";
    public static final boolean isDebug = BuildConfig.DEBUG;

    public static void log(String log) {
        if (isDebug) Log.d(TAG, log);
    }

    public static final void log(String log, Class mClass) {
        if (isDebug) Log.d(TAG, mClass.getName() + " " + log);
    }

    public static final void error(String log) {
        if (isDebug) Log.e(TAG, log);
    }

    public static final void error(String log, Class mClass) {
        if (isDebug) Log.e(TAG, mClass.getName() + " " + log);
    }
}
