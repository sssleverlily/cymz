package com.zia.gankcqupt_mvp.Util;

/**
 * Created by zia on 17-7-18.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zia.gankcqupt_mvp.View.Activity.BaseActivity;

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
