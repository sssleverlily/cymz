package com.zia.gankcqupt_mvp.view.Activity.Interface;

import android.widget.Button;
import android.widget.ImageView;

import com.zia.gankcqupt_mvp.bean.Student;

/**
 * Created by zia on 2017/5/19.
 */

public interface IDetailActivity extends BaseInterface {
    Student getStu();
    boolean getIsFour();
    void setData(String name,String id,String major,String classId,String year);
    ImageView getImageView();
    void setButtonColor(int color);
    Button getCardButton();
}
