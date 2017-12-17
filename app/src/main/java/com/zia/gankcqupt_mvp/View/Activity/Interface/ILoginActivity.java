package com.zia.gankcqupt_mvp.View.Activity.Interface;

/**
 * Created by zia on 2017/5/28.
 */

public interface ILoginActivity extends LoginImp, BaseInterface {
    String getUsername();
    String getPassword();
    void showDialog();
    void hideDialog();
}
