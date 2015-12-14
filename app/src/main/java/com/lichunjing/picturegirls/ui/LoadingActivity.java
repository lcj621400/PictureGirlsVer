package com.lichunjing.picturegirls.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;
import com.lichunjing.picturegirls.manager.AppManager;


public class LoadingActivity extends BasePicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_loading;
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this,MainActivity.class));
                AppManager.getInstance().finishActivity(LoadingActivity.this);
            }
        },2000);
    }
}
