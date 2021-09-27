package com.antonio.androidhideapireflection;

import android.os.Build;

import androidx.annotation.Keep;

import java.lang.reflect.Method;


/**
 * 让系统以为是系统类调用的它
 * https://cs.android.com/android/platform/superproject/+/master:libcore/libart/src/main/java/dalvik/system/VMRuntime.java;l=223;bpv=1;bpt=0?q=VMRuntime&sq=&ss=android%2Fplatform%2Fsuperproject
 *
 * @author antonio
 * @version 1.0
 */
@Keep
public class CustomBootClass {
    private static Object sVmRuntime;
    private static Method setHiddenApiExemptions;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                Method forName = Class.class.getDeclaredMethod("forName", String.class);
                Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);

                Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
                Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
                setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
                sVmRuntime = getRuntime.invoke(null);
            } catch (Throwable e) {
                //忽略错误
            }
        }
    }

    /**
     * 绕过所有隐藏代码
     *
     * @return true 绕过成功 false 绕过失败
     */
    public static boolean bypassAllHide() {
        try {
            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
