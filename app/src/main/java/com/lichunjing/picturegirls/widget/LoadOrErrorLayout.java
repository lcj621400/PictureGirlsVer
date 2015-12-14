package com.lichunjing.picturegirls.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lcj621400 on 2015/11/23.
 * 内有四个页面：网络错误（提供点击事件），加载出错，加载中，数据为空
 */
public class LoadOrErrorLayout extends RelativeLayout{

    //上下文对象
    private Context mContext;
    //layout加载类型
    private Type mType;

    //加载出错的布局
    private LinearLayout errorLayout;
    private int errorLayoutTag=909;
    private ImageView errorImage;
    private TextView errorMessageTextView;
    private Button errorButton;

    //加载数据为空的layout
    private LinearLayout emptyLayout;
    private int emptyLayoutTag=908;
    private ImageView emptyImageView;
    private TextView emptyMessageTextView;

    //正在加载的layout
    private LinearLayout loadingLayout;
    private int loadingLayoutTag=907;
    private ProgressBar loadingBar;

    //没有网络的布局
    private LinearLayout errorNetWorkLayout;
    private int errorNetWorkLayoutTag=906;
    private ImageView errorNetWorkImageView;
    private TextView errorNetWorkMessageTextView;
    private Button errorNetWorkButton;
    private Button errorRefreshButton;


    //控件的高度
    private  int layoutHeight;
    //控件的宽度
    private int layoutWidth;

    //枚举类型，layout加载的类型
    public enum Type{
        LOADING,ERROR,EMPTY,NORMAL;
    }

    public LoadOrErrorLayout(Context context) {
        super(context);
    }

    public LoadOrErrorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public LoadOrErrorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        this.mContext=context;
        layoutHeight=getHeight();
        layoutWidth=getWidth();
    }

    //显示没有网络的布局
    public void showErrorNetWorkLayout(@NonNull int errorNetWordImageRes,@NonNull String errorNetWrokMessage,OnClickListener errorNetWorkListener){
        hideAllChild();
        if(errorNetWorkLayout==null){
            errorNetWorkLayout=new LinearLayout(mContext);
            errorNetWorkLayout.setTag(errorNetWorkLayoutTag);
            errorNetWorkLayout.setClickable(true);
            errorNetWorkLayout.setFocusable(true);
            errorNetWorkLayout.setBackgroundColor(Color.WHITE);
            LayoutParams errorNetWorkLayoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            errorNetWorkLayout.setGravity(Gravity.CENTER);
            errorNetWorkLayout.setOrientation(LinearLayout.VERTICAL);
            if(errorNetWorkListener!=null){
                errorNetWorkLayout.setOnClickListener(errorNetWorkListener);
            }
            //网络错误的图片
            errorNetWorkImageView=new ImageView(mContext);
            LinearLayout.LayoutParams errorNetWorkImageParams=new LinearLayout.LayoutParams(dpToPx(100),dpToPx(100));
            errorNetWorkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            errorNetWorkImageView.setImageResource(errorNetWordImageRes);
            errorNetWorkLayout.addView(errorNetWorkImageView, errorNetWorkImageParams);

            //提示信息
            errorNetWorkMessageTextView=new TextView(mContext);
            LinearLayout.LayoutParams errorNetWorkTextParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);


            errorNetWorkMessageTextView.setTextSize(dpToPx(5));
            errorNetWorkMessageTextView.setTextColor(Color.RED);
            errorNetWorkMessageTextView.setGravity(Gravity.CENTER);
            int padding=dpToPx(10);
            errorNetWorkMessageTextView.setPadding(padding*2, padding, padding*2, padding);
            errorNetWorkMessageTextView.setText(errorNetWrokMessage);
            errorNetWorkLayout.addView(errorNetWorkMessageTextView, errorNetWorkTextParams);


            //点击会跳转到设置网络的页面
            errorNetWorkButton=new Button(mContext);
            LinearLayout.LayoutParams errorNetWorkButtonParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,dpToPx(30));
            errorNetWorkButton.setText("设置网络");
            errorNetWorkButton.setTextSize(dpToPx(5));
            errorNetWorkButton.setBackgroundColor(Color.BLUE);
            errorNetWorkButton.setTextColor(Color.WHITE);
            errorNetWorkButton.setGravity(Gravity.CENTER);
            errorNetWorkButton.setPadding(0,0,0,0);
            errorNetWorkButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //系统版本大于3.0
                    if(Build.VERSION.SDK_INT>10){
                        Intent netWorkIntent=new Intent(Settings.ACTION_SETTINGS);
                        mContext.startActivity(netWorkIntent);
                    }else{
                        Intent setNetWorkIntent=new Intent();
                        ComponentName name=new ComponentName("com.android.settings","com.android.settings.Settings");
                        setNetWorkIntent.setComponent(name);
                        setNetWorkIntent.setAction("android.intent.action.VIEW");
                        mContext.startActivity(setNetWorkIntent);
                    }
                }
            });
            errorNetWorkLayout.addView(errorNetWorkButton, errorNetWorkButtonParams);


            //点击会刷新
            errorRefreshButton=new Button(mContext);
            LinearLayout.LayoutParams errorNetWorkRefreshParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,dpToPx(30));
            errorNetWorkRefreshParams.topMargin=30;
            errorRefreshButton.setText("点击屏幕刷新");
            errorRefreshButton.setBackgroundColor(Color.BLUE);
            errorRefreshButton.setGravity(Gravity.CENTER);
            errorRefreshButton.setPadding(0,0,0,0);
            errorRefreshButton.setTextColor(Color.WHITE);
            errorRefreshButton.setTextSize(dpToPx(5));

            errorNetWorkLayout.addView(errorRefreshButton,errorNetWorkRefreshParams);

            this.addView(errorNetWorkLayout,errorNetWorkLayoutParams);

        }else{
            errorNetWorkLayout.setVisibility(View.VISIBLE);
        }
    }


    //显示加载错误的layout
    public void showErrorLayout(@NonNull int errorImageRes,@NonNull String errorMessage,OnClickListener errorListener){
        hideAllChild();
        if(errorLayout==null){
           //实例化错误布局
            errorLayout=new LinearLayout(mContext);
            errorLayout.setTag(errorLayoutTag);
            errorLayout.setClickable(true);
            errorLayout.setFocusable(true);
            errorLayout.setBackgroundColor(Color.WHITE);
            LayoutParams errorLayoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            errorLayout.setOrientation(LinearLayout.VERTICAL);
            errorLayout.setGravity(Gravity.CENTER);
            if(errorListener!=null){
                errorLayout.setOnClickListener(errorListener);
            }

            //创建错误的imageivew
            errorImage=new ImageView(mContext);
            errorImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageview的LayoutParams
            LinearLayout.LayoutParams errorImageParams=new LinearLayout.LayoutParams(dpToPx(100),dpToPx(100));
            errorImage.setImageResource(errorImageRes);

            //添加imageview到布局中
            errorLayout.addView(errorImage, errorImageParams);

            //错误的信息提示
            errorMessageTextView=new TextView(mContext);
            LinearLayout.LayoutParams errorMessageParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            errorMessageTextView.setTextSize(dpToPx(5));
            errorMessageTextView.setTextColor(Color.RED);
            errorMessageTextView.setGravity(Gravity.CENTER);
            int padding=dpToPx(10);
            errorMessageTextView.setText(errorMessage);
            errorMessageTextView.setPadding(padding*2,padding,padding*2,padding);

            //添加textview到布局中去
            errorLayout.addView(errorMessageTextView,errorMessageParams);

            //点击充值按钮
            errorButton=new Button(mContext);
            errorButton.setText("点击屏幕重试");

            errorButton.setTextSize(dpToPx(5));
            errorButton.setBackgroundColor(Color.BLUE);
            errorButton.setTextColor(Color.WHITE);
            errorButton.setGravity(Gravity.CENTER);
            errorButton.setPadding(0,0,0,0);

            LinearLayout.LayoutParams errorButtonParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,dpToPx(30));
            errorButtonParams.topMargin=dpToPx(10);
            //将按钮添加到布局中
            errorLayout.addView(errorButton,errorButtonParams);

            //将错误布局添加住布局中（显示加载出错的布局）
            this.addView(errorLayout,errorLayoutParams);
        }else {
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    //显示正在加载的layout
    public void showLoadingLayout(){
        hideAllChild();
        if(loadingLayout==null){
            loadingLayout=new LinearLayout(mContext);
            loadingLayout.setTag(loadingLayoutTag);
            loadingLayout.setClickable(true);
            loadingLayout.setFocusable(true);
            loadingLayout.setBackgroundColor(Color.WHITE);

            LayoutParams loadingLayoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            loadingLayout.setGravity(Gravity.CENTER);

             loadingBar=new ProgressBar(mContext);
            LinearLayout.LayoutParams loadingBarParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            loadingLayout.addView(loadingBar,loadingBarParams);


            this.addView(loadingLayout,loadingLayoutParams);
        }else{
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    //显示数据为空的layout
    public  void showEmptyLayout(@NonNull int emptyImageRes,@NonNull String emptyMessage){
        hideAllChild();
        if(emptyLayout==null){
            emptyLayout=new LinearLayout(mContext);
            emptyLayout.setTag(emptyLayoutTag);
            emptyLayout.setClickable(true);
            emptyLayout.setFocusable(true);
            emptyLayout.setBackgroundColor(Color.WHITE);

            LayoutParams emptyLayoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            emptyLayout.setOrientation(LinearLayout.VERTICAL);
            emptyLayout.setGravity(Gravity.CENTER);

            //创建错误的imageivew
            emptyImageView=new ImageView(mContext);
            emptyImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageview的LayoutParams
            LinearLayout.LayoutParams emptyImageParams=new LinearLayout.LayoutParams(dpToPx(100),dpToPx(100));
            emptyImageView.setImageResource(emptyImageRes);

            //添加imageview到布局中
            emptyLayout.addView(emptyImageView, emptyImageParams);

            //错误的信息提示
            emptyMessageTextView=new TextView(mContext);
            LinearLayout.LayoutParams emptyMessageParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            emptyMessageTextView.setTextSize(dpToPx(5));
            emptyMessageTextView.setTextColor(Color.RED);
            emptyMessageTextView.setGravity(Gravity.CENTER);
            int padding=dpToPx(10);
            emptyMessageTextView.setText(emptyMessage);
            emptyMessageTextView.setPadding(padding*2,padding,padding*2,padding);

            emptyLayout.addView(emptyMessageTextView,emptyMessageParams);

//            将错误布局添加住布局中（显示加载出错的布局）
            this.addView(emptyLayout,emptyLayoutParams);
        }else{
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    //加载数据成功时，执行此方法显示
    public  void showContent(){
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View view=getChildAt(i);
            Object obj=view.getTag();
            if(obj!=null){
                int tag= (int) obj;
                if(tag==loadingLayoutTag||tag==errorLayoutTag||tag==errorNetWorkLayoutTag||tag==emptyLayoutTag){
                    view.setVisibility(View.GONE);
                }else{
                    view.setVisibility(View.VISIBLE);
                }
            }else {
                view.setVisibility(View.VISIBLE);
            }

        }
    }



    private void hideAllChild(){
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View view=getChildAt(i);
            if(view!=null){
                view.setVisibility(View.GONE);
            }
        }
    }

    //dp转换成px的工具
    private int dpToPx(int dp){
       float mDensity=  mContext.getResources().getDisplayMetrics().density;
        return (int) (dp*mDensity+0.5f);
    }
}
