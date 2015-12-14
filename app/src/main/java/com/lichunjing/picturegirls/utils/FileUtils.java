package com.lichunjing.picturegirls.utils;

import android.os.Environment;


import com.lichunjing.picturegirls.configure.AppConfig;

import java.io.File;

/**
 * 文件操作相关的工具类
 * Created by lcj621400 on 2015/12/12.
 */
public class FileUtils {

    public static final String LOG_NAME="AppLog.log";

    /**
     * 返回存储log信息的文件
     * @return
     */
    public static final File getLogFile(){
        if(!isMounted()){
            return null;
        }
        File logFile=new File(AppConfig.DEFAULT_SAVE_LOG_PAHT,LOG_NAME);
        if(!logFile.exists()){
            logFile.mkdirs();
        }
        return logFile;
    }

    /**
     * 返回sd卡是否挂载
     * @return
     */
    public static final boolean isMounted(){
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }
}
