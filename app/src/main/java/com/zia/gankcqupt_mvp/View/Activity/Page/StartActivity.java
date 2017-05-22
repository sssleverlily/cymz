package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IStartPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.StartPresenter;
import com.zia.gankcqupt_mvp.R;

public class StartActivity extends AppCompatActivity {

    private IStartPresenter iStartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        iStartPresenter = new StartPresenter(this);
        iStartPresenter.gotoMainPage();
    }

}
