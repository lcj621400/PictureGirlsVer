package com.lichunjing.picturegirls.cache.twocache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lichunjing.picturegirls.cache.twocache.disklrucache.DiskLruCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/3/3.
 */
public class DiskLruCacheHelper {
    public DiskLruCacheHelper(Context context, int appVersion, int maxSize) {
        openCache(context, appVersion, maxSize);
    }

    private DiskLruCache mCache;

    /**
     * 打开DiskLruCache。
     */
    private void openCache(Context context, int appVersion, int maxSize) {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                mCache = DiskLruCache.open(context.getExternalCacheDir(), appVersion, 1, maxSize);
            } else {
                mCache = DiskLruCache.open(context.getCacheDir(), appVersion, 1, maxSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写出缓存。
     */
    public void write(String keyCache,OneCache cache ) throws IOException {
        if (mCache == null) throw new IllegalStateException("Must call openCache() first!");

        DiskLruCache.Editor editor = mCache.edit(keyCache);

        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(0);
            try {
                outputStream.write(cache.value.getBytes());
                editor.commit();
            } catch (Exception e) {
                Log.d("DiskLruCacheHelper", e.getMessage());
                editor.abort();
            }
        }
    }

    /**
     * 读取缓存。
     */
    public OneCache read(String keyCache) throws IOException {
        if (mCache == null) throw new IllegalStateException("Must call openCache() first!");

        DiskLruCache.Snapshot snapshot = mCache.get(keyCache);

        if (snapshot != null) {
            long lastModfied = snapshot.getLastModfied(0);
            InputStream inputStream = snapshot.getInputStream(0);
            StringBuilder sb = new StringBuilder();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return new OneCache(sb.toString(), lastModfied);
        }

        return null;
    }

    /**
     * 检查缓存是否存在。
     */
    public boolean hasCache(String keyCache) {
        try {
            return mCache.get(keyCache) != null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 同步日志。
     */
    public void syncLog() {
        try {
            mCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭DiskLruCache。
     */
    public void closeCache() {
        syncLog();
    }
}
