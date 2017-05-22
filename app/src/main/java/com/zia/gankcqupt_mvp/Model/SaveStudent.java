package com.zia.gankcqupt_mvp.Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Util.StudentUtil;

import java.util.List;

/**
 * Created by zia on 2017/5/19.
 */

public class SaveStudent {
    private final static String TAG = "SaveStudent";
    private List<Student> studentList;
    private Context context;
    private SQLiteDatabase database;
    private int p = 0,i = 0,count = 0;//进度条相关

    public SaveStudent(List<Student> studentList, Context context){
        this.studentList = studentList;
        this.context = context;
        StudentDbHelper helper = new StudentDbHelper(context,"cymz.db",null,1);
        database = helper.getWritableDatabase();
    }

    public void save(GetProgress progress,OnFinishCallBack finishCallBack){
        i=0;p=0;
        progress.status(p);
        int size = studentList.size();
        for (Student student : studentList) {
            i++;
            if(p != (int)((float) i /(float)size*100)){
                p = (int)((float) i /(float)size*100);
                progress.status(p);
                Log.d(TAG,"存入数据库进度： " + p);
            }
            count++;
            Log.d(TAG,"正在保存第"+i+"个学生");
            database.insert("Student",null,StudentUtil.student2values(student));
        }
        finishCallBack.OnFinish();
    }
}
