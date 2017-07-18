package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.widget.ImageView;

import com.zia.gankcqupt_mvp.Bean.Student;

/**
 * Created by zia on 2017/5/19.
 */

public interface IDetailActivity {
    Student getStu();
    boolean getIsFour();
    void setData(String name,String id,String major,String classId,String year);
    ImageView getImageView();
    void toast(String msg);
    void setButtonColor(int color);
}
