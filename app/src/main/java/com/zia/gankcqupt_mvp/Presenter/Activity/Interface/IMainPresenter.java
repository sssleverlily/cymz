package com.zia.gankcqupt_mvp.Presenter.Activity.Interface;

import android.content.Intent;

import com.zia.gankcqupt_mvp.Bean.Student;

import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public interface IMainPresenter {
    void setPager();
    void getData();
    void getRawImage(Intent data);
    void updataImage(Intent data);
    List<Student> getStudentList();
}
