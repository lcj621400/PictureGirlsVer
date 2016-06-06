package com.lichunjing.picturegirls.ui.pictures.gallery.fragment.recycleviewfragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseFragment;
import com.lichunjing.picturegirls.ui.pictures.bean.gallery.GirlGalleryBean;
import com.lichunjing.picturegirls.ui.pictures.bean.gallery.GirlPictureBean;
import com.lichunjing.picturegirls.cache.twocache.Cache;
import com.lichunjing.picturegirls.cache.twocache.CacheManager;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.interfacel.OnRecyclerViewItemClickListener;
import com.lichunjing.picturegirls.ui.pictures.gallery.GalleryDetialActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public abstract class RecycleViewBaseFragment extends BaseFragment {
    protected static final String ID_PARAMS="id_params";

    protected List<GirlGalleryBean> datas;
    protected int id;
    protected RecyclerView mRecycleView;
    protected RecycleViewAdapter mAdapter;

    protected LoadCacheTask mTask;

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
        mRecycleView.addOnItemTouchListener(new OnRecyclerViewItemClickListener(mRecycleView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position=viewHolder.getAdapterPosition();
                Toast.makeText(getActivity(),"点击："+position,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getActivity(), GalleryDetialActivity.class);
                i.putExtra(GalleryDetialActivity.GALLERY_URL,PicUrl.BASE_IMAGE_URL+datas.get(position).getSrc());
                startActivity(i);
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
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    private void loadData(){
        cancelCacheTask();
        mTask=new LoadCacheTask(getActivity(),id);
        mTask.execute();
    }

    private void cancelCacheTask(){
        if(mTask!=null&&!mTask.isCancelled()){
            mTask.cancel(true);
            mTask=null;
        }
    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        cancelCacheTask();
        super.onDestroy();
    }

    protected void getDatas(){
        Http.getGalleryImages(this,id, new Callback<GirlPictureBean>() {
            @Override
            public GirlPictureBean parseNetworkResponse(okhttp3.Response response) throws IOException {
                String json=response.body().string();
                GirlPictureBean girlPictureBean = JSON.parseObject(json, GirlPictureBean.class);
                CacheManager.getInstance(getActivity()).writeString(String.valueOf(id),json);
                return girlPictureBean;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(GirlPictureBean response) {
                notifyDataChange(response);
            }
        });
    }


    private  void notifyDataChange(GirlPictureBean response){
        if(response!=null){
            final List<GirlGalleryBean> list = response.getList();
            if(list!=null&&!list.isEmpty()){
                datas.addAll(list);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewViewHolder>{

        private List<GirlGalleryBean> datas;
        private Fragment fragment;
        private LayoutInflater inflater;
        private int lastPosition=-1;
        private int screenWidth;
        private int screenHeight;
        private float density;

        public RecycleViewAdapter(List<GirlGalleryBean> datas, Fragment fragment) {
            this.datas = datas;
            this.fragment = fragment;
            this.inflater=LayoutInflater.from(fragment.getActivity());
            final DisplayMetrics displayMetrics = fragment.getResources().getDisplayMetrics();
            screenHeight=displayMetrics.heightPixels;
            screenWidth=displayMetrics.widthPixels;
            Log.d("ScreenSize","width="+screenWidth);
            Log.d("ScreenSize","width="+screenHeight);
            density = displayMetrics.density;
        }

        @Override
        public RecycleViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.fragment_recycleview_item,null);
            return new RecycleViewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecycleViewViewHolder holder, int position) {
            String url= PicUrl.BASE_IMAGE_URL+datas.get(position).getSrc();
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams((int) (screenWidth-(40*density+0.5f)), (int) (screenHeight-(50*density+0.5f)-(30*density+0.5f)));
            holder.cardview.setLayoutParams(params);
            Glide.with(fragment).load(url).into(holder.fragmentImageview);
//            holder.cardview.setOnClickListener(this);
//            holder.cardview.setTag(position);
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

    class LoadCacheTask extends AsyncTask<Void,Void,GirlPictureBean>{

        private WeakReference<Context> context;
        private int queryId;

        public LoadCacheTask(Context context,int id){
            this.context=new WeakReference<Context>(context);
            this.queryId=id;
        }

        @Override
        protected GirlPictureBean doInBackground(Void... params) {
            if(context.get()!=null){
                Cache<String> cache = CacheManager.getInstance(context.get()).readString(String.valueOf(queryId));
                if(cache!=null&&!TextUtils.isEmpty(cache.value)){
                    GirlPictureBean girlPictureBean = JSON.parseObject(cache.value, GirlPictureBean.class);
                    return girlPictureBean;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(GirlPictureBean girlPictureBean) {
            if(context.get()==null){
                return;
            }else if(girlPictureBean==null){
                getDatas();
            }else {
                notifyDataChange(girlPictureBean);
            }
            super.onPostExecute(girlPictureBean);
        }
    }

}
