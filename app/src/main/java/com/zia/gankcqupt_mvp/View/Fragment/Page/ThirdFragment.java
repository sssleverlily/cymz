package com.zia.gankcqupt_mvp.View.Fragment.Page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IThirdPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.ThirdPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IThirdFragment;

/**
 * Created by zia on 2017/5/18.
 */

public class ThirdFragment extends Fragment implements IThirdFragment {

    private View view;
    private Button upData,size,downLoad,favorite;
    private TextView tv;
    private IThirdPresenter thirdPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findWidgets();
        thirdPresenter = new ThirdPresenter(this);
        setClick();
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
    }

    private void findWidgets(){
        upData = (Button)view.findViewById(R.id.third_upData);
        size = (Button)view.findViewById(R.id.third_size);
        downLoad = (Button)view.findViewById(R.id.third_downLoad);
        favorite = (Button)view.findViewById(R.id.third_favorite);
        tv = (TextView) view.findViewById(R.id.third_tv);
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
