package com.zia.gankcqupt_mvp.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.bean.Student;
import com.zia.gankcqupt_mvp.presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.utils.StudentUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zia on 2017/5/19.
 */

public class SaveStudent {

    private final static String TAG = "SaveStudentTest";
    private static int p = 0,i = 0;//进度条相关

    public static void saveInDB(final Context context, final ProgressDialog dialog){
        p=0;i=0;
        //清空数据库，恢复逻辑的一部分
        StudentDbHelper helper = new StudentDbHelper(context,"cymz.db",null,1);
        final SQLiteDatabase database = helper.getWritableDatabase();
        database.delete("Student",null,null);
        final int size = MainPresenter.students.size();
        Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Student> e) throws Exception {
                for (Student s : MainPresenter.students) {
                    e.onNext(s);
                }
                e.onComplete();
            }
        })
                .map(new Function<Student, Student>() {
                    @Override
                    public Student apply(@NonNull Student student) throws Exception {
                        database.insert("Student",null,StudentUtil.student2values(student));
                        return student;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Student>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Student student) {
                        i++;
                        dialog.setMessage("正在保存  "+student.getName());
                        if(p != (int)((float) i /(float)size*100)){
                            p = (int)((float) i /(float)size*100);
                            dialog.setProgress(p);
                            Log.d(TAG,"存入数据库进度： " + p);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(context, "发生错误！", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onComplete() {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(context, "完成！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}
