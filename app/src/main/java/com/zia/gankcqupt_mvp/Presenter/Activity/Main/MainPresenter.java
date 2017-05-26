package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Model.GetProgress;
import com.zia.gankcqupt_mvp.Model.GetStudent;
import com.zia.gankcqupt_mvp.Model.OnAllStudentGet;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IMainPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IMainActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public class MainPresenter implements IMainPresenter {

    private static final String TAG = "MainPresenterTest";
    private Context context;
    private IMainActivity mainActivity;
    public static List<Student> students = new ArrayList<>();
    public static List<Student> favorites = new ArrayList<>();


    public MainPresenter(MainActivity mainActivity){
        context = mainActivity;
        this.mainActivity = mainActivity;
    }

    @Override
    public void setDialog() {

    }

    @Override
    public void setPager() {
        ViewPager viewPager = mainActivity.getViewPager();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mainActivity.getPagerAdapter());
        TabLayout tabLayout = mainActivity.getTablayout();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void setToolbar() {
        Toolbar toolbar = mainActivity.getToolBar();
        toolbar.setTitle("重邮妹子");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setFocusable(true);//启动app时把焦点放在其他控件（不放在editext上）上防止弹出虚拟键盘
        toolbar.setFocusableInTouchMode(true);
        toolbar.requestFocus();
    }

    /**
     * 获取学生数据，储存到MainPresenter里
     */
    @Override
    public void getData() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("正在从数据库获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("耐心等待..");
        GetStudent getStudent = new GetStudent(context);
        getStudent.getFromDB(dialog);
    }


    @Override
    public List<Student> getStudentList() {
        return students;
    }
}
