package com.lichunjing.picturegirls.cache.twocache;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CacheManager {


    private static CacheManager instance;
    private LruCacheHelper mLruCache;
    private DiskLruCacheHelper mDiskLruCache;
    private Context context;

    /**
     * 磁盘缓存的大小
     */
    private  static final int DiskLruCacheSize=10*1024*1024;
    /**
     * 缓存过期时间1小时
     */
    private static final int ExpiredTime=60*60*1000;


    /**
     * 私有构造方法
     * @param context
     * @param maxSize
     */
    private CacheManager(Context context,int maxSize){
        mLruCache=new LruCacheHelper(CacheUtils.getLruCacheSize());
        mDiskLruCache=new DiskLruCacheHelper(context,CacheUtils.getAppVersion(context),maxSize);
        this.context=context.getApplicationContext();
    }

    /**
     * 静态方法获取CacheManager实例
     * @param context
     * @return
     */
    public static CacheManager getInstance(Context context){
        if(instance==null){
            synchronized (CacheManager.class){
                if(instance==null){
                    instance=new CacheManager(context.getApplicationContext(),DiskLruCacheSize);
                }
            }
        }
        return instance;
    }

    /**
     * 写字符串
     * @param key
     * @param value
     */
    public void writeString(String key,String value){
        write(key,value);
    }

    /**
     * 读字符串
     * @param key
     * @return
     */
    public Cache<String> readString(String key){
        OneCache cache = read(key);
        if(cache!=null)
            return new Cache<String>(cache.value,CacheUtils.isExpired(ExpiredTime,cache.lastModifilyTime));
        else
            return null;
    }

    /**
     * 写入JSONObject
     * @param key
     * @param object
     */
    public void writeJsonObject(String key, JSONObject object){
        write(key,object.toString());
    }

    /**
     * 读取JSONObject
     * @param key
     * @return
     * @throws JSONException
     */
    public Cache<JSONObject> readJsonObject(String key) throws JSONException {
        OneCache cache=read(key);
        if(cache!=null){
            return  new Cache<JSONObject>(new JSONObject(cache.value),CacheUtils.isExpired(ExpiredTime,cache.lastModifilyTime));
        }else{
            return null;
        }
    }

    /**
     * 写入一个缓存
     * @param key 写入缓存的key，通常为url
     * @param value 写入缓存的值
     */
    private void write(String key,String value){
        String md5Key=CacheUtils.hashKeyForDisk(key);
        long lastModfied= System.currentTimeMillis();
        OneCache cache=new OneCache(value,lastModfied);
        mLruCache.write(md5Key,cache);
        try {
            mDiskLruCache.write(md5Key,cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取一个缓存
     * @param key 读取缓存的值，通常为url
     * @return 如果缓存存在则返回缓存，否则为null
     */
    private OneCache read(String key){
        String md5Key=CacheUtils.hashKeyForDisk(key);
        OneCache cache = mLruCache.read(md5Key);
        if(cache!=null){
            return cache;
        }else{
            try {
                OneCache oneCache = mDiskLruCache.read(md5Key);
                return oneCache;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void flush(){
        mDiskLruCache.syncLog();
    }

    public void close(){
        mLruCache.closeCache();
        mDiskLruCache.closeCache();
    }

    /**
     * 清除缓存
     * 清除内存卡的cache文件夹的缓存
     * 清除sd卡的cache文件夹缓存
     */
    public void cleanCache(){
        CacheUtils.cleanCache(context);
    }

    /**
     * 清除所有缓存
     */
    public void cleanAllCache(){
        CacheUtils.cleanAllCache(context);
    }

    /**
     * 获取应用的缓存大小
     * @return
     */
    public String getCacheSize(){
        try {
            String size=CacheUtils.getTotalCacheSize(context);
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
