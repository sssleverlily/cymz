package com.zia.gankcqupt_mvp.Model;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zia.gankcqupt_mvp.Bean.Title;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.SocialPresenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zia on 2017/5/29.
 */

public class GetTitle {
    private static final String TAG = "GetTitleTest";
    private Context context;
    private SwipeRefreshLayout refreshLayout = null;

    public GetTitle(Context context) {
        this.context = context;
    }

    public void setRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        refreshLayout = swipeRefreshLayout;
    }

    /**
     * 获取Title集合并刷新adapter显示在recycler里
     */
    public void getTitlesAndShow() {
        SocialPresenter.titles.clear();
        Observable
                .create(new ObservableOnSubscribe<Title>() {
                    @Override
                    public void subscribe(@NonNull final ObservableEmitter<Title> e) throws Exception {
                        AVQuery<AVObject> titleQuery = new AVQuery<>("Title");
                        List<AVObject> titleList = titleQuery.find();
                        for (AVObject o: titleList) {
                            final Title title = new Title();
                            title.setAuthor(o.getString("author"));
                            title.setContent(o.getString("content"));
                            title.setHeadUrl(o.getString("headImage"));
                            SimpleDateFormat format = new SimpleDateFormat("MM/dd  hh:mm");
                            title.setTime(format.format(o.getUpdatedAt()));
                            DateFormat dateFormat = new SimpleDateFormat("MM/dd  hh:mm");
                            title.setTime(dateFormat.format(o.getUpdatedAt()));
                            title.setTitle(o.getString("title"));
                            title.setObjectId(o.getObjectId());
                            AVQuery<AVObject> commentQuery = new AVQuery<AVObject>("Comment");
                            commentQuery.whereEqualTo("targetId", o.getObjectId());
                            List<AVObject> commentList = commentQuery.find();
                            title.setCount(String.valueOf(commentList.size())+"条评论");
                            e.onNext(title);
                        }
                        e.onComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Title>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Title title) {
                        SocialPresenter.titles.add(title);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        SocialPresenter.titles = resort(SocialPresenter.titles);
                        Log.d(TAG,SocialPresenter.titles.toString());
                        if(refreshLayout != null) refreshLayout.setRefreshing(false);
                        SocialPresenter.adapter.refreshData(SocialPresenter.titles);
                    }
                });
    }

    private List<Title> resort(List<Title> list) {
        int i;
        List<Title> l = new ArrayList<>();
        for (i = list.size() - 1; i >= 0; i--) {
            l.add(list.get(i));
        }
        return l;
    }
}
