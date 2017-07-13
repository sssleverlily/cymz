package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
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
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IMainPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.UserUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IMainActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.PublishActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/18.
 */

public class MainPresenter implements IMainPresenter {

    private static final String TAG = "MainPresenterTest";
    private Context context;
    private IMainActivity mainActivity;
    public static List<Student> students = new ArrayList<>();
    public static List<Student> favorites = new ArrayList<>();


    public MainPresenter(MainActivity mainActivity){
        context = mainActivity;
        this.mainActivity = mainActivity;
    }

    @Override
    public void setFloatingBar() {
        mainActivity.getFloatingBar().setVisibility(View.INVISIBLE);
        mainActivity.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 2){
                    mainActivity.getFloatingBar().setVisibility(View.VISIBLE);
                }
                else{
                    mainActivity.getFloatingBar().setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mainActivity.getFloatingBar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AVUser.getCurrentUser() == null){
                    Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(context, PublishActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void setPager() {
        ViewPager viewPager = mainActivity.getViewPager();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mainActivity.getPagerAdapter());
        TabLayout tabLayout = mainActivity.getTablayout();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void setToolbar() {
        Toolbar toolbar = mainActivity.getToolBar();
        toolbar.setTitle("重邮妹子");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setFocusable(true);//启动app时把焦点放在其他控件（不放在editext上）上防止弹出虚拟键盘
        toolbar.setFocusableInTouchMode(true);
        toolbar.requestFocus();
    }

    /**
     * 获取学生数据，储存到MainPresenter里
     */
    @Override
    public void getData() {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("正在从数据库获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("耐心等待..");
        GetStudent getStudent = new GetStudent(context);
        getStudent.getFromDB(dialog);
    }

    @Override
    public void getRawImage(Intent data) {
        final Uri originalUri = data.getData();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity)context).managedQuery(originalUri, proj, null, null, null);
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
        ((Activity)context).startActivityForResult(intent, 2);
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
                if (imagePath != null)
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
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (e != null) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "似乎有些问题", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "更换头像成功！", Toast.LENGTH_SHORT).show();
                                    ImageView imageView = (ImageView) ((Activity) context).findViewById(R.id.third_image);
                                    Glide.with(context).load(imagePath).into(imageView);
                                }
                            }
                        });
                    }
                });
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(null != foutput){
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
