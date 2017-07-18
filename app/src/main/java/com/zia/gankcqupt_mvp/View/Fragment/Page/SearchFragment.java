package com.zia.gankcqupt_mvp.View.Fragment.Page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.ISearchPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.SearchPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IFirstFragment;

/**
 * Created by zia on 2017/5/18.
 */

public class SearchFragment extends Fragment implements IFirstFragment {

    private View view;
    private EditText editText = null;
    private RecyclerView recyclerView = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findWidgets();
        ISearchPresenter presenter = new SearchPresenter(this);
        presenter.setRecycler(recyclerView);
        presenter.setEdit();
    }

    private void findWidgets(){
        editText = (EditText) view.findViewById(R.id.first_edit);
        recyclerView = (RecyclerView) view.findViewById(R.id.first_recyclerView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public EditText getEdit() {
        return editText;
    }
}
