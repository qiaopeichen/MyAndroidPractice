package com.qihoo.uitl;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Looper;
import android.view.Window;


/**
 * Created by Administrator on 2017/7/16.
 */

public class QHDialog
{
    public static void showDialog(Context paramContext, String paramString)
    {
        paramContext = new Thread(new Runnable(paramContext, paramString)
        {
            public void run()
            {
                Looper.prepare();
                AlertDialog localAlertDialog = new AlertDialog.Builder(this.val$context).setMessage(this.val$msg).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener(this)
                {
                    public void onClick(, int paramInt)
                    {
                        synchronized (Thread.currentThread())
                        {
                            if (Build.VERSION.SDK_INT >= 19)
                ???.notify();
                            return;
                        }
                    }
                }).create();
                localAlertDialog.getWindow().setType(2005);
                localAlertDialog.show();
                Looper.loop();
            }
        });
        monitorenter;
        try
        {
            paramContext.start();
            if (Build.VERSION.SDK_INT >= 19)
            {
                paramContext.wait();
                return;
            }
        }
        catch (java.lang.InterruptedException paramString)
        {
        }
        finally
        {
            monitorexit;
        }
    }
}