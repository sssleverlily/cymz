package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.zia.gankcqupt_mvp.Model.PublishModel;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IPublishPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.ThirdPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IPublishActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.PublishActivity;
import com.zia.gankcqupt_mvp.View.Fragment.Page.ThirdFragment;

/**
 * Created by zia on 2017/5/29.
 */

public class PublishPresenter implements IPublishPresenter {

    private static final String TAG = "PublishPresenterTest";
    private Context context;
    private IPublishActivity activity;
    private String u1,u2,u3,u4,u5;

    public PublishPresenter(PublishActivity activity){
        this.context = activity;
        this.activity = activity;
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.publish_toolbar_publish:
                        if(activity.getTitle1() != null) {
                            PublishModel model = new PublishModel(context);
                            model.PublishTitle(activity);
                        }
                        else {
                            Toast.makeText(context, "把标题写上..", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.publish_toolbar_cancel:
                        ((Activity)context).finish();
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
