package com.zia.gankcqupt_mvp.Presenter.Fragment.Main;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Bean.Students;
import com.zia.gankcqupt_mvp.Model.GetProgress;
import com.zia.gankcqupt_mvp.Model.GetStudent;
import com.zia.gankcqupt_mvp.Model.OnAllStudentGet;
import com.zia.gankcqupt_mvp.Model.SaveStudent;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.Presenter.Fragment.Interface.IMePresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.API;
import com.zia.gankcqupt_mvp.Util.FileUtil;
import com.zia.gankcqupt_mvp.Util.Service;
import com.zia.gankcqupt_mvp.Util.SharedPreferencesUtil;
import com.zia.gankcqupt_mvp.View.Activity.Page.LoginActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RecyclerActivity;
import com.zia.gankcqupt_mvp.View.Fragment.Interface.IMeFragment;
import com.zia.gankcqupt_mvp.View.Fragment.Page.MeFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zia on 2017/5/18.
 */

public class MePresenter implements IMePresenter,PopupMenu.OnMenuItemClickListener {

    private IMeFragment fragment;
    public static String nickname = null, headUrl = null;
    private final static String TAG = "MePresenterTest";
    private TextView nick;
    private String root = "congm";

    public MePresenter(MeFragment meFragment) {
        this.fragment = meFragment;
    }


    @Override
    public void showDataCount() {
        fragment.getTextView().setText("当前数据库有" + MainPresenter.students.size() + "条数据");
    }

    @Override
    public void downLoad() {
        fragment.toast("暂不公开");
    }

    /**
     * 先刷新收藏集合，再执行逻辑
     */
    @Override
    public void gotoFavoritePage() {
        GetStudent getStudent = new GetStudent(fragment.activity());
        getStudent.getFavorite();
        if (MainPresenter.favorites.size() != 0) {
            Intent intent = new Intent(fragment.activity(), RecyclerActivity.class);
            intent.putExtra("flag", "favorite");
            fragment.activity().startActivity(intent);
        } else {
            fragment.toast("还没有收藏哟..\n或许你可以登录查看收藏");
        }
    }

    @Override
    public void upData() {
        new AlertDialog.Builder(fragment.activity()).setTitle("警告").setMessage("更新最新数据库将耗时3-10分钟，约使用3m流量。\n\n" +
                "不会丢失收藏数据，但有几率更新失败导致程序无法使用，可以重新更新即可！")
                .setPositiveButton("我想好了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDataFromCQUPT();
                    }
                })
                .setNegativeButton("再想想", null).show();
    }

    private void upDataFromJson(){
        final ProgressDialog dialog = new ProgressDialog(fragment.activity());
        dialog.setCancelable(false);
        dialog.setTitle("正在从服务器上获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("正在下载，耐心等待..");
        DownloadManager downloadManager = (DownloadManager) fragment.activity().getSystemService(Context.DOWNLOAD_SERVICE);

        String url = FileUtil.getSdCardUrl("cymz.db");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(API.getInstance(fragment.activity()).getDatabase()));
        //检查网络状态
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setDestinationInExternalPublicDir("重邮妹子","cymz.db");

    }

    @Override
    public void loginOut() {
        new AlertDialog.Builder(fragment.activity()).setTitle("退出登录").setMessage("真的要退出登录吗？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AVUser.logOut();
                        Intent intent = new Intent(fragment.activity(), LoginActivity.class);
                        fragment.activity().startActivity(intent);
                        fragment.activity().finish();
                    }
                })
                .setNegativeButton("否", null).show();
    }

    @Override
    public void showUserPop(View view) {
        PopupMenu popupMenu = new PopupMenu(fragment.activity(),view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.user_pop, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public void setUser(TextView nick, TextView sex, final ImageView img) throws Exception {
        this.nick = nick;
        final AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null) {
            String s = avUser.getString("sex");
            nickname = avUser.getString("nickname");
            nick.setText(nickname);
            sex.setText("状态：已登录");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(avUser.getAVFile("headImage") != null){
                        try {
                            final AVFile file = AVFile.withObjectId(avUser.getAVFile("headImage").getObjectId());
                            fragment.activity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (file.getUrl() != null) {
                                        headUrl = file.getThumbnailUrl(true, 150, 150);
                                        Glide.with(fragment.activity()).load(headUrl).into(img);
                                    }
                                }
                            });
                        } catch (AVException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * 更换线路策略
     */
    @Override
    public void changeRoot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.activity());
        builder.setTitle("选择线路");
        final String[] roots = {"congm  (默认,速度快,但有时候无法使用)","zzzia  (自家服务器,稳定,四六级图片查看慢)"};
        int checkedItem;
        if(SharedPreferencesUtil.getRoot(fragment.activity()).equals("congm")){
            checkedItem = 0;
        }else{
            checkedItem = 1;
        }
        builder.setSingleChoiceItems(roots, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    root = "congm";
                }
                if(i == 1){
                    root = "zzzia";
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                API.getInstance(fragment.activity()).changeRoot(root);
                Log.d(TAG,"更换线路"+root);
                fragment.toast("更换线路"+root);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    /**
     * 从教务在线更新数据到本地数据库
     * 删除已有数据库内容，更换
     */
    private void getDataFromCQUPT() {
        final ProgressDialog dialog = new ProgressDialog(fragment.activity());
        dialog.setCancelable(false);
        dialog.setTitle("正在从教务在线获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("耐心等待..");
        //执行model的获取学生方法
        GetStudent getStudent = new GetStudent(fragment.activity());
        getStudent.GetFromCQUPT(new OnAllStudentGet() {
            @Override
            public void onFinish(List<Student> studentList) {
                //从网络中获取后保存到数据库里
                fragment.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(0);
                        dialog.setTitle("正在将数据存入数据库...");
                    }
                });
                //存入数据库
                SaveStudent.saveInDB(fragment.activity(), dialog);
            }

            @Override
            public void onError() {
                fragment.toast("失败了...");
            }
            //设置进度条
        }, new GetProgress() {
            @Override
            public void status(final int percentage) {
                fragment.activity().runOnUiThread(new Runnable() {
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
                    fragment.toast("您已经登录啦..");
                }
                else{
                    Intent intent1 = new Intent(fragment.activity(),LoginActivity.class);
                    fragment.activity().startActivity(intent1);
                    fragment.activity().finish();
                }
                break;

            case R.id.user_pop_changeImage:
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                fragment.activity().startActivityForResult(intent,1);
                break;

            case R.id.user_pop_changeName:
                final EditText et = new EditText(fragment.activity());
                new AlertDialog.Builder(fragment.activity()).setTitle("修改昵称")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.isEmpty()) {
                                    fragment.toast("昵称不能为空！");
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
                                                fragment.activity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        fragment.toast("修改成功！");
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
                final EditText et1 = new EditText(fragment.activity());
                et1.setHint("密码长度大于等于6");
                new AlertDialog.Builder(fragment.activity()).setTitle("修改密码")
                        .setView(et1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et1.getText().toString();
                                if (input.isEmpty() || input.length() < 6) {
                                    fragment.toast("密码类型不符合要求！");
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
                                                fragment.activity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        fragment.toast("修改成功！");
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
