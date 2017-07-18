package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.app.Activity;

/**
 * Created by zia on 2017/5/29.
 */

public interface IPublishActivity {
    String getTitleString();
    String getContent();
    void toast(String msg);
    Activity getActivity();
}
