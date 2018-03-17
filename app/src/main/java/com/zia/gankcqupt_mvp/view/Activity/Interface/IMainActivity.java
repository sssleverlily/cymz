package com.zia.gankcqupt_mvp.view.Activity.Interface;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.zia.gankcqupt_mvp.adapter.PagerAdapter;

/**
 * Created by zia on 2017/5/18.
 */

public interface IMainActivity extends BaseInterface {
    ViewPager getViewPager();
    Toolbar getToolBar();
    TabLayout getTablayout();
    PagerAdapter getPagerAdapter();
    void setToolBar();
    void showDialog();
    void hideDialog();
    ProgressDialog getDialog();
}
