package com.zia.gankcqupt_mvp.utils;

import com.zia.gankcqupt_mvp.bean.Students;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by zia on 2017/9/28.
 */

public interface Service {
    @GET("/api")
    Observable<Students> getStudents();
}
