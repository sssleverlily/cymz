package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.zia.gankcqupt_mvp.Model.ReplyModel;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IReplyPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IReplyActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.ReplyActivity;

/**
 * Created by zia on 17-7-11.
 */

public class ReplyPresenter implements IReplyPresenter {

    private final static String TAG = "ReplyPresenterTest";
    private IReplyActivity activity = null;

    public ReplyPresenter(ReplyActivity activity){
        this.activity = activity;
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
                ReplyModel model = new ReplyModel((Context)activity);
                model.sendReply(activity);
            }
        });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {

    }
}
