package com.lichunjing.picturegirls.ui.gallery.fragment.recycleviewfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.bean.gallery.GirlGalleryBean;
import com.lichunjing.picturegirls.bean.gallery.GirlPictureBean;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.interfacel.OnRecycleViewItemClickListener;
import com.lichunjing.picturegirls.ui.gallery.GalleryDetialActivity;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class RecycleViewBaseFragment extends Fragment {
    protected static final String ID_PARAMS="id_params";

    protected List<GirlGalleryBean> datas;
    protected int id;
    protected RecyclerView mRecycleView;
    protected RecycleViewAdapter mAdapter;

    protected RecyclerView.LayoutManager mLayoutManager;




    public RecycleViewBaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id=getArguments().getInt(ID_PARAMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recycle_view, container, false);
        initViewPager(view);
        return view;
    }

    private void initViewPager(View view) {
        mRecycleView= (RecyclerView) view.findViewById(R.id.gallery_recycleview);
        mRecycleView.setLayoutManager(getLayoutManager());
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        datas=new ArrayList<>();
        mAdapter=new RecycleViewAdapter(datas,this);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i=new Intent(getActivity(), GalleryDetialActivity.class);
                i.putExtra(GalleryDetialActivity.GALLERY_URL,PicUrl.BASE_IMAGE_URL+datas.get(position).getSrc());
                startActivity(i);
            }
        });
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDatas();
    }

    protected void getDatas(){
        Http.getGalleryImages(id, new Callback<GirlPictureBean>() {
            @Override
            public GirlPictureBean parseNetworkResponse(okhttp3.Response response) throws IOException {
                String json=response.body().string();
                GirlPictureBean girlPictureBean = JSON.parseObject(json, GirlPictureBean.class);
                return girlPictureBean;
            }

            @Override
            public void onError(okhttp3.Request request, Exception e) {

            }

            @Override
            public void onResponse(GirlPictureBean response) {
                if(response!=null){
                    final List<GirlGalleryBean> list = response.getList();
                    if(list!=null&&!list.isEmpty()){
                        datas.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewViewHolder>implements View.OnClickListener{


        private List<GirlGalleryBean> datas;
        private Fragment fragment;
        private LayoutInflater inflater;
        private int lastPosition=-1;
        private OnRecycleViewItemClickListener mOnRecycleViewItemClickListener;
        private int screenWidth;
        private int screenHeight;

        public RecycleViewAdapter(List<GirlGalleryBean> datas, Fragment fragment) {
            this.datas = datas;
            this.fragment = fragment;
            this.inflater=LayoutInflater.from(fragment.getActivity());
            final DisplayMetrics displayMetrics = fragment.getResources().getDisplayMetrics();
            screenHeight=displayMetrics.heightPixels;
            screenWidth=displayMetrics.widthPixels;
        }

        @Override
        public RecycleViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.fragment_recycleview_item,null);
            return new RecycleViewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecycleViewViewHolder holder, int position) {
            String url= PicUrl.BASE_IMAGE_URL+datas.get(position).getSrc();
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(screenHeight/3,screenWidth/3);
            holder.cardview.setLayoutParams(params);
            Glide.with(fragment).load(url).into(holder.fragmentImageview);
            holder.cardview.setOnClickListener(this);
            holder.cardview.setTag(position);
            if(position>lastPosition){
                Animation animation = AnimationUtils.loadAnimation(fragment.getActivity(), R.anim.fragment_item_scale_in);
                holder.cardview.startAnimation(animation);
                lastPosition=position;
            }
        }

        @Override
        public void onViewDetachedFromWindow(RecycleViewViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.cardview.clearAnimation();
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
        public void setOnItemClickListener(OnRecycleViewItemClickListener listener){
            this.mOnRecycleViewItemClickListener=listener;
        }

        @Override
        public void onClick(View v) {
            if(mOnRecycleViewItemClickListener!=null){
                mOnRecycleViewItemClickListener.onClick(v, (Integer) v.getTag());
            }
        }

        public class RecycleViewViewHolder extends RecyclerView.ViewHolder{
            CardView cardview;
            ImageView fragmentImageview;
            public RecycleViewViewHolder(View itemView) {
                super(itemView);
                cardview= (CardView) itemView.findViewById(R.id.cardview);
                fragmentImageview= (ImageView) itemView.findViewById(R.id.fragment_imageview);
            }
        }
    }

}
