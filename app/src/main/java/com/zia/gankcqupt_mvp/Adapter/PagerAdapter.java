package com.zia.gankcqupt_mvp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zia.gankcqupt_mvp.View.Fragment.Page.FirstFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.SecondFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.ThirdFragment;

/**
 * Created by zia on 2017/5/18.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"查询","随便看看","更多"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FirstFragment();
        }else if(position == 1){
            return new SecondFragment();
        }else if(position == 2){
            return new ThirdFragment();
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
