package com.example.stn.stn;

import android.app.Application;
import android.content.Context;

/**
 * Name: MyApplication
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-07-17 19:41.
 */
public class MyApplication extends Application{
    private static Context context;
    private String UUID;
    private String token;   //登陆令牌
    private String numberJob;  //员工号
    private String tridId;    //业务流水号

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                //当发现未捕获的异常 会调用这个方法 ex
//                PrintWriter writer = null;
//                try {
//                    writer = new PrintWriter(Environment.getExternalStorageDirectory().getAbsolutePath()+"/exception.txt");
//                    //把异常写入文件
//                    ex.printStackTrace(writer);
//                } catch (FileNotFoundException e) {
//                    System.out.println("文件创建失败");
//                    e.printStackTrace();
//                }finally{
//                    if (writer != null) {
//                        writer.close();
//                    }
//                };
//                Intent intent = new Intent(context,LaunchActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
//            }
//        });
    }


    /**
     * 11月4日，全局捕获异常。重启应用。
     * @return
     */


    public static Context getContext(){
        return context;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNumberJob() {
        return numberJob;
    }

    public void setNumberJob(String numberJob) {
        this.numberJob = numberJob;
    }

    public String getTridId() {
        return tridId;
    }

    public void setTridId(String tridId) {
        this.tridId = tridId;
    }
}
