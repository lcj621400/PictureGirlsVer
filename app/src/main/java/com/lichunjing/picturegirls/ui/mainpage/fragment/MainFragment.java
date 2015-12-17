package com.lichunjing.picturegirls.ui.mainpage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.bean.cover.GirlCoverBean;
import com.lichunjing.picturegirls.bean.cover.GirlListBean;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.interfacel.OnRecycleViewItemClickListener;
import com.lichunjing.picturegirls.ui.gallery.GirlGalleryActivity;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import java.util.List;


public class MainFragment extends MainBaseFragment {
    private XRecyclerView mRecycleview;
    private View mFragmentView;

    //是否第一次可见
    private boolean isFirstVisible=true;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(int id) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_ID,id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //防止fragment预加载数据，创建fragment时，只创建一个空的fragment，当fragment显示在屏幕上时，加载数据
        if(isVisibleToUser&&isFirstVisible){
            isFirstVisible=false;
            getPicDatas(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mFragmentView==null) {
            mFragmentView = inflater.inflate(R.layout.fragment_main, container, false);
            initRecycleView(mFragmentView);
        }
        return mFragmentView;
    }

    private void initRecycleView(View view){
        mRecycleview= (XRecyclerView) view.findViewById(R.id.main_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycleview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycleview.setAdapter(recycleViewAdapter);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());
        mRecycleview.setLoadingMoreEnabled(true);
        mRecycleview.setPullRefreshEnabled(true);
        recycleViewAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                int id=picDatas.get(position).getId();
                Intent intent=new Intent(getActivity(), GirlGalleryActivity.class);
                intent.putExtra(GirlGalleryActivity.GALLERY_ID,id);
                startActivity(intent);
            }
        });
        mRecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getPicDatas(0);
            }

            @Override
            public void onLoadMore() {
                getPicDatas(1);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void getPicDatas(final int type){
        Http.getCoverList(id,page, pageCount, new ResultCallback<GirlListBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("error",e.toString());
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
            }

            @Override
            public void onResponse(GirlListBean response) {
                String s=response.toString();
                Log.d("success",s);
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
                if(response!=null){
                    List<GirlCoverBean> datas = response.getTngou();
                    if(datas==null&&datas.isEmpty()){
                        return;
                    }
                    if(type==0){
                        page=2;
                        picDatas.clear();
                        picDatas.addAll(datas);
                        recycleViewAdapter.refreshNotify();
                    }else if(type==1){
                        picDatas.addAll(datas);
                        recycleViewAdapter.loadMoreNotify();
                        page++;
                    }
                    if(response.getTotal()==picDatas.size()){
                        mRecycleview.noMoreLoading();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //viewpager销毁fragment时，存储fragment中加载的view,当再创建此fragment时，直接加载此view，防止fragment创建销毁，创建和销毁view，因为会频繁请求网络，导致页面卡顿
        if(mFragmentView!=null){
            ((ViewGroup)mFragmentView.getParent()).removeView(mFragmentView);
        }
    }
}
