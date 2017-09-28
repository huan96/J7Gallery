package com.galaxy.gallery.j7gallery.objects;


import android.graphics.Bitmap;

import java.util.Objects;

public class Image {
    private String date;
    private String bucket;
    private String path;
    private String width;
    private String height;
    private String size;
    private Bitmap imgEdited;
    private boolean isClicked;
    private boolean isAdded;

    public Image(String date, String bucket, String path, String width, String height, String size) {
        this.date = date;
        this.bucket = bucket;
        this.path = path;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public Bitmap getImgEdited() {
        return imgEdited;
    }

    public void setImgEdited(Bitmap imgEdited) {
        this.imgEdited = imgEdited;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(path, image.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
