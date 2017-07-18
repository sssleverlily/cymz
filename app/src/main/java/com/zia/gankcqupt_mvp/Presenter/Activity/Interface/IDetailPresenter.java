package com.zia.gankcqupt_mvp.Presenter.Activity.Interface;

import android.widget.Button;

/**
 * Created by zia on 2017/5/19.
 */

public interface IDetailPresenter {
    void showPic();
    void clickFavorite();
    void changeCard();
    void downLoad();
    void setData();
    void setFavoriteColor();
    void setTranslateToolbar();
    void toast(String msg);
}
