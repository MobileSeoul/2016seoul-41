package com.inu.h4.seoultriphelper.Tag;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by MIN on 2016-10-31.
 */

public class TagGroupListViewItem {
    private String groupName;
    private ArrayList<String> attribute; // 중분류(속성 태그)
    private TagGroup tagGroup;

    public TagGroupListViewItem(Context context) {
        attribute = new ArrayList<String>();
        tagGroup = new TagGroup(context);
    }

    public String getGroupName() { return groupName; }
    public void setGroupName(String sightName) {
        this.groupName = sightName;
    }

    public ArrayList<String> getAttribute() { return attribute; }
    public void setAttribute(ArrayList<String> tags) {
        attribute = tags;
        tagGroup.setTags(attribute);
    }

    public void addAttribute(String tag) { attribute.add(tag); }
}
