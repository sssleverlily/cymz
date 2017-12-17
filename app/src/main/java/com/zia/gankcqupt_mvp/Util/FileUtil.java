package com.zia.gankcqupt_mvp.Util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by zia on 2017/9/28.
 */

public class FileUtil {
    public static String getSdCardUrl(String fileName){
        File appDir = new File(Environment.getExternalStorageDirectory(), "重邮妹子");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        Log.e("FileUtil",file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
