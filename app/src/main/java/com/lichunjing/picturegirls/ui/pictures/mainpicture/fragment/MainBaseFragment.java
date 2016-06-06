package com.lichunjing.picturegirls.ui.pictures.mainpicture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseFragment;
import com.lichunjing.picturegirls.ui.pictures.bean.cover.GirlCoverBean;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.ui.pictures.mainpicture.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcj621400 on 2015/12/15.
 */
public class MainBaseFragment extends BaseFragment {


    protected static final String TYPE_ID = "type_id";
    protected int id = 1;

    /**
     * recycleview
     */
    protected MainAdapter recycleViewAdapter;
    /**
     * recycleview数据源
     */
    protected List<GirlCoverBean> picDatas;
    /**
     * 当前的页码
     */
    protected int page = 1;
    protected int pageCount = 16;

    // 当前fragment是否可见
    protected boolean isVisible;


    /**
     * ViewPager切换时，会手动调用setUserVisibleHint()方法，添加一个标记记录fragment是否第一次可见
     * 如果不是第一次可见，则不会加载数据
     */
    //是否第一次可见
    protected boolean isFirstVisible = true;

    /**
     * activity实例
     */
    protected MainActivity activity;

    /**
     * 重写onAttach()方法，获取activity的实例
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context!=null&&context instanceof MainActivity){
            activity= (MainActivity) context;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible=true;
            onVisible();
        }else{
            isVisible=false;
            onInVisible();
        }
    }

    private void onVisible(){
        onLazyLoad();
    }

    private void onInVisible(){

    }

    protected void onLazyLoad(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(TYPE_ID, 1);
        }
        picDatas = new ArrayList<>();
        recycleViewAdapter = new MainAdapter(this, picDatas);
    }



    public static class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private Fragment fragment;
        private List<GirlCoverBean> images;
        private int lastPosition = -1;
        private List<Integer> sizeHeight = new ArrayList<>();
        private List<Integer> sizeWidth=new ArrayList<>();
        private int screenHeight;
        private int screenWidth;

        public MainAdapter(Fragment fragment, List<GirlCoverBean> datas) {
            this.fragment = fragment;
            images = datas;
            DisplayMetrics displayMetrics = fragment.getActivity().getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth=displayMetrics.widthPixels;
        }


        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.main_recycleview_item, null);

            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            GirlCoverBean girlBean = images.get(position);
            holder.mainTitle.setText(girlBean.getTitle() + "");
            int height = 0;
            int width=0;
//            if (position < sizeHeight.size()) {
//                height = sizeHeight.get(position);
//                width=sizeWidth.get(position);
//            } else {
//                if (position % 2 == 0) {
//                    height = screenHeight / 3 + 50;
//                } else if (position % 3 == 0) {
//                    height = screenHeight / 3 - 50;
//                } else if (position % 5 == 0) {
//                    height = screenHeight / 3 + 30;
//                } else {
//                    height = screenHeight / 3;
//                }
//                sizeHeight.add(height);
//                width=screenWidth/2;
//                sizeWidth.add(width);
//            }
            width=screenWidth/2;
            height=screenHeight/3;
            ViewGroup.LayoutParams layoutParams =new ViewGroup.LayoutParams(width,height);
            holder.mainCard.setLayoutParams(layoutParams);

            String img = girlBean.getImg();
            String url = null;
            if (!TextUtils.isEmpty(img)) {
                url = PicUrl.BASE_IMAGE_URL + img;
                Glide.with(fragment).load(url).into(holder.mainImageview);
            }
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(fragment.getActivity(), R.anim.main_item_bottom_in);
                holder.mainCard.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public void onViewDetachedFromWindow(MainViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.mainCard.clearAnimation();
        }

        @Override
        public int getItemCount() {
            return images.size();
        }


        public void refreshNotify() {
            sizeHeight.clear();
            lastPosition = -1;
            notifyDataSetChanged();
        }

        public void loadMoreNotify() {
            notifyDataSetChanged();
        }

        public class MainViewHolder extends RecyclerView.ViewHolder {
            CardView mainCard;
            ImageView mainImageview;
            TextView mainTitle;

            public MainViewHolder(View itemView) {
                super(itemView);
                mainCard = (CardView) itemView.findViewById(R.id.cardview);
                mainImageview = (ImageView) itemView.findViewById(R.id.main_imageview);
                mainTitle = (TextView) itemView.findViewById(R.id.main_title);
            }

        }
    }

}
