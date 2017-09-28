package com.zia.gankcqupt_mvp.Bean;

import java.util.List;

/**
 * Created by zia on 2017/9/28.
 */

public class Students {

    /**
     * code : 200
     * data : [{"atschool":"在校","classid":"L0321701","classnum":"11","college":"经济管理学院","major":"工商管理(留学生)","name":"田红玉","sex":"女","studentid":"L201730023","year":"2017","zyh":"L032"}]
     * error : false
     */

    private int code;
    private boolean error;
    private List<Student> students;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
