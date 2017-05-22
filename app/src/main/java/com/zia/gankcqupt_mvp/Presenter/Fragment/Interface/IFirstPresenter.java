package com.zia.gankcqupt_mvp.Presenter.Fragment.Interface;

import android.support.v7.widget.RecyclerView;

/**
 * Created by zia on 2017/5/19.
 */

public interface IFirstPresenter {
    void search();//搜索
    void setRecycler(RecyclerView recycler);//设置recycler并设置点击事件
    void setEdit();//设置edit监听
}
