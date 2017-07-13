package com.zia.gankcqupt_mvp.Presenter.Fragment.Main;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zia.gankcqupt_mvp.Adapter.SocialAdapter;
import com.zia.gankcqupt_mvp.Bean.Title;
import com.zia.gankcqupt_mvp.Model.GetTitle;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.ISocialPresenter;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.ISocialFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.SocialFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/29.
 */

public class SocialPresenter implements ISocialPresenter {

    private Context context;
    private ISocialFragment fragment;
    public static SocialAdapter adapter;
    public static List<Title> titles = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout = null;


    public SocialPresenter(SocialFragment fragment){
        this.context = fragment.getContext();
        this.fragment = fragment;
    }

    @Override
    public void getData() {
        titles.clear();
        fragment.getWaitText().setVisibility(View.GONE);
        GetTitle getTitle = new GetTitle(context);
        if(swipeRefreshLayout != null)
        getTitle.setRefreshLayout(swipeRefreshLayout);
        getTitle.getTitlesAndShow();
        //adapter.refreshData(titles);
    }

    @Override
    public void setSwipeLayout(SwipeRefreshLayout layout) {
        swipeRefreshLayout = layout;
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {
        adapter = new SocialAdapter(context);
        recycler.setLayoutManager(new GridLayoutManager(context, 1));
        recycler.setAdapter(adapter);
    }
}
