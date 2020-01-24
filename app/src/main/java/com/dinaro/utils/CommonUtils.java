package com.dinaro.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CommonUtils {


    public static boolean isOnline(Context mContext) {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static int getWidth(Activity context){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels - 20;

        return width;
    }


    public static String getFormattedDate(String date){

        String result = "";

// SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
        sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date parsed = null; // => Date is in UTC now
        try {
            parsed = sourceFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }


        SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy");
        destFormat.setTimeZone(TimeZone.getDefault());
        result = destFormat.format(parsed);
        return result;


    }

}
