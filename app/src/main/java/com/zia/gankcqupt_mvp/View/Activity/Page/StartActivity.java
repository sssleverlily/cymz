package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IStartPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.StartPresenter;
import com.zia.gankcqupt_mvp.R;

public class StartActivity extends AppCompatActivity {

    private IStartPresenter iStartPresenter;
    private final static String TAG = "StartActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(StartActivity.this,"xKvysVojCOylmIQtqEVGaYQ3-gzGzoHsz","qRgNqRCPJt9rRsqkftCzvOS1");
        //PushService.setDefaultPushCallback(this, StartActivity.class);
        iStartPresenter = new StartPresenter(StartActivity.this);
        iStartPresenter.gotoMainPage();//presenter内判断是否登录过
    }

}
