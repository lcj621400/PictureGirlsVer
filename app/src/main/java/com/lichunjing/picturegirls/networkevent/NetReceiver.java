package com.lichunjing.picturegirls.networkevent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/5/9.
 */
public class NetReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            onNetRecevier(context);
        }
    }

    private void onNetRecevier(Context context){
        int status=NetUtils.getConnectivityStatus(context);
        NEvent event=new NEvent();
        if(status==NetStatus.TYPE_OFFLINE){
            event.isConnect=false;
            event.isChange=false;
            event.type=NetStatus.TYPE_OFFLINE;

        }else if(NetStatus.currentStatus==-1){
            //第一次链接网络
            event.isConnect=true;
            event.isChange=false;
            event.type=status;

        }else if(NetStatus.currentStatus==status){
            // 网络链接类型没有变化
            event.isConnect=true;
            event.isChange=false;
            event.type=status;
        }else{
            // 网络链接类型已经改变
            event.isConnect=true;
            event.isChange=true;
            event.type=status;
        }
        // 保存当前的网络链接状态
        NetStatus.connected=event.isConnect;
        NetStatus.currentStatus=event.type;
        // 发送通知
        notifyNetAction(event);
    }

    public void notifyNetAction(final NEvent event){
        EventBus.getDefault().post(event);
    }
}
