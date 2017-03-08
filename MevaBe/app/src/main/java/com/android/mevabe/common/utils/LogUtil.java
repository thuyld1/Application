package com.android.mevabe.common.utils;

import android.util.Log;

import static com.android.mevabe.common.AppConfig.LOG_ALLOW;
import static com.android.mevabe.common.AppConfig.LOG_TAG;

/**
 * Created by leducthuy on 3/8/17.
 */
public class LogUtil {
    public static void debug(String message) {
        if (LOG_ALLOW) {
            Log.d(LOG_TAG, message);
        }
    }

    public static void info(String message) {
        if (LOG_ALLOW) {
            Log.i(LOG_TAG, message);
        }
    }

    public static void error(String message) {
        if (LOG_ALLOW) {
            Log.e(LOG_TAG, message);
        }
    }

    public static void error(Exception e) {
        if (LOG_ALLOW) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    public static void error(String message, Exception e) {
        if (LOG_ALLOW) {
            Log.e(LOG_TAG, message, e);
        }
    }
}

