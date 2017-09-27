package com.inu.h4.seoultriphelper.Bucket;

import android.graphics.Bitmap;

public class BucketGridViewItem {
    private int id;
    private Bitmap bitmap;
    private int recommend;
    private String sightName;
    private double coordinate_x;
    private double coordinate_y;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getRecommend() {
        return recommend;
    }
    public void setRecommend(int recommend) { this.recommend = recommend; }

    public String getSightName() {
        return sightName;
    }
    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public double getCoordinate_x(){return coordinate_x;}
    public void setCoordinate_x(double coordinate_x){this.coordinate_x = coordinate_x;}

    public double getCoordinate_y(){return coordinate_y;}
    public void setCoordinate_y(double coordinate_y){this.coordinate_y=coordinate_y;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}