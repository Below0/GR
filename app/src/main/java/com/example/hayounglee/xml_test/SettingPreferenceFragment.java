package com.example.hayounglee.xml_test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.util.ULocale;
import android.preference.PreferenceFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SettingPreferenceFragment extends PreferenceFragment{
    SharedPreferences prefs;

    PreferenceScreen phonenumScreen;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting_preference);
        phonenumScreen = (PreferenceScreen)findPreference("phonenum_message");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if(prefs.getBoolean("phonenum", false)){

            phonenumScreen.setSummary("사용");
        }


        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }// onCreate

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if(key.equals("phonenum")){

                if(prefs.getBoolean("phonenum", false)){
                    phonenumScreen.setSummary("사용");

                }else{
                    phonenumScreen.setSummary("사용안함");
                }

                //2뎁스 PreferenceScreen 내부에서 발생한 환경설정 내용을 2뎁스 PreferenceScreen에 적용하기 위한 소스
                ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
            }
            if(key.equals("DETECT_ON")){

                if(prefs.getBoolean("DETECT_ON", false)){

                }else{
                }
                //2뎁스 PreferenceScreen 내부에서 발생한 환경설정 내용을 2뎁스 PreferenceScreen에 적용하기 위한 소스
            //    ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
                Log.e("setting",""+Boolean.toString(prefs.getBoolean("DETECT_ON",false)));
            }


            ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
        }
    };

}
