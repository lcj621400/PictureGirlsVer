package com.lichunjing.picturegirls.cache.jcache;

/**
 * Created by Administrator on 2016/2/15.
 * 缓存实体
 */
@Deprecated
public class JCache {
    /**
     * 缓存的数据
     */
    public String value;
    /**
     * 缓存的时间
     */
    public long lastModifilyTime;

    public JCache(String value, long lastModifilyTime) {
        this.value = value;
        this.lastModifilyTime = lastModifilyTime;
    }

    @Override
    public String toString() {
        return "JCache{" +
                "value='" + value + '\'' +
                ", lastModifilyTime=" + lastModifilyTime +
                '}';
    }
}
