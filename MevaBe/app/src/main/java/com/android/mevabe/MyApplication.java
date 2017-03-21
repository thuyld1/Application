package com.android.mevabe;

import android.app.Application;

import com.android.mevabe.common.AppData;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.utils.PrefUtil;
import com.facebook.FacebookSdk;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.info("MyApplication: onCreate()");
        FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);
        AppData.initAppData(getApplicationContext());

        // Init SharedPreferences
        PrefUtil.init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.info("MyApplication: onCreate()");

        // Release app data
        AppData.onTerminate();

        // Release SharedPreferences
        PrefUtil.onTerminate();
    }


}