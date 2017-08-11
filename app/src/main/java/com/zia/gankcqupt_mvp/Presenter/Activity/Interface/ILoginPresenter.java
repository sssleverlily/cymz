package com.zia.gankcqupt_mvp.Presenter.Activity.Interface;

import com.zia.gankcqupt_mvp.View.Activity.Interface.LoginImp;

/**
 * Created by zia on 2017/5/28.
 */

public interface ILoginPresenter {
    void gotoRegisterPage();
    void gotoMainPage();
    void login(String username,String password);
}
