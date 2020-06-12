package com.kodyuzz.kitabim.model.entity;

public class Volumes {

    /**
     * id : 3242
     * path : http://www.ewlad.biz/awazliqkitap1/HAYWANATLAR_QORUQI/00_aptur.mp3
     * title : ھايۋانلار قورۇقى (ئاپتورى: جورج ئورۋېل):0
     * ret : 0
     * book : 84
     */

    private int id;
    private String path;
    private String title;
    private int ret;
    private int book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }
}
