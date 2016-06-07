package com.lichunjing.picturegirls.ui.news.itemfragment.base;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.lichunjing.picturegirls.baseui.BaseNFragment;
import com.lichunjing.picturegirls.cache.twocache.Cache;
import com.lichunjing.picturegirls.cache.twocache.CacheManager;
import com.lichunjing.picturegirls.ui.news.bean.News;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/6/3.
 * 新闻 baseFragment
 */
public abstract class NewsItemBaseFragment extends BaseNFragment{

    protected boolean isViewCreated=false;
    protected boolean isVisible=false;

    private void canLazyLoad(){
        Log.d(TAG,"canLazyLoad执行");
        Log.d(TAG,"isViewCreated:"+isViewCreated);
        Log.d(TAG,"isVisible:"+isVisible);
        if(isViewCreated&&isVisible){
            onLazyLoad();
            Log.d(TAG,"执行onLazyLoad---自行判断");
        }
        if(isVisible()){
            Log.d(TAG,"执行onLazyLoad---isVisible()");
        }
    }

    /**
     * 在ViewPager+Fragment组合里可以实现懒加载，一般情况下次方法不会执行
     */
    protected abstract void onLazyLoad();


    /**
     * viewpager会显示调用此方法，一般情况下此方法不会被调用
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible=getUserVisibleHint();
        Log.d(TAG,"frgment用户可见");
        canLazyLoad();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG,"onHiddenChanged"+hidden);
    }

    @Override
    protected void initView(View view) {
        initNewsView(view);
        isViewCreated=true;
        canLazyLoad();
    }

    protected abstract void initNewsView(View view);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent= (ViewGroup) contentView.getParent();
        if(parent!=null){
            parent.removeView(contentView);
            Log.d(TAG,"ViewGroup移除contentView");
        }
    }





    public class LoadCacheTask extends AsyncTask<Void,Void,News>{

        private WeakReference<Context> wContext;
        private OnCacheLoadedListener<News> listener;
        private String key;
        private boolean ignoreCacheTimeOut;
        public LoadCacheTask(Context context,String key,boolean ignoreCacheTimeOut,OnCacheLoadedListener<News> listener){
            this.wContext=new WeakReference<Context>(context);
            this.key=key;
            this.ignoreCacheTimeOut=ignoreCacheTimeOut;
            this.listener=listener;
        }

        @Override
        protected News doInBackground(Void... params) {
            Cache<String> cache = CacheManager.getInstance(wContext.get()).readString(key);
            if(cache==null){
                return null;
            }
            if(ignoreCacheTimeOut||!cache.isExpired){
                String json=cache.value;
                News news= JSON.parseObject(json,News.class);
                return news;
            }
            return null;
        }

        @Override
        protected void onPostExecute(News news) {
            super.onPostExecute(news);
            if(wContext.get()!=null&&listener!=null){
                listener.onLoad(news,key);
            }
        }
    }

    public interface OnCacheLoadedListener<T>{
        void onLoad(T cache,String key);
    }
}
