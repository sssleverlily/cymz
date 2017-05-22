package com.zia.gankcqupt_mvp.Presenter.Activity.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Model.GetProgress;
import com.zia.gankcqupt_mvp.Model.GetStudent;
import com.zia.gankcqupt_mvp.Model.OnAllStudentGet;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IMainPresenter;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IMainActivity;
import com.zia.gankcqupt_mvp.View.Activity.Page.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
    public void setDialog() {

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
    }

    @Override
    public void getData() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("正在从数据库获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("耐心等待..");
        final GetStudent getStudent = new GetStudent(context);
        getStudent.getFavorite(new OnAllStudentGet() {
            @Override
            public void onFinish(List<Student> studentList) {
                favorites = studentList;
            }

            @Override
            public void onError() {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"加载收藏列表失败");
                        //Toast.makeText(context, "加载收藏列表失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        getStudent.getFromDB(new OnAllStudentGet() {
            @Override
            public void onFinish(List<Student> studentList) {
                students = studentList;
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(100);
                        dialog.hide();
                    }
                });
                //如果数据库里没找到，则加载
                if(studentList.size() == 0){
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "正在加载本地数据库", Toast.LENGTH_SHORT).show();
                        }
                    });
                    InputStream is = null;File file = null;
                    try {
                        dialog.show();
                        dialog.setProgress(0);
                        is = context.getApplicationContext().getAssets().open("cymz.db");
                        file = new File(context.getApplicationContext().getDatabasePath("cymz.db").getAbsolutePath());
                        Log.d("fileTest","targetFile: "+file.getPath());
                        copyFile(is,file);
                        getStudent.getFromDB(new OnAllStudentGet() {
                            @Override
                            public void onFinish(List<Student> studentList) {
                                students = studentList;
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "首次加载完毕，尽情使用！", Toast.LENGTH_SHORT).show();
                                        dialog.setProgress(100);
                                        dialog.hide();
                                    }
                                });
                            }

                            @Override
                            public void onError() {

                            }
                        }, new GetProgress() {
                            @Override
                            public void status(int percentage) {
                                dialog.setProgress(percentage);
                            }
                        });
                        Log.d(TAG,"复制文件成功！");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                //Toast.makeText(context, "本地数据库出错，试试更新数据库？", Toast.LENGTH_SHORT).show();
            }
        }, new GetProgress() {
            @Override
            public void status(final int percentage) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(percentage);
                    }
                });
            }
        });
    }

    /**
     * 复制数据库的方法
     * @param is
     * @param targetFile
     * @throws IOException
     */
    public void copyFile(InputStream is,File targetFile) throws IOException{
        // 新建文件输入流并对它进行缓冲
        BufferedInputStream inBuff=new BufferedInputStream(is);
        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff=new BufferedOutputStream(output);
        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len =inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();
        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        is.close();
    }

    @Override
    public List<Student> getStudentList() {
        return students;
    }
}
