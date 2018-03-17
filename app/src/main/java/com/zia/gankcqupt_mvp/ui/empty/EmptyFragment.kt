package com.zia.gankcqupt_mvp.ui.empty


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.zia.gankcqupt_mvp.R
import com.zia.gankcqupt_mvp.data.Empty
import com.zia.gankcqupt_mvp.data.NowTime
import com.zia.gankcqupt_mvp.data.model.NetworkModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_empty.*

class EmptyFragment : Fragment() {

    private var week = 0
    private var day = 0
    private var start = 1
    private var end = 2
    private var pagerAdapter: PagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setBuildingTab()
        initTime()
    }

    private fun freshEmptyClass() {
        NetworkModel.instance.getEmpty(week, day, start, end).subscribe(object : Observer<Empty> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Empty) {
                activity?.runOnUiThread {
                    pagerAdapter?.freshData(t.empty)
                    pagerAdapter?.notifyDataSetChanged()
                }
            }

            override fun onError(e: Throwable) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "向服务器获取空教室出现错误..", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }

        })
    }

    private fun initTime() {
        NetworkModel.instance.getTime().subscribe(object : Observer<NowTime> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: NowTime) {
                week = t.week.toInt() - 1
                day = t.day.toInt() - 1
                Log.e("test", "week:$week,day:$day")
                setTab()
                freshEmptyClass()
            }

            override fun onError(e: Throwable) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "向服务器获取时间出现错误..", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }

        })
    }

    private fun setTab() {
        setWeekTab(week)
        setDayTab(day)
        setTimeTab(0)
    }


    private fun setTab(tabLayout: TabLayout, array: ArrayList<String>, position: Int) {
        array.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }
        tabLayout.getTabAt(position)?.select()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                week = weekTB.selectedTabPosition + 1
                day = dayTB.selectedTabPosition + 1
                start = timeTB.selectedTabPosition * 2 + 1
                end = start + 1
                Log.e("test", "week:$week,day:$day,start:$start,end:$end")
                freshEmptyClass()
            }
        })
    }

    private fun setBuildingTab() {
        pagerAdapter = PagerAdapter(childFragmentManager)
        empty_viewpager.adapter = pagerAdapter
        empty_viewpager.offscreenPageLimit = 5
        buildingTB.setupWithViewPager(empty_viewpager)
    }

    private fun setWeekTab(position: Int) {
        val trans = arrayListOf("第一周", "第二周", "第三周", "第四周", "第五周"
                , "第六周", "第七周", "第八周", "第九周", "第十周"
                , "第十一周", "第十二周", "第十三周", "第十四周", "第十五周"
                , "第十六周", "第十七周", "第十八周", "第十九周", "第二十周")
        setTab(weekTB, trans, position)
    }

    private fun setDayTab(position: Int) {
        val trans = arrayListOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
        setTab(dayTB, trans, position)
    }

    private fun setTimeTab(position: Int) {
        val trans = arrayListOf("1-2节", "3-4节", "午间", "5-6节", "7-8节", "晚间", "9-10节", "11-12节")
        setTab(timeTB, trans, position)
    }

}
