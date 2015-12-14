package com.lichunjing.picturegirls.http;

import android.util.Log;

import com.lichunjing.picturegirls.bean.GirlArrayBean;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

/**
 * Created by lcj621400 on 2015/12/12.
 */
public class Http {


    public static final void getImage(int page,int pageSize,ResultCallback<GirlArrayBean> callback){
        String url=String.format(PicUrl.GET_IMAGE_URL,new String[]{String.valueOf(page),String.valueOf(pageSize)});
        Log.d("url",url);
//        url="http://www.tngou.net/tnfs/api/list?id=1&page=1&rows=20";
        new OkHttpRequest.Builder().url(url).get(callback);
    }
}
