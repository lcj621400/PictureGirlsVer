package com.lichunjing.picturegirls.utils;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;


import com.bumptech.glide.Glide;
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
     * 返回存储log信息的文件
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
     * 获得缓存图片的文件
     * @return
     */
    public static final File getImageCacheFile(){
        if(!isMounted()){
            return null;
        }
        File imageCacheFile=new File(AppConfig.DEFAULT_SAVE_IMAGE_PATH);
        if(!imageCacheFile.exists()){
            imageCacheFile.mkdirs();
        }
        return  imageCacheFile;
    }

    /**
     * 获取自定义的缓存目录的缓存大小，
     * @return
     */
    public static final String getImageCacheSize(Context context){
        File cacheFile= getImageCacheFile();
        long size=0;
        for(File f:cacheFile.listFiles()){
            if(f.exists()&&f.isFile()){
                size+=f.length();
            }
        }
        String formatSize = Formatter.formatFileSize(context, size);
        return formatSize;
    }

    /**
     * 获取Glide默认的缓存目录的缓存大小：/data/data/com.lichunjing.picturein/cache/image
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
     * 清除缓存
     * @return
     */
    public static final boolean clearCache(){
        try {
            File cacheFile= getImageCacheFile();
            for(File f:cacheFile.listFiles()){
                if(f.exists()&&f.isFile()){
                    f.delete();
                }
            }
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
