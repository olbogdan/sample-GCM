package com.github.flinbor.pushclint.util;
/**
 * Created by Bogdanov Oleksandr on 17,October,2015
 */

import android.util.Log;

import com.github.flinbor.pushclint.core.PushApplication;
import tk.flinbor.pushnotifications.BuildConfig;


/**
 * custom logger
 */
public class Logger {
    public static final boolean ENABLED = BuildConfig.DEBUG;
    public static final String TAG = PushApplication.class.getName();

    private Logger() {
    }

    /**
     * @param tag
     * @param message
     */
    public static void logD(String tag, String message) {
        if (ENABLED) {
            Log.d(tag, message);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void logE(String tag, String message) {
        if (ENABLED) {
            Log.e(tag, message);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void logI(String tag, String message) {
        if (ENABLED) {
            Log.e(tag, message);
        }
    }

    /**
     * @param message
     */
    public static void logD(String message) {
        if (ENABLED) {
            Log.d(TAG, message);
        }
    }

    /**
     * @param message
     */
    public static void logE(String message) {
        if (ENABLED) {
            Log.e(TAG, message);
        }
    }

    /**
     * @param message
     */
    public static void logI(String message) {
        if (ENABLED) {
            Log.e(TAG, message);
        }
    }
}
