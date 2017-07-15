package com.zia.gankcqupt_mvp.Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.RefreshCallback;
import com.avos.avoscloud.SaveCallback;
import com.zia.gankcqupt_mvp.Bean.Comment;
import com.zia.gankcqupt_mvp.Bean.Title;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.ReplyPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IReplyActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zia on 17-7-12.
 */

public class ReplyModel {

    private static final String TAG = "ReplyModelTest";
    private Context context = null;

    public ReplyModel(Context context) {
        this.context = context;
    }

    public void sendReply(final IReplyActivity activity, final ReplyPresenter presenter) {
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
                if (e != null) {
                    Log.d(TAG, "comment保存失败");
                    e.printStackTrace();
                } else {
                    AVObject titleObj = AVObject.createWithoutData("Title",activity.getObjectId());
                    titleObj.increment("count");
                    titleObj.setFetchWhenSave(true);
                    titleObj.saveInBackground();
                    AVObject avUser = AVUser.getCurrentUser();
                    avUser.increment("exp");
                    avUser.setFetchWhenSave(true);
                    avUser.saveInBackground();
                    Log.d(TAG,"count++");
                    presenter.showData();
                    ((Activity) context).runOnUiThread(new Runnable() {
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

    public void getDataAndShow(final ReplyPresenter presenter) {
        presenter.commentList.clear();
        Observable.create(new ObservableOnSubscribe<Comment>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Comment> e) throws Exception {
                AVQuery<AVObject> avQuery = new AVQuery<>("Comment");
                avQuery.whereEqualTo("targetId", presenter.activity.getObjectId());
                List<AVObject> list = avQuery.find();
                for (AVObject o : list) {
                    Comment comment = new Comment();
                    comment.setContent(o.getString("content"));
                    comment.setCreatedAt(o.getCreatedAt());
                    comment.setObjectId(o.getObjectId());
                    comment.setUserId(o.getString("userId"));
                    if (presenter.activity.getUserId().equals(comment.getUserId())) {
                        comment.setIslz(true);
                    } else comment.setIslz(false);
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd  HH:mm");
                    comment.setTime(dateFormat.format(comment.getCreatedAt()));
                    e.onNext(comment);
                    Log.d(TAG, comment.toString());
                }
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Comment>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Comment comment) {
                        presenter.commentList.add(comment);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Comment comment = new Comment();
                        Title title = presenter.activity.getFirstTitle();
                        comment.setIslz(true);
                        comment.setUserId(title.getUserId());
                        comment.setTime(title.getTime());
                        comment.setCreatedAt(title.getCreatedAt());
                        comment.setObjectId(title.getObjectId());
                        comment.setContent(title.getContent());
                        presenter.commentList.add(comment);
                        //按时间顺序排序
                        Collections.sort(presenter.commentList, new Comparator<Comment>() {
                            @Override
                            public int compare(Comment comment, Comment t1) {
                                return comment.getCreatedAt().compareTo(t1.getCreatedAt());
                            }
                        });
                        presenter.adapter.refreshData(presenter.commentList);
                    }
                });


    }

}
