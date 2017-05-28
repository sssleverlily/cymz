package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.zia.gankcqupt_mvp.Model.StudentDbHelper;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.ILoginPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Interface.ILoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.LoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RegisterActivity;

/**
 * Created by zia on 2017/5/28.
 */

public class LoginPresenter implements ILoginPresenter {

    private Context context;
    private ILoginActivity activity;
    private static final String TAG = "LoginPresenterTest";

    public LoginPresenter(LoginActivity activity){
        context = activity;
        this.activity = activity;
    }

    @Override
    public void gotoRegisterPage() {
        Log.d(TAG,"gotoRegisterPage");
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void gotoMainPage() {
        Log.d(TAG,"gotoMainPage");
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    @Override
    public void login() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setTitle("正在登录");
        dialog.setMessage("稍等");
        dialog.show();
        AVUser.logInInBackground(activity.getUsername(), activity.getPassword(), new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e != null){
                    e.printStackTrace();
                    Log.d(TAG,"登录有问题");
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.hide();
                            Toast.makeText(context, "用户或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put("nickname",avUser.getString("nickname"));
                    values.put("sex",avUser.getString("sex"));
                    AVFile avFile = avUser.getAVFile("headImage");
                    String url = null;
                    try {
                        url = avFile.getUrl();
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                    if(url != null){
                        values.put("head",url);
                        Log.d(TAG,"url:"+url);
                    }
                    Log.d(TAG,"nickname:"+avUser.getString("nickname"));
                    Log.d(TAG,"sex:"+avUser.getString("sex"));
                    StudentDbHelper helper = new StudentDbHelper(context,"cymz.db",null,1);
                    SQLiteDatabase database = helper.getWritableDatabase();
                    Log.d(TAG,values.toString());
                    database.insert("LocalData",null,values);
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.hide();
                            gotoMainPage();
                        }
                    });
                }
            }
        });
    }
}
