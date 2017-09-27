package com.inu.h4.seoultriphelper.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.inu.h4.seoultriphelper.R;

public class SettingContents extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);

        //메일 보내기
        PreferenceCategory lol = (PreferenceCategory) getPreferenceScreen().getPreference(0);
        Preference pref = lol.getPreference(1);
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d("LOG/setting", "Setting()");
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");
                it.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                it.putExtra(Intent.EXTRA_TEXT, "The email body text");
                it.setType("text/html");
                startActivity(Intent.createChooser(it, "Choose Email Client"));
                return false;
            }
        });


    }
}
