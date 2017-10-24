package com.zia.gankcqupt_mvp.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zia on 2017/9/28.
 */

/**
 * 本地配置信息储存
 */
public class SharedPreferencesUtil {
    private static final String TAG = "SharedPreferencesUtil";

    public static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }

    /**
     * 获取线路信息
     * @param context context
     * @return string
     */
    public static String getRoot(Context context){
        return getSharedPreferences(context).getString("root","congm");
    }
}
