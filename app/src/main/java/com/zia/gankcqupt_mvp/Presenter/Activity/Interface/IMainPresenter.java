package com.zia.gankcqupt_mvp.Presenter.Activity.Interface;

import com.zia.gankcqupt_mvp.Bean.Student;

import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public interface IMainPresenter {
    void setFloatingBar();
    void setPager();
    void setToolbar();
    void getData();
    List<Student> getStudentList();
}
