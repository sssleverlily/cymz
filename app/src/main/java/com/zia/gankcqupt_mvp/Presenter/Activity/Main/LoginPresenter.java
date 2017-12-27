package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.ILoginPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.UserDataUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.ILoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.LoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RegisterActivity;

/**
 * Created by zia on 2017/5/28.
 */

public class LoginPresenter implements ILoginPresenter {


    private static final String TAG = "LoginPresenterTest";
    private ILoginActivity activity;

    public LoginPresenter(ILoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void gotoRegisterPage() {
        Log.d(TAG, "gotoRegisterPage");
        Intent intent = new Intent(activity.getActivity(), RegisterActivity.class);
        View card = activity.getActivity().findViewById(R.id.login_card);
        TextInputLayout usernameInput = activity.getActivity().findViewById(R.id.login_username);
        TextInputLayout passwordInput = activity.getActivity().findViewById(R.id.login_password);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity.getActivity(),
                new Pair<>(card, LoginActivity.card_transitionName)
                , new Pair<View, String>(usernameInput, LoginActivity.username_transitionName)
                , new Pair<View, String>(passwordInput, LoginActivity.password_transitionName)
        );
        ActivityCompat.startActivity(activity.getActivity(), intent, options.toBundle());
    }

    @Override
    public void gotoMainPage() {
        Log.d(TAG, "gotoMainPage");
        Intent intent = new Intent(activity.getActivity(), MainActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity.getActivity()).toBundle());
        } else {
            activity.getActivity().startActivity(intent);
        }
        activity.getActivity().finish();
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
                    activity.getActivity().runOnUiThread(new Runnable() {
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
//                            activity.getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    activity.hideDialog();
//                                    gotoMainPage();
//                                }
//                            });
                        }
                    });
                    activity.hideDialog();
                    gotoMainPage();
                }
            }
        });
    }
}
