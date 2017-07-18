package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Title;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IReplyPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.ReplyPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IReplyActivity;

public class ReplyActivity extends AppCompatActivity implements IReplyActivity {

    private final static String TAG = "ReplyActivityTest";
    private RecyclerView recyclerView;
    private Button send;
    private EditText editText;
    private Toolbar toolbar;
    private IReplyPresenter presenter = null;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        findWidgets();
        setToolbar("回复列表",Color.parseColor("#ffffff"));
        presenter = new ReplyPresenter(this);
        presenter.setButton(send);
        presenter.setRecycler(recyclerView);
        presenter.showData(true);
    }

    private void findWidgets(){
        recyclerView = (RecyclerView) findViewById(R.id.reply_recycler);
        send = (Button) findViewById(R.id.reply_send);
        editText = (EditText) findViewById(R.id.reply_edit);
        toolbar = (Toolbar)findViewById(R.id.reply_toolbar);
    }

    @Override
    public String getEdit() {
        return editText.getText().toString();
    }

    @Override
    public void clearEdit() {
        editText.setText("");
    }

    @Override
    public RecyclerView getRecycler() {
        return recyclerView;
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Intent intent() {
        return getIntent();
    }

    @Override
    public void setToolbar(String title,int color) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(color);
    }

    @Override
    public void showDialog() {
        if(dialog == null) dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setTitle("正在发布");
        dialog.setMessage("稍等");
        dialog.show();
    }

    @Override
    public void hideDialog() {
        if(dialog == null) return;
        dialog.hide();
    }

}
