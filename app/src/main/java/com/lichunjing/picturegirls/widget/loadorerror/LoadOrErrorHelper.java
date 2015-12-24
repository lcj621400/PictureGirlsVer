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
    private LoadOrErrorView check(ViewGroup viewGroup, int loadOrErrorViewId){
        int count=viewGroup.getChildCount();
        if(count==0){
            throw new IllegalArgumentException("ViewGroup 没有子View");
        }
        View view=viewGroup.findViewById(loadOrErrorViewId);
        if(view==null){
            throw new IllegalArgumentException("ViewGroup 没有Id为 "+loadOrErrorViewId+" 的子View");
        }
        if(!(view instanceof LoadOrErrorView)){
            throw new IllegalArgumentException("Id为 "+loadOrErrorViewId+" 的子View必须是LoadOrErrorView或是其子类");
        }
        LoadOrErrorView loadOrErrorView= (LoadOrErrorView) view;
        return loadOrErrorView;
    }

    public void showNetWorkError(ViewGroup viewGroup, int loadOrErrorViewId){
        int count=viewGroup.getChildCount();
        LoadOrErrorView loadOrErrorView=check(viewGroup,loadOrErrorViewId);
        loadOrErrorView.showErrorNetWorkView(netWorkErrorDrawable,netWorkErrorMessage);
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v.getId()==View.NO_ID||v.getId()!=loadOrErrorViewId){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showLoadError(ViewGroup viewGroup,int loadOrErrorViewId,View.OnClickListener listener){
        int count=viewGroup.getChildCount();
        LoadOrErrorView loadOrErrorView=check(viewGroup,loadOrErrorViewId);
        loadOrErrorView.showLoadErrorView(loadErrorDrawable,loadErrorMessage,listener);
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v.getId()==View.NO_ID||v.getId()!=loadOrErrorViewId){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showLoading(ViewGroup viewGroup,int loadOrErrorViewId){
        int count=viewGroup.getChildCount();
        LoadOrErrorView loadOrErrorView=check(viewGroup,loadOrErrorViewId);
        loadOrErrorView.showLoadingView();
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v.getId()==View.NO_ID||v.getId()!=loadOrErrorViewId){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showEmpty(ViewGroup viewGroup,int loadOrErrorViewId){
        int count=viewGroup.getChildCount();
        LoadOrErrorView loadOrErrorView=check(viewGroup,loadOrErrorViewId);
        loadOrErrorView.showEmptyView(emptyDrawable,emptyMessage);
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v.getId()==View.NO_ID||v.getId()!=loadOrErrorViewId){
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showNormal(ViewGroup viewGroup,int loadOrErrorViewId){
        int count=viewGroup.getChildCount();
        check(viewGroup,loadOrErrorViewId);
        for(int i=0;i<count;i++){
            View v=viewGroup.getChildAt(i);
            if(v.getId()!=loadOrErrorViewId){
                v.setVisibility(View.VISIBLE);
            }else {
                v.setVisibility(View.GONE);
            }
        }
    }
}
