package com.lichunjing.picturegirls.http;

import android.util.Log;

import com.lichunjing.picturegirls.bean.cover.GirlListBean;
import com.lichunjing.picturegirls.bean.gallery.GirlPictureBean;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

/**
 * Created by lcj621400 on 2015/12/12.
 */
public class Http {


    /**
     * 获取封面列表信息
     * @param page 页码
     * @param pageSize 每页返回的数据量
     * @param callback 回调接口
     */
    public static  void getCoverList(int page, int pageSize, ResultCallback<GirlListBean> callback){
        String url=String.format(PicUrl.GET_IMAGE_URL,new Object[]{page+"",pageSize+""});
        Log.d("url",url);
//        url="http://www.tngou.net/tnfs/api/list?id=1&page=1&rows=20";
        new OkHttpRequest.Builder().url(url).get(callback);
    }

    /**
     * 获取此封面下的图集的类表信息
     * @param id
     * @param callback
     */
    public static  void getGalleryImages(int id,ResultCallback<GirlPictureBean> callback){
        String url=String.format(PicUrl.GET_GALLERY_URL,id+"");
        new OkHttpRequest.Builder().url(url).get(callback);
    }
}
