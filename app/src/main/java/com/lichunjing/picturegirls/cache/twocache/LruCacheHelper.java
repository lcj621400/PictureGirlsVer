package com.lichunjing.picturegirls.cache.twocache;

import android.util.LruCache;

/**
 * Created by Administrator on 2016/3/3.
 */
public class LruCacheHelper {
    public LruCacheHelper(int maxSize) {
        openCache(maxSize);
    }

    private  LruCache<String, OneCache> mCache;

    /**
     * 初始化LruCache。
     */
    private   void openCache(int maxSize) {
        mCache = new LruCache<String, OneCache>((int) maxSize) {
            @Override
            protected int sizeOf(String key, OneCache cache) {
                return cache.value.getBytes().length;
            }
        };

    }

    /**
     * 把图片写入缓存。
     */
    public  void write(String key, OneCache value) {
        mCache.put(key, value);
    }

    /**
     * 从缓存中读取图片数据。
     */
    public  OneCache read(String key) {
        return mCache.get(key);
    }

    public  void closeCache() {
        // 暂时没事干。
    }
}
