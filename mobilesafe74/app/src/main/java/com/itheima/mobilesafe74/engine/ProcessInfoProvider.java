package com.itheima.mobilesafe74.engine;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.util.Log;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.db.domain.ProcessInfo;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.ActivityManager.*;
import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ProcessInfoProvider {
    // 获取进程总数的方法
    public static int getProcessCount(Context ctx) {
        // 1.获取activityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        // 2.获取正在运行进程的集合
        List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        // 3.返回集合的总数
        return runningAppProcesses.size();
    }

    /**
     * @param ctx
     * @return 返回可用的内存数 bytes
     */
    public static long getAvailSpace(Context ctx) {
        // 1.获取activityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        // 2.构建存储可用内存的对象
        MemoryInfo memoryInfo = new MemoryInfo();
        // 3.给memoryInfo对象赋（可用内存）值
        am.getMemoryInfo(memoryInfo);
        // 4.获取memoryInfo中相应可用内存大小
        return memoryInfo.availMem;
    }

    /**
     * @param ctx
     * @return 返回总的内存数，单位为bytes 返回0说明异常
     */
    public static long getTotalSpace(Context ctx) {
        // 内存大小写入文件中，读取proc/meminfo文件，读取第一行，获取数字字符，转换成bytes返回
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader("proc/meminfo");
            bufferedReader = new BufferedReader(fileReader);
            String lineOne = bufferedReader.readLine();
            // 将字符串转换成字符的数组
            char[] charArray = lineOne.toCharArray();
            // 循环遍历每一个字符，如果此字符的ASCII码在0到9的区域内，说明此字符有效
            StringBuffer stringBuffer = new StringBuffer();
            for (char c : charArray) {
                if (c >= '0' && c <= '9') {
                    stringBuffer.append(c);
                }
            }
            return Long.parseLong(stringBuffer.toString()) * 1024;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null && bufferedReader != null) {
                    fileReader.close();
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

    /**
     * 得到进程信息
     * @param context 上下文环境
     * @return 当前手机正在运行的进程的相关信息
     */
//    public static List<ProcessInfo> getProcessInfo(Context ctx) {
//        // 获取进程相关信息
//        List<ProcessInfo> processInfoList = new ArrayList<>();
//        // 1.activityManager管理者对象和PackageManager管理者对象
//        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
//        PackageManager pm = ctx.getPackageManager();
//        // 2.获取正在运行的进程的集合
//        List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
//        // 3.循环遍历上述集合，获取进程相关信息（名称，包名，图标，使用内存大小，是否为系统进程（状态机））
//        for (RunningAppProcessInfo info : runningAppProcesses) {
//            ProcessInfo processInfo = new ProcessInfo();
//            // 4.获取进程的名称 == 应用的包名
//            processInfo.packageName = info.processName;
//            // 5.获取进程占用的内存大小（传递一个进程对应的pid数组）
//            android.os.Debug.MemoryInfo[] processMemoryInfo = am.getProcessMemoryInfo(new int[]{info.pid});
//            // 6.返回数组中索引位置为0的对象，为当前进程的内存信息的对象
//            android.os.Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
//            // 7.获取已使用的大小
//            processInfo.memSize = memoryInfo.getTotalPrivateDirty() * 1024;
//
//            try {
//                ApplicationInfo applicationInfo = pm.getApplicationInfo(processInfo.packageName, 0);
//                // 8.获取应用的名称
//                processInfo.name =  applicationInfo.loadLabel(pm).toString();
//                // 9.获取应用的图标
//                processInfo.icon = applicationInfo.loadIcon(pm);
//                // 10.判断是否为系统进程
//                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
//                    processInfo.isSystem = true;
//                } else {
//                    processInfo.isSystem = false;
//                }
//            } catch (PackageManager.NameNotFoundException e) {
//                // 需要处理
//                processInfo.name = info.processName;
//                processInfo.icon = ctx.getResources().getDrawable(R.mipmap.ic_launcher);
//                processInfo.isSystem = true;
//                e.printStackTrace();
//            }
//            processInfoList.add(processInfo);
//        }
//        return processInfoList;
//    }
    public static List<ProcessInfo> getProcessInfo(Context context){
        ArrayList<ProcessInfo> processInfoList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
                ActivityManager systemService = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                Log.i(TAG, "Running app number in last 60 seconds : " + stats.size());
                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    for (int i = 0; i < stats.size(); i++) {
                      /* if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                           j = i;
                       }*/
                        int i1 = stats.get(i).describeContents();
                        String processName = stats.get(i).getPackageName();
                        Log.i(TAG, "top running app is : " + processName);
                        PackageManager PM = context.getPackageManager();
                        ProcessInfo processInfo=new ProcessInfo();
                        int uidForName = android.os.Process.getUidForName(processName);
                        /***
                         * 此方法未能成功获取进程的内存信息
                         */
                        Debug.MemoryInfo[] processMemoryInfo = systemService.getProcessMemoryInfo(new int[]{uidForName});
                        //获取已使用的大小：
                        processInfo.memSize= processMemoryInfo[0].getTotalPrivateDirty()*1024;
                        processInfo.packageName= processName;
//                        processInfo.appPid=uidForName;
                        //获取应用的名称
                        try {
                            ApplicationInfo applicationInfo = PM.getApplicationInfo(processInfo.getPackageName(), 0);

                            processInfo.name= applicationInfo.loadLabel(PM).toString();

                            processInfo.icon= applicationInfo.loadIcon(PM);

                            if ((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM){
                                processInfo.isSystem=true;
                            }else {
                                processInfo.isSystem=false;
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            processInfo.name=processInfo.packageName;
                            processInfo.icon=context.getResources().getDrawable(R.mipmap.ic_launcher);
                            processInfo.isSystem=true;
                            e.printStackTrace();
                        }
                        processInfoList.add(processInfo);
                    }
                }
            }
        }

        return processInfoList;
    }

    /**
     * 杀死单个进程
     * @param ctx
     * @param processInfo 进程信息的包名
     */
    public static void killProcess(Context ctx, ProcessInfo processInfo) {
        // 1.获取ActivityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        // 2.杀死指定包名进程（权限）
        am.killBackgroundProcesses(processInfo.packageName);
    }

    /**
     * 杀死所有进程
     * @param ctx
     */
    public static void killAll(Context ctx) {
        List<ProcessInfo> processInfo = getProcessInfo(ctx);
        for (ProcessInfo info : processInfo) {
            // 4.除了手机卫士以外，其他的进程都需要去杀死
            if (info.packageName.equals(ctx.getPackageName())) {
                continue;
            }
            ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            am.killBackgroundProcesses(info.packageName);
        }
    }
}

