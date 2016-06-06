package com.lichunjing.picturegirls.cache;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lcj621400 on 2016/1/6.
 */
@Deprecated
public class CacheUtils {

    //缓存文件夹的名称
    private static final String OUT_LINE_CAHCE="outLineCache";
    //存储在内存中的缓存
    private static Map<String,OneCache> CACHE;
    //缓存目录的绝对路径
    private static  String   CACHE_DIR;
    //单例对象
    private static CacheUtils instance;


    // 设置超时时间为一个小时
    private static final long TIME_OUT_DISTANCE=60*1000;
    //私有构造函数
    private CacheUtils(Context context){
        CACHE=new HashMap<String ,OneCache>();
        CACHE_DIR=getCacheDir(context);
        if(TextUtils.isEmpty(CACHE_DIR)){
            throw new IllegalArgumentException("不能创建缓存目录");
        }
    }

    public static void initCacheUtils(Context context){
        instance=new CacheUtils(context.getApplicationContext());
    }


    // 单例模式
    public static CacheUtils getInstance(){
        return instance;
    }


    // 存储
    public void saveString(String key,String value){
        save(key,value);
    }

    /**
     * 读取缓存文件
     * @param key 读取的key，一般为url
     * @param ignoreTime 是否忽略超时时间，true则读取内存时，忽略时间
     * @return 返回指定的key的缓存，如果没有则返回null
     */
    public String getString(String key,boolean ignoreTime){
        return get(key,ignoreTime);
    }

    /**
     * 读取缓存文件，忽略缓存时间
     * @param key 读取的key，一般为url
     * @return 返回指定url的缓存，如果没有则返回null
     */
    public String getString(String key){
        return get(key,true);
    }
    // 从内存和文件中读取数据
    private String get(String key,boolean ignoreTime){
        if(TextUtils.isEmpty(key)){
            return null;
        }
        long currentTime=System.currentTimeMillis();
        String hashCodeKey=getHashCodeName(key);
        // 判断内存缓存中是否有后缓存
        if(CACHE.containsKey(hashCodeKey)){
            OneCache oneCache=CACHE.get(hashCodeKey);
            if(oneCache!=null) {
                String value = oneCache.value;
                if(!ignoreTime) {
                    long lastModifilyTime = oneCache.lastModifilyTime;
                    // 缓存已经过期了
                    if (currentTime - lastModifilyTime > TIME_OUT_DISTANCE) {
                        return null;
                    }
                }
                if (!TextUtils.isEmpty(value)) {
                    return value;
                }
            }
        }

        File file=getOneCacheFile(hashCodeKey);
        if(!file.exists()){
            return null;
        }
        if(!ignoreTime) {
            // 缓存已经过期
            if (currentTime - file.lastModified() > TIME_OUT_DISTANCE) {
                return null;
            }
        }
        StringBuilder sb=new StringBuilder();
        BufferedReader br=null;
        try {
            br=new BufferedReader(new FileReader(file));
            String temp=null;
            while ((temp=br.readLine())!=null){
                sb.append(temp);
            }
            String result=sb.toString();
            OneCache oneCache=null;
            // 如果忽略时间，则添加的内存中可能为过期的缓存，不更改缓存的缓存时间
            if(ignoreTime){
                oneCache=new OneCache(result,file.lastModified());
            }else{
                // 如果不忽略缓存时间，则将当前时间为缓存的时间
                oneCache=new OneCache(result,currentTime);
            }

            // 移除内存缓存中数据
            if(CACHE.containsKey(hashCodeKey)){
                CACHE.remove(hashCodeKey);
            }
            // 将数据添加到内存中
            CACHE.put(hashCodeKey,oneCache);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 存储字符串到内存和文件
    private void save(String key,String value){
        if(TextUtils.isEmpty(key)){
            return;
        }
        // 当前的时间
        long currentTime=System.currentTimeMillis();
        // hashCode名称
        String hashCodeName=getHashCodeName(key);
        // 存入内存缓存中
        if(CACHE.containsKey(hashCodeName)){
            CACHE.remove(hashCodeName);
        }
        OneCache oneCache=new OneCache(value,currentTime);
        CACHE.put(key,oneCache);
        // 存储到文件中
        File file=getOneCacheFile(hashCodeName);
        file.setLastModified(currentTime);
        BufferedWriter bw=null;
        try {
            bw=new BufferedWriter(new FileWriter(file,false),1024);
            bw.write(value);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    private String getCacheDir(Context context){
        File file=context.getExternalCacheDir();
        if(file.exists()){
                File outLineFile=new File(file,OUT_LINE_CAHCE);
                if(!outLineFile.exists()) {
                    outLineFile.mkdirs();
                    return outLineFile.getAbsolutePath();
                }
        }else{
               File inCacheFile=context.getCacheDir();
                File outLineFile=new File(inCacheFile,OUT_LINE_CAHCE);
                if(!outLineFile.exists()){
                    outLineFile.mkdirs();
                    return outLineFile.getAbsolutePath();
                }
            }
        return null;
        }

    private File getOneCacheFile(String fileName){
        File file=new File(CACHE_DIR,fileName+".txt");
        return file;
    }

    /**
     * 编码
     * @return
     */
    private String getHashCodeName(String key){
        return String.valueOf(key.hashCode());
    }



    class OneCache{
        String value;
        long lastModifilyTime;

        public OneCache(String value,long lastModifilyTime){
            this.value=value;
            this.lastModifilyTime=lastModifilyTime;
        }
    }

}
