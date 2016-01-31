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
import android.widget.Toast;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.manager.AppManager;
import com.lichunjing.picturegirls.network.NetStatusObserver;
import com.lichunjing.picturegirls.network.NetStatusReceiver;
import com.lichunjing.picturegirls.network.NetStatusUtils;

import de.greenrobot.event.EventBus;


/**
 * BaseActivity
 */
public abstract class BasePicActivity extends AppCompatActivity implements View.OnClickListener{

    public  String TAG ;

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
    protected boolean hasNetWork=true;
    /**
     * 标记当前activity是否获得焦点
     */
    protected boolean hasFocus=true;

    /**
     * 网络状态变化的监视器
     */
    protected NetStatusObserver mNetStatusObserver;

    protected AlertDialog loadingDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //应用管理器，添加此activity
        AppManager.getInstance().addActivity(this);
        TAG=getClass().getSimpleName();
        //获得屏幕参数
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeiht = displayMetrics.heightPixels;
        //获得application对象
        mApplication = (BasePicApp) getApplication();
        //是否注册EventBus
        if (eventBus()) {
            registEventBus();
        }
        //实例化网络变化监听器
        mNetStatusObserver = new NetStatusObserver() {
            @Override
            public void onNetConnected(NetStatusUtils.NetType netType) {
                hasNetWork = true;
                if (!hasFocus) {
                    Log.d(TAG, TAG + ":失去焦点，连接网络");
                } else {
                    //当前activity获得焦点，回调此方法
                    onNetConnectedFocus("当前已连接"+netType.name()+"网络");
                    Log.d(TAG, TAG + ":获得焦点，连接网络");
                }
            }

            @Override
            public void onNetDisConnected() {
                hasNetWork = false;
                //判断当前页面是否有焦点
                if (!hasFocus) {
                    Log.d(TAG, TAG + ":失去焦点，断开网络");
                } else {
                    //当前activity获得焦点，执行此方法
                    onNetDisConnectedFocus();
                    Log.d(TAG, TAG + ":获得焦点，断开网络");
                }
            }
        };

        //注册监听网络变化的广播接收器
        NetStatusReceiver.registerNetStatusReceiver(this,mNetStatusObserver);
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
     * 初始化相关操作，带参数
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
    protected void dMissLoadingDialog(){
        if(loadingDialog!=null&&loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    /**
     * 显示短Toast
     * @param message
     */
    protected void showToastShort(@NonNull String message){
        Toast.makeText(mApplication,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示短Toast
     * @param message
     */
    protected void showToastLong(@NonNull String message){
        Toast.makeText(mApplication,message,Toast.LENGTH_LONG).show();
    }

    /**
     * 如果需要使用eventbus，则重写此方法，返回true
     * @return
     */
    protected boolean eventBus(){
        return false;
    }

    /**
     * 注册eventbus
     */
    protected void registEventBus(){
        EventBus.getDefault().register(this);
    }

    /**
     * 解除注册EventBus
     */
    protected void unRegistEventBus(){
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"-----onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"-----onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"-----onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"-----onRestart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"-----onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果注册了eventbus，则解除注册EventBus
        if(eventBus()){ unRegistEventBus();}
        //解除注册监听网络变化的广播接收器
        NetStatusReceiver.unRegistNetStatusReceiver(this,mNetStatusObserver);
        //应用管理结束当前activity
        AppManager.getInstance().finishActivity(this);
        Log.d(TAG,"-----onDestroy()");
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 当前activity是否获得焦点
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.hasFocus=hasFocus;
    }

    /**
     * 当前网络连接，且当前activity获取焦点，会执行此方法
     * @param
     */
    protected void onNetConnectedFocus(String message){

    }

    /**
     * 当前网络断开，且当前activity获得焦点，会执行此方法
     */
    protected void onNetDisConnectedFocus(){

    }

    /**
     * 返回当前网络是否连接，true：连接；false：未连接
     * @return
     */
    public boolean isHasNetWork() {
        return hasNetWork;
    }

    /**
     * 返回当前activity是否获得焦点，true：获得焦点；false：为获得焦点
     * @return
     */
    public boolean isHasFocus() {
        return hasFocus;
    }


    protected void finishActivity(){
        finish();
    }
}
