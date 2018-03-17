package com.zia.gankcqupt_mvp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zia.gankcqupt_mvp.R

/**
 * Created by zia on 2017/12/10.
 */

object GlideUtil {

    fun loadthumbnail(context: Context, imageView: ImageView, url: String, height: Int, width: Int) {
        val options = RequestOptions()
                .placeholder(R.mipmap.transparent)
                .error(R.mipmap.error_icon)
        Glide.with(context).load(url).apply(options.override(width, height)).into(imageView)
    }

    fun loadthumbnail(context: Context, imageView: ImageView, url: String) {
        val options = RequestOptions()
                .placeholder(R.mipmap.transparent)
                .error(R.mipmap.error_icon)
                .override(200, 300)
        Glide.with(context).load(url).apply(options).into(imageView)
    }

    fun load(context: Context, imageView: ImageView, url: String) {
        val options = RequestOptions()
                .placeholder(R.mipmap.transparent)
                .error(R.mipmap.error_icon)
        Glide.with(context).load(url).apply(options).into(imageView)
    }
}
