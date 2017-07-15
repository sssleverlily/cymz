package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.content.Context;
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
    public IReplyActivity activity = null;
    public ReplyAdapter adapter = null;
    private Context context = null;
    public List<Comment> commentList = new ArrayList<>();
    private ReplyModel model = null;

    public ReplyPresenter(ReplyActivity activity){
        this.activity = activity;
        context = activity;
        model = new ReplyModel(activity);
    }

    @Override
    public void setButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AVUser.getCurrentUser() == null){
                    Toast.makeText((Context)activity, "请先登录", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "请先登录");
                    return;
                }
                if(activity.getEdit().isEmpty()){
                    Toast.makeText((Context) activity, "回复不能为空", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "回复不能为空");
                    return;
                }
                model.sendReply(activity,ReplyPresenter.this);
            }
        });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ReplyAdapter(context);
        recycler.setAdapter(adapter);
    }

    @Override
    public void showData(boolean isTop) {
        commentList.clear();
        model.getDataAndShow(this,isTop);
    }

    @Override
    public void setToolBar(Toolbar toolBar) {
        toolBar.setTitle("回复列表");
        toolBar.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    public RecyclerView getRecycler() {
        return activity.getRecycler();
    }
}
