package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.ILoginPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.LoginPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.ILoginActivity;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    private TextView skip,register,login;
    private EditText user,psw;
    private ILoginPresenter presenter;

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
                presenter.login();
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
    public TextView getTv() {
        return register;
    }
}
