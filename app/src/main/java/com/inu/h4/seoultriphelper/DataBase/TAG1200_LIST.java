package com.inu.h4.seoultriphelper.DataBase;

import android.util.Log;

/**
 * Created by MIN on 2016-10-31.
 */

public class TAG1200_LIST {
    private String[] tag1200Data = new String[2];
    // [0] = SIGHT ID
    // [1] = TAG ID

    public void setTag1200Data(String tag1200_sight_id,
                               String tag1200_tag_id){
        tag1200Data[0] = tag1200_sight_id;
        tag1200Data[1] = tag1200_tag_id;
    }

    public String getData(int index){
        Log.d("LOG/TAG1200_LIST", "getlistdata : " + tag1200Data[index]);
        return tag1200Data[index];
    }
}