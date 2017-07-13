package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IRegisterPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IRegisterActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.RegisterActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by zia on 2017/5/28.
 */

public class RegisterPresenter implements IRegisterPresenter {

    private Context context;
    private IRegisterActivity activity;
    private String sex;
    private String imagePath;
    private static final String TAG = "RegisterPresenterTest";

    public RegisterPresenter(RegisterActivity activity) {
        context = activity;
        this.activity = activity;
    }


    @Override
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public void register() {
        if (activity.getUsername() == null || activity.getPassword() == null || activity.getNickname() == null) {
            Toast.makeText(context, "把信息填完整哟..", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
            dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
            dialog.setTitle("正在登录");
            dialog.setMessage("稍等");
            dialog.show();
            final AVUser user = new AVUser();
            user.setUsername(activity.getUsername());
            user.setPassword(activity.getPassword());
            user.put("sex", sex);
            user.signUpInBackground(new SignUpCallback() {//先注册
                @Override
                public void done(AVException e) {
                    if (e != null) {
                        Log.d(TAG, "注册有问题");
                        e.printStackTrace();
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "用户名被占用", Toast.LENGTH_SHORT).show();
                                dialog.hide();
                            }
                        });
                    } else {
                        user.put("nickname", activity.getNickname());//保存信息
                        AVFile avFile = null;
                        try {
                            if (imagePath != null)
                                avFile = AVFile.withAbsoluteLocalPath(activity.getUsername(), imagePath);
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        if (avFile != null) user.put("headImage", avFile);
                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e != null) {
                                    Log.d(TAG, "注册有问题");
                                    e.printStackTrace();
                                } else {
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "注册成功！", Toast.LENGTH_SHORT).show();
                                            AVUser.logOut();
                                            dialog.hide();
                                        }
                                    });
                                }
                                ((Activity) context).finish();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void setImage(final ImageView image, Intent data) {
        //final ContentResolver resolver = context.getContentResolver();
        if (data != null) {
            final Uri originalUri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = ((Activity) context).managedQuery(originalUri, proj, null, null, null);
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
            ((Activity) context).startActivityForResult(intent, 200);
            //Toast.makeText(context, imagePath, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Bitmap changeImage(final ImageView image, Intent data) {
        final Bitmap bmap = data.getParcelableExtra("data");
        // 显示剪切的图像
        image.setImageBitmap(bmap);
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
            imagePath = file.getAbsolutePath();
            Log.d(TAG,"changed image path:"+imagePath);
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    image.setImageBitmap(bmap);
                }
            });
        } catch (FileNotFoundException e) {
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
        return bmap;
    }
}