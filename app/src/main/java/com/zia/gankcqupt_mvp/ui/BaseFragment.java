package com.zia.gankcqupt_mvp.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by zia on 2018/3/17.
 */
public abstract class BaseFragment<P extends LifePresenter>
        extends Fragment implements BaseView {

    private P lifePresenter;

    protected abstract P createPresenter();

    protected P getPresenter() {
        if (lifePresenter == null)
            lifePresenter = createPresenter();
        return lifePresenter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLifecycle().addObserver(getPresenter());
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
}
