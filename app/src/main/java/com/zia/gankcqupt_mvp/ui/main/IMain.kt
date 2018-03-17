package com.zia.gankcqupt_mvp.ui.main

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.View
import com.zia.gankcqupt_mvp.ui.LifePresenter

/**
 * Created by zia on 2018/3/16.
 */
interface IMain : LifePresenter {
    fun setViewPager(viewPager: ViewPager, fragmentManager: FragmentManager, tabLayout: TabLayout)
    fun initData()
}