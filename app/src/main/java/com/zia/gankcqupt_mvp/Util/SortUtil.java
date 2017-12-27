package com.zia.gankcqupt_mvp.Util;

import com.zia.gankcqupt_mvp.Bean.Student;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zia on 2017/12/27.
 */

public class SortUtil {

    //按年级排序，新生在上面
    public static void reSortByYear(List<Student> list) {
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                return Integer.parseInt(t1.getYear()) - Integer.parseInt(student.getYear());
            }
        });
    }
}
