package com.zia.gankcqupt_mvp.view.Activity.Page;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.bean.Student;
import com.zia.gankcqupt_mvp.presenter.Activity.Interface.IDetailPresenter;
import com.zia.gankcqupt_mvp.presenter.Activity.Main.DetailPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.utils.Code;
import com.zia.gankcqupt_mvp.view.Activity.BaseActivity;
import com.zia.gankcqupt_mvp.view.Activity.Interface.IDetailActivity;

public class DetailActivity extends BaseActivity implements IDetailActivity {

    private TextView name, classId, id, major;
    private ImageView imageView;
    private Button save, favorite, card, back;
    private CardView cardLayout;
    private RelativeLayout infoLayout;
    private LinearLayout buttonsLayout;
    private IDetailPresenter detailPresenter;
    public static final String ELEMENT = "Element";
    private boolean isInfoLayout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findWidgets();
        ViewCompat.setTransitionName(imageView, ELEMENT);//切入动画效果
        detailPresenter = new DetailPresenter(this);
        detailPresenter.setFavoriteColor();//是否收藏,显示红色或黑色
        detailPresenter.setTranslateToolbar();
        detailPresenter.setData();
        detailPresenter.showPic();
        setClick();
    }

    private void setClick(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPresenter.downLoad();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPresenter.clickFavorite();
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPresenter.changeCard();
            }
        });
        cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout();
            }
        });
    }

    @Override
    protected void findWidgets() {
        major = findViewById(R.id.detail_major);
        id = findViewById(R.id.detail_id);
        classId = findViewById(R.id.detail_classId);
        name = findViewById(R.id.detail_name);
        save = findViewById(R.id.save);
        favorite = findViewById(R.id.favorite);
        card = findViewById(R.id.card);
        imageView = findViewById(R.id.detail_image);
        cardLayout = findViewById(R.id.detail_layout_card);
        infoLayout = findViewById(R.id.detail_layout_info);
        buttonsLayout = findViewById(R.id.detail_layout_buttons);
        back = findViewById(R.id.detail_button_back);
    }

    private void switchLayout() {
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(500);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isInfoLayout) {
                    infoLayout.setVisibility(View.VISIBLE);
                    buttonsLayout.setVisibility(View.GONE);
                } else {
                    infoLayout.setVisibility(View.GONE);
                    buttonsLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setDuration(500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isInfoLayout) {
                    infoLayout.setVisibility(View.VISIBLE);
                    buttonsLayout.setVisibility(View.GONE);
                } else {
                    infoLayout.setVisibility(View.GONE);
                    buttonsLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (isInfoLayout) {
            infoLayout.startAnimation(fadeOut);
            buttonsLayout.startAnimation(fadeIn);
            isInfoLayout = false;
        } else {
            infoLayout.startAnimation(fadeIn);
            buttonsLayout.startAnimation(fadeOut);
            isInfoLayout = true;
        }
    }

    @Override
    public Student getStu() {
        return (Student)getIntent().getSerializableExtra("student");
    }

    @Override
    public boolean getIsFour() {
        return getIntent().getBooleanExtra("isfour",false);
    }

    @Override
    public void setData(String name, String id, String major, String classId, String year) {
        this.name.setText(name);
        this.id.setText(id);
        this.major.setText(major);
        this.classId.setText(classId);
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void setButtonColor(int color) {
        favorite.setTextColor(color);
    }

    @Override
    public Button getCardButton() {
        return card;
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
