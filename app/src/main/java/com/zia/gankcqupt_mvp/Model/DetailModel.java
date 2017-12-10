package com.zia.gankcqupt_mvp.Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Util.API;
import com.zia.gankcqupt_mvp.Util.GlideUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zia on 2017/5/19.
 */

public class DetailModel {

    private static String url;
    private Context context;

    public DetailModel(Context context){
        this.context = context;
    }

    public void setPic(ImageView imageView,boolean isFour,String studentId){
        if(isFour) {
            url = API.getInstance(context).getCET(studentId);
        }
        else{
            url = API.getInstance(context).getYKT(studentId);
        }
        GlideUtil.INSTANCE.load(context, imageView, url);
    }

    public void savePic(final Student student, final boolean isFour){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url;
                    if(isFour) {
                        url = API.getInstance(context).getCET(student.studentid);
                    }
                    else{
                        url = API.getInstance(context).getYKT(student.studentid);
                    }
                    URL imgUrl = new URL(url);
                    HttpURLConnection urlConnection = (HttpURLConnection) imgUrl.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    InputStream inputStream = urlConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    File appDir = new File(Environment.getExternalStorageDirectory(), "重邮妹子");
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    String fileName;
                    if(isFour){
                        fileName = student.getName() + "_cet" + ".jpg";
                    }else{
                        fileName = student.getName() + "_ykt" + ".jpg";
                    }
                    File file = new File(appDir, fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "图片已保存", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
