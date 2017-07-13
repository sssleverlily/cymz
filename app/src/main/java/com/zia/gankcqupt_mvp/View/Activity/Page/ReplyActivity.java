package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IReplyPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.ReplyPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IReplyActivity;

public class ReplyActivity extends AppCompatActivity implements IReplyActivity {

    private final static String TAG = "ReplyActivityTest";
    private RecyclerView recyclerView;
    private Button send;
    private EditText editText;
    private IReplyPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        findWidgets();
        presenter = new ReplyPresenter(this);
        presenter.setButton(send);
        presenter.setRecycler(recyclerView);
        presenter.showData();
    }

    private void findWidgets(){
        recyclerView = (RecyclerView) findViewById(R.id.reply_recycler);
        send = (Button) findViewById(R.id.reply_send);
        editText = (EditText) findViewById(R.id.reply_edit);
    }

    @Override
    public String getEdit() {
        return editText.getText().toString();
    }

    @Override
    public String getObjectId() {
        Intent intent = getIntent();
        if (intent != null){
            Log.d(TAG,"get objID: "+intent.getStringExtra("objectId"));
            if(intent.getStringExtra("objectId") != null){
                return intent.getStringExtra("objectId");
            }
        }
        return "get objectId error!";
    }

    @Override
    public String getUserId() {
        Intent intent = getIntent();
        if (intent != null){
            Log.d(TAG,"get userID: "+intent.getStringExtra("userId"));
            if(intent.getStringExtra("userId") != null){
                return intent.getStringExtra("userId");
            }
        }
        return "get userId error!";
    }
}
