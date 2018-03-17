package com.zia.gankcqupt_mvp.ui.start

import android.os.Bundle
import com.zia.gankcqupt_mvp.ui.BaseAppCompatActivity

class StartActivity : BaseAppCompatActivity<IStart>(), StartView {


    private val TAG = StartActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataInBackground()
    }

    override fun initDataInBackground() {
        presenter.initLeanCloud(this@StartActivity)
        presenter.initModel()
        presenter.checkPermission(this@StartActivity)
    }

    override fun createPresenter(): IStart {
        return StartPresenter(this)
    }

    override fun getPermissionCode(): Int {
        return 100
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }
}
