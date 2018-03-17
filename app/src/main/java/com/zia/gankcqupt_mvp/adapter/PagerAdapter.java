package com.zia.gankcqupt_mvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zia.gankcqupt_mvp.ui.empty.EmptyFragment;
import com.zia.gankcqupt_mvp.view.Fragment.Page.SearchFragment;
import com.zia.gankcqupt_mvp.view.Fragment.Page.SortFragment;
import com.zia.gankcqupt_mvp.ui.me.MeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"查询", "分类", "空教室", "更多"};
    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new SearchFragment());
        fragments.add(new SortFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new MeFragment());
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 4) {
            return fragments.get(position);
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
