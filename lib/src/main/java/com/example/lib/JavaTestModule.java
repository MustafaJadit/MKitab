package com.example.lib;

import java.io.File;
import java.io.IOException;

public class JavaTestModule {
    public static void main(String[] args) {
        File file = new File("D:\\a\\b\\c");
        if (file.exists()) {
            System.out.println("exist");
        }else {
            System.out.println("not exist");
            file.mkdirs();
            file=new File(file.getAbsolutePath()+"\\d.txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}