package com.inu.h4.seoultriphelper.DataBase;

import java.util.ArrayList;

/**
 * Created by MIN on 2016-10-31.
 */

public class TAG1100ARRAY {
    private static TAG1100ARRAY instance = null;

    private ArrayList<TAG1100_LIST> tag1100Array;

    private TAG1100ARRAY() { tag1100Array = new ArrayList<>(); }

    public static TAG1100ARRAY getInstance() {
        if(instance == null) {
            instance = new TAG1100ARRAY();
        }

        return instance;
    }

    public void addData(TAG1100_LIST data) { instance.tag1100Array.add(data); }

    public ArrayList<TAG1100_LIST> getData(){ return instance.tag1100Array; }
}