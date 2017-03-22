package com.android.mevabe.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by hoangnm on 9/16/15.
 */
public class PrefUtil {
    private static SharedPreferences mPreferences = null;

    public static void init(Context context) {
        mPreferences = context.getSharedPreferences(context.getPackageName()
                .toString(), Context.MODE_PRIVATE);
    }

    public static void onTerminate() {
        mPreferences = null;
    }

    public void remove(String key) {
        mPreferences.edit().remove(key).commit();
    }

    // *************** Action control ************* //
    public static void writeBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean readBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public static void writeInt(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }

    public static int readInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public static void writeString(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }

    public static String readString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public static void writeFloat(String key, float value) {
        mPreferences.edit().putFloat(key, value).commit();
    }

    public static float readFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public static void writeLong(String key, long value) {
        mPreferences.edit().putLong(key, value).commit();
    }

    public static long readLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public static void writeList(String key, Set<String> value) {
        mPreferences.edit().putStringSet(key, value).commit();
    }

    public static Set<String> readList(String key, Set<String> defValue) {
        return mPreferences.getStringSet(key, defValue);
    }
}