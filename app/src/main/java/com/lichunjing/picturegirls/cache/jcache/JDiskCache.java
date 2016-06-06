package com.lichunjing.picturegirls.cache.jcache;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.lichunjing.picturegirls.cache.jinterface.ICache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2016/2/15.
 * 磁盘存储
 */
@Deprecated
public class JDiskCache implements ICache {

    /**
     * 缓存工具类
     */
    private JCacheUtils cacheUtils;

    /**
     * 构造方法
     * @param context 上下文对象
     */
    public JDiskCache(Context context) {
        cacheUtils = new JCacheUtils(context);
    }

    /**
     * 读取sk卡缓存
     * @param key 读取缓存的key
     * @return
     */
    @Override
    public JCache readCache(String key) {
        return read(key);
    }

    /**
     * 跟新sd卡缓存
     * @param key 要更新缓存的key
     * @param value 要更新的缓存
     * @param updateTime 更新时间
     */
    @Override
    public void updateCache(String key, String value, long updateTime) {
        save(key, value, updateTime);
    }

    /**
     * 清除sd卡缓存
     */
    @Override
    public void clearCache() {
        String cacheDir=cacheUtils.getCacheDir();
        if(TextUtils.isEmpty(cacheDir)){
            JCacheUtils.log("缓存目录为空，不能清理sd卡缓存");
        }
        File cacheFile=new File(cacheDir);
        if(cacheFile.isDirectory()){
            int count=0;
            File[] files = cacheFile.listFiles();
            for(File f:files){
                if(f.exists()&&f.isFile()){
                    f.delete();
                    count++;
                }
            }
            JCacheUtils.log("清除sd卡缓存成功,成功删除缓存文件数量："+count);
        }else {
            JCacheUtils.log("清除缓存失败，缓存目录不是目录");
        }
    }

    /**
     * 从文件读取缓存
     * @param key
     * @return
     */
    private JCache read(String key) {
        File oneCacheFile = cacheUtils.getOneCacheFile(key);
        if (!oneCacheFile.exists()) {
            JCacheUtils.log("Sd卡缓存不存在文件："+key);
            return null;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(oneCacheFile));
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            JCacheUtils.log("读取sk卡缓存文件："+key+" 异常："+e.getMessage());
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        JCacheUtils.log("成功读取sd卡缓存文件："+key);
        return new JCache(sb.toString(), oneCacheFile.lastModified());
    }

    /**
     * 存储字符串到内存和文件
     */
    private void save(String key, String value, long lastModifyTime) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        // 存储到文件中
        File file = cacheUtils.getOneCacheFile(key);
        if(file==null){
            JCacheUtils.log("不能创建sd换存文件，请检查缓存目录是否设置正确");
            return;
        }
        file.setLastModified(lastModifyTime);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, false), 1024);
            bw.write(value);
            bw.flush();
        } catch (IOException e) {
            JCacheUtils.log("写入sd卡缓存文件："+key+" 异常:"+e.getMessage());
            return;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        JCacheUtils.log("成功写入sd卡缓存文件："+key+"---值为："+value);
    }

    public JCacheUtils getJCacheUtils(){
        return cacheUtils;
    }

    public long getDiskCacheSize(){
        long size=0;
        String cacheDir = cacheUtils.getCacheDir();
        if(TextUtils.isEmpty(cacheDir)){
            return size;
        }
        File file=new File(cacheDir);
        File[] files = file.listFiles();
        for(File f:files){
            if(f.exists()&&f.isFile()){
                size+=f.length();
            }
        }
        return size;
    }

    public String getDiskCacheFormatSize(Context context){
        return Formatter.formatFileSize(context,getDiskCacheSize());
    }

}
