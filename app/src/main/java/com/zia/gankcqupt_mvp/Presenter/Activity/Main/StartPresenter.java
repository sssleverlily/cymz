package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IStartPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Page.LoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;

/**
 * Created by zia on 2017/5/17.
 */

public class StartPresenter implements IStartPresenter {
    private static final String TAG = "StartPresenterTest";
    private Context context;

    public StartPresenter(Context context){
        this.context = context;
    }

    @Override
    public void gotoMainPage() {
        Log.d(TAG,"gotoMainPage");
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    @Override
    public void gotoPage() {
//        AVUser user = AVUser.getCurrentUser();
//        if(user != null){
//            gotoMainPage();
//        }
//        else gotoLoginPage();
        gotoMainPage();
    }

    @Override
    public void gotoLoginPage() {
        Log.d(TAG,"gotoLoginPage");
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
