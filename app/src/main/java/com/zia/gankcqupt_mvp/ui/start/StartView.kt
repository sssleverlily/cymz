package com.zia.gankcqupt_mvp.ui.start

import com.zia.gankcqupt_mvp.ui.BaseView

/**
 * Created by zia on 2018/3/15.
 */
interface StartView : BaseView {
    fun getPermissionCode(): Int
    fun initDataInBackground()
}