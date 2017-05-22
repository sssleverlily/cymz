package com.zia.gankcqupt_mvp.Model;

import com.zia.gankcqupt_mvp.Bean.Student;

import java.util.List;

/**
 * Created by zia on 2017/5/19.
 */

public interface OnAllStudentGet {
    void onFinish(List<Student> studentList);
    void onError();
}
