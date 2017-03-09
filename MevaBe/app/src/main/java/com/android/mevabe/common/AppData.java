package com.android.mevabe.common;

import android.content.Context;

import com.android.mevabe.model.MyProfile;
import com.android.mevabe.services.db.DBService;
import com.facebook.Profile;

/**
 * Created by thuyld on 3/9/17.
 */

public class AppData {
    public static Context context;
    private static MyProfile profile;
    private static DBService dbService;

    /**
     * Initial application data
     *
     * @param context Context
     */
    public static void initAppData(Context context) {
        AppData.context = context;
        profile = new MyProfile(Profile.getCurrentProfile());

        // Create DB service
        dbService = new DBService(context);
    }

    /**
     * Release data when terminate
     */
    public static void onTerminate() {
        AppData.context = null;

        dbService.closeDB();
        dbService = null;
        profile = null;
    }

    // ****** Login profile controll ***** //
    public static MyProfile getMyProfile() {
        return profile;
    }

    public static void setLoginProfile(Profile loginProfile) {
        profile.setMyPro(loginProfile);
    }
}
