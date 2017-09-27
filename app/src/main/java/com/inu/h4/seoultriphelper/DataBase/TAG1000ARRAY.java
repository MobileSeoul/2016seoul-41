package com.inu.h4.seoultriphelper.DataBase;

import java.util.ArrayList;

/**
 * Created by MIN on 2016-10-30.
 */

public class TAG1000ARRAY {
    private static TAG1000ARRAY instance = null;

    private ArrayList<TAG1000_LIST> tag1000Array;

    private TAG1000ARRAY() {
        tag1000Array = new ArrayList<>();
    }

    public static TAG1000ARRAY getInstance() {
        if(instance == null) {
            instance = new TAG1000ARRAY();
        }

        return instance;
    }

    public void addData(TAG1000_LIST data) {
        instance.tag1000Array.add(data);
    }

    public ArrayList<TAG1000_LIST> getData(){
        return instance.tag1000Array;
    }
}