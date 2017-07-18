package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.zia.gankcqupt_mvp.Adapter.ReplyAdapter;
import com.zia.gankcqupt_mvp.Bean.Comment;
import com.zia.gankcqupt_mvp.Bean.Title;
import com.zia.gankcqupt_mvp.Model.ReplyModel;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IReplyPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IReplyActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.ReplyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 17-7-11.
 */

public class ReplyPresenter implements IReplyPresenter {

    private final static String TAG = "ReplyPresenterTest";
    private IReplyActivity activity = null;
    private ReplyAdapter adapter = null;
    private List<Comment> commentList = new ArrayList<>();
    private ReplyModel model = null;

    public ReplyPresenter(ReplyActivity activity){
        this.activity = activity;
        model = new ReplyModel(activity);
    }

    @Override
    public void setButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AVUser.getCurrentUser() == null){
                    activity.toast("请先登录");
                    Log.d(TAG, "请先登录");
                    return;
                }
                if(activity.getEdit().isEmpty()){
                    activity.toast("回复不能为空");
                    Log.d(TAG, "回复不能为空");
                    return;
                }
                model.sendReply(ReplyPresenter.this);
            }
        });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(activity.getActivity()));
        adapter = new ReplyAdapter(activity.getActivity());
        recycler.setAdapter(adapter);
    }

    @Override
    public void showData(boolean isTop) {
        commentList.clear();
        model.getDataAndShow(this,isTop);
    }


    @Override
    public RecyclerView getRecycler() {
        return activity.getRecycler();
    }

    @Override
    public ReplyAdapter getAdapter() {
        return adapter;
    }

    @Override
    public String getEdit() {
        return activity.getEdit();
    }

    @Override
    public String getObjectId() {
        Intent intent = activity.intent();
        if (intent != null){
            Log.d(TAG,"get objID: "+intent.getStringExtra("objectId"));
            if(intent.getStringExtra("objectId") != null){
                return intent.getStringExtra("objectId");
            }
        }
        return "get objectId error!";
    }

    @Override
    public String getUserId() {
        Intent intent = activity.intent();
        if (intent != null){
            Log.d(TAG,"get userID: "+intent.getStringExtra("userId"));
            if(intent.getStringExtra("userId") != null){
                return intent.getStringExtra("userId");
            }
        }
        return "get userId error!";
    }

    @Override
    public List<Comment> getComments() {
        return commentList;
    }

    @Override
    public void clearEdit() {
        activity.clearEdit();
    }

    @Override
    public Title getFirstTitle() {
        Intent intent = activity.intent();
        if(intent != null){
            Title title = (Title)intent.getSerializableExtra("title");
            Log.d(TAG,"get Title:"+title.toString());
            return title;
        }
        return null;
    }

    @Override
    public void showDialog() {
        activity.showDialog();
    }

    @Override
    public void hideDialog() {
        activity.hideDialog();
    }
}
