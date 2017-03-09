package com.android.mevabe.common.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.mevabe.R;

/**
 * Created by leducthuy on 3/8/17.
 */
public class DialogUtil {

    /**
     * Show yes, cancel dialog to confirm
     *
     * @param context Context
     * @param message String
     * @param handler DialogInterface.OnClickListener
     */
    public static void showYesCancel(Context context, String message, DialogInterface.OnClickListener handler) {
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, handler)
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }
}
