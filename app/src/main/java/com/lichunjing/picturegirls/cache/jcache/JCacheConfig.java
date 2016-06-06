package com.lichunjing.picturegirls.cache.jcache;

/**
 * Created by Administrator on 2016/2/15.
 */
@Deprecated
public class JCacheConfig {

    public static final String CACHE_TAG="JCache";

    /**
     * 是否debug
     */
    public static boolean DEBUG=true;
    /**
     * 内存缓存的最大数量
     */
    public static int CACHE_COUNT=200;
    /**
     * 磁盘缓存的大小
     */
    public static long CACHE_SIZE=50*1024*1024;

    /**
     * 设置过期时间为1个小时
     */
    public static long EXPIRED_TIME=1*60*60;
}
