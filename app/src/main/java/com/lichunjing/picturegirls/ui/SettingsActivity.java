package com.lichunjing.picturegirls.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;
import com.lichunjing.picturegirls.utils.FileUtils;

public class SettingsActivity extends BasePicActivity {
    /**
     * 显示缓存大小
     */
    private TextView cacheSize;
    //清楚缓存
    private RelativeLayout clearCache;

    private AlertDialog confirmDialog;

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
        initToolBar("设置", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cacheSize= (TextView) findViewById(R.id.cache_size);
        clearCache= (RelativeLayout) findViewById(R.id.clear_cache);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });
    }

    @Override
    protected void initStatus() {
        super.initStatus();
        getCacheSize();
    }

    private void clearCache(){
        FileUtils.clearGlideDefaultCache(this);
        new Thread(){
            @Override
            public void run() {
                super.run();
                final boolean success = FileUtils.clearCache();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dissLoadingDialog();
                        if(success){
                            getCacheSize();
                        }else{
                            showToast("清楚缓存失败");
                        }
                    }
                });
            }
        }.start();

    }

    private void getCacheSize(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                final String imageCacheSize = FileUtils.getImageCacheSize(mApplication);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cacheSize.setText(imageCacheSize);
                    }
                });
            }
        }.start();
    }
    private void showConfirmDialog(){
        if(confirmDialog==null){
            confirmDialog=new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("是否要清除缓存？")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showLoadingDialog("正在清理缓存...");
                    clearCache();
                }
            }).show();
        }else{
            confirmDialog.show();
        }
    }
}
