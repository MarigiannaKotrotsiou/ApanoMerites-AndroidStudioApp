package com.example.login_register_db;

public class ImageData {private int imageViewId;
    private Class<?> slideActivityClass;
    private int headingResId;
    private int descriptionResId;

    public ImageData(int imageViewId, Class<?> slideActivityClass, int headingResId, int descriptionResId) {
        this.imageViewId = imageViewId;
        this.slideActivityClass = slideActivityClass;
        this.headingResId = headingResId;
        this.descriptionResId = descriptionResId;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public Class<?> getSlideActivityClass() {
        return slideActivityClass;
    }

    public int getHeadingResId() {
        return headingResId;
    }

    public int getDescriptionResId() {
        return descriptionResId;
    }
}

