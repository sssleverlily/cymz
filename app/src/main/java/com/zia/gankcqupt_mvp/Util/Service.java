package com.zia.gankcqupt_mvp.Util;

import com.zia.gankcqupt_mvp.Bean.Students;

import retrofit2.http.GET;
import rx.Observable;


/**
 * Created by zia on 2017/9/28.
 */

public interface Service {
    @GET("/api")
    Observable<Students> getStudents();
}
