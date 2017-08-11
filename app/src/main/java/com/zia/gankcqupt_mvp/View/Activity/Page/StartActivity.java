package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IStartPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.StartPresenter;
import com.zia.gankcqupt_mvp.R;

public class StartActivity extends AppCompatActivity {

    private IStartPresenter iStartPresenter;
    private final static String TAG = "StartActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(StartActivity.this,"xKvysVojCOylmIQtqEVGaYQ3-gzGzoHsz","qRgNqRCPJt9rRsqkftCzvOS1");
        PushService.setDefaultPushCallback(this, StartActivity.class);
        iStartPresenter = new StartPresenter(StartActivity.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iStartPresenter.gotoPage();//presenter内判断是否登录过
            }
        }, 500);
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    // 关联  installationId 到用户表等操作……
                    Log.d(TAG,"保存到服务器--->installationId:  "+ installationId);
                    if(AVUser.getCurrentUser() != null){
                        AVUser.getCurrentUser().put("installationId",installationId);
                        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if(e == null){
                                    Log.d(TAG,"installationId上传用户信息成功");
                                }else{
                                    Log.d(TAG,"installationId上传用户信息失败");
                                }
                            }
                        });
                    }
                } else {
                    // 保存失败，输出错误信息
                    e.printStackTrace();
                    Log.d(TAG,"installationId保存到服务器失败");
                }
            }
        });
    }

}
