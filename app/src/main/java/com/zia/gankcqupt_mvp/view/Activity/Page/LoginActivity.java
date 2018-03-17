package com.zia.gankcqupt_mvp.view.Activity.Page;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.presenter.Activity.Interface.ILoginPresenter;
import com.zia.gankcqupt_mvp.presenter.Activity.Main.LoginPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.view.Activity.BaseActivity;
import com.zia.gankcqupt_mvp.view.Activity.Interface.ILoginActivity;

public class LoginActivity extends BaseActivity implements ILoginActivity {

    private TextView skip,register,login;
    public static TextInputLayout user,psw;
    private ILoginPresenter presenter;
    private ProgressDialog dialog;
    public final static String card_transitionName = "register_card";
    public final static String username_transitionName = "register_username";
    public final static String password_transitionName = "register_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        findWidgets();
        setClick();
    }

    @Override
    protected void findWidgets() {
        skip = findViewById(R.id.login_skip);
        register = findViewById(R.id.login_register);
        login = findViewById(R.id.login_login);
        user = findViewById(R.id.login_username);
        psw = findViewById(R.id.login_password);
    }

    private void setClick() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.gotoMainPage();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.gotoRegisterPage();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearError();
                if(getUsername().isEmpty()){
                    setUsernameFormatError();
                    return;
                }
                if(getPassword().isEmpty()){
                    setPasswordError();
                    return;
                }
                presenter.login(getUsername(),getPassword());
            }
        });
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
        user.setError("用户名不能为空");
    }

    @Override
    public void setPasswordError() {
        psw.setError("密码错误");
    }

    @Override
    public void setUsernameFormatError() {
        user.setError("用户名不能为空");
    }

    @Override
    public void clearError() {
        user.setErrorEnabled(false);
        psw.setErrorEnabled(false);
    }
}
