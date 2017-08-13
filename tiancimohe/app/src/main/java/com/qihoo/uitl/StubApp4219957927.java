package com.qihoo.util;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class StubApp4219957927 extends Application
{
    private static Context context;
    private static boolean loadFromLib;
    public static Application newApp;
    public static Application runApp = null;
    private static String soName;
    public static String strEntryApplication = "com.qihoo360.crypt.entryRunApplication";

    static
    {
        newApp = null;
        soName = "libjiagu";
        loadFromLib = false;
    }

    public static void ChangeTopApplication()
    {
        Context localContext = runApp.getBaseContext();
        try
        {
            interface7(newApp, localContext);
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    public static boolean copy(Context paramContext, String paramString1, String paramString2, String paramString3)
    {
        int j;
        paramString3 = paramString2 + "/" + paramString3;
        paramString2 = new File(paramString2);
        if (!(paramString2.exists()))
            paramString2.mkdir();
        try
        {
            Object localObject = new File(paramString3);
            if (((File)localObject).exists())
            {
                paramString2 = paramContext.getResources().getAssets().open(paramString1);
                localObject = new FileInputStream((File)localObject);
                BufferedInputStream localBufferedInputStream1 = new BufferedInputStream(paramString2);
                BufferedInputStream localBufferedInputStream2 = new BufferedInputStream((InputStream)localObject);
                if (!(isSameFile(localBufferedInputStream1, localBufferedInputStream2)))
                    break label244;
                j = 1;
                paramString2.close();
                ((InputStream)localObject).close();
                localBufferedInputStream1.close();
                localBufferedInputStream2.close();
                if (j != 0)
                    return j;
            }
            paramContext = paramContext.getResources().getAssets().open(paramString1);
            paramString1 = new FileOutputStream(paramString3);
            paramString2 = new byte[7168];
            int i = paramContext.read(paramString2);
            if (i <= 0)
                break label204;
            paramString1.write(paramString2, 0, i);
        }
        catch (Exception paramContext)
        {
            paramContext.printStackTrace();
            return false;
        }
        label204: paramString1.close();
        paramContext.close();
        try
        {
            Runtime.getRuntime().exec("chmod 755 " + paramString3);
            label238: label244: return true;
        }
        catch (Exception paramContext)
        {
            break label238:
            j = 0;
        }
    }

    public static Context getAppContext()
    {
        return context;
    }

    public static Application getNewAppInstance(Context paramContext)
    {
        try
        {
            if (newApp == null)
            {
                paramContext = paramContext.getClassLoader();
                if (paramContext != null)
                {
                    paramContext = paramContext.loadClass(strEntryApplication);
                    if (paramContext != null)
                        newApp = (Application)paramContext.newInstance();
                }
            }
            return newApp;
        }
        catch (Exception paramContext)
        {
            paramContext.printStackTrace();
        }
    }

    private void initAssetForNative()
    {
        try
        {
            Class.forName("com.qihoo.dexjiagu.TransitMgr").getMethod("initAssetForNative", new Class[] { Context.class }).invoke(null, new Object[] { this });
            return;
        }
        catch (Exception localException)
        {
        }
    }

    private void initCrashReport()
    {
        try
        {
            Class.forName("com.qihoo.bugreport.CrashReport").getDeclaredMethod("init", new Class[] { Context.class }).invoke(null, new Object[] { getApplicationContext() });
            return;
        }
        catch (Throwable localThrowable)
        {
        }
    }

    public static native void interface10(Context paramContext);

    public static native void interface11(int paramInt);

    public static native void interface5(Application paramApplication);

    public static native String interface6(String paramString);

    public static native boolean interface7(Application paramApplication, Context paramContext);

    public static native boolean interface8(Application paramApplication, Context paramContext);

    public static boolean isSameFile(BufferedInputStream paramBufferedInputStream1, BufferedInputStream paramBufferedInputStream2)
    {
        int j;
        try
        {
            byte[] arrayOfByte1;
            byte[] arrayOfByte2;
            j = paramBufferedInputStream1.available();
            int i = paramBufferedInputStream2.available();
            if (j == i)
            {
                arrayOfByte1 = new byte[j];
                arrayOfByte2 = new byte[i];
                paramBufferedInputStream1.read(arrayOfByte1);
                paramBufferedInputStream2.read(arrayOfByte2);
                i = 0;
            }
            while (i < j)
            {
                int k = arrayOfByte1[i];
                int l = arrayOfByte2[i];
                if (k != l)
                    return false;
                i += 1;
            }
            return true;
        }
        catch (FileNotFoundException paramBufferedInputStream1)
        {
            paramBufferedInputStream1.printStackTrace();
            return false;
        }
        catch (IOException paramBufferedInputStream1)
        {
            paramBufferedInputStream1.printStackTrace();
        }
        return false;
    }

    // ERROR //
    public static Boolean isX86Arch()
    {
        // Byte code:
        //   0: getstatic 220	android/os/Build:SUPPORTED_32_BIT_ABIS	[Ljava/lang/String;
        //   3: astore_2
        //   4: aload_2
        //   5: arraylength
        //   6: istore_1
        //   7: iconst_0
        //   8: istore_0
        //   9: iload_0
        //   10: iload_1
        //   11: if_icmpge +148 -> 159
        //   14: aload_2
        //   15: iload_0
        //   16: aaload
        //   17: ldc 222
        //   19: invokevirtual 228	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   22: ifeq +10 -> 32
        //   25: iconst_1
        //   26: invokestatic 234	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
        //   29: astore_2
        //   30: aload_2
        //   31: areturn
        //   32: iload_0
        //   33: iconst_1
        //   34: iadd
        //   35: istore_0
        //   36: goto -27 -> 9
        //   39: astore_2
        //   40: getstatic 237	android/os/Build:CPU_ABI	Ljava/lang/String;
        //   43: ldc 222
        //   45: invokevirtual 228	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   48: ifne +14 -> 62
        //   51: getstatic 240	android/os/Build:CPU_ABI2	Ljava/lang/String;
        //   54: ldc 222
        //   56: invokevirtual 228	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   59: ifeq +8 -> 67
        //   62: iconst_1
        //   63: invokestatic 234	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
        //   66: areturn
        //   67: new 242	java/io/RandomAccessFile
        //   70: dup
        //   71: ldc 244
        //   73: ldc 246
        //   75: invokespecial 249	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
        //   78: astore_3
        //   79: aload_3
        //   80: astore_2
        //   81: aload_3
        //   82: invokevirtual 252	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
        //   85: astore 4
        //   87: aload 4
        //   89: ifnull +62 -> 151
        //   92: aload_3
        //   93: astore_2
        //   94: aload 4
        //   96: ldc 254
        //   98: invokevirtual 228	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   101: ifeq +39 -> 140
        //   104: aload_3
        //   105: astore_2
        //   106: aload 4
        //   108: ldc 222
        //   110: invokevirtual 228	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   113: ifeq +27 -> 140
        //   116: iconst_1
        //   117: invokestatic 234	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
        //   120: astore 4
        //   122: aload 4
        //   124: astore_2
        //   125: aload_3
        //   126: ifnull -96 -> 30
        //   129: aload_3
        //   130: invokevirtual 255	java/io/RandomAccessFile:close	()V
        //   133: aload 4
        //   135: areturn
        //   136: astore_2
        //   137: aload 4
        //   139: areturn
        //   140: aload_3
        //   141: astore_2
        //   142: aload_3
        //   143: invokevirtual 252	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
        //   146: astore 4
        //   148: goto -61 -> 87
        //   151: aload_3
        //   152: ifnull +7 -> 159
        //   155: aload_3
        //   156: invokevirtual 255	java/io/RandomAccessFile:close	()V
        //   159: iconst_0
        //   160: invokestatic 234	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
        //   163: areturn
        //   164: astore 4
        //   166: aconst_null
        //   167: astore_3
        //   168: aload_3
        //   169: astore_2
        //   170: aload 4
        //   172: invokevirtual 209	java/io/FileNotFoundException:printStackTrace	()V
        //   175: aload_3
        //   176: ifnull -17 -> 159
        //   179: aload_3
        //   180: invokevirtual 255	java/io/RandomAccessFile:close	()V
        //   183: goto -24 -> 159
        //   186: astore_2
        //   187: goto -28 -> 159
        //   190: astore 4
        //   192: aconst_null
        //   193: astore_3
        //   194: aload_3
        //   195: astore_2
        //   196: aload 4
        //   198: invokevirtual 210	java/io/IOException:printStackTrace	()V
        //   201: aload_3
        //   202: ifnull -43 -> 159
        //   205: aload_3
        //   206: invokevirtual 255	java/io/RandomAccessFile:close	()V
        //   209: goto -50 -> 159
        //   212: astore_2
        //   213: goto -54 -> 159
        //   216: astore_3
        //   217: aconst_null
        //   218: astore_2
        //   219: aload_2
        //   220: ifnull +7 -> 227
        //   223: aload_2
        //   224: invokevirtual 255	java/io/RandomAccessFile:close	()V
        //   227: aload_3
        //   228: athrow
        //   229: astore_2
        //   230: goto -71 -> 159
        //   233: astore_2
        //   234: goto -7 -> 227
        //   237: astore_3
        //   238: goto -19 -> 219
        //   241: astore 4
        //   243: goto -49 -> 194
        //   246: astore 4
        //   248: goto -80 -> 168
        //
        // Exception table:
        //   from	to	target	type
        //   0	7	39	java/lang/NoSuchFieldError
        //   14	25	39	java/lang/NoSuchFieldError
        //   129	133	136	java/lang/Exception
        //   67	79	164	java/io/FileNotFoundException
        //   179	183	186	java/lang/Exception
        //   67	79	190	java/io/IOException
        //   205	209	212	java/lang/Exception
        //   67	79	216	finally
        //   155	159	229	java/lang/Exception
        //   223	227	233	java/lang/Exception
        //   81	87	237	finally
        //   94	104	237	finally
        //   106	116	237	finally
        //   142	148	237	finally
        //   170	175	237	finally
        //   196	201	237	finally
        //   81	87	241	java/io/IOException
        //   94	104	241	java/io/IOException
        //   106	116	241	java/io/IOException
        //   142	148	241	java/io/IOException
        //   81	87	246	java/io/FileNotFoundException
        //   94	104	246	java/io/FileNotFoundException
        //   106	116	246	java/io/FileNotFoundException
        //   142	148	246	java/io/FileNotFoundException
    }

    public static native Location mark(LocationManager paramLocationManager, String paramString);

    public static native void mark();

    public static native void mark(Location paramLocation);

    public static native void n0110();

    public static native int n0111();

    public static native void n01110(int paramInt);

    public static native long n0112();

    public static native Object n0113();

    public static native void n01130(Object paramObject);

    public static native int n01131(Object paramObject);

    public static native int n01131311(Object paramObject1, int paramInt1, Object paramObject2, int paramInt2);

    private void prepareInitCrashReport()
    {
        try
        {
            Class.forName("com.qihoo.bugreport.CrashReport").getDeclaredMethod("prepareInit", new Class[0]).invoke(null, new Object[0]);
            return;
        }
        catch (Throwable localThrowable)
        {
        }
    }

    protected void attachBaseContext(Context paramContext)
    {
        Object localObject;
        Boolean localBoolean1;
        String str;
        Boolean localBoolean2;
        super.attachBaseContext(paramContext);
        context = paramContext;
        if (runApp == null)
            runApp = this;
        if (newApp == null)
        {
            localObject = paramContext.getFilesDir().getParentFile().getAbsolutePath();
            str = ((String)localObject) + "/.jiagu";
            localBoolean2 = isX86Arch();
            localObject = Boolean.valueOf(false);
            localBoolean1 = Boolean.valueOf(false);
            if ((Build.CPU_ABI.contains("64")) || (Build.CPU_ABI2.contains("64")))
                localObject = Boolean.valueOf(true);
            if ((Build.CPU_ABI.contains("mips")) || (Build.CPU_ABI2.contains("mips")))
                localBoolean1 = Boolean.valueOf(true);
            if (!(loadFromLib))
                break label235;
            if (!(localBoolean2.booleanValue()))
                break label226;
            System.loadLibrary("jiagu_x86");
        }
        interface5(runApp);
        newApp = getNewAppInstance(paramContext);
        if (newApp != null);
        try
        {
            while (true)
            {
                localObject = Application.class.getDeclaredMethod("attach", new Class[] { Context.class });
                if (localObject != null)
                {
                    ((Method)localObject).setAccessible(true);
                    ((Method)localObject).invoke(newApp, new Object[] { paramContext });
                }
                interface8(newApp, paramContext);
                initAssetForNative();
                return;
                label226: System.loadLibrary("jiagu");
            }
            if (localBoolean1.booleanValue())
            {
                label235: copy(paramContext, soName + "_mips.so", str, soName + ".so");
                if ((!(((Boolean)localObject).booleanValue())) || (localBoolean1.booleanValue()))
                    break label574;
                if (!(localBoolean2.booleanValue()))
                    break label520;
                copy(paramContext, soName + "_x64.so", str, soName + "_64.so");
            }
            while (true)
            {
                while (true)
                {
                    while (true)
                    {
                        while (true)
                            System.load(str + "/" + soName + "_64.so");
                        if (!(localBoolean2.booleanValue()))
                            break;
                        copy(paramContext, soName + "_x86.so", str, soName + ".so");
                    }
                    copy(paramContext, soName + ".so", str, soName + ".so");
                }
                label520: copy(paramContext, soName + "_a64.so", str, soName + "_64.so");
            }
            label574: System.load(str + "/" + soName + ".so");
        }
        catch (Exception localException)
        {
            while (true)
            {
                while (true)
                    localException.printStackTrace();
                System.exit(1);
            }
        }
    }

    public native void n11110(int paramInt);

    public native void n111110(int paramInt1, int paramInt2);

    public native void n111133330(int paramInt, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4);

    public native void n11120(long paramLong);

    public native Object n1113();

    public native void n11130(Object paramObject);

    public native int n11131(Object paramObject);

    public native Object n111311133(Object paramObject1, int paramInt1, int paramInt2, int paramInt3, Object paramObject2);

    public native Object n11133(Object paramObject);

    public void onCreate()
    {
        super.onCreate();
        if (Configuration.ENABLE_CRASH_REPORT)
            prepareInitCrashReport();
        ChangeTopApplication();
        if (newApp != null)
            newApp.onCreate();
        if (Configuration.ENABLE_CRASH_REPORT)
            initCrashReport();
        interface10(getAppContext());
    }
}