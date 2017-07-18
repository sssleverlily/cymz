package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.zia.gankcqupt_mvp.Adapter.PagerAdapter;

/**
 * Created by zia on 2017/5/18.
 */

public interface IMainActivity {
    ViewPager getViewPager();
    Toolbar getToolBar();
    TabLayout getTablayout();
    PagerAdapter getPagerAdapter();
    FloatingActionButton getFloatingBar();
    void toast(String msg);
    void setToolBar();
    void showDialog();
    void hideDialog();
    Activity getActivity();
    ProgressDialog getDialog();
}
