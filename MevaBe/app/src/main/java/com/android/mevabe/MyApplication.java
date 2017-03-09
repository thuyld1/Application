package com.android.mevabe;

import android.app.Application;

import com.android.mevabe.common.AppData;
import com.facebook.FacebookSdk;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);
        AppData.initAppData(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // Release app data
        AppData.onTerminate();
    }


}