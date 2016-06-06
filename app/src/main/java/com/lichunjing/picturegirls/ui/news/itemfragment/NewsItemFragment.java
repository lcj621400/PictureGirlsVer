package com.lichunjing.picturegirls.ui.news.itemfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.cache.twocache.CacheManager;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.interfacel.OnRecyclerViewItemClickListener;
import com.lichunjing.picturegirls.networkevent.NetUtils;
import com.lichunjing.picturegirls.ui.news.bean.News;
import com.lichunjing.picturegirls.ui.news.itemfragment.base.NewsItemBaseFragment;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/3.
 * 新闻fragment
 */
public class NewsItemFragment extends NewsItemBaseFragment implements
        OnLoadMoreListener,OnRefreshListener,NewsItemBaseFragment.OnCacheLoadedListener<News> {

    public static final String NEWS_TYPE="news_type";

    private static final int LOADMORE=99,REFRESH=98,NORMAL=97;
    private RecyclerView recyclerView;
    private List<News.NewsBean> datas;
    private LoadCacheTask cacheTask;
    private NewsAdapter adapter;
    private int newsType;
    private int currentEventType;
    private SwipeToLoadLayout swipeToLoadLayout;

    private int page=1;
    private int rows=20;
    /**
     * 静态方法获取实例
     * @param type
     * @return
     */
    public static NewsItemFragment getInstance(int type){
        Bundle args=new Bundle();
        args.putInt(NEWS_TYPE,type);
        NewsItemFragment fragment=new NewsItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!=null){
            newsType=arguments.getInt(NEWS_TYPE);
        }
    }

    @Override
    protected void onLazyLoad() {
        currentEventType=NORMAL;
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
                getDatas();
            }
        });
    }

    @Override
    protected void initNewsView(View view) {
        // 刷新和加载更多
        swipeToLoadLayout= (SwipeToLoadLayout) view.findViewById(R.id.swipe_to_load);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);

        // recyclerview
        recyclerView= (RecyclerView) view.findViewById(R.id.swipe_target);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new OnRecyclerViewItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position=viewHolder.getAdapterPosition();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newsitem;
    }

    private void getDatas(){
        if(cacheTask!=null){
            cacheTask.cancel(true);
        }
        String url=String.format(PicUrl.GET_NEWS_URL,new String[]{String.valueOf(newsType),String.valueOf(page),String.valueOf(rows)});
        boolean hasNetWork= NetUtils.isConnected(activity.getApplicationContext());
        if(hasNetWork) {
            cacheTask = new LoadCacheTask(activity, url, false,this);
        }else {
            cacheTask = new LoadCacheTask(activity, url, true,this);
        }
    }

    private void getNetDatas(final String url){
        Http.getNewsList(this, url, new Callback<News>() {
            @Override
            public News parseNetworkResponse(Response response) throws Exception {
                String json=response.body().string();
                News news= JSON.parseObject(json,News.class);
                CacheManager.getInstance(activity.getApplicationContext()).writeString(url,json);
                return news;
            }

            @Override
            public void onError(Call call, Exception e) {
                Log.d(TAG,e==null?"访问服务器出错":e.getMessage());
                swipeToLoadLayout.setRefreshing(false);
                swipeToLoadLayout.setLoadingMore(false);
            }

            @Override
            public void onResponse(News response) {
                displayNews(response);
            }
        });
    }

    private void displayNews(News news){
        if(datas==null){
            datas=new ArrayList<>();
        }
        if(currentEventType==NORMAL){
            datas.addAll(news.getTngou());
            adapter=new NewsAdapter(datas);
            recyclerView.setAdapter(adapter);
            swipeToLoadLayout.setRefreshing(false);
        }else if(currentEventType==REFRESH){
            datas.clear();
            datas.addAll(news.getTngou());
            adapter.notifyDataSetChanged();
            swipeToLoadLayout.setRefreshing(false);
        }else if(currentEventType==LOADMORE){
            datas.addAll(news.getTngou());
            adapter.notifyDataSetChanged();
            swipeToLoadLayout.setLoadingMore(false);
        }
        currentEventType=NORMAL;
        if(datas.size()==news.getTotal()){
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }else {
            swipeToLoadLayout.setLoadMoreEnabled(true);
        }
    }


    @Override
    public void onLoadMore() {
        currentEventType=LOADMORE;
        page++;
        getDatas();
    }

    @Override
    public void onRefresh() {
        currentEventType=REFRESH;
        page=1;
        getDatas();
    }

    /**
     * 读取缓存的回调方法
     * @param cache 缓存
     * @param key 缓存的key，此处对应的为url
     */
    @Override
    public void onLoad(News cache, String key) {
        if(cache!=null){
            displayNews(cache);
        }else {
            boolean hasNetWork=NetUtils.isConnected(activity.getApplicationContext());
            if(hasNetWork){
                getNetDatas(key);
            }else {
                Toast.makeText(activity.getApplicationContext(),"没有网络链接",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

        private List<News.NewsBean> datas;
        public NewsAdapter(List<News.NewsBean> datas){
            this.datas=datas;
        }

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class NewsViewHolder extends RecyclerView.ViewHolder{

            public NewsViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
