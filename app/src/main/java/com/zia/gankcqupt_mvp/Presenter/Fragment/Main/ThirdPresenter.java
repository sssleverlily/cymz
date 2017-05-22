package com.zia.gankcqupt_mvp.Presenter.Fragment.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Model.GetProgress;
import com.zia.gankcqupt_mvp.Model.GetStudent;
import com.zia.gankcqupt_mvp.Model.OnAllStudentGet;
import com.zia.gankcqupt_mvp.Model.OnFinishCallBack;
import com.zia.gankcqupt_mvp.Model.SaveStudent;
import com.zia.gankcqupt_mvp.Model.StudentDbHelper;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IThirdPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Page.RecyclerActivity;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IThirdFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.ThirdFragment;

import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public class ThirdPresenter implements IThirdPresenter {

    private Context context;
    private IThirdFragment thirdFragment;

    public ThirdPresenter(ThirdFragment thirdFragment){
        this.thirdFragment = thirdFragment;
        context = thirdFragment.getContext();
    }


    @Override
    public void showDataCount() {
        thirdFragment.getTextView().setText("当前数据库有"+ MainPresenter.students.size()+"条数据");
    }

    @Override
    public void downLoad() {
        Toast.makeText(context, "暂不开放", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoFavoritePage() {
        GetStudent getStudent = new GetStudent(context);
        getStudent.getFavorite(new OnAllStudentGet() {
            @Override
            public void onFinish(List<Student> studentList) {
                Intent intent = new Intent(context, RecyclerActivity.class);
                intent.putExtra("flag","favorite");
                context.startActivity(intent);
            }

            @Override
            public void onError() {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "当前没有收藏...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void upData() {
        new AlertDialog.Builder(context).setTitle("警告").setMessage("更新数据库将耗时3-10分钟，约使用3m流量。\n\n请保证网络良好，内网外入没有问题。\n\n" +
                "若进度条很久没动，说明网络有问题，可以直接杀掉后台，再次启动程序恢复原始数据\n\n" +
                "不会丢失收藏数据，但有几率更新失败导致程序无法使用，可以卸载后重新安装！")
                .setPositiveButton("我想好了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDataFromCQUPT();
                    }
                })
                .setNegativeButton("再想想",null).show();
    }

    /**
     * 从教务在线更新数据到本地数据库
     * 删除已有数据库内容，更换
     */
    private void getDataFromCQUPT(){
        StudentDbHelper helper = new StudentDbHelper(context,"cymz.db",null,1);
        SQLiteDatabase database = helper.getWritableDatabase();
        //先清空Student表内容,防止只保存一部分数据导致保护逻辑无法判断，影响使用
        database.delete("Student",null,null);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("正在从教务在线获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("耐心等待..");
        //执行model的获取学生方法
        GetStudent getStudent = new GetStudent(context);
        getStudent.GetFromCQUPT(new OnAllStudentGet() {
            @Override
            public void onFinish(List<Student> studentList) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(100);
                    }
                });
                //存入数据库
                SaveStudent saveStudent = new SaveStudent(studentList,context);
                saveStudent.save(new GetProgress() {
                    @Override
                    public void status(final int percentage) {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.setTitle("正在存入数据库");
                                dialog.setProgress(percentage);
                            }
                        });
                    }
                }, new OnFinishCallBack() {
                    @Override
                    public void OnFinish() {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.hide();
                                Toast.makeText(context, "更新数据库成功！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            @Override
            public void onError() {
                Toast.makeText(context, "失败了...", Toast.LENGTH_SHORT).show();
            }
            //设置进度条
        }, new GetProgress() {
            @Override
            public void status(final int percentage) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(percentage);
                    }
                });
            }
        });
    }
}
