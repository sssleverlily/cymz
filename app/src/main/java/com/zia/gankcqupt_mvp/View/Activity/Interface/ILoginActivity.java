package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.widget.TextView;

/**
 * Created by zia on 2017/5/28.
 */

public interface ILoginActivity {
    String getUsername();
    String getPassword();
    void showDialog();
    void hideDialog();
    void toast(String msg);
}
