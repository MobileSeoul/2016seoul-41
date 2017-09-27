package com.inu.h4.seoultriphelper.Tag;

import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by MIN on 2016-10-01.
 */

public class TagContentListItem {
    private int image;
    private String sightName;
    private Button getBucketButton;
    private Button recommendButton;
    private Button moreInfoButton;
    private String locationTypeTag; // 대분류(장소의 종류)
    private ArrayList<String> attribute; // 중분류(속성 태그)

    public TagContentListItem() {
        attribute = new ArrayList<String>();
    }

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
    public Button getGetBucketButton() {
        return getBucketButton;
    }
    public void setGetBucketButton(Button getBucketButton) {
        this.getBucketButton = getBucketButton;
    }
    public Button getRecommendButton() {
        return recommendButton;
    }
    public void setRecommendButton(Button recommendButton) {
        this.recommendButton = recommendButton;
    }
    public Button getMoreInfoButton() {
        return moreInfoButton;
    }
    public void setMoreInfoButton(Button moreInfoButton) {
        this.moreInfoButton = moreInfoButton;
    }

    public String getLocationTypeTag() { return locationTypeTag; }
    public void setLocationTypeTag(String locationTypeTag) { this.locationTypeTag = locationTypeTag; }

    public ArrayList<String> getAttribute() { return attribute; }
    public void addAttribute(String tag) { attribute.add(tag); }
}
