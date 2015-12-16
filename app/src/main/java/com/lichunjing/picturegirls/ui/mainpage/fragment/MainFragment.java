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
import com.lichunjing.picturegirls.ui.gallery.GirlGalleryActivity;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import java.util.List;


public class MainFragment extends MainBaseFragment {
    protected XRecyclerView mRecycleview;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        initRecycleView(view);
        return view;
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
        recycleViewAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
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
        getPicDatas(0);
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
}
