package com.lichunjing.picturegirls.widget.loadorerror;

import android.app.ListActivity;
import android.location.GpsStatus;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lcj621400 on 2015/12/24.
 */
public class LoadOrErrorHelper {

    private static LoadOrErrorHelper helper;


    private int netWorkErrorDrawable=-1;
    private String netWorkErrorMessage="";

    private int loadErrorDrawable=-1;
    private String loadErrorMessage="";

    private int emptyDrawable=-1;
    private String emptyMessage="";


    private LoadOrErrorHelper(){};

    public static LoadOrErrorHelper getInstance(){
        if(helper==null){
            helper=new LoadOrErrorHelper();
        }
        return helper;
    }

    public LoadOrErrorHelper initNetWorkError(int drawableId,String message){
        this.netWorkErrorDrawable=drawableId;
        this.netWorkErrorMessage=message;
        return this;
    }

    public LoadOrErrorHelper initLoadError(int drawableId, String message){
        this.loadErrorDrawable=drawableId;
        this.loadErrorMessage=message;
        return this;
    }

    public LoadOrErrorHelper initEmpty(int drawableId,String message){
        this.emptyDrawable=drawableId;
        this.emptyMessage=message;
        return this;
    }


    public void showNetWorkError(ViewGroup viewGroup, LoadOrErrorView errorView){
        int count=viewGroup.getChildCount();
        if(count==0){
            throw new IllegalArgumentException("ViewGroup 没有子View");
        }
        if(errorView==null){
            throw new IllegalArgumentException("LoadOrErrorView 为空");
        }
        errorView.showErrorNetWorkView(netWorkErrorDrawable,netWorkErrorMessage);
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v!=errorView){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showLoadError(ViewGroup viewGroup,LoadOrErrorView errorView,View.OnClickListener listener){
        int count=viewGroup.getChildCount();
        if(count==0){
            throw new IllegalArgumentException("ViewGroup 没有子View");
        }
        if(errorView==null){
            throw new IllegalArgumentException("LoadOrErrorView 为空");
        }
        errorView.showLoadErrorView(loadErrorDrawable,loadErrorMessage,listener);
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v!=errorView){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showLoading(ViewGroup viewGroup,LoadOrErrorView errorView){
        int count=viewGroup.getChildCount();
        if(count==0){
            throw new IllegalArgumentException("ViewGroup 没有子View");
        }
        if(errorView==null){
            throw new IllegalArgumentException("LoadOrErrorView 为空");
        }
        errorView.showLoadingView();
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v!=errorView){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showEmpty(ViewGroup viewGroup,LoadOrErrorView errorView){
        int count=viewGroup.getChildCount();
        if(count==0){
            throw new IllegalArgumentException("ViewGroup 没有子View");
        }
        if(errorView==null){
            throw new IllegalArgumentException("LoadOrErrorView 为空");
        }
        errorView.showEmptyView(emptyDrawable,emptyMessage);
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v!=errorView){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showNormal(ViewGroup viewGroup,LoadOrErrorView errorView){
        int count=viewGroup.getChildCount();
        if(count==0){
            throw new IllegalArgumentException("ViewGroup 没有子View");
        }
        if(errorView==null){
            throw new IllegalArgumentException("LoadOrErrorView 为空");
        }
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v==errorView){
                v.setVisibility(View.GONE);
            }else{
                v.setVisibility(View.VISIBLE);
            }
        }
    }
}
