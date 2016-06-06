package com.lichunjing.picturegirls.cache.twocache;

/**
 * Created by Administrator on 2016/3/7.
 */
public class OneCache {
    /**
     * 缓存的数据
     */
    public String value;
    /**
     * 缓存的时间
     */
    public long lastModifilyTime;

    public OneCache(String value, long lastModifilyTime) {
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
