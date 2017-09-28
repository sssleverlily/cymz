package com.zia.gankcqupt_mvp.Util;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.google.gson.Gson;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;

import org.json.JSONException;

import java.util.List;

/**
 * Created by zia on 2017/9/28.
 */

public class UserDataUtil {

    private final static String TAG = "UserDataUtil";

    /**
     * 保存收藏列表到服务器端
     */
    public static void pushFavorites(){
        //防止保存空集合
        if(MainPresenter.favorites == null || MainPresenter.favorites.size() == 0) return;
        if(AVUser.getCurrentUser() == null) return;

        AVQuery<AVObject> query = new AVQuery<>("UserData");
        query.whereEqualTo("userId",AVUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    AVObject avObject = null;
                    if(list.size() == 0){//服务器上没有数据，新建
                        avObject = new AVObject("UserData");
                        avObject.put("userId", AVUser.getCurrentUser().getObjectId());
                    }else{//服务器上有数据，直接更新数据
                        avObject = list.get(0);
                    }
                    avObject.put("favorites",MainPresenter.favorites);
                    avObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e == null){
                                Log.d(TAG,"保存favorites成功！");
                            }else{
                                e.printStackTrace();
                                Log.d(TAG,"保存favorites失败！");
                            }
                        }
                    });
                }else{
                    e.printStackTrace();
                    Log.d(TAG,"query.findInBackground error!");
                }
            }
        });
    }

    public interface PullListener{
        void onPulled();
    }

    /**
     * 获取收藏集合
     */
    public static void pullFavorites(final PullListener pullListener){
        if(AVUser.getCurrentUser() == null) return;
        AVQuery<AVObject> avQuery = new AVQuery<>("UserData");
        avQuery.whereEqualTo("userId", AVUser.getCurrentUser().getObjectId());
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() == 0) return;
                    AVObject avObject = list.get(0);
                    try {
                        for (int i = 0; i < avObject.getJSONArray("favorites").length(); i++) {
                            Gson gson = new Gson();
                            Student student = gson.fromJson(avObject.getJSONArray("favorites").get(i).toString(), Student.class);
                            Log.d(TAG, "favorite student: " + student.getName());
                            StudentUtil.addStudentToFavorites(student);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                    Log.d(TAG, "pullConversationList error!");
                }
                pushFavorites();
                if(pullListener != null)
                    pullListener.onPulled();
            }
        });
    }
}
