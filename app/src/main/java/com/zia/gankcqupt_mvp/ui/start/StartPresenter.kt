package com.zia.gankcqupt_mvp.ui.start

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.avos.avoscloud.AVOSCloud
import com.zia.gankcqupt_mvp.data.model.NetworkModel
import com.zia.gankcqupt_mvp.ui.BasePresenter
import com.zia.gankcqupt_mvp.ui.main.MainActivity
import com.zia.gankcqupt_mvp.utils.PermissionsUtil
import com.zia.gankcqupt_mvp.utils.loge

/**
 * Created by zia on 2018/3/15.
 */
class StartPresenter(val view: StartView) : BasePresenter<StartView>(view), IStart {


    override fun initModel() {
        NetworkModel.instance.pullFavorites()
    }

    private val TAG = StartPresenter::class.java.canonicalName

    //权限申请
    override fun checkPermission(context: Context) {
        if (PermissionsUtil.hasDiskPermission(context as Activity, view.getPermissionCode()))
            goMain(context)
    }

    override fun onRequestPermissionsResult(context: Context, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            view.getPermissionCode() -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //同意权限申请
                    loge(TAG, "获取权限成功！")
                    goMain(context)
                } else { //拒绝权限申请
                    loge(TAG, "没有获取到权限..")
                }
                activity.finish()
            }
        }
    }

    override fun goMain(context: Context) {
        activity.startActivity(Intent(context, MainActivity::class.java))
    }

    override fun initLeanCloud(context: Context) {
        //初始化LeanCloud
        AVOSCloud.initialize(context, "xKvysVojCOylmIQtqEVGaYQ3-gzGzoHsz", "qRgNqRCPJt9rRsqkftCzvOS1")
    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        loge(TAG, "onCreate")
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        loge(TAG, "onDestroy")
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    override fun onResume(lifecycleOwner: LifecycleOwner) {
        loge(TAG, "onResume")
    }

}