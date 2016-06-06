package com.lichunjing.picturegirls.networkevent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * Created by Administrator on 2016/5/9.
 */
public final class NetEvent {

    private static final String NET_ACTION="com.picturegirl.net.action";
    private static final String ACTION= ConnectivityManager.CONNECTIVITY_ACTION;

    private final Context context;
    private final BroadcastReceiver netReceiver;

    public NetEvent(Context context){
        this.context=context;
        netReceiver=new NetReceiver();
    }

    public void regist(){
        IntentFilter filter=new IntentFilter(ACTION);
        context.registerReceiver(netReceiver,filter);
    }

    public void unRegist(){
        context.unregisterReceiver(netReceiver);
    }

}
