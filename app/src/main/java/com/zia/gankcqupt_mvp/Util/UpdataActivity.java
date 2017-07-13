package com.zia.gankcqupt_mvp.Util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zia on 17-7-13.
 */

public class UpdataActivity extends AppCompatActivity {

    private static final String TAG = "UpdataActivityTest";
    private static final int GET_PHOTO_DISK = 1;
    private static final int CHANGE_SIZE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updataImage();
    }

    public void updataImage(){
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GET_PHOTO_DISK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_PHOTO_DISK:
                if (data != null) {
                    final Uri originalUri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
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
                    startActivityForResult(intent, CHANGE_SIZE);
                }
                break;
            case CHANGE_SIZE:
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
                    String imagePath = file.getAbsolutePath();
                    Log.d(TAG,"changed image path:"+imagePath);

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
                break;
        }
    }
}
