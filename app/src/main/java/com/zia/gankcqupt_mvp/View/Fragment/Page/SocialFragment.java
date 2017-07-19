package com.zia.gankcqupt_mvp.View.Fragment.Page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.ISocialPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.SocialPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.ISocialFragment;

/**
 * Created by zia on 2017/5/28.
 */

public class SocialFragment extends Fragment implements ISocialFragment {

    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textView;
    private ISocialPresenter presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new SocialPresenter(this);
        findWidgets();
        presenter.setRecycler(recyclerView);
        presenter.setSwipeLayout(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });
    }

    private void findWidgets() {
        recyclerView = (RecyclerView)view.findViewById(R.id.social_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.social_swipe);
        textView = (TextView)view.findViewById(R.id.social_wait);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_social,container,false);
        return view;
    }

    @Override
    public TextView getWaitText() {
        return textView;
    }
}
