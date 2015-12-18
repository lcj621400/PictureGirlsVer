package com.lichunjing.picturegirls.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.manager.AppManager;
import com.squareup.picasso.Picasso;


/**
 * Created by lcj621400 on 2015/12/11.
 */
public abstract class BasePicActivity extends AppCompatActivity implements View.OnClickListener{

    protected String tag;

    protected BasePicApp mApplication;


    protected int mScreenWidth;
    protected int mScreenHeiht;

    protected AlertDialog loadingDialog;

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
     * 初始化toolbar
     * @param title
     * @return
     */
    protected Toolbar initToolBar(String title,boolean showDefaultBack,View.OnClickListener backListener){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(title);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        if(showDefaultBack) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(backListener);
        }
        return toolbar;
    }

    /**
     * 初始化floatactionbutton
     * @param listener
     * @return
     */
    protected FloatingActionButton initFloatActionButton(View.OnClickListener listener){
        FloatingActionButton  mFloatButton = (FloatingActionButton) findViewById(R.id.float_button);
        if(listener!=null)
        mFloatButton.setOnClickListener(listener);
        return mFloatButton;
    }
    /**
     * 显示耗时操作的对话框
     * @param message 显示的信息
     */
    protected void showLoadingDialog(@NonNull String message){
        if(loadingDialog==null){
            loadingDialog=new AlertDialog.Builder(this).setMessage(message).setTitle("提示").show();
        }else{
            loadingDialog.show();
        }
    }

    /**
     * 隐藏对话框，可以设置消失提示消失
     */
    protected void dissLoadingDialog(){
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
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
