package com.zia.gankcqupt_mvp.Bean;

import java.io.Serializable;

/**
 * Created by zia on 2017/3/21.
 */

public class Student implements Serializable {
    public String classNum;
    public String studentId;
    public String name;
    public String sex;
    public String classId;
    public String zyh;
    public String major;
    public String college;
    public String year;
    public String atSchool;

    public String getAtSchool() {
        return atSchool;
    }

    public void setAtSchool(String atSchool) {
        this.atSchool = atSchool;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getZyh() {
        return zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh;
    }
}
