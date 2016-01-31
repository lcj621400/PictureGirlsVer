package com.lichunjing.picturegirls.http;

import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by lcj621400 on 2015/12/12.
 */
public class Http {


    /**
     * 获取封面列表信息
     *
     * @param page     页码
     * @param pageSize 每页返回的数据量
     * @param callback 回调接口
     */
    public static void getCoverList(int id, int page, int pageSize, Callback callback) {
        String url = String.format(PicUrl.GET_IMAGE_URL, new Object[]{id + "", page + "", pageSize + ""});
        Log.d("url", url);
//        url="http://www.tngou.net/tnfs/api/list?id=1&page=1&rows=20";
        OkHttpUtils.get().url(url).build().execute(callback);
    }

    /**
     * 获取此封面下的图集的类表信息
     *
     * @param id
     * @param callback
     */
    public static void getGalleryImages(int id, Callback callback) {
        String url = String.format(PicUrl.GET_GALLERY_URL, id + "");
        OkHttpUtils.get().url(url).build().execute(callback);
    }
}
