package com.inu.h4.seoultriphelper.Tag;

import java.util.ArrayList;

/**
 * Created by MIN on 2016-10-09.
 */
public class SelectedTagInstance {
    private static SelectedTagInstance instance = null;

    private String category;
    private ArrayList<String> subtags;

    private SelectedTagInstance() {
        category = "";
        subtags = new ArrayList<>();
    }

    public static SelectedTagInstance getInstance() {
        if(instance == null) {
            instance = new SelectedTagInstance();
        }

        return instance;
    }
    
    public static void removeInstance() {
        instance = null;
    }

    public static String getCategory() { return instance.category; }

    public static void setCategory(String category) { instance.category = category; }

    public static ArrayList<String> getSubtags() {
        return instance.subtags;
    }

    public static void setSubtags(ArrayList<String> subtags) {
        instance.subtags = subtags;
    }
    public static void addSubtags(String subtag) { instance.subtags.add(subtag); }
    public static void clearSubtags() { instance.subtags = new ArrayList<>(); }
}