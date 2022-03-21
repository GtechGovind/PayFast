package com.gtech.payfast.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {

    private static final String PREF_APP = "ATEK_STATIC_DATA";

    private SharedPrefUtils() {
        throw new UnsupportedOperationException( "Should not create instance of Util class. Please use as static..");
    }


    static public boolean getBooleanData(Context context, String key) {

        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getBoolean(key, false);
    }


    static public int getIntData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(key, 0);
    }


    // Get Data
    static public String getStringData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, null);
    }


    // Save Data
    static public void saveData(Context context, String key, String val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(key, val).apply();
    }

    static public void saveData(Context context, String key, int val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(key, val).apply();
    }


    static public void saveData(Context context, String key, boolean val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, val)
                .apply();
    }

    static public SharedPreferences.Editor getSharedPrefEditor(Context context, String pref) {
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit();
    }

    static public void saveData(SharedPreferences.Editor editor) {
        editor.apply();
    }
}
