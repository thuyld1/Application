package com.android.mevabe;

import android.app.Application;

import com.android.mevabe.model.MyProfile;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class MyApplication extends Application {
    private MyProfile profile;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);

        profile = new MyProfile(Profile.getCurrentProfile());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        profile = null;
    }

    // ****** Getters and Setters ***** //
    public MyProfile getMyProfile() {
        return profile;
    }

    public void setLoginProfile(Profile loginProfile) {
        profile.setMyPro(loginProfile);
    }


}