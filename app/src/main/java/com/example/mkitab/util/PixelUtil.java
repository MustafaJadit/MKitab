package com.example.mkitab.util;

import android.content.res.Resources;

public class PixelUtil {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
