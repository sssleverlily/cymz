package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.zia.gankcqupt_mvp.Bean.Title;

/**
 * Created by zia on 17-7-11.
 */

public interface IReplyActivity {
    String getEdit();
    void toast(String msg);
    Activity getActivity();
    Intent intent();
    void setToolbar(String title,int color);
    ProgressDialog getDialog();
    RecyclerView getRecyclerView();
    EditText getEditText();
}
