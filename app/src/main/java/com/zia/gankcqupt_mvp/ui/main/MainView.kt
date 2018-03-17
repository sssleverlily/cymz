package com.zia.gankcqupt_mvp.ui.main

import android.app.ProgressDialog
import com.zia.gankcqupt_mvp.ui.BaseView


/**
 * Created by zia on 2018/3/16.
 */
interface MainView : BaseView {
    fun initView()
    fun loadData()
    fun getDialog(): ProgressDialog
}