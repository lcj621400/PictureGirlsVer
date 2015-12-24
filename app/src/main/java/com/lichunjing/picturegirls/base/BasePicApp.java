package com.lichunjing.picturegirls.base;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.configure.AppException;
import com.lichunjing.picturegirls.widget.loadorerror.LoadOrErrorHelper;
import com.squareup.okhttp.OkHttpClient;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

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
    }

    private void initOkHttp() {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
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
        Glide.get(this).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(new OkHttpClient()));
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
