package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;


import com.avos.avoscloud.AVUser;
import com.zia.gankcqupt_mvp.Model.PublishModel;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IPublishPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IPublishActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.PublishActivity;

/**
 * Created by zia on 2017/5/29.
 */

public class PublishPresenter implements IPublishPresenter {

    private static final String TAG = "PublishPresenterTest";
    private IPublishActivity activity;
    private String u1,u2,u3,u4,u5;

    public PublishPresenter(PublishActivity activity){
        this.activity = activity;
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.publish_toolbar_publish:
                        if(AVUser.getCurrentUser() == null){
                            activity.toast("请先登录");
                            break;
                        }
                        if(activity.getTitleString() == null) {
                            activity.toast("把标题写上..");
                            break;
                        }
                        PublishModel model = new PublishModel(activity.getActivity());
                        model.PublishTitle(activity.getTitleString(),activity.getContent());
                        break;
                    case R.id.publish_toolbar_cancel:
                        activity.getActivity().finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setImage(ImageView i1, ImageView i2, ImageView i3, ImageView i4, ImageView i5) {

    }
}
