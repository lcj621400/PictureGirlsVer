package com.lichunjing.picturegirls.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * 应用管理里类，应用广利类
 * Created by lcj621400 on 2015/12/11.
 */
public class AppManager {

    /**
     * 存储activity的栈集合
     */
    private static Stack<Activity> activityStack;
    /**
     * 唯一实例
     */
    private static AppManager instance;

    private AppManager(){
        //私有构造方法
    }

    /**
     * 静态方法返回 AppManager的实例
     * @return
     */
    public static AppManager getInstance(){
        if(instance==null){
            instance=new AppManager();
        }
        return instance;
    }

    /**
     * 返回栈顶的activity
     * @return
     */
    public static Activity getCurrentActivity(){
        return activityStack.lastElement();
    }

    /**
     * 添加activity到栈中
     * @param activity
     */
    public void addActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束指定的activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if(activity!=null&&!activity.isFinishing()){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }

    /**
     * 结束指定的activiy
     * @param clacc
     */
    public void finishActivity(Class<?> clacc){
        for(Activity activity:activityStack){
            if(activity.getClass().equals(clacc)){
               finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有的activity
     */
    public void finishAllActivity(){
        for(Activity activity:activityStack){
            if(activity!=null) {
                finishActivity(activity);
            }
        }
        activityStack.clear();
    }

    /**
     * 结束app
     */
    public void exitApp(){
        finishAllActivity();
    }


}
