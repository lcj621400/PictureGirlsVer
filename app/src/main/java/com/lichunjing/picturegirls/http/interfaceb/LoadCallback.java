package com.lichunjing.picturegirls.http.interfaceb;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface LoadCallback<T> {
    void onSuccess(T result);

    void onFailer();
}
