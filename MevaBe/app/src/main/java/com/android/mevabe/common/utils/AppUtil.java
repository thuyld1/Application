package com.android.mevabe.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * App Utility
 */
public class AppUtil {
    public static void showKeyboard(Context pContext, View v) {
        InputMethodManager imm = (InputMethodManager) pContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Context pContext) {
        InputMethodManager imm = (InputMethodManager) pContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * Check internet connection
     *
     * @param context Context
     * @return true if connected
     */
    public static boolean hasInternet(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Check internet for connection
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Open google play link
     *
     * @param context     Context
     * @param packageName String
     */
    public static void openGooglePlay(final Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (isAppInstalled(context, "com.android.vending")) {
            intent.setData(Uri.parse("market://details?id=" + packageName));
        } else {
            intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
        }
        context.startActivity(intent);
    }


    /**
     * Checks if is app installed.
     *
     * @param context     the context
     * @param packageName the package name
     * @return true, if is app installed
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Convert dp to px
     *
     * @param context Context
     * @param dp      int
     * @return int
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Convert dp to px
     *
     * @param context Context
     * @param px      int
     * @return int
     */
    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}
