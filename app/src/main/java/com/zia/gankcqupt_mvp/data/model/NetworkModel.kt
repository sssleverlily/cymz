package com.zia.gankcqupt_mvp.data.model

import com.zia.gankcqupt_mvp.data.API
import com.zia.gankcqupt_mvp.data.Empty
import com.zia.gankcqupt_mvp.data.NowTime
import com.zia.gankcqupt_mvp.data.Students
import com.zia.gankcqupt_mvp.utils.UserDataUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by zia on 2017/11/22.
 */
class NetworkModel private constructor() {

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkModel()
        }
    }

    private val myService: MyService = Retrofit
            .Builder()
            .baseUrl(API.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyService::class.java)

    fun searchByName(name: String): Observable<Students> {
        return myService.getStudentsByName(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun searchById(id: String): Observable<Students> {
        return myService.getStudentsById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getTime(): Observable<NowTime> {
        return myService.getTime()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getEmpty(week: Int, day: Int, start: Int, end: Int): Observable<Empty> {
        val map = HashMap<String, String>()
        map["week"] = week.toString()
        map["day"] = day.toString()
        map["start"] = start.toString()
        map["end"] = end.toString()
        return myService.getEmpty(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    /**
     * 获取收藏集合
     */
    fun pullFavorites() {
        UserDataUtil.pullFavorites(null)
    }
}