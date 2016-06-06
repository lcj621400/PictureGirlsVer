package com.lichunjing.picturegirls.networkevent;

/**
 * Created by Administrator on 2016/5/9.
 */
public final class NEvent {
    public boolean isConnect;
    public int type;
    public boolean isChange;

    public NEvent(){

    }

    public NEvent(boolean isConnect,int type,boolean isChange){
        this.isConnect=isConnect;
        this.type=type;
        this.isChange=isChange;
    }
}
