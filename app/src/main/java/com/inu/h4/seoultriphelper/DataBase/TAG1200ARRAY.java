package com.inu.h4.seoultriphelper.DataBase;

import java.util.ArrayList;

/**
 * Created by MIN on 2016-10-31.
 */

public class TAG1200ARRAY {
    private static TAG1200ARRAY instance = null;

    private ArrayList<TAG1200_LIST> tag1200Array;

    private TAG1200ARRAY() {
        tag1200Array = new ArrayList<>();
    }

    public static TAG1200ARRAY getInstance() {
        if(instance == null) {
            instance = new TAG1200ARRAY();
        }

        return instance;
    }

    public void addData(TAG1200_LIST data) {
        instance.tag1200Array.add(data);
    }

    public ArrayList<TAG1200_LIST> getData(){
        return instance.tag1200Array;
    }
}