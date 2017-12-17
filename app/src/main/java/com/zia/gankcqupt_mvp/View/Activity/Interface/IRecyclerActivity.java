package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zia on 2017/5/19.
 */

public interface IRecyclerActivity extends BaseInterface {
    String getFlag();
    SwipeRefreshLayout getSwipeFreshLayout();
    RecyclerView getRecyclerView();
}
