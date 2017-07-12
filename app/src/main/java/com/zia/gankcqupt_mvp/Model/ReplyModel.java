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
import com.zia.gankcqupt_mvp.View.Activity.Interface.IReplyActivity;

/**
 * Created by zia on 17-7-12.
 */

public class ReplyModel {

    private static final String TAG = "ReplyModelTest";
    private Context context = null;

    public ReplyModel(Context context){
        this.context = context;

    }

    public void sendReply(IReplyActivity activity){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setTitle("正在发布");
        dialog.setMessage("稍等");
        dialog.show();
        AVObject comment = new AVObject("Comment");
        comment.put("targetId", activity.getObjectId());
        comment.put("content", activity.getEdit());
        comment.put("userId", AVUser.getCurrentUser().getObjectId());
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e != null){
                    Log.d(TAG, "comment保存失败");
                    e.printStackTrace();
                } else {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "comment保存成功");
                            dialog.hide();
                            Toast.makeText(context, "发布成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


}
