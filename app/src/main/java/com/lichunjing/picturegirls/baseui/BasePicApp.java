package com.lichunjing.picturegirls.baseui;

import android.app.Application;
import android.content.Context;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.configure.AppException;
import com.lichunjing.picturegirls.widget.loadorerror.LoadOrErrorHelper;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lcj621400 on 2015/12/11.
 */
public class BasePicApp extends Application{

    private  RefWatcher mRefWatcher;


    /**
     * BasePicApp实例
     */
    private static BasePicApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
//        initUnCatchException();
        initLoadOrErrorArgs();
        initOkHttp();
        initLogger();
        initLeakCanary();
    }

    /**
     * 初始化logger打印信息，在真是版本需要设置logLevel(LogLevel.NONE)关闭log信息
     */
    private void initLogger(){
        Logger.init("L-C-J").hideThreadInfo().logLevel(LogLevel.FULL).methodCount(2).methodOffset(2).logTool(new AndroidLogTool());
    }

    /**
     * 配置内存检测
     */
    private void initLeakCanary(){
        mRefWatcher= LeakCanary.install(this);
    }

    /**
     * 获取RefWatcher
     */
    public static RefWatcher getRefWatcher(Context context){
        BasePicApp app= (BasePicApp) context.getApplicationContext();
        return app.mRefWatcher;
    }

    /**
     * 初始化okhttp配置信息
     */
    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("L-C-J"))
                .connectTimeout(15,TimeUnit.MINUTES)
                .readTimeout(15,TimeUnit.MINUTES)
                .writeTimeout(15,TimeUnit.MINUTES)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化加载出错或是网络错误等信息
     */
    public void initLoadOrErrorArgs(){
        LoadOrErrorHelper.getInstance().initEmpty(R.drawable.ym_head,"暂时没有内容可以显示").initLoadError(R.drawable.ym_head,"加载失败，点击屏幕重试").initNetWorkError(R.drawable.ym_head,"当前已断开网络，请检查网络连接");
    }

    /**
     * 设置处理未捕获的异常
     */
    private void initUnCatchException(){
        AppException.getInstance().init(getInstance());
    }
    public static BasePicApp getInstance(){
        return  instance;
    }
}
