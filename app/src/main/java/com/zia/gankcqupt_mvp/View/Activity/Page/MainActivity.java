package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.Toast;

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
    private ProgressDialog dialog;
    private final static String TAG = "MainActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        findWidgets();
        setToolBar();
        mainPresenter = new MainPresenter(this);
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
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setToolBar() {
        toolbar.setTitle("重邮妹子");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setFocusable(true);//启动app时把焦点放在其他控件（不放在editext上）上防止弹出虚拟键盘
        toolbar.setFocusableInTouchMode(true);
        toolbar.requestFocus();
    }

    @Override
    public void showDialog() {
        if(dialog == null) dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("正在从数据库获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("耐心等待..");
    }

    @Override
    public void hideDialog() {
        if (dialog == null) return;
        dialog.hide();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public ProgressDialog getDialog() {
        return dialog;
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
