package com.antonio.androidhideapireflection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 绕过限制
     *
     * @param view
     */
    public void bypass(View view) {
        int ret = AndroidHideApiHelper.bypass(MainActivity.this);
        Toast.makeText(this, "" + ret, Toast.LENGTH_SHORT).show();
    }

    /**
     * 测试反射
     *
     * @param view
     */
    public void test(View view) {
        try {
            Class<?> activityClass = Class.forName("dalvik.system.VMRuntime");
            Method field = activityClass.getDeclaredMethod("setHiddenApiExemptions", String[].class);
            field.setAccessible(true);
            Log.i(TAG, "call success!!");
            Toast.makeText(this, "反射成功", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            Log.e(TAG, "error:", e);
            Toast.makeText(this, "反射失败", Toast.LENGTH_SHORT).show();
        }

    }
}