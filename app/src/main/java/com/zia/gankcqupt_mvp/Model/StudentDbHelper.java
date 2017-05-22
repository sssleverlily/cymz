package com.zia.gankcqupt_mvp.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zia on 2017/5/18.
 */

public class StudentDbHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String CREATE_STUDENT = "create table Student (" +
            "id integer primary key autoincrement," +
            "atschool text," +
            "classid text," +
            "classnum text," +
            "college text," +
            "major text," +
            "name text," +
            "sex text," +
            "studentid text," +
            "year text," +
            "zyh text)";

    public static final String CREATE_FAVORITE = "create table Favorite (" +
            "id integer primary key autoincrement," +
            "atschool text," +
            "classid text," +
            "classnum text," +
            "college text," +
            "major text," +
            "name text," +
            "sex text," +
            "studentid text," +
            "year text," +
            "zyh text)";


    public StudentDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STUDENT);
        sqLiteDatabase.execSQL(CREATE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
