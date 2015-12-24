package com.lichunjing.picturegirls.utils;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.lichunjing.picturegirls.configure.AppConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 文件操作相关的工具类
 * Created by lcj621400 on 2015/12/12.
 */
public class FileUtils {

    public static final String LOG_NAME="AppLog.txt";


    /**
     * 返回存储log信息的文件,存储在自定义的文件夹下
     * @return
     */
    public static final File getLogFile(){
        if(!isMounted()){
            return null;
        }
        File logFilePath=new File(AppConfig.DEFAULT_SAVE_LOG_PAHT);
        if(!logFilePath.exists()){
            logFilePath.mkdirs();
        }
        File logFile=new File(logFilePath.getAbsolutePath()+File.separator+LOG_NAME);
        if(!logFile.exists()){
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logFile;
    }

    /**
     * 获取缓存文件，存储在app私有的文件中
     * @return
     */
    public static final File getLogFilePrivate(Context context){
        File filesDir = context.getFilesDir();
        File logFile=new File(filesDir.getAbsolutePath()+File.separator+LOG_NAME);
        if(!logFile.exists()){
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logFile;
    }

    /**
     * 此缓存为自定义的Glide缓存文件夹，为sd卡的cache缓存目录
     * @param context
     * @return
     */
    public static String getGlideCustomCacheSize(Context context){
        File cacheFile=context.getExternalCacheDir();
        File glideCacheFile=new File(cacheFile, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
        if(!glideCacheFile.exists()){
            return "0.00 B";
        }
        File[] files=glideCacheFile.listFiles();
        long size=0;
        for(File f:files){
            if(f.exists()&&f.isFile()){
                size+=f.length();
            }
        }
        return Formatter.formatFileSize(context,size);
    }

    /**
     * 清除自定义缓存
     * @param context
     * @return
     */
    public static boolean clearCustomGlideCache(Context context){
        try {
            File cacheFile=context.getExternalCacheDir();
            File glideCacheFile=new File(cacheFile, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
            if(!glideCacheFile.exists()){
                return false;
            }
            File[] files=glideCacheFile.listFiles();
            for(File f:files){
                if(f.exists()&&f.isFile()){
                    f.delete();
                }
            }
            return true;
        }catch (Exception e){
           return false;
        }
    }


    /**
     * 此缓存为app的内部存储的私有存储文件（此版本的Glide的bug，如果设置了新的存储路径，但Glide不会获得用户设置的缓存的位置的缓存）获取Glide默认的缓存目录的缓存大小：/data/data/com.lichunjing.picturein/cache/image
     * 如果设置了自定义的目录缓存，则此缓存下没有缓存文件。（默认缓存会随app卸载，清理）
     * @return
     */
    public static final String getGlideDefaultCacheSize(Context context){
        File cacheFile=Glide.getPhotoCacheDir(context);
        File[] files = cacheFile.listFiles();
        long size=0;
        for(File f:files){
            if(f.exists()&&f.isFile()){
                size+=f.length();
            }
        }
        String formatSize=Formatter.formatFileSize(context,size);
        return  formatSize;
    }

    /**
     * 清理Glide默认的缓存
     * @param context
     * @return
     */
    public static final boolean clearGlideDefaultCache(Context context){
        try {
            Glide.get(context).clearDiskCache();
        }catch (Exception e){
            return false;
        }
        return true;
    }



    /**
     * 返回sd卡是否挂载
     * @return
     */
    public static final boolean isMounted(){
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }
}
