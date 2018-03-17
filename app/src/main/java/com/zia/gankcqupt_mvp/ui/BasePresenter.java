package com.zia.gankcqupt_mvp.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zia on 2018/3/15.
 */
public abstract class BasePresenter<V extends BaseView> {

    private final String TAG = this.getClass().getSimpleName();
    private V v;

    private V getView() {
        return v;
    }

    protected Context getContext() {
        return v.getContext();
    }

    protected Activity getActivity() {
        return (Activity) v.getContext();
    }

    public BasePresenter(@NotNull V v) {
        this.v = v;
    }

    protected void showLoading() {
        getView().showLoading();
    }

    protected void showMessage(String msg) {
        getView().showMessage(msg);
    }

    protected void showError(String msg) {
        getView().showError(msg);
    }
}
