package com.zia.gankcqupt_mvp.ui.main

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import com.zia.gankcqupt_mvp.R
import com.zia.gankcqupt_mvp.data.model.DataManager
import com.zia.gankcqupt_mvp.ui.BaseAppCompatActivity
import kotlinx.android.synthetic.main.activity_main_kotlin.*

class MainActivity : BaseAppCompatActivity<IMain>(), MainView {


    override fun initView() {
        presenter.setViewPager(viewPager, supportFragmentManager, tabLayout)
    }

    override fun loadData() {
        presenter.initData()
    }


    override fun getDialog(): ProgressDialog {
        val dialog = ProgressDialog(this)
        dialog.setCancelable(false)
        dialog.setTitle("正在从数据库获取数据...")
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.progress = 0
        return dialog
    }

    override fun createPresenter(): IMain {
        return MainPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.enterTransition = Explode().setDuration(1000)
        setContentView(R.layout.activity_main_kotlin)
        initView()
        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        DataManager.instance.getPhotoPicker()?.attachToActivityForResult(requestCode, resultCode, data)
    }
}
