package com.lichunjing.picturegirls.ui.me.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.lichunjing.picturegirls.utils.BaseSPUtils;

/**
 * Created by Administrator on 2016/5/27.
 * app 应用而配置
 */
public class AppConfigUtils extends BaseSPUtils{

    private static final String APP_FIRST_START_KEY="app.first.start";

    /**
     * 设置app是否是第一次启动
     * @param context
     * @param isAppFirstStart
     */
    public static void setAppFristStart(Context context,boolean isAppFirstStart){
        SharedPreferences.Editor editor=getEditor(getDefaultSharedPreferences(context));
        saveBoolean(editor,APP_FIRST_START_KEY,isAppFirstStart);
        commit(editor);
    }

    /**
     * 得到app是否第一次启动
     * @param context
     * @return
     */
    public static boolean isAppFirstStart(Context context){
        return getBoolean(getDefaultSharedPreferences(context),APP_FIRST_START_KEY,true);
    }
}
