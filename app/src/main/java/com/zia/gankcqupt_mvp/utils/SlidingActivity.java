package com.zia.gankcqupt_mvp.utils;

/**
 * Created by zia on 17-7-18.
 */

import android.os.Bundle;

import com.zia.gankcqupt_mvp.view.Activity.BaseActivity;

public abstract class SlidingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    protected boolean enableSliding() {
        return true;
    }
}
