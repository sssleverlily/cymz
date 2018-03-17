package com.zia.gankcqupt_mvp.view.Activity.Page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.presenter.Activity.Interface.IRegisterPresenter;
import com.zia.gankcqupt_mvp.presenter.Activity.Main.RegisterPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.utils.Code;
import com.zia.gankcqupt_mvp.utils.ToastUtil;
import com.zia.gankcqupt_mvp.view.Activity.BaseActivity;
import com.zia.gankcqupt_mvp.view.Activity.Interface.IRegisterActivity;

public class RegisterActivity extends BaseActivity implements IRegisterActivity {

    private TextInputLayout user,psw,nickname;
    private TextView register;
    private ImageView header;
    private IRegisterPresenter presenter;
    private ProgressDialog dialog;
    private static final int GET_PHOTO_DISK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
        findWidgets();
        setClick();
    }

    private void setClick() {
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GET_PHOTO_DISK);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearError();
                presenter.register(getUsername(),getPassword(),getNickname());
            }
        });
    }

    @Override
    protected void findWidgets() {
        user = findViewById(R.id.register_username);
        psw = findViewById(R.id.register_password);
        register = findViewById(R.id.register_register);
        header = findViewById(R.id.register_imageView);
        nickname = findViewById(R.id.register_nickname);
    }

    @Override
    public String getUsername() {
        if(user.getEditText() == null) return "";
        return user.getEditText().getText().toString();
    }

    @Override
    public String getPassword() {
        if(psw.getEditText() == null) return "";
        return psw.getEditText().getText().toString();
    }

    @Override
    public String getNickname() {
        if(nickname.getEditText() == null) return "";
        return nickname.getEditText().getText().toString();
    }

    @Override
    public void toast(String msg) {
        ToastUtil.showToast(this,msg);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showDialog() {
        if(dialog == null) dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setTitle("正在登录");
        dialog.setMessage("稍等");
        dialog.show();
    }

    @Override
    public void hideDialog() {
        if(dialog == null) return;
        dialog.hide();
    }

    @Override
    public void setUsernameError() {
        user.setError("该用户名被占用");
    }

    @Override
    public void setPasswordError() {
        psw.setError("密码格式为6-15位");
    }

    @Override
    public void setUsernameFormatError() {
        user.setError("用户名不能为空");
    }

    @Override
    public void setNicknameFormatError() {
        nickname.setError("昵称不能为空");
    }

    @Override
    public void clearError() {
        user.setErrorEnabled(false);
        psw.setErrorEnabled(false);
        nickname.setErrorEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_PHOTO_DISK:
                presenter.setImage(header,data);
                break;
            case 200:
                presenter.changeImage(header,data);
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
