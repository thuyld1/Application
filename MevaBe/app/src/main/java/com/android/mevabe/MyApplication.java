package com.android.mevabe;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class MyApplication extends Application {
    private Profile profile;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);

        profile = Profile.getCurrentProfile();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        profile = null;
    }

    // ****** Getters and Setters ***** //
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


}