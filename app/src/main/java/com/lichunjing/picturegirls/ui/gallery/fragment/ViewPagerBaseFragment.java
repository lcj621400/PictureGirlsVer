package com.lichunjing.picturegirls.ui.gallery.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.bean.gallery.GirlGalleryBean;
import com.lichunjing.picturegirls.bean.gallery.GirlPictureBean;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.widget.jellyViewPager.JellyViewPager;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcj621400 on 2015/12/15.
 */
public class ViewPagerBaseFragment extends Fragment {

    protected static final String ID_PARAMS="id_params";

    protected List<GirlGalleryBean>  datas;
    protected GirlViewPagerAdapter viewPagerAdapter;
    protected int id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas=new ArrayList<>();
        viewPagerAdapter=new GirlViewPagerAdapter(getActivity(),datas);
        if(getArguments()!=null) {
            id = getArguments().getInt(ID_PARAMS);
        }
    }



    protected void getDatas(final int type, final ViewPager viewPager){
        Http.getGalleryImages(id, new ResultCallback<GirlPictureBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(GirlPictureBean response) {
                if(response!=null){
                    final List<GirlGalleryBean> list = response.getList();
                    if(list!=null&&!list.isEmpty()){
                       datas.addAll(list);
                        viewPagerAdapter.notifyDataSetChanged();
                        if(type==1) viewPager.setAdapter(viewPagerAdapter);
                    }
                }
            }
        });
    }



    protected void setViewPager(@NonNull ViewPager viewPager){
        viewPager.setAdapter(viewPagerAdapter);
        getDatas(0,null);
    }

    protected void setJellyViewPager(JellyViewPager viewPager){
        getDatas(1,viewPager);
    }

    protected void notifyDateChange(){
        viewPagerAdapter.notifyDataSetChanged();
    }




    public class  GirlViewPagerAdapter extends PagerAdapter{

        private List<GirlGalleryBean> datas;
        private Context context;
        private List<View> views;
        private LayoutInflater inflater;

        public GirlViewPagerAdapter(Context context,List<GirlGalleryBean> datas){
            this.context=context;
            this.datas=datas;
            views =new ArrayList<>();
            inflater=LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view=inflater.inflate(R.layout.viewpager_pic_item,null);
            ImageView imageView= (ImageView) view.findViewById(R.id.imageview);

            String url=PicUrl.BASE_IMAGE_URL+datas.get(position).getSrc();
            Picasso.with(context).load(url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).tag(context).into(imageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
            views.add(view);
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }


}
