package com.android.mevabe.services.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.mevabe.common.AppConfig;
import com.android.mevabe.model.MyProfile;
import com.android.mevabe.model.ProfileChildModel;
import com.android.mevabe.model.VaccinationsPlanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyld on 3/7/17.
 */
public class DBVacinations {

    /**
     * Get list child of parent
     *
     * @param myProfile MyProfile
     * @param child     ProfileChildModel
     * @return List<VaccinationsPlanModel>
     */
    public List<VaccinationsPlanModel> getVaccinationsPlan(MyProfile myProfile, ProfileChildModel child) {
        Log.i(AppConfig.LOG_TAG, "DBVacinations: getVaccinationsPlan");
        List<VaccinationsPlanModel> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        String sql = "SELECT p.c_id, v._id, v.v_code, v.v_name, v.v_period, v.v_short_des, v.v_url FROM vaccinations_plan p INNER JOIN vaccines v on p.v_id = v._id WHERE p.status = 0 %s AND v.status = 0;";

        String selection = null;
        if (child != null) {
            sql = String.format(sql, "AND p.c_id = " + child.getId());

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsPlanModel item = null;
            // Parse data from DB
            while (cursor.moveToNext()) {
                int i = 0;
                long vaccinID = cursor.getLong(++i);
                long vaccinCode = cursor.getLong(++i);
                String vaccineName = cursor.getString(++i);
                String vaccinePeriod = cursor.getString(++i);
                String vaccineShortDes = cursor.getString(++i);
                String vaccineURL = cursor.getString(++i);
                item = new VaccinationsPlanModel(child, vaccinID, vaccinCode, vaccineName, vaccinePeriod, vaccineShortDes, vaccineURL);
                result.add(item);
            }
            cursor.close();
        } else {
            sql = String.format(sql, "");

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsPlanModel item = null;
            // Parse data from DB
            while (cursor.moveToNext()) {
                int i = 0;
                long childID = cursor.getLong(i);
                long vaccinID = cursor.getLong(++i);
                long vaccinCode = cursor.getLong(++i);
                String vaccineName = cursor.getString(++i);
                String vaccinePeriod = cursor.getString(++i);
                String vaccineShortDes = cursor.getString(++i);
                String vaccineURL = cursor.getString(++i);
                child = myProfile.getChild(childID);
                item = new VaccinationsPlanModel(child, vaccinID, vaccinCode, vaccineName, vaccinePeriod, vaccineShortDes, vaccineURL);
                result.add(item);
            }
            cursor.close();
        }


        return result;
    }

}
