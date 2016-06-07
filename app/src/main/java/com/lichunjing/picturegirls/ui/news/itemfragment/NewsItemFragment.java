package com.lichunjing.picturegirls.ui.news.itemfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.cache.twocache.CacheManager;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.interfacel.OnRecyclerViewItemClickListener;
import com.lichunjing.picturegirls.networkevent.NetUtils;
import com.lichunjing.picturegirls.ui.news.NewsDetialActivity;
import com.lichunjing.picturegirls.ui.news.bean.News;
import com.lichunjing.picturegirls.ui.news.itemfragment.base.NewsItemBaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.SimpleDateFormat;
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
    private boolean firstLoad=true;
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
        if(firstLoad) {
            swipeToLoadLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setRefreshing(true);
                    getDatas();
                }
            });
            firstLoad=false;
        }
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
                String url=datas.get(position).getFromurl();
                Intent detialIntent=new Intent(activity, NewsDetialActivity.class);
                detialIntent.putExtra(NewsDetialActivity.DETIAL_URL,url);
                startActivity(detialIntent);
            }
        });
        datas=new ArrayList<>();
        adapter=new NewsAdapter(this,datas);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newsitem;
    }

    private void getDatas(){
        if(cacheTask!=null){
            cacheTask.cancel(true);
        }
        String url=String.format(PicUrl.GET_NEWS_URL,new Object[]{String.valueOf(newsType),String.valueOf(page),String.valueOf(rows)});
        boolean hasNetWork= NetUtils.isConnected(activity.getApplicationContext());
        if(hasNetWork) {
            cacheTask = new LoadCacheTask(activity, url, false,this);
        }else {
            cacheTask = new LoadCacheTask(activity, url, true,this);
        }
        cacheTask.execute();
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
        if(currentEventType==REFRESH){
            Log.d(TAG,"刷新返回");
            datas.clear();
            datas.addAll(news.getTngou());
            adapter.notifyDataSetChanged();
            swipeToLoadLayout.setRefreshing(false);
        }else if(currentEventType==LOADMORE){
            Log.d(TAG,"加载更多返回");
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
        Log.d(TAG,"加载更多");
    }

    @Override
    public void onRefresh() {
        currentEventType=REFRESH;
        page=1;
        getDatas();
        Log.d(TAG,"刷新");
    }

    /**
     * 读取缓存的回调方法
     * @param cache 缓存
     * @param key 缓存的key，此处对应的为url
     */
    @Override
    public void onLoad(News cache, String key) {
        if(cache!=null){
            Log.d(TAG,"读取缓存");
            displayNews(cache);
        }else {
            boolean hasNetWork=NetUtils.isConnected(activity.getApplicationContext());
            if(hasNetWork){
                Log.d(TAG,"联网加载");
                getNetDatas(key);
            }else {
                Toast.makeText(activity.getApplicationContext(),"没有网络链接",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }

    public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

        private List<News.NewsBean> datas;
        private SimpleDateFormat format;
        private NewsItemFragment fragment;
        public NewsAdapter(NewsItemFragment fragment,List<News.NewsBean> datas){
            this.datas=datas;
            this.fragment=fragment;
            this.format=new SimpleDateFormat("MM-dd HH:mm:ss");
        }

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsitem,null));
        }

        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {
            News.NewsBean news = datas.get(position);
            holder.newsTitle.setText(news.getTitle());
            holder.newsDes.setText(news.getDescription());
            holder.newsFrom.setText(news.getFromname());
            holder.newsCount.setText(String.valueOf(news.getCount()));
            holder.newsTime.setText(format.format(news.getTime()));
            Glide.with(fragment).load(PicUrl.BASE_IMAGE_URL+news.getImg()).into(holder.newsImg);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class NewsViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitle;
            TextView newsDes;
            TextView newsFrom;
            TextView newsCount;
            TextView newsTime;
            ImageView newsImg;
            public NewsViewHolder(View itemView) {
                super(itemView);
                newsTitle= (TextView) itemView.findViewById(R.id.news_title);
                newsDes= (TextView) itemView.findViewById(R.id.news_des);
                newsFrom= (TextView) itemView.findViewById(R.id.news_from);
                newsCount= (TextView) itemView.findViewById(R.id.news_count);
                newsTime= (TextView) itemView.findViewById(R.id.news_time);
                newsImg= (ImageView) itemView.findViewById(R.id.news_img);
            }
        }
    }

}
