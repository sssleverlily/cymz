package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVOSCloud;
import com.zia.gankcqupt_mvp.View.Activity.BaseActivity;

public class StartActivity extends BaseActivity {

    private final static String TAG = "StartActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(StartActivity.this,"xKvysVojCOylmIQtqEVGaYQ3-gzGzoHsz","qRgNqRCPJt9rRsqkftCzvOS1");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void findWidgets() {

    }

}
