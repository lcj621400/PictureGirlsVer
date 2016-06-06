package com.lichunjing.picturegirls.ui.me.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseActivity;
import com.lichunjing.picturegirls.cache.twocache.CacheManager;
import com.lichunjing.picturegirls.utils.FileUtils;

import java.lang.ref.WeakReference;

/**
 * 设置页面
 */
public class SettingsActivity extends BaseActivity {

    public static final String TAG="SettingsActivity";
    /**
     * 显示缓存大小
     */
    private TextView glideCacheSize;
    private TextView allCacheSize;
    //清楚缓存
    private TextView cleanGlideCache;
    private TextView cleanAllCache;

    private AlertDialog confirmDialog;

    private CleanCacheTask cleanCacheTask;
    private ComputCacheSizeTask computeCacheSizeTask;

    public static final int CLEAN_GLIDE_CAHCE_TYPE=0;
    public static final int CLEAN_ALL_CAHCE_TYPE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initToolBar("设置", true, getDefaultBackListener());
        glideCacheSize = (TextView) findViewById(R.id.glide_cache_size);
        allCacheSize= (TextView) findViewById(R.id.all_cache_size);
        cleanGlideCache = (TextView) findViewById(R.id.clean_glide_cache);
        cleanAllCache= (TextView) findViewById(R.id.clean_all_cache);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        cleanGlideCache.setOnClickListener(this);
        cleanAllCache.setOnClickListener(this);
    }

    @Override
    protected void initStatus() {
        super.initStatus();
        getCacheSize();
    }

    /**
     * 清除缓存
     */
    private void clearCache(int type){
        if(cleanCacheTask!=null){
            cleanCacheTask.cancel(true);
            cleanCacheTask=null;
        }
        cleanCacheTask=new CleanCacheTask(this,type);
        cleanCacheTask.execute();
    }

    /**
     * 获取缓存大小
     */
    private void getCacheSize(){
        if(computeCacheSizeTask!=null){
            computeCacheSizeTask.cancel(true);
            computeCacheSizeTask=null;
        }
        computeCacheSizeTask=new ComputCacheSizeTask(this);
        computeCacheSizeTask.execute();
    }

    /**
     * 显示清除缓存对话框
     */
    private void showConfirmDialog(final int type){
        if(confirmDialog==null){
            confirmDialog=new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("是否要清除缓存？")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showLoadingDialog("正在清理缓存...");
                    clearCache(type);
                }
            }).show();
        }else{
            confirmDialog.show();
        }
    }


    private void setDisplayCacheSize(String[] size){

        glideCacheSize.setText(TextUtils.isEmpty(size[1])?"0M":size[1]);
        allCacheSize.setText(TextUtils.isEmpty(size[0])?"0M":size[0]);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.clean_glide_cache:
                showConfirmDialog(CLEAN_GLIDE_CAHCE_TYPE);
                break;
            case R.id.clean_all_cache:
                showConfirmDialog(CLEAN_ALL_CAHCE_TYPE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cleanCacheTask!=null&&!cleanCacheTask.isCancelled()){
            cleanCacheTask.cancel(true);
        }
        if(computeCacheSizeTask!=null&&!computeCacheSizeTask.isCancelled()){
            computeCacheSizeTask.cancel(true);
        }
    }

    /**
     * 计算glide图片缓存的大小
     */
    static class ComputCacheSizeTask extends AsyncTask<Void,Void,String[]>{

        WeakReference<SettingsActivity> activity;

        public ComputCacheSizeTask(SettingsActivity activity){
            this.activity=new WeakReference<>(activity);
        }
        @Override
        protected String[] doInBackground(Void... params) {
            String allCache= CacheManager.getInstance(activity.get()).getCacheSize();
            String glideCache= FileUtils.getGlideDefaultCacheSize(activity.get().getApplicationContext());
            return new String[]{allCache,glideCache};
        }
        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            if(activity.get()!=null){
                activity.get().setDisplayCacheSize(result);
            }

        }
    }

    /**
     * 清除glide缓存
     */
    static class CleanCacheTask extends AsyncTask<Void,Void,Boolean>{

        WeakReference<SettingsActivity> activity;
        private int type;

        public CleanCacheTask(SettingsActivity activity,int type){
            this.activity=new WeakReference<>(activity);
            this.type=type;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(type==SettingsActivity.CLEAN_GLIDE_CAHCE_TYPE) {
                return FileUtils.clearGlideDefaultCache(activity.get().getApplicationContext());
            }else if(type==SettingsActivity.CLEAN_ALL_CAHCE_TYPE){
                try {
                    CacheManager.getInstance(activity.get()).cleanCache();
                    return true;
                }catch (Exception e){
                    Log.d(TAG,"清除缓存失败"+e);
                    return false;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(activity.get()!=null){
                activity.get().dMissLoadingDialog();
                if(result){
                    activity.get().getCacheSize();
                    activity.get().showToastShort("清除缓存成功");
                }else {
                    activity.get().showToastShort("清除缓存失败");
                }
            }

        }
    }


}
