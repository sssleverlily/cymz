package com.zia.gankcqupt_mvp.Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Main.MePresenter;

/**
 * Created by zia on 2017/5/29.
 */

public class PublishModel {

    private static final String TAG = "PublishModelTest";
    private Context context;

    public PublishModel(Context context){
        this.context = context;
    }

    public void PublishTitle(String title,String content){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setTitle("正在发布");
        dialog.setMessage("稍等");
        dialog.show();
        AVObject t = new AVObject("Title");
        t.put("title", title);
        t.put("content", content);
        t.put("author", MePresenter.nickname);
        t.put("headImage", MePresenter.headUrl);
        t.put("userId", AVUser.getCurrentUser().getObjectId());
        t.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    Log.d(TAG, "title保存失败");
                    e.printStackTrace();
                } else {
                    GetTitle getTitle = new GetTitle(context);
                    getTitle.getTitlesAndShow();
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "title保存成功");
                            dialog.hide();
                            Toast.makeText(context, "发布成功！", Toast.LENGTH_SHORT).show();
                            ((Activity)context).finish();
                        }
                    });

                }
            }
        });
    }
}
