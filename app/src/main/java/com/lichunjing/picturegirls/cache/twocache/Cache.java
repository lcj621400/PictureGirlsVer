package com.lichunjing.picturegirls.cache.twocache;



/**
 * Created by Administrator on 2016/3/7.
 */
public class Cache<T> {
    /**
     * 缓存的值
     */
    public T value;
    /**
     * 缓存是否过期
     */
    public boolean isExpired;

    /**
     * 构造方法
     * @param value
     * @param isExpired
     */
    public Cache(T value,boolean isExpired){
        this.value=value;
        this.isExpired=isExpired;
    }
}
