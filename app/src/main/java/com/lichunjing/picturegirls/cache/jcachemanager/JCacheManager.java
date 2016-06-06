package com.lichunjing.picturegirls.cache.jcachemanager;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.lichunjing.picturegirls.cache.jcache.JCache;
import com.lichunjing.picturegirls.cache.jcache.JCacheConfig;
import com.lichunjing.picturegirls.cache.jcache.JCacheUtils;
import com.lichunjing.picturegirls.cache.jcache.JDiskCache;
import com.lichunjing.picturegirls.cache.jcache.JLruCache;

import java.io.File;

/**
 * Created by Administrator on 2016/2/15.
 */
@Deprecated
public class JCacheManager {

    /**
     * 内存缓存
     */
    private JLruCache lruCache;
    /**
     * sd卡缓存
     */
    private JDiskCache diskCache;

    /**
     * 缓存管理单例
     */
    private static JCacheManager instance;

    /**
     * 上下文管理对象
     */
    private Context mContext;

    /**
     * 静态方法获取实例
     * @param context 上下文对象
     * @return
     */
    public static JCacheManager getInstance(Context context){
        if(instance==null){
            synchronized (JCacheManager.class){
                if(instance==null) {
                    instance = new JCacheManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造方法
     * @param context 上下文对象
     */
    private JCacheManager(Context context){
        lruCache=new JLruCache();
        diskCache=new JDiskCache(context);
        mContext=context.getApplicationContext();
        JCacheUtils.log("创建JCacheManager实例");
//        String path=context.getCacheDir()+ File.separator+"AJCache";
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"AJCache";
        setDiskCacheDir(path);
    }

    /**
     * 读取缓存
     * @param key 读取缓存的key
     * @return
     */
    public JCache readCache(String key){
        String hashCodeKey=getHashCodeName(key);
        JCacheUtils.log("读取缓存文件："+hashCodeKey+"---原始名称为："+key);
        JCache jCache = lruCache.readCache(hashCodeKey);
        if(jCache!=null){
            return jCache;
        }else {
            jCache = diskCache.readCache(hashCodeKey);
            // 从sd卡读取缓存，需要加入内存缓存
            if(jCache!=null) {
                updateLruCache(hashCodeKey, jCache);
            }
        }
        return jCache;
    }


    /**
     * 更新缓存
     * @param key 缓存的key
     * @param value 缓存的值
     * @param updateTime 更新缓存的时间
     */
    public void updateCache(@NonNull String key, @NonNull String value, @NonNull long updateTime){
        lruCache.updateCache(getHashCodeName(key),value,updateTime);
        diskCache.updateCache(getHashCodeName(key),value,updateTime);
    }

    /**
     * 更新缓存，更新的时间默认为系统的当前时间
     * @param key 缓存的key
     * @param value 缓存的值
     */
    public void updateCache(String key,String value){
        lruCache.updateCache(getHashCodeName(key),value,System.currentTimeMillis());
        diskCache.updateCache(getHashCodeName(key),value,System.currentTimeMillis());
    }

    /**
     * 更新内存缓存
     * @param key 缓存的key
     * @param cache 缓存的值
     */
    private void updateLruCache(String key,JCache cache){
        lruCache.updateCache(key,cache.value,cache.lastModifilyTime);
    }

    /**
     * 获得制定key的hashCode编码
     * @return
     */
    private String getHashCodeName(String key){
        return String.valueOf(key.hashCode());
    }

    /**
     * 清理缓存
     */
    public void clearCache(){
        lruCache.clearCache();
        diskCache.clearCache();
    }

    /**
     * 清理内存缓存
     */
    public void clearLruCache(){
        lruCache.clearCache();
    }

    /**
     * 清理sd卡缓存
     */
    public void clearDiskCache(){
        diskCache.clearCache();
    }

    /**
     * 设置缓存目录
     * @param path 缓存目录的绝对路径
     * @return
     */
    public JCacheManager setDiskCacheDir(String path){
        diskCache.getJCacheUtils().setCacheDir(path);
        return instance;
    }

    /**
     * 内存缓存的最大数量
     * @param lruCacheMaxSize
     * @return
     */
    public JCacheManager setLruCacheMaxSize(int lruCacheMaxSize){
        JCacheConfig.CACHE_COUNT=lruCacheMaxSize;
        JCacheUtils.log("设置内存缓存最大数量："+lruCacheMaxSize);
        return instance;
    }

    /**
     * sd卡缓存文件的最大值
     * @param diskCacheMaxSize sd卡缓存的大小 单位：byte
     * @return
     */
    public JCacheManager setDiskCacheMaxSize(long diskCacheMaxSize){
        JCacheConfig.CACHE_SIZE=diskCacheMaxSize;
        JCacheUtils.log("设置sd卡缓存文件的大小为："+diskCacheMaxSize+" byte");
        return instance;
    }

    /**
     * 缓存过期的时间
     * @param expiredTime 缓存过期的时间 单位：妙
     * @return
     */
    public JCacheManager setExpiredTime(long expiredTime){
        JCacheConfig.EXPIRED_TIME=expiredTime;
        JCacheUtils.log("设置缓存过期的时间为："+expiredTime+" 秒");
        return instance;
    }

    /**
     * 设置是否debug，打印debug信息
     * @param debug
     * @return
     */
    public JCacheManager setDebug(boolean debug){
        JCacheConfig.DEBUG=debug;
        JCacheUtils.log("设置debug模式为："+debug);
        return instance;
    }

    public long getDiskCacheSize(){
        return diskCache.getDiskCacheSize();
    }

    public String getDiskCacheFormatSize(){
        return diskCache.getDiskCacheFormatSize(mContext);
    }


    /**
     * 判读缓存是否过期是否过期
     * @return
     */
    public static boolean isExpired(JCache cache){
        if(cache==null){
            return true;
        }
        return System.currentTimeMillis()-cache.lastModifilyTime> JCacheConfig.EXPIRED_TIME;
    }
}
