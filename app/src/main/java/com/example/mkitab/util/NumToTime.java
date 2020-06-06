package com.example.mkitab.util;

public class NumToTime {
    public static final String getTimeFromNum(int num) {
        int s = num / 1000;
        int h = s / 3600;
        int m = s % 3600 / 60;
        int second = s % 3600 % 60;
        String zero = "";
        if (second < 10) zero = "0";
        return (h > 0 ? h + " : " : "") + ((m > 0 ? m + " : " : "00 : ")) + zero + second;
    }
}
