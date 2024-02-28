package com.example.login_register_db;

public class MyTask {

    private String title;
    private String descr;
    private int image;

    public MyTask(String title, String descr, int image) {
        this.title = title;
        this.descr = descr;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
