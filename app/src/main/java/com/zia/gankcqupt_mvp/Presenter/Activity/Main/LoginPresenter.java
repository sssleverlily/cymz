package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.zia.gankcqupt_mvp.Model.GetStudent;
import com.zia.gankcqupt_mvp.Model.StudentDbHelper;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.ILoginPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.UserDataUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.ILoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.LoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/28.
 */

public class LoginPresenter implements ILoginPresenter {


    private static final String TAG = "LoginPresenterTest";
    private LoginActivity activity;

    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void gotoRegisterPage() {
        Log.d(TAG, "gotoRegisterPage");
        Intent intent = new Intent(activity, RegisterActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View card = activity.findViewById(R.id.login_card);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, card, card.getTransitionName());
            activity.startActivity(intent, optionsCompat.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    public void gotoMainPage() {
        Log.d(TAG, "gotoMainPage");
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void login(String username, String password) {
        activity.showDialog();
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e != null) {
                    e.printStackTrace();
                    Log.d(TAG, "登录有问题");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.setPasswordError();
                            activity.hideDialog();
                        }
                    });
                } else {
                    ContentValues values = new ContentValues();
                    values.put("nickname", avUser.getString("nickname"));
                    values.put("sex", avUser.getString("sex"));
                    AVFile avFile = avUser.getAVFile("headImage");
                    String url = null;
                    try {
                        url = avFile.getUrl();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (url != null) {
                        values.put("head", url);
                        Log.d(TAG, "url:" + url);
                    }
                    Log.d(TAG, "nickname:" + avUser.getString("nickname"));
                    Log.d(TAG, "sex:" + avUser.getString("sex"));
                    //拉取服务器的收藏
                    UserDataUtil.pullFavorites(new UserDataUtil.PullListener() {
                        @Override
                        public void onPulled() {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.hideDialog();
                                    gotoMainPage();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
