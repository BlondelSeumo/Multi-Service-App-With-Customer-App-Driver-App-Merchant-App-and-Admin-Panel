package com.ourdevelops.ornids.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefrence {
    public static SharedPreferences myPrefs;
    public static SharedPreferences.Editor prefsEditor;

    public static SharedPrefrence myObj;

    private SharedPrefrence() {

    }

     public static SharedPrefrence getInstance(Context ctx) {
        if (myObj == null) {
            myObj = new SharedPrefrence();
            myPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            prefsEditor = myPrefs.edit();
        }
        return myObj;
    }

}
