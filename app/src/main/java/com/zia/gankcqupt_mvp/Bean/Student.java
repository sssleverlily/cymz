package com.zia.gankcqupt_mvp.Bean;

import java.io.Serializable;

/**
 * Created by zia on 2017/3/21.
 */

public class Student implements Serializable {
    public String classnum;
    public String studentid;
    public String name;
    public String sex;
    public String classid;
    public String zyh;
    public String major;
    public String college;
    public String year;
    public String atschool;

    @Override
    public String toString() {
        return name + " \n" + studentid;
    }

    public String getAtschool() {
        return atschool;
    }

    public void setAtschool(String atschool) {
        this.atschool = atschool;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassnum() {
        return classnum;
    }

    public void setClassnum(String classnum) {
        this.classnum = classnum;
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

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
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
