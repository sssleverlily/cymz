package com.zia.gankcqupt_mvp.ui.start

import android.content.Context
import com.zia.gankcqupt_mvp.ui.LifePresenter

/**
 * Created by zia on 2018/3/15.
 */
interface IStart : LifePresenter {
    fun initLeanCloud(context: Context)
    fun checkPermission(context: Context)
    fun initModel()
    fun goMain(context: Context)
    fun onRequestPermissionsResult(context: Context, requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}