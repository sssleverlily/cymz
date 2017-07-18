package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.Presenter.Activity.Interface.IDetailPresenter;
import com.zia.gankcqupt_mvp.Presenter.Activity.Main.DetailPresenter;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Interface.IDetailActivity;

public class DetailActivity extends AppCompatActivity implements IDetailActivity {

    private TextView name,year,classId,id,major;
    private ImageView imageView;
    private Button save,favorite,card;
    private IDetailPresenter detailPresenter;
    public static final String ELEMENT = "Element";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
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
    }

    private void findWidgets(){
        major = (TextView) findViewById(R.id.detail_major);
        id = (TextView) findViewById(R.id.detail_id);
        classId = (TextView) findViewById(R.id.detail_classId);
        year = (TextView) findViewById(R.id.detail_year);
        name = (TextView) findViewById(R.id.detail_name);
        save = (Button) findViewById(R.id.save);
        favorite = (Button) findViewById(R.id.favorite);
        card = (Button)findViewById(R.id.card);
        imageView = (ImageView) findViewById(R.id.detail_image);
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
        this.year.setText(year);
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setButtonColor(int color) {
        favorite.setTextColor(color);
    }
}
