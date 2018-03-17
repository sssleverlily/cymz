package com.zia.gankcqupt_mvp.ui.main

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.zia.gankcqupt_mvp.adapter.PagerAdapter
import com.zia.gankcqupt_mvp.data.model.DataManager
import com.zia.gankcqupt_mvp.model.GetStudent
import com.zia.gankcqupt_mvp.ui.BasePresenter
import com.zia.gankcqupt_mvp.utils.loge
import java.io.IOException

/**
 * Created by zia on 2018/3/16.
 */
class MainPresenter(private val v: MainView) : BasePresenter<MainView>(v), IMain {

    private val tag = javaClass.canonicalName

    override fun setViewPager(viewPager: ViewPager, fragmentManager: FragmentManager, tabLayout: TabLayout) {
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = PagerAdapter(fragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.isFocusable = true//启动app时把焦点放在其他控件（不放在editext上）上防止弹出虚拟键盘
        tabLayout.isFocusableInTouchMode = true
        tabLayout.requestFocus()
    }

    override fun initData() {
        DataManager.instance.setPhotoPicker(activity)
        val dialog = v.getDialog()
        dialog.show()
        val getStudent = GetStudent(context)
        try {
            getStudent.getFromDB(dialog)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onResume(lifecycleOwner: LifecycleOwner) {
        loge(tag, "onResume")
    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        loge(tag, "onCreate")
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        loge(tag, "onDestroy")
    }
}