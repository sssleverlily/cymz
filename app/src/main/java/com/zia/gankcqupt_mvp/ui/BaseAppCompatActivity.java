package com.zia.gankcqupt_mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zia on 2018/3/15.
 */
public abstract class BaseAppCompatActivity<P extends LifePresenter>
        extends AppCompatActivity implements BaseView {

    private final String TAG = this.getClass().getSimpleName();
    private P lifePresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(getPresenter());
    }

    protected abstract P createPresenter();

    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    protected P getPresenter() {
        if (lifePresenter == null)
            lifePresenter = createPresenter();
        return lifePresenter;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
