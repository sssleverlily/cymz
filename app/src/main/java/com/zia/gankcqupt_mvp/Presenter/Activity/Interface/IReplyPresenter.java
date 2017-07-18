package com.zia.gankcqupt_mvp.Presenter.Activity.Interface;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.zia.gankcqupt_mvp.Adapter.ReplyAdapter;
import com.zia.gankcqupt_mvp.Bean.Comment;
import com.zia.gankcqupt_mvp.Bean.Title;

import java.util.List;

/**
 * Created by zia on 17-7-11.
 */

public interface IReplyPresenter {
    void setButton(Button button);
    void setRecycler();
    void showData(boolean isTop);
    ReplyAdapter getAdapter();
    String getEdit();
    String getObjectId();
    String getUserId();
    Title getFirstTitle();
}
