package com.zia.gankcqupt_mvp.presenter.Activity.Interface;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Created by zia on 2017/5/19.
 */

public interface IRecyclerPresenter {
    void setToolbar(Toolbar toolbar);
    void setSwipeLayout(SwipeRefreshLayout swipeLayout);
    void setRecycler(RecyclerView recycler);
    void changeCard();
    void reSort();
    void showStudent();
}
