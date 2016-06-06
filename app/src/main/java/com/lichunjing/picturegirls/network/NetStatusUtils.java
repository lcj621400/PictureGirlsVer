package com.lichunjing.picturegirls.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;

/**
 * 检查网络联机和连接类型的工具类
 * 
 * @author lcj621400
 *
 */
@Deprecated
public class NetStatusUtils {

	/**
	 *
	 * @author lcj621400
	 *
	 */
	public enum NetType {
		WIFI, CMNET, CMWAP, NONE
	}

	/**
	 * 判断网络连接是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
		if(activeNetworkInfo!=null){
			return activeNetworkInfo.isAvailable();
		}
		return false;
//		NetworkInfo[] info = manager.getAllNetworkInfo();
//		if (info != null) {
//			for (int i = 0; i < info.length; i++) {
//				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//					return true;
//				}
//			}
//		}
//		return false;
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
		if (activeNetworkInfo != null) {
			return activeNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 判断是否是wifi连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWiFiConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiInfo != null) {
			return wifiInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 判断是否是移动网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileInfo != null) {
			return mobileInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 获取的连接网络的类型
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
		if (activeNetworkInfo != null) {
			return activeNetworkInfo.getType();
		}
		return -1;
	}

	/**
	 * 获得连接网络的类型
	 * 
	 * @param context
	 * @return
	 */
	public static NetType getAPNType(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
		if (activeNetworkInfo == null) {
			return NetType.NONE;
		}
		int nType = activeNetworkInfo.getType();
		if (nType == ConnectivityManager.TYPE_WIFI) {
			return NetType.WIFI;
		} else if (nType == ConnectivityManager.TYPE_MOBILE) {
			if (activeNetworkInfo.getExtraInfo()
					.toLowerCase(Locale.getDefault()).equals("cmnet")) {
				return NetType.CMNET;
			} else {
				return NetType.CMWAP;
			}
		}
		return NetType.NONE;

	}

}
