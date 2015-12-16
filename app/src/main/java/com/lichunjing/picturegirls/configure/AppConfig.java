package com.lichunjing.picturegirls.configure;

import android.os.Environment;

import java.io.File;

/**
 * 系统配置信息，用于保存系统的配置
 * Created by lcj621400 on 2015/12/12.
 */
public class AppConfig {
    /**
     * 应用在sd卡中创建缓存目录的名称
     */
    public static final String APP_NAME="GirlsGallery";

    /**
     * 图片的默认缓存位置
     */
    public static final String DEFAULT_SAVE_IMAGE_PATH=
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator
                    +APP_NAME
                    +File.separator
                    +"image";
    /**
     * 文件的默认缓存位置
     */
    public static final String DEFAULT_SAVE_FILE_PATH=
            Environment.getExternalStorageDirectory().getAbsolutePath()
            +File.separator
            +APP_NAME
            +File.separator
            +"file";
    /**
     * app运行的log信息
     */
    public static final String DEFAULT_SAVE_LOG_PAHT=
            Environment.getExternalStorageDirectory().getAbsolutePath()
            +File.separator
            +APP_NAME
            +File.separator
            +"log";
}
