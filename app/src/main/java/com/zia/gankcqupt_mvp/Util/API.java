package com.zia.gankcqupt_mvp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by zia on 2017/9/28.
 */

public class API {
    private static API instance;
    private static final String TAG = "API";
    private String root = "congm";
    private Context context;

    public static API getInstance(Context context){
        if(instance == null){
            synchronized (API.class){
                if(instance == null){
                    instance = new API(context);
                }
            }
        }
        return instance;
    }

    private API(Context context){
        this.context = context;
        root = SharedPreferencesUtil.getSharedPreferences(context).getString("root","congm");
    }

    public String getCET(String id){
        if(root.equals("congm")){
            String url = "http://172.22.80.212.cqupt.congm.in/PHOTO0906CET/" + id + ".jpg";
            LogUtil.d(TAG,url);
            return url;
        }else{
            String url = "http://139.199.176.72/cet/"+id+".jpg";
            LogUtil.d(TAG,url);
            return url;
        }
    }

    public String getYKT(String id){
        if(root.equals("congm")){
            String url = "http://jwzx.cqupt.congm.in/showstuPic.php?xh=" + id;
            LogUtil.d(TAG,url);
            return url;
        }else{
            String url = "http://139.199.176.72/ykt/" + id + ".jpg";
            LogUtil.d(TAG,url);
            return url;
        }
    }

    public String getDatabase(){
        return "http://zzzia.net/py/StudentData.db";
    }

    public boolean changeRoot(String root){
        Log.e(TAG,"root has been changed, now is "+root);
        this.root = root;
        SharedPreferences.Editor editor = SharedPreferencesUtil.getEditor(context);
        editor.putString("root",root);
        return editor.commit();
    }
}
