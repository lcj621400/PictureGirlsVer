package com.lichunjing.picturegirls.utils;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

import com.bumptech.glide.Glide;
import com.lichunjing.picturegirls.configure.AppConfig;

import java.io.File;
import java.io.IOException;

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
     * 清除文件夹中的所有文件，可以是多级目录
     * @return
     */
    public static boolean deleteFiles(File firDir){
        if(firDir==null){
            return false;
        }
        try {
            if(firDir.isFile()){
                return firDir.delete();
            }
            if(firDir.isDirectory()) {
                File[] files = firDir.listFiles();
                for (File f : files) {
                    if (f.exists() && f.isFile()) {
                        f.delete();
                    }else {
                        deleteFiles(f);
                    }
                }
            }
            return true;
        }catch (Exception e){
           return false;
        }
    }


    /**
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
