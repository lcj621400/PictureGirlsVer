package com.lichunjing.picturegirls.http;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by lcj621400 on 2015/12/12.
 * 访问网络获取数据
 */
public class Http {


    /**
     * 获取图片封面列表信息
     *
     * @param callback 回调接口
     */
    public static void  getCoverList(Object object,String url, Callback callback) {
        OkHttpUtils.get().url(url).tag(object).build().execute(callback);
    }

    public static void getCoverList(String url, Callback callback){
        OkHttpUtils.get().url(url).build().execute(callback);
    }

    /**
     * 获取此图册封面下的图集的类表信息
     *
     * @param id 要获取图片的id
     * @param callback
     */
    public static void getGalleryImages(Object object,int id, Callback callback) {
        String url = String.format(PicUrl.GET_GALLERY_URL, String.valueOf(id));
        OkHttpUtils.get().url(url).tag(object).build().execute(callback);
    }

    /**
     * 获取新闻列表信息
     * @param object tag值，用于取消请求
     * @param url 请求url
     * @param callback 用户实现的回调接口
     */
    public static void getNewsList(Object object,String url,Callback callback){
        OkHttpUtils.get().url(url).tag(object).build().execute(callback);
    }
}
