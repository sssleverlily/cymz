package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.zhihu.matisse.Matisse;
import com.zia.gankcqupt_mvp.Adapter.PagerAdapter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IMainPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.MainPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.Code;
import com.zia.gankcqupt_mvp.Util.ToastUtil;
import com.zia.gankcqupt_mvp.Util.UserDataUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IMainActivity;

import java.util.List;

import static com.zia.gankcqupt_mvp.Util.Code.REQUEST_CODE_CHOOSE;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private IMainPresenter mainPresenter;
    private ProgressDialog dialog;
    private final static String TAG = "MainActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_main);
        findWidgets();
        setToolBar();
        mainPresenter = new MainPresenter(this);
        mainPresenter.setPager();
        if(mainPresenter.getStudentList().size() == 0)
        mainPresenter.getData();
    }

    private void findWidgets(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.mToolbar);
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    public TabLayout getTablayout() {
        return tabLayout;
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return new PagerAdapter(getSupportFragmentManager());
    }

    @Override
    public void toast(String msg) {
        ToastUtil.showToast(this,msg);
    }

    @Override
    public void setToolBar() {
        toolbar.setTitle("重邮妹子");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setFocusable(true);//启动app时把焦点放在其他控件（不放在editext上）上防止弹出虚拟键盘
        toolbar.setFocusableInTouchMode(true);
        toolbar.requestFocus();
    }

    @Override
    public void showDialog() {
//        if(dialog == null)
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("正在从数据库获取数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        dialog.setProgress(0);
        dialog.setMessage("耐心等待..");
    }

    @Override
    public void hideDialog() {
        if (dialog == null) return;
        dialog.hide();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public ProgressDialog getDialog() {
        return dialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    mainPresenter.getRawImage(data);
                }
                break;
            case 2:
                    mainPresenter.updataImage(data);
                break;
            case REQUEST_CODE_CHOOSE:
                if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    Log.d("MainActivityMatisse", "mSelected: " + mSelected);
                    UserDataUtil.changAvatar(mSelected.get(0),MainActivity.this, new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null){
                                ToastUtil.showToast(MainActivity.this,"更新头像成功");
                            }else e.printStackTrace();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Code.DISK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //同意权限申请
                    toast("申请权限成功！");
                }else { //拒绝权限申请
                    toast("没有获取到权限哦..");
                }
                break;
            default:
                break;
        }
    }

}
