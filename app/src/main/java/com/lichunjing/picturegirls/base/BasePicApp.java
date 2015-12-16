package com.lichunjing.picturegirls.base;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.lichunjing.picturegirls.configure.AppException;
import com.zhy.http.okhttp.OkHttpClientManager;

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
        initUnCatchException();
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
