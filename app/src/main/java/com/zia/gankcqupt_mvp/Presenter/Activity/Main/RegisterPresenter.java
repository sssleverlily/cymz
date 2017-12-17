package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IRegisterPresenter;
import com.zia.gankcqupt_mvp.Util.Code;
import com.zia.gankcqupt_mvp.Util.PermissionsUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IRegisterActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.LoginActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zia on 2017/5/28.
 */

public class RegisterPresenter implements IRegisterPresenter {


    private IRegisterActivity activity;
    private String imagePath;
    private static final String TAG = "RegisterPresenterTest";

    public RegisterPresenter(IRegisterActivity activity) {
        this.activity = activity;
    }


    @Override
    public void register(final String username, final String password, String nickname) {
        if(nickname.isEmpty()){
            activity.setNicknameFormatError();
        }
        else if (username.isEmpty()) {
            activity.setUsernameFormatError();
        }else if(password.isEmpty()){
            activity.setPasswordError();
        }
        else
        {
            activity.showDialog();
            final AVUser user = new AVUser();
            user.setUsername(activity.getUsername());
            user.setPassword(activity.getPassword());
            user.signUpInBackground(new SignUpCallback() {//先注册
                @Override
                public void done(AVException e) {
                    if (e != null) {
                        Log.d(TAG, "注册有问题");
                        e.printStackTrace();
                        activity.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.hideDialog();
                                activity.setUsernameError();
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
                                    e.printStackTrace();
                                } else {
                                    activity.getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            activity.toast("注册成功！");
                                            AVUser.logOut();
                                            activity.hideDialog();
                                        }
                                    });
                                }
                                //保存installation到服务器
                                if(LoginActivity.user != null && LoginActivity.psw != null){
                                    LoginActivity.user.getEditText().setText(username);
                                    LoginActivity.psw.getEditText().setText(password);
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    activity.getActivity().finishAfterTransition();
                                }else{
                                    activity.getActivity().finish();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void setImage(final ImageView image, Intent data) {
        //请求权限
        if (!PermissionsUtil.hasDiskPermission(activity.getActivity(), Code.DISK)) return;
        if (data != null) {
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
            activity.getActivity().startActivityForResult(intent, 200);
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
            activity.getActivity().runOnUiThread(new Runnable() {
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
