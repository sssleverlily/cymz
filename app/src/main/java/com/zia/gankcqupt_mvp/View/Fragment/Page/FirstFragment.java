package com.zia.gankcqupt_mvp.View.Fragment.Page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IFirstPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.FirstPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IFirstFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public class FirstFragment extends Fragment implements IFirstFragment {

    private View view;
    private EditText editText = null;
    private RecyclerView recyclerView = null;
    private IFirstPresenter firstPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findWidgets();
        firstPresenter = new FirstPresenter(this);
        firstPresenter.setRecycler(recyclerView);
        firstPresenter.setEdit();
    }

    private void findWidgets(){
        editText = (EditText) view.findViewById(R.id.first_edit);
        recyclerView = (RecyclerView) view.findViewById(R.id.first_recyclerView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public EditText getEdit() {
        return editText;
    }
}
