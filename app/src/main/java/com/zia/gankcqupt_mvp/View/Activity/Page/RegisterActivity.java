package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
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
import com.zia.gankcqupt_mvp.View.Activity.Interface.IRegisterActivity;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity {

    private EditText user,psw,nickname;
    private TextView register;
    private ImageView header;
    private RadioGroup group;
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
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.register_male:
                        presenter.setSex("male");
                        break;
                    case R.id.register_female:
                        presenter.setSex("female");
                        break;
                }
            }
        });
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
                presenter.register(getUsername(),getPassword(),getNickname());
            }
        });
    }

    private void findWidgets(){
        user = (EditText)findViewById(R.id.register_username);
        psw = (EditText)findViewById(R.id.register_password);
        register = (TextView)findViewById(R.id.register_register);
        header = (ImageView)findViewById(R.id.register_imageView);
        group = (RadioGroup)findViewById(R.id.register_group);
        nickname = (EditText)findViewById(R.id.register_nickname);
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
    public String getNickname() {
        return nickname.getText().toString();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
