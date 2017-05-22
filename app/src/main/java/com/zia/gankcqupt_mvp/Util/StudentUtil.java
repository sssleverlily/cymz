package com.zia.gankcqupt_mvp.Util;

import android.content.ContentValues;

import com.zia.gankcqupt_mvp.Bean.Student;

/**
 * Created by zia on 2017/5/19.
 */

public class StudentUtil {

    private static ContentValues values = new ContentValues();

    public static ContentValues student2values(Student student){
        values.clear();
        values.put("name",student.getName());
        values.put("sex",student.getSex());
        values.put("year",student.getYear());
        values.put("studentid",student.getStudentId());
        values.put("major",student.getMajor());
        values.put("classid",student.getClassId());
        values.put("zyh",student.getZyh());
        values.put("classnum",student.getClassNum());
        values.put("college",student.getCollege());
        values.put("atschool",student.getAtSchool());
        return values;
    }
}
