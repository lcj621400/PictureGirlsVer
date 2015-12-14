package com.lichunjing.picturegirls.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;
import com.lichunjing.picturegirls.bean.GirlArrayBean;
import com.lichunjing.picturegirls.bean.GirlBean;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.callback.ResultCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BasePicActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private XRecyclerView mRecycleview;
    private MainAdapter recycleViewAdapter;
    private List<String> recycleDatas;
    private List<GirlBean> picDatas;
    private int page=1;
    private int pageCoun=20;

    private int loadCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPicDatas(0);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        picDatas=new ArrayList<>();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mRecycleview.setLoadingMoreEnabled(true);
        mRecycleview.setPullRefreshEnabled(true);
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
    protected void initStatus() {
        super.initStatus();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recycleDatas=new ArrayList<>();
        recycleDatas.addAll(getDatas());
        recycleViewAdapter=new MainAdapter(this,picDatas);
        mRecycleview.setAdapter(recycleViewAdapter);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());
        recycleViewAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(mApplication,position+"",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getPicDatas(final int type){
        Http.getImage(page, pageCoun, new ResultCallback<GirlArrayBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("error",e.toString());
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
            }

            @Override
            public void onResponse(GirlArrayBean response) {
                String s=response.toString();
                Log.d("success",s);
                mRecycleview.loadMoreComplete();
                mRecycleview.refreshComplete();
                if(response!=null){
                    List<GirlBean> datas = response.getTngou();
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

    private List<String> getDatas() {
        String url = "http://img.xiami.net/images/artistlogo/71/14371074393671.jpg";
        String url2="http://pic.yesky.com/uploadImages/2015/306/41/XD187USUJTU8.jpg";
        List<String> datas=new ArrayList<>();
        for(int i = 0;i<20;i++) {
            if(i%2==0){
                datas.add(url2);
            }else{
                datas.add(url);
            }
        }
        return  datas;
    }

    @Override
    protected void initViews() {
        super.initViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("写真");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecycleview= (XRecyclerView) findViewById(R.id.main_recycleview);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelTag(this);
    }

    public static class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements View.OnClickListener{

        private Context context;
        private List<GirlBean> images;
        private int lastPosition=-1;
        private List<int[]> size=new ArrayList<int[]>();
        private OnItemClickListener mOnItemClickListener;
        private int screenWidth;
        private int screenHeight;

        public MainAdapter(Context context,List<GirlBean> datas){
            this.context=context;
            images=datas;
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            screenWidth=displayMetrics.widthPixels;
            screenHeight=displayMetrics.heightPixels;
        }
        interface OnItemClickListener{
            void onClick(View view, int position);
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.main_recycleview_item,null);

            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            GirlBean girlBean = images.get(position);
            holder.mainTitle.setText(girlBean.getTitle()+"");
            String img=girlBean.getImg();
            String url=null;
            if(!TextUtils.isEmpty(img)){
                url= PicUrl.BASE_IMAGE_URL+img;
                int width=0;
                int height=0;
                if(position<size.size()){
                    int[] sizes = size.get(position);
                    height=sizes[1];
                    width=sizes[0];
                }else{
                    double result=0;
                    final double random = Math.random();
                    if(random>0.5){
                        result=random-0.5;
                    }else{
                        result=random-0.2;
                    }
                    height= (int) (screenHeight/3+ result*(screenHeight/3));
                    width= (int) (screenWidth/2+result*(screenWidth/2));
                    size.add(new int[]{width,height});
                }
                ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(width,height);
                holder.mainCard.setLayoutParams(params);
                Picasso.with(context).load(url).resize(width,height).centerCrop().tag(context).into(holder.mainImageview);
            }
            if(position>lastPosition){
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.main_item_bottom_in);
                holder.mainCard.startAnimation(animation);
                lastPosition=position;
            }
            holder.mainCard.setOnClickListener(this);
            holder.mainCard.setTag(position);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.mOnItemClickListener=listener;
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onClick(v, (Integer) v.getTag());
            }
        }

        public void refreshNotify(){
            size.clear();
            lastPosition=-1;
            notifyDataSetChanged();
        }

        public void loadMoreNotify(){
            notifyDataSetChanged();
        }

        public class MainViewHolder extends RecyclerView.ViewHolder{
            CardView mainCard;
            ImageView mainImageview;
            TextView mainTitle;
            public MainViewHolder(View itemView) {
                super(itemView);
                mainCard= (CardView) itemView.findViewById(R.id.cardview);
                mainImageview= (ImageView) itemView.findViewById(R.id.main_imageview);
                mainTitle= (TextView) itemView.findViewById(R.id.main_title);
            }
        }
    }


}
