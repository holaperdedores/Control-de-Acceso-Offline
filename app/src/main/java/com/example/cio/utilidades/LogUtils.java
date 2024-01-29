package com.example.cio.utilidades;

import android.util.Log;

import com.example.cio.BuildConfig;

public class LogUtils {

    public static void LOGI(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void LOGE(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }
}
