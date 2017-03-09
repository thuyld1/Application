package com.android.mevabe.services.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.model.MyProfile;
import com.android.mevabe.model.ProfileChildModel;
import com.android.mevabe.model.VaccinationsHistoryModel;
import com.android.mevabe.model.VaccinationsPlanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyld on 3/7/17.
 */
public class DBVacinations {

    /**
     * Get list vaccines in plan list
     *
     * @param myProfile MyProfile
     * @param child     ProfileChildModel
     * @return List<VaccinationsPlanModel>
     */
    public List<VaccinationsPlanModel> getVaccinationsPlan(MyProfile myProfile, ProfileChildModel child) {
        LogUtil.debug("DBVacinations: getVaccinationsPlan");
        List<VaccinationsPlanModel> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        String sql = "SELECT p.c_id, v._id, v.v_code, v.v_name, v.v_period, v.v_short_des, v.v_url FROM " +
                "vaccinations_plan p INNER JOIN vaccines v on p.v_id = v._id WHERE p.status = 0 %s AND v.status = 0;";

        String selection = null;
        if (child != null) {
            sql = String.format(sql, "AND p.c_id = " + child.getId());

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsPlanModel item = null;
            int i = 0;

            // Parse data from DB
            while (cursor.moveToNext()) {
                i = 0;
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
            int i = 0;

            // Parse data from DB
            while (cursor.moveToNext()) {
                i = 0;
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

    /**
     * Get list vaccines in history list
     *
     * @param myProfile MyProfile
     * @param child     ProfileChildModel
     * @return List<VaccinationsHistoryModel>
     */
    public List<VaccinationsHistoryModel> getVaccinationsHistory(MyProfile myProfile, ProfileChildModel child) {
        LogUtil.debug("DBVacinations: getVaccinationsHistory");
        List<VaccinationsHistoryModel> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        String sql = "SELECT h._id, h.c_id, h.in_date, h.in_place, h.in_note, h.status, v._id, v.v_code, v.v_name, v" +
                ".v_period, v.v_short_des, v.v_url FROM vaccinations_history h INNER JOIN vaccines v on h.v_id = v" +
                "._id WHERE h.status = 0 %s AND v.status = 0;";

        String selection = null;
        if (child != null) {
            sql = String.format(sql, "AND p.c_id = " + child.getId());

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsHistoryModel item = null;
            int i = 0;

            // Parse data from DB
            while (cursor.moveToNext()) {
                item = new VaccinationsHistoryModel(child);
                result.add(item);

                // Parse data from cursor
                i = 0;
                item.setHistoryID(cursor.getLong(i));
                ++i;
                item.setInDate(cursor.getLong(++i));
                item.setInPlace(cursor.getString(++i));
                item.setInNote(cursor.getString(++i));
                item.setStatus(cursor.getInt(++i));

                item.setVaccineID(cursor.getLong(++i));
                item.setVaccineCode(cursor.getLong(++i));
                item.setVaccineName(cursor.getString(++i));
                item.setVaccinePeriod(cursor.getString(++i));
                item.setVaccineShortDes(cursor.getString(++i));
                item.setVaccineURL(cursor.getString(++i));
            }
            cursor.close();
        } else {
            sql = String.format(sql, "");

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsHistoryModel item = null;
            int i = 0;

            // Parse data from DB
            while (cursor.moveToNext()) {
                item = new VaccinationsHistoryModel(child);
                result.add(item);

                // Parse data from cursor
                i = 0;
                item.setHistoryID(cursor.getLong(i));
                item.setChild(myProfile.getChild(cursor.getLong(++i)));
                item.setInDate(cursor.getLong(++i));
                item.setInPlace(cursor.getString(++i));
                item.setInNote(cursor.getString(++i));
                item.setStatus(cursor.getInt(++i));

                item.setVaccineID(cursor.getLong(++i));
                item.setVaccineCode(cursor.getLong(++i));
                item.setVaccineName(cursor.getString(++i));
                item.setVaccinePeriod(cursor.getString(++i));
                item.setVaccineShortDes(cursor.getString(++i));
                item.setVaccineURL(cursor.getString(++i));
            }
            cursor.close();
        }


        return result;
    }

}
