package com.antonio.androidhideapireflection;

import static android.os.Build.VERSION.SDK_INT;
import static com.antonio.androidhideapireflection.CustomBootClass.bypassAllHide;

import android.content.Context;
import android.os.Build;

public class AndroidHideApiHelper {

    /**
     * 解除封禁
     *
     * @param context 上下文对象Application的context
     * @return 0 解封成功 -1解封失败
     */
    public static int bypass(Context context) {
        if (SDK_INT < Build.VERSION_CODES.P) {
            return 0;
        }

        if (bypassAllHide()) {
            return 0;
        }
        return -1;
    }
}
