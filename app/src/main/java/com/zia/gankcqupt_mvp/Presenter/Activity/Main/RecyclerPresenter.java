package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Adapter.RecyclerAdapter;
import com.zia.gankcqupt_mvp.Adapter.RecyclerOnClickListener;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IRecyclerPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IRecyclerActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.DetailActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RecyclerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zia on 2017/5/19.
 */

public class RecyclerPresenter implements IRecyclerPresenter {

    private IRecyclerActivity activity;
    public static RecyclerAdapter adapter;
    public static boolean isFavorateList = false;
    private List<Student> newlist = new ArrayList<Student>();
    private boolean ISFOUR = true;

    public RecyclerPresenter(RecyclerActivity recyclerActivity){
        this.activity = recyclerActivity;
    }

    //倒序排列方法
    private List<Student> reSort(List<Student> avObjectList){
        int i;List<Student> list = new ArrayList<>();
        for(i=avObjectList.size()-1;i>=0;i--){
            list.add(avObjectList.get(i));
        }
        return list;
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        if (activity.getFlag().equals("favorite")){
            toolbar.setTitle("我的收藏");
            isFavorateList = true;
        }
        else {
            toolbar.setTitle("看妹子");
            isFavorateList = false;
        }
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void setSwipeLayout(final SwipeRefreshLayout swipeLayout) {
        swipeLayout.setColorSchemeColors(activity.getActivity().getResources().getColor(R.color.colorPrimary));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Collections.shuffle(newlist);
                adapter.reFresh(newlist,ISFOUR);
                swipeLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new RecyclerAdapter(activity.getActivity());
        recycler.setAdapter(adapter);
    }

    @Override
    public void changeCard() {
        ISFOUR = !ISFOUR;
        adapter.reFresh(newlist,ISFOUR);
    }

    @Override
    public void reSort() {
        Collections.shuffle(newlist);
        adapter.reFresh(newlist,ISFOUR);
    }

    @Override
    public void showStudent() {
        newlist.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String value = activity.getFlag();
                if(value.equals("2017全部妹子")){
                    for (Student temp : MainPresenter.students) {
                        if (temp.getSex().equals("女") && temp.getYear().equals("2017")) {
                            newlist.add(temp);
                        }
                    }
                }
                //如果是加载收藏，执行这个
                else if(value.equals("favorite")){
                    newlist = MainPresenter.favorites;
                }
                //传入字符串是学院
                else if(value.charAt(value.length()-1) == '院') {
                    for (Student temp : MainPresenter.students) {
                        if (temp.getSex().equals("女") && temp.getCollege().equals(value)) {
                            newlist.add(temp);
                        }
                    }
                }
                //传入字符串是专业
                else{
                    for (Student temp : MainPresenter.students) {
                        if (temp.getSex().equals("女") && temp.getMajor().equals(value)) {
                            newlist.add(temp);
                        }
                    }
                }
                //倒序排列
                newlist = reSort(newlist);
                activity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.reFresh(newlist,ISFOUR);
                        activity.toast("捕获妹子： " + newlist.size() + "个");
                    }
                });
            }
        }).start();
    }
}
