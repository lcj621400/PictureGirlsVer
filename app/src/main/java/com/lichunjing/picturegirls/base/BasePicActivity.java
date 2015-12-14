package com.lichunjing.picturegirls.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.lichunjing.picturegirls.manager.AppManager;


/**
 * Created by lcj621400 on 2015/12/11.
 */
public abstract class BasePicActivity extends AppCompatActivity implements View.OnClickListener{

    protected String tag;

    protected BasePicApp mApplication;


    protected int mScreenWidth;
    protected int mScreenHeiht;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        tag=this.getLocalClassName();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth=displayMetrics.widthPixels;
        mScreenHeiht=displayMetrics.heightPixels;
        mApplication= (BasePicApp) getApplication();
        //设置页面布局
        if(getLayout()!=0){
            setContentView(getLayout());
            init(savedInstanceState);
            initViews();
            initEvent();
            initStatus();
        }
    }

    /**
     * 初始化相关操作
     * @param savedInstanceState
     */
    protected void init(Bundle savedInstanceState){};

    /**
     * 初始化view
     */
    protected void initViews(){ };

    /**
     * 初始化监听事件
     */
    protected void initEvent(){};

    /**
     * 初始化页面状态
     */
    protected void initStatus(){};

    /**
     * 获得当前页面的布局
     * @return
     */
    public abstract int getLayout();

    /**
     * 显示耗时操作的对话框
     * @param message 显示的信息
     */
    protected void showLoadingDialog(@NonNull String message){

    }

    /**
     * 隐藏对话框，可以设置消失提示消失
     * @param message
     */
    protected void dissLoadingDialog(final String message){

    }

    protected void showToast(@NonNull String message){

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag,"-----onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag,"-----onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag,"-----onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag,"-----onRestart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag,"-----onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag,"-----onDestroy()");
    }

    @Override
    public void onClick(View v) {

    }


}
