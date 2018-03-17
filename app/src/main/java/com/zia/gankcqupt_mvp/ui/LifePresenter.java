package com.zia.gankcqupt_mvp.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zia on 2018/3/15.
 */
public interface LifePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(@NotNull LifecycleOwner lifecycleOwner);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@NotNull LifecycleOwner lifecycleOwner);

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(@NotNull LifecycleOwner lifecycleOwner);

}
