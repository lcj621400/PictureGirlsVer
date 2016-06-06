package com.lichunjing.picturegirls.baseui;

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
import android.widget.Toast;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.network.NetStatusObserver;
import com.lichunjing.picturegirls.networkevent.NEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG;

    /**
     * application对象
     */
    protected BasePicApp mApplication;


    /**
     * 屏幕宽
     */
    protected int mScreenWidth;
    /**
     * 屏幕高
     */
    protected int mScreenHeiht;

    /**
     * 标记是否有网络连接
     */
    protected boolean hasNetWork = true;

    /**
     * 网络状态变化的监视器
     */
    protected NetStatusObserver mNetStatusObserver;

    protected AlertDialog loadingDialog;

    protected boolean isVisible;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //应用管理器，添加此activity
//        AppManager.getInstance().addActivity(this);
        TAG = getClass().getSimpleName();
        //获得屏幕参数
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeiht = displayMetrics.heightPixels;
        //获得application对象
        mApplication = (BasePicApp) getApplication();
        //是否注册EventBus
        registEventBus();

        init();
        //设置页面布局
        setContentView(getLayout());
        initViews();
        initEvent();
        initStatus();
    }

    /**
     * 初始化相关操作，带参数
     */
    protected void init() {
    }

    /**
     * 初始化view
     */
    protected void initViews() {
    }

    /**
     * 初始化监听事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化页面状态
     */
    protected void initStatus() {
    }

    /**
     * 获得当前页面的布局
     *
     * @return
     */
    public abstract int getLayout();

    /**
     * 初始化toolbar
     *
     * @param title
     * @return
     */
    protected Toolbar initToolBar(String title, boolean showDefaultBack, View.OnClickListener backListener) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(title);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        if (showDefaultBack) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(backListener);
        }
        return toolbar;
    }

    protected View.OnClickListener getDefaultBackListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

    /**
     * 初始化floatactionbutton
     *
     * @param listener
     * @return
     */
    protected FloatingActionButton initFloatActionButton(View.OnClickListener listener) {
        FloatingActionButton mFloatButton = (FloatingActionButton) findViewById(R.id.float_button);
        if (listener != null)
            mFloatButton.setOnClickListener(listener);
        return mFloatButton;
    }

    /**
     * 显示耗时操作的对话框
     *
     * @param message 显示的信息
     */
    protected void showLoadingDialog(@NonNull String message) {
        if (loadingDialog == null) {
            loadingDialog = new AlertDialog.Builder(this).setMessage(message).setTitle("提示").show();
        } else {
            loadingDialog.show();
        }
    }

    /**
     * 隐藏对话框，可以设置消失提示消失
     */
    protected void dMissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 显示短Toast
     *
     * @param message
     */
    protected void showToastShort(@NonNull String message) {
        Toast.makeText(mApplication, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示短Toast
     *
     * @param message
     */
    protected void showToastLong(@NonNull String message) {
        Toast.makeText(mApplication, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 注册eventbus
     */
    protected void registEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 解除注册EventBus
     */
    protected void unRegistEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    protected void onNetEvent(NEvent event) {
        if (isVisible) {
            Log.d("网络链接" + getClass().getSimpleName(), "isConnected:" + event.isConnect + "--isChange:" + event.isChange + "--type:" + event.type);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "-----onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
        Log.d(TAG, "-----onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
        Log.d(TAG, "-----onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "-----onRestart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "-----onStop()");
    }

    @Override
    protected void onDestroy() {
        //如果注册了eventbus，则解除注册EventBus
        unRegistEventBus();
        super.onDestroy();
        Log.d(TAG, "-----onDestroy()");
    }

    @Override
    public void onClick(View v) {
    }

    protected void finishActivity() {
        finish();
    }
}
