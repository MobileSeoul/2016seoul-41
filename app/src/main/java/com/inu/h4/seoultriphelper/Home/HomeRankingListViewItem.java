package com.inu.h4.seoultriphelper.Home;

import android.graphics.Bitmap;

public class HomeRankingListViewItem {
    private int placeid;
    private int week_recommend;
    private int month_recommend;
    private int ranking;
    private String sightName;
    private double rating;
    private int recommendCount;
    private Bitmap imageBitmap;

    public int getPlaceid(){ return placeid;}
    public void setPlaceid(int placeid){this.placeid = placeid;}
    public int getRanking() {
        return ranking;
    }
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
    public int getWeek_recommend(){ return week_recommend; }
    public void setWeek_recommend(int week_recommend){ this.week_recommend = week_recommend; }
    public int getMonth_recommend(){ return month_recommend; }
    public void setMonth_recommend(int month_recommend){ this.month_recommend = month_recommend; }
    public String getSightName() {
        return sightName;
    }
    public void setSightName(String sightName) {
        this.sightName = sightName;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public int getRecommendCount() {
        return recommendCount;
    }
    public void setRecommendCount(int recommendCount) {
        this.recommendCount = recommendCount;
    }
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}