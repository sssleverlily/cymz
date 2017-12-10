package com.zia.gankcqupt_mvp.Util;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * Created by zia on 17-7-13.
 */

public class UserInfo {

    private static final String TAG = "UserInfoTest";

    private AVUser user = null;

    public UserInfo(String userId) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("_User");
        user = (AVUser) query.get(userId);
        if(user == null) Log.e(TAG,"user not find!");
    };

    public String getImageUrl() {
        return ((AVFile)user.get("headImage")).getUrl();
    }

    public String getThumbnailUrl(int width,int height){
        return ((AVFile)user.get("headImage")).getThumbnailUrl(true,width,height);
    }

    public String getNickname(){
        return user.getString("nickname");
    }

    public String getUsername(){
        return user.getUsername();
    }

    public String getSex(){
        return user.getString("sex");
    }
}
