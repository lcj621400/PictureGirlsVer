package com.lichunjing.picturegirls.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/5/27.
 * base SharedPreferences Utils
 */
public class BaseSPUtils {

    protected static final String DEFAULT_SP_NAME = "PictureSP";
    protected static final int DEFAULT_SP_MODE = Context.MODE_PRIVATE;

    protected static final int DEFAULT_INT_VALUE = 0;
    protected static final boolean DEFAULT_BOOLEAN_VALUE = false;
    protected static final float DEFAULT_FLOAT_VALUE = 0f;
    protected static final long DEFAULT_LONG_VALUE = 0l;
    protected static final String DEFAULT_STRING_VALUE = null;

    private static SharedPreferences instanceWithDefaultName;
    private static SharedPreferences instanceWithCustomName;


    /**
     * 得到SharedPreferences对象。
     * 1、Mode默认为Context.MODE_PRIVATE；
     * 2、sharedPreferences的name，如果不传，则使用默认的name
     *
     * @param context
     * @return SharedPreferences对象
     */
    protected static SharedPreferences getDefaultSharedPreferences(Context context) {
        if (instanceWithDefaultName==null) {
            instanceWithDefaultName=context.getApplicationContext().getSharedPreferences(DEFAULT_SP_NAME, DEFAULT_SP_MODE);
        }
        return instanceWithDefaultName;
    }

    protected static SharedPreferences getCustomSharedPreferences(Context context, String spName){
        if(instanceWithCustomName==null){
            instanceWithCustomName=context.getApplicationContext().getSharedPreferences(spName, DEFAULT_SP_MODE);
        }
        return instanceWithCustomName;
    }

    protected static SharedPreferences.Editor getEditor(SharedPreferences sp) {
        return sp.edit();
    }

    protected static void saveInt(SharedPreferences.Editor editor, String key, int value) {
        editor.putInt(key, value);
    }

    protected static int getInt(SharedPreferences sp, String key, int defaultIntValue) {
        return sp.getInt(key, defaultIntValue);
    }


    protected static void saveBoolean(SharedPreferences.Editor editor, String key, boolean value) {
        editor.putBoolean(key, value);
    }

    protected static boolean getBoolean(SharedPreferences sp, String key, boolean defaultIntValue) {
        return sp.getBoolean(key, defaultIntValue);
    }

    protected static void saveFloat(SharedPreferences.Editor editor, String key, float value) {
        editor.putFloat(key, value);
    }

    protected static float getFloat(SharedPreferences sp, String key, float defaultIntValue) {
        return sp.getFloat(key, defaultIntValue);
    }

    protected static void saveLong(SharedPreferences.Editor editor, String key, long value) {
        editor.putLong(key, value);
    }

    protected static long getLong(SharedPreferences sp, String key, long defaultIntValue) {
        return sp.getLong(key, defaultIntValue);
    }

    protected static void saveString(SharedPreferences.Editor editor, String key, String value) {
        editor.putString(key, value);
    }

    protected static String getString(SharedPreferences sp, String key, String defaultIntValue) {
        return sp.getString(key, defaultIntValue);
    }

    protected static void remove(SharedPreferences.Editor editor,String key){
        editor.remove(key);
    }

    protected static void clear(SharedPreferences.Editor editor){
        editor.clear();
    }

    protected static void commit(SharedPreferences.Editor editor){
        editor.commit();
    }
}
