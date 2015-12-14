package com.lichunjing.picturegirls.base;

import android.app.Application;

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
    }

    public static BasePicApp getInstance(){
        return  instance;
    }
}
