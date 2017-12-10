package com.zia.gankcqupt_mvp.Model;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.zia.gankcqupt_mvp.Adapter.SocialAdapter;
import com.zia.gankcqupt_mvp.Bean.Title;
import com.zia.gankcqupt_mvp.Util.UserInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
    public void getTitlesAndShow(final SocialAdapter adapter, final List<Title> titles) {
        titles.clear();
        Observable
                .create(new ObservableOnSubscribe<Title>() {
                    @Override
                    public void subscribe(@NonNull final ObservableEmitter<Title> e) throws Exception {
                        AVQuery<AVObject> titleQuery = new AVQuery<>("Title");
                        List<AVObject> titleList = titleQuery.find();
                        for (AVObject o: titleList) {
                            final Title title = new Title();
                            title.setUserId(o.getString("userId"));
                            UserInfo userInfo = new UserInfo(title.getUserId());
                            Log.d(TAG, userInfo.getNickname());
                            title.setAuthor(userInfo.getNickname());
                            title.setHeadUrl(userInfo.getThumbnailUrl(100,100));
                            title.setContent(o.getString("content"));
                            DateFormat dateFormat = new SimpleDateFormat("MM/dd  hh:mm");
                            title.setTime(dateFormat.format(o.getUpdatedAt()));
                            title.setUpdatedAt(o.getUpdatedAt());
                            title.setCreatedAt(o.getCreatedAt());
                            title.setTitle(o.getString("title"));
                            title.setObjectId(o.getObjectId());
                            title.setCount(String.valueOf((int)o.get("count"))+"条评论");
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
                        titles.add(title);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Collections.sort(titles, new Comparator<Title>() {
                            @Override
                            public int compare(Title title, Title t1) {
                                return t1.getTime().compareTo(title.getTime());
                            }
                        });
                        Log.d(TAG,titles.toString());
                        adapter.refreshData(titles);
                        if(refreshLayout != null) refreshLayout.setRefreshing(false);

                    }
                });
    }
}
