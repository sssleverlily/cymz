package com.zia.gankcqupt_mvp.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zia.gankcqupt_mvp.R

/**
 * Created by zia on 2018/3/15.
 */
fun toast(context: Context, massage: String) {
    (context as Activity).runOnUiThread {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}

fun logd(tag: String, massage: String) {
    Log.d(tag, massage)
}

fun loge(tag: String, massage: String) {
    Log.e(tag, massage)
}

fun Glide.loadthumbnail(context: Context, imageView: ImageView, url: String) {
    val options = RequestOptions()
            .placeholder(R.mipmap.transparent)
            .error(R.mipmap.error_icon)
            .override(200, 300)
    Glide.with(context).load(url).apply(options).into(imageView)
}