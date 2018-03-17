package com.zia.gankcqupt_mvp.presenter.Fragment.Interface;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zia on 2017/5/29.
 */

public interface ISocialPresenter {
    void getData();
    void setSwipeLayout(SwipeRefreshLayout layout);
    void setRecycler(RecyclerView recycler);
}
