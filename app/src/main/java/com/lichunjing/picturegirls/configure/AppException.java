package com.lichunjing.picturegirls.configure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;

import com.lichunjing.picturegirls.base.BasePicApp;
import com.lichunjing.picturegirls.manager.AppManager;
import com.lichunjing.picturegirls.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义未捕获异常类
 * Created by lcj621400 on 2015/12/12.
 */
public class AppException extends Exception implements Thread.UncaughtExceptionHandler{

    private BasePicApp mApplicationContext;
    private static AppException INSTANCE=new AppException();
    private Thread.UncaughtExceptionHandler mDefaultHandler;


    private AppException(){

    }

    public static AppException getInstance(){
        return INSTANCE;
    }
    public void init(Context context){
        mApplicationContext= (BasePicApp) context.getApplicationContext();
        mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(handleException(ex)&&mDefaultHandler!=null){
            System.exit(0);
        }else{
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义异常处理，手机错误信息，发送错误报告
     * @return true 处理了异常信息.false 未处理
     */
    private boolean handleException(Throwable ex){
        if(ex==null||mApplicationContext==null){
            return false;
        }
        boolean success=true;
        try {
            //将异常信息写入sd卡
            success=saveToSdcard(ex);
        }catch (Exception e){
        }finally {
            if(!success){
                //如果没有将因此信息写入sd卡，则直接返回错误
                return false;
            }else{
                final Activity currentActivity = AppManager.getInstance().getCurrentActivity();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Looper.prepare();
                        //弹出对话框，提示用户，程序发生异常，将要关闭
                        showExitDialog(currentActivity);
                        Looper.loop();
                    }
                }.start();
            }
        }
        return true;
    }


    /**
     * 将异常信息写入sd的文件中
     * @param ex
     * @return
     */
    private boolean saveToSdcard(Throwable ex) throws IOException {
        //标记是否覆盖，还是追加
        boolean append=false;

        try {
        File logFile= FileUtils.getLogFile();
        if(logFile==null){
            return false;
        }
        //如果logFile上次编辑的时间超过5秒，则追加信息的log信息
        if(System.currentTimeMillis()-logFile.lastModified()>5000){
            append=true;
        }
        //实例化输出流
        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(logFile,append)));

        String time=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        //写入时间
        pw.println(time);
        //导出手机信息
        collectPhoneInfo(pw);
        //换行
        pw.println();
        // 导出异常的调用栈信息
        ex.printStackTrace(pw);
        pw.println();
        pw.close();
        }catch (Exception e){
        }
        return append;
    }

    /**
     * 收集设备信息
     */
    private void collectPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = mApplicationContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mApplicationContext.getPackageName(),
                PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        pw.println();

        // android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.println();

        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
        pw.println();
    }

    /**
     * 显示程序异常对话框，提示用户应用即将关闭
     * @param activity
     */
    private void showExitDialog(Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle("程序发生异常,啦啦啦啦啦啦")
                .setCancelable(false)
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //退出应用程序，结束运行此app的虚拟机
                //参数 0：正常退出
                //参数不为0时，为非正常退出
                System.exit(-1);
            }
        }).show();
    }
}
