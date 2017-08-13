package com.qihoo.util;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
public class QhJobService extends JobService
{
    static final int jobId = 1;

    public static native void interface10();

    public static void schedule(Context paramContext)
    {
        JobScheduler localJobScheduler = (JobScheduler)paramContext.getSystemService("jobscheduler");
        Object localObject = localJobScheduler.getAllPendingJobs().iterator();
        do
            if (!(((Iterator)localObject).hasNext()))
                break label46;
        while (((JobInfo)((Iterator)localObject).next()).getId() != 1);
        return;
        label46: localObject = new ComponentName(paramContext.getPackageName(), QhJobService.class.getName());
        paramContext.getPackageManager().setComponentEnabledSetting((ComponentName)localObject, 1, 1);
        localJobScheduler.schedule(new JobInfo.Builder(1, (ComponentName)localObject).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(TimeUnit.DAYS.toMillis(4416224577932230657L)).build());
    }

    public boolean onStartJob(JobParameters paramJobParameters)
    {
        new Thread(this, "DexOptJobService_DexOptimization", paramJobParameters)
        {
            public void run()
            {
                QhJobService.interface10();
                this.this$0.jobFinished(this.val$jobParams, false);
            }
        }
                .start();
        return true;
    }

    public boolean onStopJob(JobParameters paramJobParameters)
    {
        return false;
    }
}