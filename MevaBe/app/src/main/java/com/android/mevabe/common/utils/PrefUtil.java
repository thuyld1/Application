package com.android.mevabe.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hoangnm on 9/16/15.
 */
public class PrefUtil {
    public static final int MODE = Context.MODE_PRIVATE;
//    private static SharedPreferences mPreferences = null;
//    private static SharedPreferences.Editor mEditor = null;
    private static Context mContext = null;

    public static void init(Context context) {
        mContext = context;
//        mPreferences = context.getSharedPreferences(context.getPackageName()
//                .toString(), MODE);
//        mEditor = mPreferences.edit();
    }

    public static void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        if (editor != null) {
            editor.putBoolean(key, value).commit();
        }
    }

    public static boolean readBoolean(String key, boolean defValue) {
        SharedPreferences sharedPrefs = getPreferences();
        if (sharedPrefs != null) {
            return sharedPrefs.getBoolean(key, defValue);
        }
        return defValue;
    }

    public static void writeInteger(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        if (editor != null) {
            editor.putInt(key, value).commit();
        }

    }

    public static int readInteger(String key, int defValue) {
        SharedPreferences sharedPrefs = getPreferences();
        if (sharedPrefs != null) {
            return sharedPrefs.getInt(key, defValue);
        }
        return defValue;
    }

    public static void writeString(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        if (editor != null) {
            editor.putString(key, value).commit();
        }
    }

    public static String readString(String key, String defValue) {
        SharedPreferences sharedPrefs = getPreferences();
        if (sharedPrefs != null) {
            return sharedPrefs.getString(key, defValue);
        }
        return defValue;
    }

    public static void writeFloat(String key, float value) {
        SharedPreferences.Editor editor = getEditor();
        if (editor != null) {
            editor.putFloat(key, value).commit();
        }
    }

    public static float readFloat(String key, float defValue) {
        SharedPreferences sharedPrefs = getPreferences();
        if (sharedPrefs != null) {
            return sharedPrefs.getFloat(key, defValue);
        }
        return defValue;
    }

    public static void writeLong(String key, long value) {
        SharedPreferences.Editor editor = getEditor();
        if (editor != null) {
            editor.putLong(key, value).commit();
        }
    }

    public static long readLong(String key, long defValue) {
        SharedPreferences sharedPrefs = getPreferences();
        if (sharedPrefs != null) {
            return sharedPrefs.getLong(key, defValue);
        }
        return defValue;
    }

    public static SharedPreferences getPreferences() {
        if (mContext != null) {
            return mContext.getSharedPreferences(mContext.getPackageName().toString(), MODE);
        }
        return null;
    }

    public static SharedPreferences.Editor getEditor() {
        SharedPreferences sharedPrefs = getPreferences();
        if (sharedPrefs != null) {
            return sharedPrefs.edit();
        }
        return null;
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = getEditor();
        if (editor != null) {
            editor.remove(key).commit();
        }
    }
}
