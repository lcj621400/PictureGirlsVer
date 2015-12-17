package com.lichunjing.picturegirls.ui.gallery.fragment.viewpagerfragment;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.bean.gallery.GirlGalleryBean;
import com.lichunjing.picturegirls.bean.gallery.GirlPictureBean;
import com.lichunjing.picturegirls.http.Http;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.ui.gallery.GalleryDetialActivity;
import com.lichunjing.picturegirls.widget.jellyViewPager.JellyViewPager;
import com.squareup.okhttp.Request;
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
        viewPagerAdapter=new GirlViewPagerAdapter(this,datas);
        if(getArguments()!=null) {
            id = getArguments().getInt(ID_PARAMS);
        }
        viewPagerAdapter.setOnPageClickListener(new GirlViewPagerAdapter.OnPageClickListener() {
            @Override
            public void onPageClick(View v, String url) {
                Intent intent=new Intent(getActivity(), GalleryDetialActivity.class);
                intent.putExtra(GalleryDetialActivity.GALLERY_URL,url);
                startActivity(intent);
            }
        });
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




    public static class  GirlViewPagerAdapter extends PagerAdapter implements View.OnClickListener{

        private List<GirlGalleryBean> datas;
        private Fragment fragment;
        private LayoutInflater inflater;

        private OnPageClickListener mPageClickListener;

        public GirlViewPagerAdapter(Fragment fragment,List<GirlGalleryBean> datas){
            this.fragment=fragment;
            this.datas=datas;
            inflater=LayoutInflater.from(fragment.getActivity());
        }


        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=inflater.inflate(R.layout.viewpager_pic_item,null);
            ImageView imageView= (ImageView) view.findViewById(R.id.imageview);
            String url=PicUrl.BASE_IMAGE_URL+datas.get(position).getSrc();
            Glide.with(fragment).load(url).into(imageView);
            view.setOnClickListener(this);
            view.setTag(url);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }


        public void setOnPageClickListener(OnPageClickListener listener){
            this.mPageClickListener=listener;
        }
        @Override
        public void onClick(View v) {
            if(mPageClickListener!=null){
                mPageClickListener.onPageClick(v, (String) v.getTag());
            }
        }

        public interface OnPageClickListener{
            void onPageClick(View v,String url);
        }
    }


}
