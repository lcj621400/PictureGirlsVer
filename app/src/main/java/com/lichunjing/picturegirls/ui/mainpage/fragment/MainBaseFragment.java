package com.lichunjing.picturegirls.ui.mainpage.fragment;

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
import com.lichunjing.picturegirls.bean.cover.GirlCoverBean;
import com.lichunjing.picturegirls.http.PicUrl;
import com.lichunjing.picturegirls.interfacel.OnRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcj621400 on 2015/12/15.
 */
public class MainBaseFragment extends Fragment{


    protected static final String TYPE_ID="type_id";
    protected int id=1;

    protected MainAdapter recycleViewAdapter;
    protected List<GirlCoverBean> picDatas;
    protected int page=1;
    protected int pageCount=10;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            id=getArguments().getInt(TYPE_ID,1);
        }
        picDatas=new ArrayList<>();
        recycleViewAdapter=new MainAdapter(this,picDatas);
    }




    public static class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements View.OnClickListener{

        private Fragment fragment;
        private List<GirlCoverBean> images;
        private int lastPosition=-1;
        private List<int[]> size=new ArrayList<int[]>();
        private OnRecycleViewItemClickListener mOnItemClickListener;
        private int screenWidth;
        private int screenHeight;

        public MainAdapter(Fragment fragment,List<GirlCoverBean> datas){
            this.fragment=fragment;
            images=datas;
            DisplayMetrics displayMetrics = fragment.getActivity().getResources().getDisplayMetrics();
            screenWidth=displayMetrics.widthPixels;
            screenHeight=displayMetrics.heightPixels;
        }


        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(fragment.getActivity()).inflate(R.layout.main_recycleview_item,null);

            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            GirlCoverBean girlBean = images.get(position);
            holder.mainTitle.setText(girlBean.getTitle()+"");
            String img=girlBean.getImg();
            String url=null;
            if(!TextUtils.isEmpty(img)){
                url= PicUrl.BASE_IMAGE_URL+img;
                int width=0;
                int height=0;
//                if(position<size.size()){
//                    int[] sizes = size.get(position);
//                    height=sizes[1];
//                    width=sizes[0];
//                }else{
//                    double result=0;
//                    final double random = Math.random();
//                    if(random>0.5){
//                        result=random-0.5;
//                    }else{
//                        result=random-0.2;
//                    }
//                    height= (int) (screenHeight/3+ result*(screenHeight/3));
//                    width= (int) (screenWidth/2+result*(screenWidth/2));
//                    size.add(new int[]{width,height});
//                }
                width= screenWidth/2;
                if(position%2==0){
                    height= screenHeight/3+50;
                }else if(position%3==0){
                    height=screenHeight/3-50;
                }else if(position%5==0){
                    height=screenHeight/3+30;
                } else{
                    height=screenHeight/3;
                }
                ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(width,height);
                holder.mainCard.setLayoutParams(params);
                Glide.with(fragment).load(url).override(width,height).into(holder.mainImageview);
            }
            if(position>lastPosition){
                Animation animation = AnimationUtils.loadAnimation(fragment.getActivity(), R.anim.main_item_bottom_in);
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

        public void setOnItemClickListener(OnRecycleViewItemClickListener listener){
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
