package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.zia.gankcqupt_mvp.Adapter.RecyclerAdapter;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IRecyclerPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.UserDataUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IRecyclerActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RecyclerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zia on 2017/5/19.
 */

public class RecyclerPresenter implements IRecyclerPresenter {

    private IRecyclerActivity activity;
    public static RecyclerAdapter adapter;
    public static boolean isFavoriteList = false;
    private List<Student> newList = new ArrayList<>();
    private boolean ISFOUR = true;
    private StaggeredGridLayoutManager manager;

    public RecyclerPresenter(IRecyclerActivity recyclerActivity) {
        this.activity = recyclerActivity;
    }

    //倒序排列方法
    private void reSort(List<Student> avObjectList) {
        Collections.sort(avObjectList, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                return Integer.parseInt(t1.getYear()) - Integer.parseInt(student.getYear());
            }
        });
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        if (activity.getFlag().equals("favorite")){
            toolbar.setTitle("我的收藏");
            isFavoriteList = true;
        }
        else {
            toolbar.setTitle("看妹子");
            isFavoriteList = false;
        }
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void setSwipeLayout(final SwipeRefreshLayout swipeLayout) {
        swipeLayout.setColorSchemeColors(activity.getActivity().getResources().getColor(R.color.colorPrimary));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(activity.getFlag().equals("favorite")){
                    refreshByCloud();
                }else{
                    Collections.shuffle(newList);
                    adapter.reFresh(newList,ISFOUR);
                    swipeLayout.setRefreshing(false);
                    //重置，防止继续下载图片
                    adapter.notifyDataSetChanged();
                    activity.getRecyclerView().scrollToPosition(0);
                }
            }
        });
    }

    private void refreshByCloud(){
        UserDataUtil.pullFavorites(new UserDataUtil.PullListener() {
            @Override
            public void onPulled() {
                activity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.reFresh(MainPresenter.favorites,ISFOUR);
                        if(activity.getSwipeFreshLayout() != null)
                            activity.getSwipeFreshLayout().setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        manager.invalidateSpanAssignments();//防止第一行到顶部有空白区域
        recycler.setLayoutManager(manager);
        adapter = new RecyclerAdapter(activity.getActivity());
        recycler.setAdapter(adapter);
        if(MainPresenter.favorites.size() == 0){
            refreshByCloud();
        }
    }

    @Override
    public void changeCard() {
        ISFOUR = !ISFOUR;
        adapter.reFresh(newList,ISFOUR);
    }

    @Override
    public void reSort() {
        Collections.shuffle(newList);
        adapter.reFresh(newList,ISFOUR);
    }

    @Override
    public void showStudent() {
        newList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String value = activity.getFlag();
                if(value.equals("2017全部妹子")){
                    for (Student temp : MainPresenter.students) {
                        if (temp.getSex().equals("女") && temp.getYear().equals("2017")) {
                            newList.add(temp);
                        }
                    }
                }
                //如果是加载收藏，执行这个
                else if(value.equals("favorite")){
                    newList = MainPresenter.favorites;
                }
                //传入字符串是学院
                else if(value.charAt(value.length()-1) == '院') {
                    for (Student temp : MainPresenter.students) {
                        if (temp.getSex().equals("女") && temp.getCollege().equals(value)) {
                            newList.add(temp);
                        }
                    }
                }
                //传入字符串是专业
                else{
                    for (Student temp : MainPresenter.students) {
                        if (temp.getSex().equals("女") && temp.getMajor().equals(value)) {
                            newList.add(temp);
                        }
                    }
                }
                //倒序排列
                reSort(newList);
                activity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.reFresh(newList,ISFOUR);
                        activity.toast("捕获妹子： " + newList.size() + "个");
                    }
                });
            }
        }).start();
    }
}
