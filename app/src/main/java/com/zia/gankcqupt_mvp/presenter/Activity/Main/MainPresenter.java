package com.zia.gankcqupt_mvp.presenter.Activity.Main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.zia.gankcqupt_mvp.bean.Student;
import com.zia.gankcqupt_mvp.model.GetStudent;
import com.zia.gankcqupt_mvp.presenter.Activity.Interface.IMainPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.utils.UserDataUtil;
import com.zia.gankcqupt_mvp.view.Activity.Interface.IMainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public class MainPresenter implements IMainPresenter {

    private static final String TAG = "MainPresenterTest";

    private IMainActivity activity;
    public static List<Student> students = new ArrayList<>();
    public static List<Student> favorites = new ArrayList<>();

    public MainPresenter(IMainActivity mainActivity) {
        this.activity = mainActivity;
    }

    @Override
    public void setPager() {
        ViewPager viewPager = activity.getViewPager();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(activity.getPagerAdapter());
        TabLayout tabLayout = activity.getTablayout();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    /**
     * 获取数据，储存到MainPresenter里
     */
    @Override
    public void getData() {
        activity.showDialog();
        GetStudent getStudent = new GetStudent(activity.getActivity());
        try {
            getStudent.getFromDB(activity.getDialog());
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDataUtil.pullFavorites(null);
    }

    @Override
    public void getRawImage(Intent data) {
        final Uri originalUri = data.getData();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getActivity().managedQuery(originalUri, proj, null, null, null);
        //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        //imagePath = cursor.getString(column_index);
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(originalUri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);// 输出图片大小
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        activity.getActivity().startActivityForResult(intent, 2);
    }

    @Override
    public void updataImage(Intent data) {
        final Bitmap bmap = data.getParcelableExtra("data");
        // 图像保存到文件中
        FileOutputStream foutput = null;
        try {
            File appDir = new File(Environment.getExternalStorageDirectory(), "重邮妹子");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = "head.jpg";
            File file = new File(appDir, fileName);
            foutput = new FileOutputStream(file);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, foutput);
            final String imagePath = file.getAbsolutePath();
            Log.d(TAG, "changed image path:" + imagePath);
            AVFile avFile = null;
            try {
                avFile = AVFile.withAbsoluteLocalPath(AVUser.getCurrentUser().getUsername(), imagePath);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            final AVUser user = AVUser.getCurrentUser();
            if (avFile != null) {
                user.put("headImage", avFile);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(final AVException e) {
                        activity.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (e != null) {
                                    e.printStackTrace();
                                    activity.toast("似乎有些问题");
                                } else {
                                    activity.toast("更换头像成功！");
                                    ImageView imageView = (ImageView) activity.getActivity().findViewById(R.id.third_image);
                                    Glide.with(activity.getActivity()).load(imagePath).into(imageView);
                                }
                            }
                        });
                    }
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != foutput) {
                try {
                    foutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Student> getStudentList() {
        return students;
    }
}
