package com.zia.gankcqupt_mvp.View.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zia.gankcqupt_mvp.Util.ToastUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.BaseInterface;

/**
 * Created by zia on 2017/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findWidgets();
    }

    protected abstract void findWidgets();

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void toast(String str) {
        ToastUtil.showToast(this, str);
    }
}
