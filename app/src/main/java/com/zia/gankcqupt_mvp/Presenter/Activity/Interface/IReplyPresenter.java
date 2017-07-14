package com.zia.gankcqupt_mvp.Presenter.Activity.Interface;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

/**
 * Created by zia on 17-7-11.
 */

public interface IReplyPresenter {
    void setButton(Button button);
    void setRecycler(RecyclerView recycler);
    void showData();
    void setToolBar(Toolbar toolBar);
}
