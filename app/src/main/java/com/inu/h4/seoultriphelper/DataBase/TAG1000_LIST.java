package com.inu.h4.seoultriphelper.DataBase;

import android.util.Log;

/**
 * Created by MIN on 2016-10-30.
 */

public class TAG1000_LIST {
    private String[] tag1000Data = new String[2];
    // [0] = 태그 그룹 아이디
    // [1] = 태그 그룹 이름

    public void setTag1000Data(String tag1000_id,
                               String tag1000_name){
        tag1000Data[0] = tag1000_id;
        tag1000Data[1] = tag1000_name;
    }

    public String getData(int index){
        Log.d("LOG/TAG1000_LIST", "getlistdata : " + tag1000Data);
        return tag1000Data[index];
    }
}