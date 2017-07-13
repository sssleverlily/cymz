package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.zia.gankcqupt_mvp.Adapter.PagerAdapter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IMainPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IMainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private IMainPresenter mainPresenter;
    private final static String TAG = "MainActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        findWidgets();
        mainPresenter = new MainPresenter(this);
        mainPresenter.setToolbar();
        mainPresenter.setFloatingBar();
        mainPresenter.setPager();
        if(mainPresenter.getStudentList().size() == 0)
        mainPresenter.getData();
    }

    private void findWidgets(){
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        toolbar = (Toolbar)findViewById(R.id.mToolbar);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingBar);
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    public TabLayout getTablayout() {
        return tabLayout;
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return new PagerAdapter(getSupportFragmentManager());
    }

    @Override
    public FloatingActionButton getFloatingBar() {
        return floatingActionButton;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    mainPresenter.getRawImage(data);
                }
                break;
            case 2:
                    mainPresenter.updataImage(data);
                break;
        }
    }

}
