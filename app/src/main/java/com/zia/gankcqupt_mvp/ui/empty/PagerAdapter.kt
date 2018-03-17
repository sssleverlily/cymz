package com.zia.gankcqupt_mvp.ui.empty

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * Created by zia on 2018/3/7.
 */
class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = ArrayList<ContentFragment>()
    private val count = 5

    fun freshData(empty: String) {
        try {
            val rooms = empty.substring(1, empty.length - 1)
                    .replace(" ", "")
                    .split(",")
            val trans = arrayListOf("2", "3", "4", "5", "8")
            for (i in 0 until trans.size) {
                var old = ""
                val sb = StringBuilder("")
                rooms.forEach {
                    if (old != it.substring(1, 2)) {
                        old = it.substring(1, 2)
                        sb.append("\n\n\n")
                    }
                    if (it.substring(0, 1) == trans[i])
                        sb.append(it).append("    ")
                }
                fragments[i].setText(sb.toString().trim())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        for (i in 0 until count) {
            fragments.add(ContentFragment())
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    private val trans = arrayListOf("二教", "三教", "四教", "五教", "八教")
    override fun getPageTitle(position: Int): CharSequence? {
        return trans[position]
    }
}
