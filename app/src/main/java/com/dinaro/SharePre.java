package com.dinaro;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharePre {

    public static String getUserBuyerId(Context conteActivity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(conteActivity);
        String Type = sharedPreferences.getString("pin", "");
        return Type;
    }

    public static void SaveId(Context context, String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }


}
