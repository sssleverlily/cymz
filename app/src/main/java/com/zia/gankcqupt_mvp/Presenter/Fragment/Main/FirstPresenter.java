package com.zia.gankcqupt_mvp.Presenter.Fragment.Main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Adapter.FirstAdapter;
import com.zia.gankcqupt_mvp.Adapter.RecyclerOnClickListener;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IFirstPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Page.DetailActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IFirstFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.FirstFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zia on 2017/5/18.
 */

public class FirstPresenter implements IFirstPresenter {

    private static final String TAG = "FirstPresenterTest";
    private Context context;
    private IFirstFragment firstFragment;
    private FirstAdapter adapter;
    private List<Student> list = new ArrayList<>();

    public FirstPresenter(FirstFragment firstFragment){
        this.firstFragment = firstFragment;
        context = firstFragment.getContext();
    }


    @Override
    public void search() {
        list.clear();//先清空要显示的集合
        String text = firstFragment.getEdit().getText().toString();
        if(JudgeNumber(text)){
            //数字
            for (Student student : MainPresenter.students) {
                if (student.getStudentId().contains(text)) {
                    list.add(student);
                }
            }
        }
        else {
            for (Student student : MainPresenter.students) {
                if (student.getName().contains(text)){
                    list.add(student);
                }
            }
        }
        adapter.reFresh(list);
    }

    private boolean JudgeNumber(String str) {
        Pattern pattern = Pattern.compile("^[1-9]\\d*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    @Override
    public void setRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new GridLayoutManager(context,1));
        adapter = new FirstAdapter(context);
        recycler.setAdapter(adapter);
    }

    @Override
    public void setEdit() {
        firstFragment.getEdit().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i && KeyEvent.ACTION_DOWN == keyEvent.getAction()){
                    search();
                    return true;
                }
                return false;
            }
        });
        firstFragment.getEdit().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!firstFragment.getEdit().getText().toString().equals(""))
                    search();
            }
        });
    }
}
