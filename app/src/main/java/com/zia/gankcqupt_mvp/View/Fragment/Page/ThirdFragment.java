package com.zia.gankcqupt_mvp.View.Fragment.Page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IThirdPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.ThirdPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IThirdFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zia on 2017/5/18.
 */

public class ThirdFragment extends Fragment implements IThirdFragment {

    private View view;
    private Button upData,size,downLoad,favorite,out;
    private TextView tv,nickname,sex;
    private ImageView head;
    private LinearLayout userLayout;
    private IThirdPresenter thirdPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findWidgets();
        thirdPresenter = new ThirdPresenter(this);
        setClick();
        thirdPresenter.setUser(nickname,sex,head);
    }

    private void setClick(){
        upData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdPresenter.upData();
            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdPresenter.showDataCount();
            }
        });
        downLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdPresenter.downLoad();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {thirdPresenter.gotoFavoritePage();
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdPresenter.loginOut();
            }
        });
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {thirdPresenter.changeORlogin();
            }
        });
    }

    private void findWidgets(){
        upData = (Button)view.findViewById(R.id.third_upData);
        size = (Button)view.findViewById(R.id.third_size);
        downLoad = (Button)view.findViewById(R.id.third_downLoad);
        favorite = (Button)view.findViewById(R.id.third_favorite);
        out = (Button)view.findViewById(R.id.third_loginOut);
        tv = (TextView) view.findViewById(R.id.third_tv);
        head = (CircleImageView)view.findViewById(R.id.third_image);
        nickname = (TextView)view.findViewById(R.id.third_nickname);
        sex = (TextView)view.findViewById(R.id.third_sex);
        userLayout = (LinearLayout)view.findViewById(R.id.third_userLayout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third,container,false);
        return view;
    }

    @Override
    public TextView getTextView() {
        return tv;
    }
}
