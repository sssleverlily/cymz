package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.zia.gankcqupt_mvp.Bean.Title;

/**
 * Created by zia on 17-7-11.
 */

public interface IReplyActivity {
    String getEdit();
    String getObjectId();
    String getUserId();
    Title getFirstTitle();
    void clearEdit();
    RecyclerView getRecycler();
}
