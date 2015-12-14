package com.lichunjing.picturegirls.http;

import com.google.gson.Gson;
import com.lichunjing.picturegirls.bean.GirlArrayBean;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by lcj621400 on 2015/12/12.
 */
public class TextHttp {

    public static void get() {


        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://www.tngou.net/tnfs/api/list?id=1&page=1&rows=20")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
//请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String htmlStr = response.body().string();
                Gson gson = new Gson();
                GirlArrayBean girlArrayBean = gson.fromJson(htmlStr, GirlArrayBean.class);
            }
        });
    }
}
