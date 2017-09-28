package com.zia.gankcqupt_mvp.View.Fragment.Page;

import android.app.Activity;
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
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IMePresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.MePresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IMeFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zia on 2017/5/18.
 */

public class MeFragment extends Fragment implements IMeFragment {

    private View view;
    private Button upData,size,downLoad,favorite,out,changeRoot;
    private TextView tv,nickname,sex;
    private ImageView head;
    private LinearLayout userLayout;
    private IMePresenter presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findWidgets();
        presenter = new MePresenter(this);
        setClick();
        try {
            presenter.setUser(nickname,sex,head);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setClick(){
        upData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.upData();
            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showDataCount();
            }
        });
        downLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.downLoad();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.gotoFavoritePage();
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loginOut();
            }
        });
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showUserPop(view);}
        });
        changeRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.changeRoot();
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
        changeRoot = (Button)view.findViewById(R.id.third_changeRoot);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me,container,false);
        return view;
    }

    @Override
    public TextView getTextView() {
        return tv;
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }
}
