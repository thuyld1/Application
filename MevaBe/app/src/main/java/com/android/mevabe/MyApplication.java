package com.android.mevabe;

import android.app.Application;

import com.android.mevabe.model.MyProfile;
import com.android.mevabe.services.db.DBService;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class MyApplication extends Application {
    private MyProfile profile;
    private DBService dbService;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);

        profile = new MyProfile(Profile.getCurrentProfile());

        // Create DB service
        dbService = new DBService(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        dbService.closeDB();
        dbService = null;
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