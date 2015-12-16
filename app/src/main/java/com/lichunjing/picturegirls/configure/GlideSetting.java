package com.lichunjing.picturegirls.configure;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.lichunjing.picturegirls.utils.FileUtils;

import java.io.File;

/**
 * Created by lcj621400 on 2015/12/16.
 * Glide全局配置类
 */
public class GlideSetting implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置缓存位置
        builder.setDiskCache(new GlideDiskCacheFactory(111));
        //设置图片的质量
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }


    public class GlideDiskCacheFactory extends DiskLruCacheFactory{

        public GlideDiskCacheFactory(CacheDirectoryGetter cacheDirectoryGetter, int diskCacheSize) {
            super(cacheDirectoryGetter, diskCacheSize);
        }

        public GlideDiskCacheFactory(String diskCacheFolder, int diskCacheSize) {
            super(diskCacheFolder, diskCacheSize);
        }
        public GlideDiskCacheFactory(int diskCacheSize) {
            super(new CacheDirectoryGetter() {
                @Override
                public File getCacheDirectory() {
                    File catchFile= FileUtils.getImageCacheFile();
                    return catchFile;
                }
            }, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
        }

    }
}
