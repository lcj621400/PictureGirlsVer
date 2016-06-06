package com.lichunjing.picturegirls.baseui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/4/24.
 * 所有fragment的基类
 */
public abstract class BaseNFragment extends Fragment{

    protected static  String TAG;
    protected View contentView;
    protected FragmentActivity activity;


    protected abstract int getLayoutId();

    protected boolean needEventBus(){
        return false;
    }

    protected abstract void initView(View view);




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=getClass().getSimpleName();
        Log.d(TAG,"onCreate");
        if(needEventBus()){
            EventBus.getDefault().register(this);
            Log.d(TAG,"注册EventBus");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        if(contentView==null) {
            Log.d(TAG,"contentView为null，重新创建");
            contentView = inflater.inflate(getLayoutId(), container, false);
        }else {
            Log.d(TAG,"contentView不为null，未重新创建");
        }
        initView(contentView);

        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        BasePicApp.getRefWatcher(getActivity()).watch(this);
        if(needEventBus()){
            EventBus.getDefault().unregister(this);
            Log.d(TAG,"解除注册EventBus");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
    }
}
