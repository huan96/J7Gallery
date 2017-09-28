package com.galaxy.gallery.j7gallery.until;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;

import com.galaxy.gallery.j7gallery.objects.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


public class ManagerGalary {
    private static final String TAG = "ManagerGalary";
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<Image> arrImage = new ArrayList<>();
    private Context mContext;
    private int count = 0;

    public ManagerGalary(Context context) {
        this.mContext = context;
        queryGalary();
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public ArrayList<Image> getArrImage() {
        return arrImage;
    }

    public void setArrImage(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }

    public void queryGalary() {
        imagePaths.clear();
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.HEIGHT
        };

// content:// style URI for the "primary" external storage volume
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

// Make the query.
        Cursor cur = mContext.getContentResolver().query(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );
        try {
            if (cur.moveToFirst()) {
                String path;
                String bucket;
                String date;
                String size;
                String width;
                String height;
                int bucketColumn = cur.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                int dateColumn = cur.getColumnIndex(
                        MediaStore.Images.Media.DATE_TAKEN);
                int pathColum = cur.getColumnIndex(
                        MediaStore.Images.Media.DATA);
                int sizeColum = cur.getColumnIndex(
                        MediaStore.Images.Media.SIZE
                );
                int widthColum = cur.getColumnIndex(
                        MediaStore.Images.Media.WIDTH
                );
                int heightColum = cur.getColumnIndex(
                        MediaStore.Images.Media.HEIGHT
                );
                do {
                    // Get the field values
                    bucket = cur.getString(bucketColumn);
                    date = cur.getString(dateColumn);
                    width = cur.getString(widthColum);
                    height = cur.getString(heightColum);
                    path = cur.getString(pathColum);
                    size = cur.getString(sizeColum);
                    if (size == null) {
                        return;
                    }
                    int sizeImage = Integer.parseInt(size);
                    sizeImage /= 1024;
                    String mydate = convertDate(date);
                    count++;
                    if ((new File(path)).exists()) {
                        imagePaths.add(path);
                        arrImage.add(new Image(mydate, bucket, path, width, height, sizeImage + ""));
                    }
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            Log.e("error:", e.toString());
        }

    }

    private String convertDate(String myDate) {
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = null;
        try {
            long millisecond = Long.parseLong(myDate);
            dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    private String convertDate2(String myDate) {
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = null;
        try {
            long millisecond = Long.parseLong(myDate);
            dateString = DateFormat.format("mm:ss", new Date(millisecond)).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public ArrayList<Image> getAllVideo() {
        HashSet<Image> videoItemHashSet = new HashSet<>();
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DURATION};
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String date = convertDate(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)) + "000");
                String duration = convertDate2(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
                videoItemHashSet.add(new Image(date, "", path, "", "", duration));
            } while (cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Image> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }
}
