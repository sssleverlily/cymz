package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.ILoginPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.LoginPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.ILoginActivity;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    private TextView skip,register,login;
    private EditText user,psw;
    private ILoginPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        findWidgets();
        setClick();
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
                if(!getUsername().isEmpty() || !getPassword().isEmpty())
                presenter.login(getUsername(),getPassword());
            }
        });
    }

    private void findWidgets() {
        skip = (TextView)findViewById(R.id.login_skip);
        register = (TextView)findViewById(R.id.login_register);
        login = (TextView)findViewById(R.id.login_login);
        user = (EditText)findViewById(R.id.login_username);
        psw = (EditText)findViewById(R.id.login_password);
    }

    @Override
    public String getUsername() {
        return user.getText().toString();
    }

    @Override
    public String getPassword() {
        return psw.getText().toString();
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
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
