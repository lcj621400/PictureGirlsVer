package com.lichunjing.picturegirls.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lichunjing.picturegirls.bean.cover.GirlListBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by lcj621400 on 2015/12/24.
 */
public abstract class GirlListCallback extends Callback<GirlListBean>{
    @Override
    public GirlListBean parseNetworkResponse(Response response) throws IOException {
        String json=response.body().toString();
        Log.d("lcj",json);
        GirlListBean bean = JSON.parseObject(json, GirlListBean.class);
        return bean;
    }


}
