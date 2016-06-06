package com.lichunjing.picturegirls.cache.jinterface;

import com.lichunjing.picturegirls.cache.jcache.JCache;

/**
 * Created by Administrator on 2016/2/15.
 * 缓存抽象接口
 */
@Deprecated
public interface ICache {

    /**
     * 读取缓存
     * @param key 读取换成的key
     * @return
     */
    JCache readCache(String key);

    /**
     * 更新缓存
     * @param key 要更新缓存的key
     * @param value 要更新的缓存
     * @param updateTime 更新时间
     */
    void updateCache(String key,String value,long updateTime);

    /**
     * 清理缓存
     */
    void clearCache();
}
