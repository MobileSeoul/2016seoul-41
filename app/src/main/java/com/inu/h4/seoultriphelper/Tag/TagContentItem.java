package com.inu.h4.seoultriphelper.Tag;

import java.util.ArrayList;

/**
 * Created by MIN on 2016-10-01.
 */

public class TagContentItem {
    private int placeId;
    private int image;
    private String sightName;
    private int recommend;
    private String category; // 대분류(장소의 종류)
    private ArrayList<String> attribute; // 중분류(속성 태그)

    public TagContentItem() {
        attribute = new ArrayList<String>();
    }

    public int getPlaceId() { return placeId; }
    public void setPlaceId(int placeId) { this.placeId = placeId; }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public String getSightName() {
        return sightName;
    }
    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public int getRecommend() {
        return recommend;
    }
    public void setRecommend(int recommend) { this.recommend = recommend; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public ArrayList<String> getAttribute() { return attribute; }
    public void addAttribute(String tag) { attribute.add(tag); }
}
