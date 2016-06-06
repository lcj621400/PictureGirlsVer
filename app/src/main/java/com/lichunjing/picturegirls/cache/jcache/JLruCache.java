package com.lichunjing.picturegirls.cache.jcache;

import android.support.v4.util.LruCache;

import com.lichunjing.picturegirls.cache.jinterface.ICache;

/**
 * Created by Administrator on 2016/2/15.
 * 内存缓存，存储String类型的数据
 */
@Deprecated
public class JLruCache implements ICache {
    private static LruCache<String,JCache> lCache;

    public JLruCache(){
        lCache=new LruCache<String,JCache>(JCacheUtils.getLruCacheSize()){
            @Override
            protected int sizeOf(String key, JCache value) {
                return value.value.getBytes().length;
            }
        };
    }
    @Override
    public JCache readCache(String key) {
        JCache cache = lCache.get(key);
        if(cache==null){
            JCacheUtils.log("读取内存缓存文件："+key+"---结果为：null");
        }
        return cache;
    }

    @Override
    public void updateCache(String key, String value, long updateTime) {
        lCache.put(key,new JCache(value,updateTime));
    }

    @Override
    public void clearCache() {

    }
}
