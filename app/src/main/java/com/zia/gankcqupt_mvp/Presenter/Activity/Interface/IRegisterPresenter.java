package com.zia.gankcqupt_mvp.Presenter.Activity.Interface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by zia on 2017/5/28.
 */

public interface IRegisterPresenter {
    void setSex(String sex);
    void register(String username,String password,String nickname);
    void setImage(ImageView image,Intent data);
    Bitmap changeImage(ImageView image, Intent data);
}
