package com.zia.gankcqupt_mvp.view.Fragment.Interface;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by zia on 2017/5/18.
 */

public interface IMeFragment {
    TextView getTextView();
    void toast(String msg);
    Activity activity();
}
