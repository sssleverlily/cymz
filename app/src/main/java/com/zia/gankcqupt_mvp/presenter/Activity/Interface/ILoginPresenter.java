package com.zia.gankcqupt_mvp.presenter.Activity.Interface;

/**
 * Created by zia on 2017/5/28.
 */

public interface ILoginPresenter {
    void gotoRegisterPage();
    void gotoMainPage();
    void login(String username,String password);
}
