package com.zia.gankcqupt_mvp.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.Util.HttpUtil;
import com.zia.gankcqupt_mvp.Util.StudentUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    /**
     * 获取数据库中收藏列表
     */
    public void getFavorite(){
        Cursor cursor = database.query("Favorite",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setClassid(cursor.getString(cursor.getColumnIndex("classid")));
                student.setZyh(cursor.getString(cursor.getColumnIndex("zyh")));
                student.setAtschool(cursor.getString(cursor.getColumnIndex("atschool")));
                student.setClassnum(cursor.getString(cursor.getColumnIndex("classnum")));
                student.setCollege(cursor.getString(cursor.getColumnIndex("college")));
                student.setMajor(cursor.getString(cursor.getColumnIndex("major")));
                student.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                student.setStudentid(cursor.getString(cursor.getColumnIndex("studentid")));
                student.setYear(cursor.getString(cursor.getColumnIndex("year")));
                StudentUtil.addStudentToFavorites(student);
                Log.d(TAG,student.getName());
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 从数据库中获取数据
     * @param dialog 进度条
     */
    public void getFromDB(final ProgressDialog dialog){//final OnAllStudentGet onAllStudentGet, final GetProgress setprogress
        i=0;p=0;MainPresenter.students.clear();
        Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Student> e) throws Exception {
                Cursor cursor = database.query("Student",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        Student student = new Student();
                        student.setName(cursor.getString(cursor.getColumnIndex("name")));
                        student.setClassid(cursor.getString(cursor.getColumnIndex("classid")));
                        student.setZyh(cursor.getString(cursor.getColumnIndex("zyh")));
                        student.setAtschool(cursor.getString(cursor.getColumnIndex("atschool")));
                        student.setClassnum(cursor.getString(cursor.getColumnIndex("classnum")));
                        student.setCollege(cursor.getString(cursor.getColumnIndex("college")));
                        student.setMajor(cursor.getString(cursor.getColumnIndex("major")));
                        student.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                        student.setStudentid(cursor.getString(cursor.getColumnIndex("studentid")));
                        student.setYear(cursor.getString(cursor.getColumnIndex("year")));
                        e.onNext(student);
                        MainPresenter.students.add(student);
                    }while (cursor.moveToNext());
                    if(MainPresenter.students.size() <= 20000){
                        database.delete("Student",null,null);
                        getFromDB(dialog);
                    }
                }
                else {
                    InputStream is;File file;
                    is = context.getApplicationContext().getAssets().open("cymz.db");
                    file = new File(context.getApplicationContext().getDatabasePath("cymz.db").getAbsolutePath());
                    Log.d("fileTest","targetFile: "+file.getPath());
                    copyFile(is,file);
                    e.onComplete();
                    getFromDB(dialog);
                }
                cursor.close();
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Student>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Student student) {
                        i++;
                        if(p != (int)((float) i / 19000f * 100)){
                            p = (int)((float) i / 19000f * 100);
                            dialog.setProgress(p);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.hide();
                        Toast.makeText(context, "有点小问题...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        dialog.hide();
                        Toast.makeText(context, "加载完成！", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 从内网外入中获取数据
     * @param onAllStudentGet
     * @param getProgress
     */
    public void GetFromCQUPT(final OnAllStudentGet onAllStudentGet, final GetProgress getProgress){
        HttpUtil.sendOkHttpRequest("http://jwzx.cqupt.congm.in/jwzxtmp/pubBjsearch.php?action=bjStu", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "网络有问题？", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<String> classList = new ArrayList<String>();
                List<Student> studentList = new ArrayList<Student>();
                i = 0;p = 0;int count = 0;
                //开始解析班级
                String classIDregex = "(?<=bj=).*?(?=')";
                Pattern pattern = Pattern.compile(classIDregex);
                Matcher matcher = pattern.matcher(response.body().string());
                while (matcher.find()){
                    if(matcher.group() != null){
                        classList.add("http://jwzx.cqupt.congm.in/jwzxtmp/showBjStu.php?bj=" + matcher.group());
                        Log.d(TAG,matcher.group());
                    }
                }
                getProgress.status(0);
                //解析完毕，获取所有班级地址
                Log.d(TAG,"解析完毕，已获取所有班级地址，接下来解析每一个班级...");
                int size = classList.size();//乘3是为了这部分为整个进度条的1/3
                HttpURLConnection connection = null;
                for (String u : classList) {
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
                    //Log.d(TAG, "获取http: " + res.toString());
                    Pattern patterntbody = Pattern.compile("<tbody>[\\s\\S]*?</tbody>");
                    Matcher matcher1 = patterntbody.matcher(res.toString());
                    String tbody = "";
                    while (matcher1.find()){
                        tbody += matcher1.group();
                    }
                    Log.d(TAG, "获取tbody: " + tbody);
                    Pattern pattern2 = Pattern.compile("<tr><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
                    Matcher matcher2 = pattern2.matcher(tbody);
                    while (matcher2.find()){
                        Student student = new Student();
                        student.setClassnum(matcher2.group(1));
                        student.setStudentid(matcher2.group(2));
                        student.setName(matcher2.group(3));
                        student.setSex(matcher2.group(4));
                        student.setClassid(matcher2.group(5));
                        student.setZyh(matcher2.group(6));
                        student.setMajor(matcher2.group(7));
                        student.setCollege(matcher2.group(8));
                        student.setYear(matcher2.group(9));
                        student.setAtschool(matcher2.group(10));
                        count++;
                        Log.d(TAG,count+" : "+student.getName());
                        studentList.add(student);
                    }
                    in.close();
                }
                MainPresenter.students = studentList;
                onAllStudentGet.onFinish(studentList);
                //以上将所有学生信息获取并保存到类里，需要存到数据库里
            }
        });
    }

    /**
     * 复制数据库的方法
     * @param is
     * @param targetFile
     * @throws IOException
     */
    private void copyFile(InputStream is,File targetFile) throws IOException{
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
}
