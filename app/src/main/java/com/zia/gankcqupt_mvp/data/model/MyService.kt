package com.zia.gankcqupt_mvp.data.model

import com.zia.gankcqupt_mvp.data.Empty
import com.zia.gankcqupt_mvp.data.NowTime
import com.zia.gankcqupt_mvp.data.Students
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by zia on 2017/11/22.
 */

interface MyService {

    @GET("api/name={name}")
    fun getStudentsByName(@Path("name") name: String): Observable<Students>

    @GET("api/id={id}")
    fun getStudentsById(@Path("id") id: String): Observable<Students>

    @GET("empty/")
    fun getTime(): Observable<NowTime>


    @POST("empty/")
    fun getEmpty(@QueryMap map: Map<String, String>): Observable<Empty>
}
