package com.android.mevabe.common;

import android.content.Context;

import com.android.mevabe.common.model.MyProfile;
import com.android.mevabe.common.db.DBProfile;
import com.android.mevabe.common.db.DBService;
import com.android.mevabe.common.utils.LogUtil;
import com.facebook.Profile;

/**
 * Created by thuyld on 3/9/17.
 */

public class AppData {
    public static Context context;
    private static MyProfile profile;
    private static DBService dbService;
    private static DBProfile dbProfile;

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
        dbProfile = new DBProfile();

        // Apdate login account
        setLoginProfile(Profile.getCurrentProfile());
    }

    /**
     * Release data when terminate
     */
    public static void onTerminate() {
        AppData.context = null;
        profile = null;

        dbService.closeDB();
        dbService = null;
        dbProfile = null;
    }

    // ****** Login profile control ***** //
    public static MyProfile getMyProfile() {
        return profile;
    }

    public static void setLoginProfile(Profile loginProfile) {
        LogUtil.debug("AppData: setLoginProfile => " + loginProfile);
        profile.setMyPro(loginProfile);

        // Get list children of account
        if (loginProfile != null) {
            profile.setChildren(dbProfile.getMyChildren(loginProfile.getId()));
        } else {
            // Clear old data if empty profile information
            profile.resetChild();
        }
    }
}
