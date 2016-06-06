package com.lichunjing.picturegirls.network;

/**
 * 网络状态观察者
 * 
 *
 */
@Deprecated
public interface NetStatusObserver {

	/**
	 * 网络连接类型变化时
	 * @param netType
     */
	void onNetConnected(NetStatusUtils.NetType netType);

	/**
	 * 网络断开连接时
	 */
	void onNetDisConnected();

}
