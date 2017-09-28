package com.galaxy.gallery.j7gallery.objects;

import java.util.ArrayList;

/**
 * Created by huand on 9/21/2017.
 */

public class AlbumTime {
    private String timeTitle;
    private ArrayList<Image> arrImage;

    public AlbumTime(String timeTitle, ArrayList<Image> arrImage) {
        this.timeTitle = timeTitle;
        this.arrImage = arrImage;
    }

    public void addImage(Image image) {
        arrImage.add(image);
    }

    public String getTimeTitle() {
        return timeTitle;
    }

    public void setTimeTitle(String timeTitle) {
        this.timeTitle = timeTitle;
    }

    public ArrayList<Image> getArrImage() {
        return arrImage;
    }

    public void setArrImage(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }
}
