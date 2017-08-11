package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IRegisterPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.RegisterPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.Util.ToastUtil;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IRegisterActivity;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity {

    private TextInputLayout user,psw,nickname;
    private TextView register;
    private ImageView header;
    private IRegisterPresenter presenter;
    private ProgressDialog dialog;
    private static final int GET_PHOTO_DISK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    private void findWidgets(){
        user = (TextInputLayout)findViewById(R.id.register_username);
        psw = (TextInputLayout)findViewById(R.id.register_password);
        register = (TextView)findViewById(R.id.register_register);
        header = (ImageView)findViewById(R.id.register_imageView);
        nickname = (TextInputLayout)findViewById(R.id.register_nickname);
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
}
