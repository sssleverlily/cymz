package com.zia.gankcqupt_mvp.Presenter.Fragment.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Model.GetProgress;
import com.zia.gankcqupt_mvp.Model.GetStudent;
import com.zia.gankcqupt_mvp.Model.OnAllStudentGet;
import com.zia.gankcqupt_mvp.Model.SaveStudent;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IThirdPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Page.LoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RecyclerActivity;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IThirdFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.ThirdFragment;

import java.io.FileNotFoundException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zia on 2017/5/18.
 */

public class ThirdPresenter implements IThirdPresenter,PopupMenu.OnMenuItemClickListener {

    private Context context;
    private IThirdFragment thirdFragment;
    public static String nickname = null, headUrl = null;
    private final static String TAG = "ThirdPresenterTest";
    private TextView nick;

    public ThirdPresenter(ThirdFragment thirdFragment) {
        this.thirdFragment = thirdFragment;
        context = thirdFragment.getContext();
    }


    @Override
    public void showDataCount() {
        thirdFragment.getTextView().setText("当前数据库有" + MainPresenter.students.size() + "条数据");
    }

    @Override
    public void downLoad() {
        Toast.makeText(context, "暂不开放", Toast.LENGTH_SHORT).show();
    }

    /**
     * 先刷新收藏集合，再执行逻辑
     */
    @Override
    public void gotoFavoritePage() {
        GetStudent getStudent = new GetStudent(context);
        getStudent.getFavorite();
        if (MainPresenter.favorites.size() != 0) {
            Intent intent = new Intent(context, RecyclerActivity.class);
            intent.putExtra("flag", "favorite");
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "还没有收藏哟..", Toast.LENGTH_SHORT).show();
        }
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
                .setNegativeButton("再想想", null).show();
    }

    @Override
    public void loginOut() {
        new AlertDialog.Builder(context).setTitle("退出登录").setMessage("真的要退出登录吗？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AVUser.logOut();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                })
                .setNegativeButton("否", null).show();
    }

    @Override
    public void showUserPop(View view) {
        PopupMenu popupMenu = new PopupMenu(context,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.user_pop, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public void setUser(TextView nick, TextView sex, final ImageView img) {
        this.nick = nick;
        final AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null) {
            String s = avUser.getString("sex");
            nickname = avUser.getString("nickname");
            nick.setText(nickname);
            sex.setText("状态：已登录");
            Observable.create(new ObservableOnSubscribe<AVFile>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<AVFile> e) throws Exception {
                    if(avUser.getAVFile("headImage") != null)
                    e.onNext(AVFile.withObjectId(avUser.getAVFile("headImage").getObjectId()));
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AVFile>() {
                        @Override
                        public void accept(@NonNull AVFile avFile) throws Exception {
                            if (avFile.getUrl() != null) {
                                headUrl = avFile.getThumbnailUrl(true, 150, 150);
                                Glide.with(context).load(headUrl).into(img);
                            }
                        }
                    });
        }
    }

    /**
     * 从教务在线更新数据到本地数据库
     * 删除已有数据库内容，更换
     */
    private void getDataFromCQUPT() {
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
                //从网络中获取后保存到数据库里
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(0);
                        dialog.setTitle("正在将数据存入数据库...");
                    }
                });
                //存入数据库
                SaveStudent.saveInDB(context, dialog);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "失败了...", Toast.LENGTH_SHORT).show();
            }
            //设置进度条
        }, new GetProgress() {
            @Override
            public void status(final int percentage) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(percentage);
                    }
                });
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.use_pop_login:
                if(AVUser.getCurrentUser() != null){
                    Toast.makeText(context, "您已经登录啦..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent1 = new Intent(context,LoginActivity.class);
                    ((Activity)context).startActivity(intent1);
                    ((Activity)context).finish();
                }
                break;

            case R.id.user_pop_changeImage:
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((Activity)context).startActivityForResult(intent,1);
                break;

            case R.id.user_pop_changeName:
                final EditText et = new EditText(context);
                new AlertDialog.Builder(context).setTitle("修改昵称")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.isEmpty()) {
                                    Toast.makeText(context, "昵称不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    AVUser user = AVUser.getCurrentUser();
                                    user.put("nickname",input);
                                    user.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if(e != null){
                                                e.printStackTrace();
                                            }
                                            else{
                                                ((Activity)context).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(context, "修改成功！", Toast.LENGTH_SHORT).show();
                                                        nick.setText(AVUser.getCurrentUser().getString("nickname"));
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.user_pop_changePassword:
                final EditText et1 = new EditText(context);
                et1.setHint("密码长度大于等于6");
                new AlertDialog.Builder(context).setTitle("修改密码")
                        .setView(et1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et1.getText().toString();
                                if (input.isEmpty() || input.length() < 6) {
                                    Toast.makeText(context, "密码类型不符合要求！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    AVUser user = AVUser.getCurrentUser();
                                    user.setPassword(input);
                                    user.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if(e != null){
                                                e.printStackTrace();
                                            }
                                            else{
                                                ((Activity)context).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(context, "修改成功！", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.user_pop_loginOut:
                loginOut();
                break;
        }
        return false;
    }
}
