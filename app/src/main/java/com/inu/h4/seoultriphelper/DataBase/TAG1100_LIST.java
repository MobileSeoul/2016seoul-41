package com.inu.h4.seoultriphelper.DataBase;

import android.util.Log;

/**
 * Created by MIN on 2016-10-31.
 */

public class TAG1100_LIST {
    private String[] tag1100Data = new String[3];
    // [0] = 태그 아이디
    // [1] = 태그 이름
    // [2] = 태그 그룹 아이디

    public void setTag1100Data(String tag1100_id,
                               String tag1100_name,
                               String tag1100_tag_group_id){
        tag1100Data[0] = tag1100_id;
        tag1100Data[1] = tag1100_name;
        tag1100Data[2] = tag1100_tag_group_id;
    }

    public String getData(int index){
        Log.d("LOG/TAG1100_LIST", "getlistdata : " + tag1100Data[index]);
        return tag1100Data[index];
    }
}