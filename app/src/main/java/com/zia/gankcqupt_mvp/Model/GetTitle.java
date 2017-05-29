package com.zia.gankcqupt_mvp.Model;

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

/**
 * Created by zia on 2017/5/29.
 */

public class GetTitle {
    private static final String TAG = "GetTitleTest";
    private Context context;
    private SwipeRefreshLayout refreshLayout = null;

    public GetTitle(Context context){
        this.context = context;
    }

    public void setRefreshLayout(SwipeRefreshLayout swipeRefreshLayout){
        refreshLayout = swipeRefreshLayout;
    }

    public void getTitlesAndShow(){
        SocialPresenter.titles.clear();
        AVQuery<AVObject> query = new AVQuery<>("Title");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e != null){
                    Log.d(TAG,"getTitlesAndShow error!");
                }else{
                    for (AVObject o : list) {
                        Title title = new Title();
                        title.setAuthor(o.getString("author"));
                        title.setContent(o.getString("content"));
                        title.setHeadUrl(o.getString("headImage"));
                        /*SimpleDateFormat format = new SimpleDateFormat("MM/dd  hh:mm");
                        title.setTime(format.format(o.getUpdatedAt()));*/
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd  hh:mm");
                        title.setTime(dateFormat.format(o.getUpdatedAt()));
                        title.setTitle(o.getString("title"));
                        title.setObjectId(o.getObjectId());
                        Log.d(TAG,title.toString());
                        SocialPresenter.titles.add(title);
                    }
                }
                SocialPresenter.titles = resort(SocialPresenter.titles);
                if(refreshLayout != null)
                refreshLayout.setRefreshing(false);
                SocialPresenter.adapter.refreshData(SocialPresenter.titles);
            }
        });
    }

    private List<Title> resort(List<Title> list){
        int i;List<Title> l = new ArrayList<>();
        for(i=list.size()-1;i>=0;i--){
            l.add(list.get(i));
        }
        return l;
    }
}
