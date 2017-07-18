package com.zia.gankcqupt_mvp.Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.RefreshCallback;
import com.avos.avoscloud.SaveCallback;
import com.zia.gankcqupt_mvp.Adapter.ReplyAdapter;
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

    public void sendReply(final List<Comment> comments, final String targetId, final String userId,
                          final ReplyAdapter adapter, final RecyclerView recyclerView, final Title title, final EditText editText, final boolean isTopfinal, final ProgressDialog dialog, String content) {
        dialog.show();
        AVObject comment = new AVObject("Comment");
        comment.put("targetId", targetId);
        comment.put("content", content);
        comment.put("userId", AVUser.getCurrentUser().getObjectId());
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    Log.d(TAG, "comment保存失败");
                    e.printStackTrace();
                } else {
                    AVObject titleObj = AVObject.createWithoutData("Title",targetId);
                    titleObj.increment("count");
                    titleObj.setFetchWhenSave(true);
                    titleObj.saveInBackground();
                    AVObject avUser = AVUser.getCurrentUser();
                    avUser.increment("exp");
                    avUser.setFetchWhenSave(true);
                    avUser.saveInBackground();
                    Log.d(TAG,"count++");
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "comment保存成功");
                            dialog.hide();
                            getDataAndShow(comments,targetId,userId,adapter,recyclerView,title,editText,true);
                            Toast.makeText(context, "发布成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    //从服务器获取数据并刷新加载到recycler上
    public void getDataAndShow(final List<Comment> comments, final String targetId, final String userId,
                               final ReplyAdapter adapter, final RecyclerView recyclerView, final Title title, final EditText editText, final boolean isTop) {
        comments.clear();
        Observable.create(new ObservableOnSubscribe<Comment>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Comment> e) throws Exception {
                AVQuery<AVObject> avQuery = new AVQuery<>("Comment");
                avQuery.whereEqualTo("targetId", targetId);
                List<AVObject> list = avQuery.find();
                for (AVObject o : list) {
                    Comment comment = new Comment();
                    comment.setContent(o.getString("content"));
                    comment.setCreatedAt(o.getCreatedAt());
                    comment.setObjectId(o.getObjectId());
                    comment.setUserId(o.getString("userId"));
                    if (userId.equals(comment.getUserId())) {
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
                        comments.add(comment);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Comment comment = new Comment();
                        comment.setIslz(true);
                        comment.setUserId(title.getUserId());
                        comment.setTime(title.getTime());
                        comment.setCreatedAt(title.getCreatedAt());
                        comment.setObjectId(title.getObjectId());
                        comment.setContent(title.getContent());
                        comments.add(comment);
                        //按时间顺序排序
                        Collections.sort(comments, new Comparator<Comment>() {
                            @Override
                            public int compare(Comment comment, Comment t1) {
                                return comment.getCreatedAt().compareTo(t1.getCreatedAt());
                            }
                        });
                        adapter.refreshData(comments);
                        if(!isTop) {
                           recyclerView.smoothScrollToPosition(adapter.getListSize() + 1);
                        }
                        editText.setText("");
                    }
                });


    }

}
