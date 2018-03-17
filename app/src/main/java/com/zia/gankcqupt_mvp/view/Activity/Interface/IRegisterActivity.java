package com.zia.gankcqupt_mvp.view.Activity.Interface;

/**
 * Created by zia on 2017/5/28.
 */

public interface IRegisterActivity extends LoginImp, BaseInterface {
    String getUsername();
    String getPassword();
    String getNickname();
    void showDialog();
    void hideDialog();
    void setNicknameFormatError();
}
