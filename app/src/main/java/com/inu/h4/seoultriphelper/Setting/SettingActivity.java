package com.inu.h4.seoultriphelper.Setting;

import android.app.Activity;
import android.os.Bundle;

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setTitle("설정");

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingContents()).commit();


    }
}