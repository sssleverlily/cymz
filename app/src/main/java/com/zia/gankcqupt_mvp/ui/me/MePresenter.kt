package com.zia.gankcqupt_mvp.ui.me

import android.app.Activity
import android.app.ProgressDialog
import android.arch.lifecycle.LifecycleOwner
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SaveCallback
import com.bumptech.glide.Glide
import com.zia.gankcqupt_mvp.R
import com.zia.gankcqupt_mvp.bean.Student
import com.zia.gankcqupt_mvp.data.model.DataManager
import com.zia.gankcqupt_mvp.model.GetStudent
import com.zia.gankcqupt_mvp.model.SaveStudent
import com.zia.gankcqupt_mvp.presenter.Activity.Main.MainPresenter
import com.zia.gankcqupt_mvp.view.Activity.Page.LoginActivity
import com.zia.gankcqupt_mvp.ui.BasePresenter
import com.zia.gankcqupt_mvp.utils.PhotoPicker
import com.zia.gankcqupt_mvp.utils.loge
import com.zia.gankcqupt_mvp.utils.toast
import com.zia.gankcqupt_mvp.view.Activity.Page.RecyclerActivity
import java.io.FileNotFoundException

/**
 * Created by zia on 2018/3/17.
 */
class MePresenter(private val view: MeView) : BasePresenter<MeView>(view),
        IMe, PopupMenu.OnMenuItemClickListener {

    val tag = javaClass.name

    override fun update() {
        AlertDialog.Builder(context).setTitle("警告").setMessage("更新最新数据库将耗时3-10分钟，约使用3m流量。\n\n" + "不会丢失收藏数据，但有几率更新失败导致程序无法使用，可以重新更新即可！")
                .setPositiveButton("我想好了") { _, _ -> getDataFromCQUPT() }
                .setNegativeButton("再想想", null).show()
    }

    override fun goFavorite() {
        val getStudent = GetStudent(context)
        getStudent.getFavorite()
        if (MainPresenter.favorites.size != 0) {
            val intent = Intent(context, RecyclerActivity::class.java)
            intent.putExtra("flag", "favorite")
            activity.startActivity(intent)
        } else {
            toast(context, "还没有收藏哟..\n或许你可以登录查看收藏")
        }
    }

    override fun changeName() {
        val et = EditText(context)
        AlertDialog.Builder(context).setTitle("修改昵称")
                .setView(et)
                .setPositiveButton("确定") { dialog, which ->
                    val input = et.text.toString()
                    if (input.isEmpty()) {
                        toast(context, "昵称不能为空！")
                    } else {
                        val user = AVUser.getCurrentUser()
                        user.put("nickname", input)
                        user.saveInBackground(object : SaveCallback() {
                            override fun done(e: AVException?) {
                                if (e != null) {
                                    e.printStackTrace()
                                } else {
                                    activity.runOnUiThread {
                                        toast(context, "修改成功！")
                                        val name = activity.findViewById<TextView>(R.id.me_nickname)
                                        name.text = AVUser.getCurrentUser().getString("nickname")
                                    }
                                }
                            }
                        })
                    }
                }
                .setNegativeButton("取消", null)
                .show()
    }

    override fun changePassword() {
        val et1 = EditText(context)
        et1.hint = "密码长度大于等于6"
        AlertDialog.Builder(context).setTitle("修改密码")
                .setView(et1)
                .setPositiveButton("确定") { dialog, which ->
                    val input = et1.text.toString()
                    if (input.isEmpty() || input.length < 6) {
                        toast(context, "密码类型不符合要求！")
                    } else {
                        val user = AVUser.getCurrentUser()
                        user.setPassword(input)
                        user.saveInBackground(object : SaveCallback() {
                            override fun done(e: AVException?) {
                                if (e != null) {
                                    e.printStackTrace()
                                } else {
                                    activity.runOnUiThread(Runnable { toast(context, "修改成功！") })
                                }
                            }
                        })
                    }
                }
                .setNegativeButton("取消", null)
                .show()
    }

    override fun setUserInfo() {
        val avUser = AVUser.getCurrentUser()
        if (avUser != null) {
            val name = activity.findViewById<TextView>(R.id.me_nickname)
            val sex = activity.findViewById<TextView>(R.id.me_sex)
            val avatar = activity.findViewById<ImageView>(R.id.me_image)
            val nickname = avUser.getString("nickname")
            name.text = nickname
            sex.text = "状态：已登录"
            Thread(Runnable {
                if (avUser.getAVFile<AVFile>("headImage") != null) {
                    try {
                        val file = AVFile.withObjectId(avUser.getAVFile<AVFile>("headImage").objectId)
                        activity.runOnUiThread({
                            if (file.url != null) {
                                val headUrl = file.getThumbnailUrl(true, 150, 150)
                                Glide.with(activity).load(headUrl).into(avatar)
                            }
                        })
                    } catch (e: AVException) {
                        e.printStackTrace()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }).start()
        }
    }

    override fun loginOut() {
        AlertDialog.Builder(context).setTitle("退出登录").setMessage("真的要退出登录吗？")
                .setPositiveButton("是") { _, _ ->
                    AVUser.logOut()
                    val intent = Intent(context, LoginActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                }
                .setNegativeButton("否", null).show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val self = AVUser.getCurrentUser()
        when (item?.itemId) {
            R.id.use_pop_login -> goLogin()
            R.id.user_pop_changeImage -> {
                if (self == null) {
                    toast(context, "请先登录..")
                    return false
                }
                changeAvatar()
            }
            R.id.user_pop_changeName -> {
                changeName()
            }
            R.id.user_pop_changePassword -> {

            }
            R.id.user_pop_loginOut -> loginOut()
        }
        return false
    }

    override fun goLogin() {
        if (AVUser.getCurrentUser() != null) {
            toast(view.context, "您已经登录啦..")
        } else {
            val intent1 = Intent(view.context, LoginActivity::class.java)
            activity.startActivity(intent1)
            activity.finish()
        }
    }

    override fun changeAvatar() {
        val self = AVUser.getCurrentUser()
        //开始选择图片
        loge(tag, "select")
        DataManager.instance.setPhotoPickerListener(PhotoPicker.PhotoSelectListener { outputFile, outputUri ->
            loge(tag, outputFile.absolutePath)
            val dialog = ProgressDialog(context)
            dialog.setCancelable(false)
            dialog.setTitle("正在上传..")
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            dialog.show()
            val avFile = AVFile.withAbsoluteLocalPath(self.username, outputFile.absolutePath)
            self.put("headImage", avFile)
            self.saveInBackground(object : SaveCallback() {
                override fun done(p0: AVException?) {
                    activity.runOnUiThread {
                        dialog.hide()
                        if (p0 != null) {
                            p0.printStackTrace()
                            toast(view.context, "上传失败")
                        } else {
                            toast(view.context, "更新成功")
                            Glide.with(view.context as Activity)
                                    .load(outputFile)
                                    .into(activity.findViewById(R.id.me_image) as ImageView)
                        }
                    }
                }
            })
        })
        DataManager.instance.getPhotoPicker()?.selectPhoto()
    }

    override fun showUserPop(view: View) {
        val popupMenu = PopupMenu(context, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.user_pop, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        DataManager.instance.getPhotoPicker()?.attachToActivityForResult(requestCode, resultCode, data)
    }

    /**
     * 从教务在线更新数据到本地数据库
     * 删除已有数据库内容，更换
     */
    private fun getDataFromCQUPT() {
        val dialog = ProgressDialog(context)
        dialog.setCancelable(false)
        dialog.setTitle("正在从教务在线获取数据...")
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.show()
        dialog.progress = 0
        dialog.setMessage("耐心等待..")
        //执行model的获取学生方法
        GetStudent(context).GetFromCQUPT(object : GetStudent.OnAllStudentGet {
            override fun onFinish(studentList: List<Student>) {
                //从网络中获取后保存到数据库里
                activity.runOnUiThread {
                    dialog.progress = 0
                    dialog.setTitle("正在将数据存入数据库...")
                }
                //存入数据库
                SaveStudent.saveInDB(context, dialog)
            }

            override fun onError() {
                toast(context, "失败了...")
            }
            //设置进度条
        }, object : GetStudent.GetProgress {
            override fun status(percentage: Int) {
                activity.runOnUiThread { dialog.progress = percentage }
            }
        })
    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {

    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
    }

    override fun onResume(lifecycleOwner: LifecycleOwner) {
    }

}