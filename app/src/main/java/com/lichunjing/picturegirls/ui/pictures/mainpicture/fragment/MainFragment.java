package com.lichunjing.picturegirls.ui.pictures.mainpicture.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.ui.pictures.bean.cover.GirlCoverBean;
import com.lichunjing.picturegirls.ui.pictures.bean.cover.GirlListBean;
import com.lichunjing.picturegirls.cache.twocache.Cache;
import com.lichunjing.picturegirls.cache.twocache.CacheManager;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.interfacel.OnRecyclerViewItemClickListener;
import com.lichunjing.picturegirls.networkevent.NetUtils;
import com.lichunjing.picturegirls.ui.pictures.gallery.GirlGalleryActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Call;


public class MainFragment extends MainBaseFragment {
    // recycleview
    private XRecyclerView mRecycleview;
    // fragment布局view
    private View mFragmentView;

    // 正在加载的view
    private RelativeLayout loadingView;
    // 网络连接出错的view
    private RelativeLayout networkErrorView;


    //视图是否已经创建完成
    private boolean isPrepare = false;

    // 加载更多
    private static final int LOAD_MORE = 0;
    // 刷新
    private static final int REFRESH = 1;
    // 第一次正常加载
    private static final int NORMAL = 2;

    private static final String baseCacheKey = "MainPager";

    private ReadCacheTask mCacheTask;

    public MainFragment() {
        // Required empty public constructor
    }


    /**
     * 静态构造方法获取实例
     *
     * @param id
     * @return
     */
    public static MainFragment newInstance(int id) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(R.layout.fragment_main, container, false);
            loadingView = (RelativeLayout) mFragmentView.findViewById(R.id.loading_view);
            networkErrorView = (RelativeLayout) mFragmentView.findViewById(R.id.network_error_view);
            networkErrorView.setClickable(true);
            networkErrorView.setOnClickListener(retryListener);
            initRecycleView(mFragmentView);
        }
        isPrepare = true;
        onLazyLoad();
        return mFragmentView;
    }

    /**
     * 实现fragment懒加载机制
     * 1、当系统调用setUserVisibleHint()方法时，得到变量boolean isVisibleToUser
     * 2、如果isVisibleToUser=true，则会执行onLazyLoad()方法，进行懒加载
     * 3、在onLazyLoad()方法中会判断boolean isPrepare变量，isPrepare变量为fragemnt创建view结束的标记
     * 4、在onLazyLoad()方法中，如果isVisibleToUser=true&&=true,则进行懒加载
     * 说明：系统调用setUserVisibleHint()方法时，此时fragment的view可能还没有创建，此时执行懒加载会报空指针异常
     * 所以在fragment创建view完成后，需要再次执行onLazyLoad()方法进行懒加载
     */
    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        if (isVisible && isPrepare && isFirstVisible) {
            getDatas(NORMAL);
        }
    }

    /**
     * 初始化recycleview
     *
     * @param view
     */
    private void initRecycleView(View view) {
        mRecycleview = (XRecyclerView) view.findViewById(R.id.main_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycleview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecycleview.setAdapter(recycleViewAdapter);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());
        mRecycleview.setLoadingMoreEnabled(true);
        mRecycleview.setPullRefreshEnabled(true);
        mRecycleview.setRefreshProgressStyle(ProgressStyle.BallScale);
        mRecycleview.setLoadingMoreProgressStyle(ProgressStyle.LineScale);
        mRecycleview.addOnItemTouchListener(new OnRecyclerViewItemClickListener(mRecycleview) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position=viewHolder.getAdapterPosition();
                Toast.makeText(getActivity(),"点击："+position,Toast.LENGTH_SHORT).show();
                int id = picDatas.get(position-1).getId();
                Intent intent = new Intent(getActivity(), GirlGalleryActivity.class);
                intent.putExtra(GirlGalleryActivity.GALLERY_ID, id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                int position=viewHolder.getAdapterPosition();
                Toast.makeText(getActivity(),"长按："+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDoubleClick(RecyclerView.ViewHolder viewHolder) {
                int position=viewHolder.getAdapterPosition();
                Toast.makeText(getActivity(),"双击："+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getDatas(REFRESH);
            }

            @Override
            public void onLoadMore() {
                getDatas(LOAD_MORE);
            }
        });
    }


    private void getDatas(final int type) {
        if(isFirstVisible){
            showLoadingView();
            isFirstVisible=false;
        }
        // 读缓存
        cancelCacheTask();
        String key = getRequestUrl();
        mCacheTask = new ReadCacheTask(getActivity(), key, type);
        mCacheTask.execute();
    }

    /**
     * 取消读取缓存的任务
     */
    private void cancelCacheTask(){
        if(mCacheTask!=null&&!mCacheTask.isCancelled()){
            mCacheTask.cancel(true);
            mCacheTask=null;
        }
    }

    /**
     * 取消当前fragment的请求
     */
    private void cancelHttpTask(){
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private String getRequestUrl(){
        return String.format(PicUrl.GET_IMAGE_URL, new Object[]{String.valueOf(id), String.valueOf(page), String.valueOf(pageCount)});
    }


    /**
     * 根据type类型，联网获取数据
     *
     * @param type
     */
    private void getPicDatas(final int type) {
        if (type == NORMAL) {
            if (!NetUtils.isConnected(getActivity())) {
                if (isFirstVisible) {
                    // 显示网络连接出错页面
                    showErrorView();
                    isFirstVisible = false;
                } else {
                    Toast.makeText(getActivity(), "网络连接不给力", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        } else if (type == REFRESH || type == LOAD_MORE) {
            if (!NetUtils.isConnected(getActivity())) {
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
                Toast.makeText(getActivity(), "网络连接不给力", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        final String url = getRequestUrl();
        Http.getCoverList(this,url, new Callback<GirlListBean>() {
            public GirlListBean parseNetworkResponse(okhttp3.Response response) throws IOException {
                String json = response.body().string();
                CacheManager.getInstance(getContext()).writeString(url,json);
                GirlListBean bean = null;
                try {
                    bean = JSON.parseObject(json, GirlListBean.class);
                } catch (Exception e) {

                }
                return bean;
            }

            @Override
            public void onError(Call call, Exception e) {
                Log.d("error", e.toString());
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
            }


            @Override
            public void onResponse(GirlListBean response) {
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
                if (response == null) {
                    return;
                }
                responseContinue(response, type);

            }
        });

    }


    private void responseContinue(GirlListBean response, int type) {
        showContentView();

        List<GirlCoverBean> datas = response.getTngou();
        if (datas == null || datas.isEmpty()) {
            return;
        }
        if (type == REFRESH || type == NORMAL) {
            // 刷新
            page = 2;
            picDatas.clear();
            picDatas.addAll(datas);
            recycleViewAdapter.refreshNotify();
        } else if (type == LOAD_MORE) {
            // 加载更多
            picDatas.addAll(datas);
            recycleViewAdapter.loadMoreNotify();
            page++;
        }
        if (response.getTotal() == picDatas.size()) {
            mRecycleview.noMoreLoading();
        }
    }

    /**
     * 显示断开网络时的view
     */
    private void showErrorView() {
        networkErrorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        mRecycleview.setVisibility(View.GONE);
    }

    /**
     * 显示正在加载的view
     */
    private void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
        networkErrorView.setVisibility(View.GONE);
        mRecycleview.setVisibility(View.GONE);
    }

    /**
     * 显示recycleview
     */
    private void showContentView() {
        mRecycleview.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        networkErrorView.setVisibility(View.GONE);
    }

    // 点击屏幕重试监听器
    View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDatas(NORMAL);
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //viewpager销毁fragment时，存储fragment中加载的view,当再创建此fragment时，直接加载此view，防止fragment创建销毁，创建和销毁view，因为会频繁请求网络，导致页面卡顿
        if (mFragmentView != null) {
            ViewGroup parentView = ((ViewGroup) mFragmentView.getParent());
            if (parentView != null) {
                parentView.removeView(mFragmentView);
            }
        }
    }

    @Override
    public void onDestroy() {
        cancelCacheTask();
        cancelHttpTask();
        super.onDestroy();
    }

    /**
     * 读取换的异步任务
     */
    class ReadCacheTask extends AsyncTask<Void, Void, GirlListBean> {
        private WeakReference<Context> context;
        private String key;
        private boolean hasNetwork;
        private int type;

        public ReadCacheTask(Context context, String key,int type) {
            this.context = new WeakReference<Context>(context);
            this.key = key;
            this.hasNetwork=NetUtils.isConnected(context);
            this.type=type;
        }

        @Override
        protected GirlListBean doInBackground(Void... params) {
            Cache<String> cache = CacheManager.getInstance(context.get()).readString(key);
            if (cache == null) {
                // 没有缓存
                Log.d("cache",key+" 没有缓存");
                return null;
            } else if ((!cache.isExpired) || (!hasNetwork)) {
                // 缓存没有过期,或者断网时
                Log.d("cache","读取缓存缓存");
                Log.d("cache","key:"+key);
                Log.d("cache","value:"+cache.value);
                GirlListBean bean = null;
                try {
                    bean = JSON.parseObject(cache.value, GirlListBean.class);
                } catch (Exception e) {
                }
                return bean;
            }
            return null;
        }

        @Override
        protected void onPostExecute(GirlListBean s) {
            super.onPostExecute(s);
            if (context.get() != null) {
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
                if (s == null&&hasNetwork) {
                    // 没有缓存数据，联网加载
                    getPicDatas(type);
                } else if(s==null&&!hasNetwork) {
//                    showErrorView();
                }else {
                    responseContinue(s, type);
                }
            }
        }
    }
}
