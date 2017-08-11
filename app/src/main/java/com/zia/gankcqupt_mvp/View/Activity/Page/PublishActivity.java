package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IPublishPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.PublishPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.SlidingActivity;
import com.zia.gankcqupt_mvp.Util.ToastUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IPublishActivity;

public class PublishActivity extends SlidingActivity implements IPublishActivity {

    private Toolbar toolbar;
    private IPublishPresenter presenter;
    private ImageView image1,image2,image3,image4,image5;
    private EditText title,content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        findWidgets();
        presenter = new PublishPresenter(this);
        presenter.setToolbar(toolbar);
    }

    private void findWidgets() {
        toolbar = (Toolbar)findViewById(R.id.publish_toolbar);
        setSupportActionBar(toolbar);
        image1 = (ImageView)findViewById(R.id.publish_image1);
        image2 = (ImageView)findViewById(R.id.publish_image2);
        image3 = (ImageView)findViewById(R.id.publish_image3);
        image4 = (ImageView)findViewById(R.id.publish_image4);
        image5 = (ImageView)findViewById(R.id.publish_image5);
        title = (EditText)findViewById(R.id.publish_title);
        content = (EditText)findViewById(R.id.publish_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish_toolbar,menu);
        return true;
    }

    @Override
    public String getTitleString() {
        return title.getText().toString();
    }

    @Override
    public String getContent() {
        return content.getText().toString();
    }

    @Override
    public void toast(String msg) {
        ToastUtil.showToast(this,msg);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
