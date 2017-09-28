package com.galaxy.gallery.j7gallery.objects;

import java.util.ArrayList;

/**
 * Created by Peih Gnaoh on 8/20/2017.
 */

public class Album {
    private String bucket;
    private String pathFirstImage;
    private ArrayList<Image> arrImage;
    private boolean isClicked;

    public Album(String bucket, String pathFirstImage, ArrayList<Image> arrImage) {
        this.bucket = bucket;
        this.pathFirstImage = pathFirstImage;
        this.arrImage = arrImage;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public void addImage(Image image) {
        arrImage.add(image);
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPathFirstImage() {
        return pathFirstImage;
    }

    public void setPathFirstImage(String pathFirstImage) {
        this.pathFirstImage = pathFirstImage;
    }

    public ArrayList<Image> getArrImage() {
        return arrImage;
    }

    public void setArrImage(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }
}
