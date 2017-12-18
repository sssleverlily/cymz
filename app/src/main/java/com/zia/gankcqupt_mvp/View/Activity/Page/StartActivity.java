package com.zia.gankcqupt_mvp.View.Activity.Page;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVOSCloud;
import com.zia.gankcqupt_mvp.Util.Code;
import com.zia.gankcqupt_mvp.Util.PermissionsUtil;
import com.zia.gankcqupt_mvp.View.Activity.BaseActivity;

public class StartActivity extends BaseActivity {

    private final static String TAG = "StartActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(StartActivity.this, "xKvysVojCOylmIQtqEVGaYQ3-gzGzoHsz", "qRgNqRCPJt9rRsqkftCzvOS1");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Code.DISK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //同意权限申请
                    toast("申请权限成功！");
                } else { //拒绝权限申请
                    toast("没有获取到权限哦..");
                }
                break;
            default:
                break;
        }
    }

}
