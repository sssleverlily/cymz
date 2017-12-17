package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.avos.avoscloud.AVUser;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Model.DetailModel;
import com.zia.gankcqupt_mvp.Model.StudentDbHelper;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IDetailPresenter;
import com.zia.gankcqupt_mvp.Util.Code;
import com.zia.gankcqupt_mvp.Util.PermissionsUtil;
import com.zia.gankcqupt_mvp.Util.StudentUtil;
import com.zia.gankcqupt_mvp.Util.UserDataUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IDetailActivity;

/**
 * Created by zia on 2017/5/19.
 */

public class DetailPresenter implements IDetailPresenter {

    private Context context;
    private IDetailActivity activity;
    private static final String TAG = "DetailPresenterTest";
    private static boolean isFour = true;
    private DetailModel model;
    private Student student;

    public DetailPresenter(IDetailActivity iDetailActivity) {
        this.activity = iDetailActivity;
        context = iDetailActivity.getActivity();
        isFour = iDetailActivity.getIsFour();
        model = new DetailModel(context);
        student = iDetailActivity.getStu();
    }

    @Override
    public void showPic() {
        model.setPic(activity.getImageView(),isFour,student.studentid);
    }

    /**
     * 设置收藏按钮的点击事件
     */
    @Override
    public void clickFavorite() {
        StudentDbHelper helper = new StudentDbHelper(context,"cymz.db",null,1);
        SQLiteDatabase database = helper.getWritableDatabase();
        for (int i=0;i<MainPresenter.favorites.size();i++) {
            Student temp = MainPresenter.favorites.get(i);
            if (temp.getStudentid().equals(student.getStudentid())) {
                Log.d(TAG,"在收藏数据库中找到该学生，删除"+student.getStudentid());
                database.delete("Favorite","studentid = ?",new String[]{student.getStudentid()});
                if(MainPresenter.favorites != null ){//判断是否在收藏列表
                    MainPresenter.favorites.remove(i);
                    if(RecyclerPresenter.adapter != null && RecyclerPresenter.isFavoriteList)
                        RecyclerPresenter.adapter.reFresh(MainPresenter.favorites,isFour);
                }
                activity.setButtonColor(Color.BLACK);
                toast("取消收藏成功");
                UserDataUtil.pushFavorites();
                return;
            }
        }
        Log.d(TAG,"未在收藏数据库中找到该学生，添加");
        long f = database.insert("Favorite",null, StudentUtil.student2values(student));
        if(f != (long)-1){
            if(MainPresenter.favorites != null){
                StudentUtil.addStudentToFavorites(student);
                if(RecyclerPresenter.adapter != null && RecyclerPresenter.isFavoriteList)
                    RecyclerPresenter.adapter.reFresh(MainPresenter.favorites,isFour);
            }
            activity.setButtonColor(Color.RED);
            if(AVUser.getCurrentUser() == null){
                toast("收藏成功!\n登录即可保存在云端");
            }else{
                toast("收藏成功!");
            }
            UserDataUtil.pushFavorites();
        }
        else{
            toast("数据库出了点问题..收藏失败...");
        }

    }

    @Override
    public void changeCard() {
        isFour = !isFour;
        if(isFour){
            activity.getCardButton().setText("一卡通");
        }else{
            activity.getCardButton().setText("四六级");
        }
        showPic();
    }

    @Override
    public void downLoad() {
        //请求权限
        if (!PermissionsUtil.hasDiskPermission(activity.getActivity(), Code.DISK)) return;
        model.savePic(student,isFour);
    }

    @Override
    public void setData() {
        activity.setData(student.getName(),student.getStudentid(),student.getMajor(),student.getClassid(),student.getYear());
    }

    @Override
    public void setFavoriteColor() {
        for (Student temp : MainPresenter.favorites) {
            if (temp.getStudentid().equals(student.getStudentid())) {
                activity.setButtonColor(Color.RED);
                return;
            }
        }
    }

    @Override
    public void setTranslateToolbar() {
        Window window = ((Activity)context).getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#00000000"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ViewGroup mContentView = (ViewGroup) ((Activity)context).findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }

    @Override
    public void toast(String msg) {
        if(activity != null) activity.toast(msg);
    }
}
