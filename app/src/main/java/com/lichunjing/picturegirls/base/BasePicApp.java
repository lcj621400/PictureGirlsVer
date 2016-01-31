package com.lichunjing.picturegirls.base;

import android.app.Application;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.configure.AppException;
import com.lichunjing.picturegirls.widget.loadorerror.LoadOrErrorHelper;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lcj621400 on 2015/12/11.
 */
public class BasePicApp extends Application{


    /**
     * BasePicApp实例
     */
    private static BasePicApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
//        initUnCatchException();
        initGlide();
        initLoadOrErrorArgs();
        initOkHttp();
        initLogger();
    }

    /**
     * 初始化logger打印信息，在真是版本需要设置logLevel(LogLevel.NONE)关闭log信息
     */
    private void initLogger(){
        Logger.init("L-C-J").hideThreadInfo().logLevel(LogLevel.FULL).methodCount(2).methodOffset(2).logTool(new AndroidLogTool());
    }

    /**
     * 初始化okhttp配置信息
     */
    private void initOkHttp() {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        okHttpClient.newBuilder().connectTimeout(15,TimeUnit.MINUTES)
                .readTimeout(15,TimeUnit.MINUTES)
                .writeTimeout(15,TimeUnit.MINUTES);
    }

    /**
     * 初始化加载出错或是网络错误等信息
     */
    public void initLoadOrErrorArgs(){
        LoadOrErrorHelper.getInstance().initEmpty(R.drawable.ym_head,"暂时没有内容可以显示").initLoadError(R.drawable.ym_head,"加载失败，点击屏幕重试").initNetWorkError(R.drawable.ym_head,"当前已断开网络，请检查网络连接");
    }
    /**
     * 设置glide使用okhttp
     */
    public void initGlide(){
//        Glide.get(this).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(new OkHttpClient.Builder().build());
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
