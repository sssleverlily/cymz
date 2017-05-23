package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.zia.gankcqupt_mvp.Adapter.PagerAdapter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IMainPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IMainActivity;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ProgressDialog dialog;
    private IMainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        findWidgets();
        mainPresenter = new MainPresenter(this);
        mainPresenter.setToolbar();
        mainPresenter.setPager();
        if(mainPresenter.getStudentList().size() == 0)
        mainPresenter.getData();
    }

    private void findWidgets(){
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        toolbar = (Toolbar)findViewById(R.id.mToolbar);

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


}
