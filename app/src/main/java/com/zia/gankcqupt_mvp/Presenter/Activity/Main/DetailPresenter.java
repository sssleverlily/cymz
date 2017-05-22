package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Model.DetailModel;
import com.zia.gankcqupt_mvp.Model.StudentDbHelper;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IDetailPresenter;
import com.zia.gankcqupt_mvp.Util.StudentUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IDetailActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.DetailActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zia on 2017/5/19.
 */

public class DetailPresenter implements IDetailPresenter {

    private Context context;
    private IDetailActivity detailActivity;
    private static final String TAG = "DetailPresenterTest";
    private static boolean isFour = true;
    private DetailModel model;
    private Student student;
    private Button button;

    public DetailPresenter(DetailActivity detailActivity){
        this.detailActivity = detailActivity;
        context = detailActivity;
        isFour = detailActivity.getIsFour();
        model = new DetailModel(detailActivity);
        student = detailActivity.getStu();
    }


    @Override
    public void showPic() {
        model.setPic(detailActivity.getImageView(),isFour,student.studentId);
    }

    /**
     * 设置收藏按钮的点击事件
     */
    @Override
    public void clickFavorite() {
        StudentDbHelper helper = new StudentDbHelper(context,"cymz.db",null,1);
        SQLiteDatabase database = helper.getWritableDatabase();
        for (Student temp : MainPresenter.favorites) {
            if (temp.getStudentId().equals(student.getStudentId())) {
                Log.d(TAG,"在收藏数据库中找到该学生，删除");
                database.delete("Favorite","studentid = ?",new String[]{student.getStudentId()});
                MainPresenter.favorites.remove(student);
                button.setTextColor(Color.BLACK);
                Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Log.d(TAG,"未在收藏数据库中找到该学生，添加");
        database.insert("Favorite",null, StudentUtil.student2values(student));
        MainPresenter.favorites.add(student);
        button.setTextColor(Color.RED);
        Toast.makeText(context, "收藏成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeCard() {
        isFour = !isFour;
        showPic();
    }

    @Override
    public void downLoad() {
        model.savePic(student,isFour);
    }

    @Override
    public void setData() {
        detailActivity.setData(student.getName(),student.getStudentId(),student.getMajor(),student.getClassId(),student.getYear());
    }

    @Override
    public void setFavoriteColor(Button button) {
        this.button = button;
        for (Student temp : MainPresenter.favorites) {
            if (temp.getStudentId().equals(student.getStudentId())) {
                button.setTextColor(Color.RED);
                return;
            }
        }
    }
}
