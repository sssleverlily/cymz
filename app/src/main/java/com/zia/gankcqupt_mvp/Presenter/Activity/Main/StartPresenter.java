package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IStartPresenter;
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
    public void gotoCompletePage() {

    }
}
