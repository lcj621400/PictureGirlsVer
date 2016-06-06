package com.lichunjing.picturegirls.network;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义广播接收器，接收网络连接和断开或是变化的接收器
 * 
 * @author lcj621400
 *
 */
@Deprecated
public class NetStatusReceiver extends BroadcastReceiver {

	private static final String TAG="NetStatusReceiver";

	// 监听网络的广播接收器
	private static NetStatusReceiver netStatusRecevier=new NetStatusReceiver();

	// 存储NetStatusObserver的集合，每注册一个则添加进集合，解除注册时，移除此NetStatusObserver
	private static List<NetStatusObserver> observers = new ArrayList<NetStatusObserver>();

	// 连接网络时，网络的连接类型
	private NetStatusUtils.NetType netType;

	// 网络是否可用
	private boolean networkAvailable;

	@Override
	public void onReceive(Context context, Intent intent) {
		// 得到action
		String action = intent.getAction();
		// 判断action是否是网络变化的action
		if (action.equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
			// 判断网络是连接还是断开
			networkAvailable = NetStatusUtils.isNetworkAvailable(context);
			if (networkAvailable) {
				// 网络连接时，得到网络的连接类型
				netType = NetStatusUtils.getAPNType(context);
			}
			// 通知注册广播的用户，网络发生变化
			notify(context);
		}

	}

	// 通知所有注册广播的用户，网络发生变化
	private void notify(Context context) {
		// 判断应用是否在前台，如果不在前台则不通知
//		boolean activityTop = isActivityTop(context);
		boolean activityTop=true;
		if (activityTop) {
			for (NetStatusObserver ob : observers) {
				if (networkAvailable) {
					ob.onNetConnected(netType);
				} else {
					ob.onNetDisConnected();
				}
			}
		}
	}
	// 判断当前应用是否在前台
	public  boolean isActivityTop(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = manager
				.getRunningAppProcesses();
		if (appProcesses == null || appProcesses.isEmpty()) {
			return false;
		}
		for (ActivityManager.RunningAppProcessInfo app : appProcesses) {
			if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				if (app.processName.equals(context.getPackageName())) {
					return true;
				}
			}
		}
		return false;
	}

	// 得到NetStatusRecevier的实例，使用同步锁，保证只实例化一个对象
	private static NetStatusReceiver getRecevier() {
//		if (netStatusRecevier == null) {
//			synchronized (NetStatusReceiver.class) {
//				netStatusRecevier = new NetStatusReceiver();
//			}
//		}
//		return netStatusRecevier;

		return  new NetStatusReceiver();
	}

	// 注册广播接收接收器，并传入NetStatusObserver(接口，内含网络变化的具体方法)
	public static void registerNetStatusReceiver(Activity context,
			NetStatusObserver observer) {
		// 注册时，添加进集合
//		observers.add(observer);
//		Log.d(TAG,"Observer添加注册："+context.getClass().getSimpleName());
//		Log.d(TAG,"Observer数量："+observers.size());
		// 添加action
		IntentFilter filter = new IntentFilter();
		// 添加网络变化的action
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		// 注册广播接收器
		context.registerReceiver(getRecevier(), filter);
		Log.d(TAG,context.getClass().getSimpleName()+"注册Observer");
	}

	public static void unRegistNetStatusReceiver(Activity context,
			NetStatusObserver observer) {
//		if (observers.contains(observer)) {
//			// 集合中移除NetStatusObserver
//			observers.remove(observer);
//			Log.d(TAG,"Observer解除注册："+context.getClass().getSimpleName());
//			Log.d(TAG,"Observer数量："+observers.size());
//		}
		// 判断activity是否被销毁
		if (context != null) {
			// 解除注册广播
			context.unregisterReceiver(getRecevier());
			Log.d(TAG,context.getClass().getSimpleName()+"解除注册Observer");
		}
	}

}
