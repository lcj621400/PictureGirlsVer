package com.lichunjing.picturegirls.cache.jcache;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2016/2/15.
 * 缓存工具类，管理磁盘缓存存放的位置，内存缓存的数目
 */
@Deprecated
public class JCacheUtils {

    /**
     * 缓存目录的名称
     */
    public static final String CACHE_NAME="JCache";

    /**
     * 缓存的路径
     */
    private static String CACHE_DIR;

    /**
     * 构造方法
     * @param context 上下问对象
     */
    public JCacheUtils(Context context){
        CACHE_DIR=generateCacheDir(context.getApplicationContext());
        JCacheUtils.log("缓存目录默认路径："+CACHE_DIR);
    }

    /**
     * 获得缓存目录的绝对路径
     * @param context
     * @return
     */
    public String generateCacheDir(Context context){
        File jCacheDir=null;
        File externalCacheDir = context.getExternalCacheDir();
        if(externalCacheDir.exists()){
            jCacheDir=new File(externalCacheDir,CACHE_NAME);
            if(!jCacheDir.exists()){
                jCacheDir.mkdirs();
            }

            return jCacheDir.getAbsolutePath();
        }else{
            File cacheDir = context.getCacheDir();
            if(cacheDir.exists()){
                jCacheDir=new File(cacheDir,CACHE_NAME);
                if(!jCacheDir.exists()){
                    jCacheDir.mkdirs();
                }
                return jCacheDir.getAbsolutePath();
            }
        }
        throw new IllegalStateException("不能创建缓存目录");
    }

    /**
     * 得到一个缓存文件
     * @param fileName
     * @return
     */
    public File getOneCacheFile(String fileName){
        File oneCacheFile=new File(CACHE_DIR,fileName+".txt");
        if(oneCacheFile.exists()) {
            JCacheUtils.log("读取缓存文件："+fileName);
        }else {
            JCacheUtils.log("尝试创建新的缓存文件："+fileName);
        }
        return oneCacheFile;
    }

    public String getCacheDir(){
        return CACHE_DIR;
    }

    public void setCacheDir(String cacheDir){
        CACHE_DIR=cacheDir;
        File cacheFile=new File(cacheDir);
        if(!cacheFile.exists()){
            cacheFile.mkdirs();
        }
        JCacheUtils.log("设置缓存目录为："+cacheDir);
    }

    /**
     * 获取合适的内存缓存的大小
     * @return
     */
    public static int getLruCacheSize(){
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8
        return cacheSize;
    }


    /**
     * 打印log信息
     * @param message
     */
    public static void log(String message){
        if(JCacheConfig.DEBUG){
            Log.i(JCacheConfig.CACHE_TAG,message);
        }
    }

}
