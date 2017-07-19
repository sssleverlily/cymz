package com.zia.gankcqupt_mvp.Presenter.Fragment.Interface;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zia on 2017/5/18.
 */

public interface IMePresenter {
    void showDataCount();
    void downLoad();
    void gotoFavoritePage();
    void upData();
    void loginOut();
    void showUserPop(View view);
    void setUser(TextView nick, TextView sex, ImageView img) throws Exception;
}
