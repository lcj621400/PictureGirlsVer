package com.lichunjing.picturegirls.utils;

import android.os.Environment;


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
     * 返回sd卡是否挂载
     * @return
     */
    public static final boolean isMounted(){
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }
}
