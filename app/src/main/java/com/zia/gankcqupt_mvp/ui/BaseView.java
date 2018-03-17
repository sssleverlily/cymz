package com.zia.gankcqupt_mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import io.reactivex.annotations.Nullable;

/**
 * Created by zia on 2018/3/15.
 */
public interface BaseView {
    void showLoading();

    void showMessage(String msg);

    void showError(String msg);

    Context getContext();

    Activity getActivity();
}
