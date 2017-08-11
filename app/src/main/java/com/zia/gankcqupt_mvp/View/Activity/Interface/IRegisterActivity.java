package com.zia.gankcqupt_mvp.View.Activity.Interface;

import android.app.Activity;

/**
 * Created by zia on 2017/5/28.
 */

public interface IRegisterActivity extends LoginImp {
    String getUsername();
    String getPassword();
    String getNickname();
    void toast(String msg);
    Activity getActivity();
    void showDialog();
    void hideDialog();
    void setNicknameFormatError();
}
