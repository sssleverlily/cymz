package com.zia.gankcqupt_mvp.ui.me

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.zia.gankcqupt_mvp.ui.LifePresenter

/**
 * Created by zia on 2018/3/17.
 */
interface IMe : LifePresenter {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun showUserPop(view: View)
    fun update()
    fun goFavorite()
    fun goLogin()
    fun changeAvatar()
    fun changeName()
    fun changePassword()
    fun loginOut()
    fun setUserInfo()
}