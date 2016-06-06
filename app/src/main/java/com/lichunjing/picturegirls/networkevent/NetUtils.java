package com.lichunjing.picturegirls.networkevent;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/5/9.
 */
public class NetUtils {

    /**
     * 获取网络链接的类型
     *
     * @param context
     * @return
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetStatus.TYPE_WIFI;
            }
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NetStatus.TYPE_MOBILE;
            }
        }
        return NetStatus.TYPE_OFFLINE;
    }

    /**
     * 判断是否存在网络链接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        //判断是否已经判断过网络链接,-1表示没有判断过
        if (NetStatus.currentStatus == -1) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                NetStatus.connected=true;
                return true;
            }
            NetStatus.connected=false;
            return false;
        }else if(NetStatus.currentStatus==NetStatus.TYPE_OFFLINE){
            return false;
        }
        return true;
    }
}
