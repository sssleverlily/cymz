package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avos.avoscloud.AVOSCloud;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IStartPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.StartPresenter;
import com.zia.gankcqupt_mvp.R;

public class StartActivity extends AppCompatActivity {

    private IStartPresenter iStartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_start);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AVOSCloud.initialize(StartActivity.this,"xKvysVojCOylmIQtqEVGaYQ3-gzGzoHsz","qRgNqRCPJt9rRsqkftCzvOS1");
                iStartPresenter = new StartPresenter(StartActivity.this);
                iStartPresenter.gotoPage();//presenter内判断是否登录过
            }
        }, 500);

    }

}
