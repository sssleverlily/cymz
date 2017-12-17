package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IRecyclerPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.RecyclerPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.SlidingActivity;
import com.zia.gankcqupt_mvp.Util.ToastUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IRecyclerActivity;

public class RecyclerActivity extends SlidingActivity implements IRecyclerActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static String TAG = "RecyclerActivityTest";
    private IRecyclerPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        findWidgets();
        presenter = new RecyclerPresenter(this);
        presenter.setToolbar(toolbar);
        setSupportActionBar(toolbar);
        presenter.setRecycler(recyclerView);
        presenter.setSwipeLayout(swipeRefreshLayout);
        presenter.showStudent();

    }

    @Override
    protected void findWidgets() {
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        toolbar = findViewById(R.id.recycler_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_refresh:
                presenter.reSort();
                break;
            case R.id.toolbar_switch:
                presenter.changeCard();
                break;
        }
        return true;
    }

    @Override
    public String getFlag() {
        return getIntent().getStringExtra("flag");
    }

    @Override
    public void toast(String msg) {
        ToastUtil.showToast(this,msg);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public SwipeRefreshLayout getSwipeFreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
