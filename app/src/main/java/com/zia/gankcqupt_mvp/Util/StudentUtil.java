package com.zia.gankcqupt_mvp.Util;

import android.content.ContentValues;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;

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
        values.put("studentid",student.getStudentid());
        values.put("major",student.getMajor());
        values.put("classid",student.getClassid());
        values.put("zyh",student.getZyh());
        values.put("classnum",student.getClassnum());
        values.put("college",student.getCollege());
        values.put("atschool",student.getAtschool());
        return values;
    }

    public static void addStudentToFavorites(Student student){
        if(MainPresenter.favorites == null || student == null) return;
        for (Student s : MainPresenter.favorites) {
            if(s.getStudentid().equals(student.getStudentid())){
                return;
            }
        }
        MainPresenter.favorites.add(student);
    }
}
