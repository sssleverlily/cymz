package com.zia.gankcqupt_mvp.data

/**
 * Created by zia on 2018/3/16.
 */
data class Student(var classNum: String
                   , var studentId: String
                   , var name: String
                   , var sex: String
                   , var classId: String
                   , var zyh: String
                   , var major: String
                   , var college: String
                   , var year: String
                   , var atSchool: String)

data class Students(var code: Int = 400
                    , var error: Boolean
                    , var students: List<Student>?)

data class Empty(var status: Int = 400
                 , var info: String = ""
                 , var empty: String = "")

data class NowTime(var status: Int = 400
                   , var info: String = ""
                   , var week: String = ""
                   , var day: String = ""
                   , var lastUpdate: String = ""
                   , var dataInfo: String = ""
                   , var version: String = ""
                   , var apk: String = "")