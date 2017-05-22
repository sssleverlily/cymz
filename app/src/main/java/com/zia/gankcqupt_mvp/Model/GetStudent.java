package com.zia.gankcqupt_mvp.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.Util.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zia on 2017/5/18.
 */

public class GetStudent {

    private Context context;
    private static final String TAG = "GetStudentTest";
    private static int i = 0;//计数器
    private static int p = 0;//进度
    private SQLiteDatabase database;

    public GetStudent(Context context){
        this.context = context;
        StudentDbHelper helper = new StudentDbHelper(context,"cymz.db",null,1);
        database = helper.getWritableDatabase();
    }

    public void getFavorite(final OnAllStudentGet onAllStudentGet){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Student> studentList = new ArrayList<Student>();
                Cursor cursor = database.query("Favorite",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        Student student = new Student();
                        student.setName(cursor.getString(cursor.getColumnIndex("name")));
                        student.setClassId(cursor.getString(cursor.getColumnIndex("classid")));
                        student.setZyh(cursor.getString(cursor.getColumnIndex("zyh")));
                        student.setAtSchool(cursor.getString(cursor.getColumnIndex("atschool")));
                        student.setClassNum(cursor.getString(cursor.getColumnIndex("classnum")));
                        student.setCollege(cursor.getString(cursor.getColumnIndex("college")));
                        student.setMajor(cursor.getString(cursor.getColumnIndex("major")));
                        student.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                        student.setStudentId(cursor.getString(cursor.getColumnIndex("studentid")));
                        student.setYear(cursor.getString(cursor.getColumnIndex("year")));
                        studentList.add(student);
                        Log.d(TAG,student.getName());
                    }while (cursor.moveToNext());
                }
                else {
                    onAllStudentGet.onError();
                }
                cursor.close();
                onAllStudentGet.onFinish(studentList);
                if(MainPresenter.favorites != null) MainPresenter.favorites = studentList;//更新presenter里的收藏列表
            }
        }).start();
    }

    public void getFromDB(final OnAllStudentGet onAllStudentGet, final GetProgress setprogress){
        new Thread(new Runnable() {
            @Override
            public void run() {
                i=0;p=0;
                List<Student> studentList = new ArrayList<Student>();
                setprogress.status(0);
                Cursor cursor = database.query("Student",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        i++;
                        if(p != (int)((float) i / 20000f * 100)){
                            p = (int)((float) i / 20000f * 100);
                            Log.d(TAG,"当前进度： " +p );
                            setprogress.status(p);
                        }
                        Student student = new Student();
                        student.setName(cursor.getString(cursor.getColumnIndex("name")));
                        student.setClassId(cursor.getString(cursor.getColumnIndex("classid")));
                        student.setZyh(cursor.getString(cursor.getColumnIndex("zyh")));
                        student.setAtSchool(cursor.getString(cursor.getColumnIndex("atschool")));
                        student.setClassNum(cursor.getString(cursor.getColumnIndex("classnum")));
                        student.setCollege(cursor.getString(cursor.getColumnIndex("college")));
                        student.setMajor(cursor.getString(cursor.getColumnIndex("major")));
                        student.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                        student.setStudentId(cursor.getString(cursor.getColumnIndex("studentid")));
                        student.setYear(cursor.getString(cursor.getColumnIndex("year")));
                        studentList.add(student);
                        //Log.d(TAG,student.getName());
                    }while (cursor.moveToNext());
                }
                else {
                    onAllStudentGet.onError();
                }
                cursor.close();
                onAllStudentGet.onFinish(studentList);
            }
        }).start();
    }

    public void GetFromCQUPT(final OnAllStudentGet onAllStudentGet, final GetProgress getProgress){
        HttpUtil.sendOkHttpRequest("http://jwzx.cqupt.congm.in/jwzxtmp/pubBjsearch.php?action=bjStu", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "网络有问题？", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<String> classIdList = new ArrayList<String>();
                List<String> classURL = new ArrayList<String>();
                List<Student> studentList = new ArrayList<Student>();
                i = 0;p = 0;int count = 0;
                //开始解析班级
                String classIDregex = "(?<=bj=).*?(?=')";
                Pattern pattern = Pattern.compile(classIDregex);
                Matcher matcher = pattern.matcher(response.body().string());
                while (matcher.find()){
                    if(matcher.group() != null){
                        classIdList.add(matcher.group());
                        Log.d(TAG,matcher.group());
                    }
                }
                for (String id : classIdList) {
                    classURL.add("http://jwzx.cqupt.congm.in/jwzxtmp/showBjStu.php?bj=" + id);
                }
                getProgress.status(0);
                //解析完毕，获取所有班级地址
                Log.d(TAG,"解析完毕，已获取所有班级地址，接下来解析每一个班级...");
                int size = classURL.size();//乘3是为了这部分为整个进度条的1/3
                HttpURLConnection connection = null;
                for (String u : classURL) {
                    i++;//当数字改变时才刷新进度，节省内存
                    if(p !=(int)((float) i /(float)size*100)){
                        p =(int)((float) i /(float)size*100);
                        Log.d(TAG,"当前进度： " +p );
                        getProgress.status(p);
                    }
                    URL url = new URL(u);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        res.append(line);
                    }
                    Log.d(TAG, "获取http: " + res.toString());
                    Pattern pattern2 = Pattern.compile("<tr><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
                    Matcher matcher2 = pattern2.matcher(res);
                    while (matcher2.find()){
                        Student student = new Student();
                        student.setClassNum(matcher2.group(1));
                        student.setStudentId(matcher2.group(2));
                        student.setName(matcher2.group(3));
                        student.setSex(matcher2.group(4));
                        student.setClassId(matcher2.group(5));
                        student.setZyh(matcher2.group(6));
                        student.setMajor(matcher2.group(7));
                        student.setCollege(matcher2.group(8));
                        student.setYear(matcher2.group(9));
                        student.setAtSchool(matcher2.group(10));
                        count++;
                        Log.d(TAG,count+" : "+student.getName());
                        studentList.add(student);
                    }
                    in.close();
                }
                onAllStudentGet.onFinish(studentList);
                //以上将所有学生信息获取并保存到类里，需要存到数据库里
            }
        });
    }
}
