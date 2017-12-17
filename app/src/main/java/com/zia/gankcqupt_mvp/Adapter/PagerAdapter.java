package com.zia.gankcqupt_mvp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zia.gankcqupt_mvp.View.Fragment.Page.MeFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.SearchFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.SortFragment;

/**
 * Created by zia on 2017/5/18.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"查询", "分类", "更多"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new SearchFragment();
        }else if(position == 1){
            return new SortFragment();
        }else if(position == 2){
            return new MeFragment();
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
