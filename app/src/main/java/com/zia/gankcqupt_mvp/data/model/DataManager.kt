package com.zia.gankcqupt_mvp.data.model

import android.app.Activity
import android.content.Context
import com.zia.gankcqupt_mvp.utils.PhotoPicker

/**
 * Created by zia on 2018/3/17.
 */
class DataManager private constructor() {
    private var photoPicker: PhotoPicker? = null

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DataManager()
        }
    }

    fun setPhotoPicker(activity: Activity) {
        photoPicker = PhotoPicker(activity, true)
    }

    fun setPhotoPickerListener(listener: PhotoPicker.PhotoSelectListener) {
        photoPicker?.setListener(listener)
    }

    fun getPhotoPicker(): PhotoPicker? {
        return photoPicker
    }
}