package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
                Log.d(TAG,activity.getEdit());
            }
        });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {

    }
}
