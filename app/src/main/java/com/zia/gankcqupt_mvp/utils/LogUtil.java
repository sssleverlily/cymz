package com.zia.gankcqupt_mvp.utils;

import android.util.Log;

/**
 * Created by zia on 2017/10/24.
 */

public class LogUtil {

    private static final boolean openLog = true;

    public static void d(String tag, String msg) {
        if (!openLog) return;
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!openLog) return;
        Log.e(tag, msg);
    }
}
