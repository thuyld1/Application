package com.android.mevabe.common.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.model.MyProfile;
import com.android.mevabe.common.model.ProfileChildModel;
import com.android.mevabe.common.model.VaccinationsHistoryModel;
import com.android.mevabe.common.model.VaccinationsPlanModel;
import com.android.mevabe.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
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

        List<VaccinationsPlanModel> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        String sql = "SELECT p._id, p.c_id, v._id, v.v_code, v.v_name, v.v_period, v.v_short_des, v.v_url FROM " +
                "vaccinations_plan p INNER JOIN vaccines v on p.v_id = v._id WHERE p.status = 0 %s AND v.status = 0;";

        String selection = null;
        if (child != null) {
            sql = String.format(sql, "AND p.c_id = " + child.getId());
            LogUtil.debug("DBVacinations: getVaccinationsPlan for child " + child.getName());

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsPlanModel item = null;
            int i = 0;

            // Parse data from DB
            while (cursor.moveToNext()) {
                i = 0;
                long planID = cursor.getLong(i++);
                long vaccinID = cursor.getLong(++i);
                long vaccinCode = cursor.getLong(++i);
                String vaccineName = cursor.getString(++i);
                String vaccinePeriod = cursor.getString(++i);
                String vaccineShortDes = cursor.getString(++i);
                String vaccineURL = cursor.getString(++i);
                item = new VaccinationsPlanModel(child, planID, vaccinID, vaccinCode, vaccineName, vaccinePeriod,
                        vaccineShortDes, vaccineURL);
                result.add(item);
            }
            cursor.close();
        } else {
            sql = String.format(sql, "");
            LogUtil.debug("DBVacinations: getVaccinationsPlan for ALL ");

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsPlanModel item = null;
            int i = 0;

            // Parse data from DB
            while (cursor.moveToNext()) {
                i = 0;
                long planID = cursor.getLong(i);
                long childID = cursor.getLong(++i);
                long vaccinID = cursor.getLong(++i);
                long vaccinCode = cursor.getLong(++i);
                String vaccineName = cursor.getString(++i);
                String vaccinePeriod = cursor.getString(++i);
                String vaccineShortDes = cursor.getString(++i);
                String vaccineURL = cursor.getString(++i);
                child = myProfile.getChild(childID);
                item = new VaccinationsPlanModel(child, planID, vaccinID, vaccinCode, vaccineName, vaccinePeriod,
                        vaccineShortDes, vaccineURL);
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

        String sql = "SELECT h._id, h.c_id, h.in_date, h.in_status, h.in_place, h.in_note, h.status, v._id, v.v_code, v.v_name, v" +
                ".v_period, v.v_short_des, v.v_url FROM vaccinations_history h INNER JOIN vaccines v on h.v_id = v" +
                "._id WHERE h.status = 0 %s AND v.status = 0 ORDER BY h.in_status, h.in_date;";

        String selection = null;
        if (child != null) {
            sql = String.format(sql, "AND h.c_id = " + child.getId());

            Cursor cursor = db.rawQuery(sql, null);
            VaccinationsHistoryModel item = null;
            int i = 0;

            // Parse data from DB
            while (cursor.moveToNext()) {
                item = new VaccinationsHistoryModel(child);
                result.add(item);

                // Parse data from cursor
                i = 0;
                item.setId(cursor.getLong(i));
                ++i;
                item.setChild(child);
                item.setInDate(cursor.getLong(++i));
                item.setInStatus(cursor.getInt(++i));
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
                item.setId(cursor.getLong(i));
                item.setChild(myProfile.getChild(cursor.getLong(++i)));
                item.setInDate(cursor.getLong(++i));
                item.setInStatus(cursor.getInt(++i));
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

    /**
     * Add vaccine plan for child
     *
     * @param planID        long
     * @param childID       long
     * @param vaccineID     long
     * @param injectionDate long
     * @param status        int
     * @param place         String
     * @param note          String
     * @return boolean
     */
    public boolean addVaccinePlan(long planID, long childID, long vaccineID, long injectionDate,
                                  int status, String place, String note) {
        boolean result = false;
        SQLiteDatabase db = DBService.getWritableDatabase();

        db.beginTransaction();
        try {
            // Add new item
            ContentValues values = new ContentValues();
            values.put(DBConstants.VACHIS_CHILD_ID, childID);
            values.put(DBConstants.VACHIS_VACCINE_ID, vaccineID);
            values.put(DBConstants.VACHIS_INJECTION_DATE, injectionDate);
            values.put(DBConstants.VACHIS_INJECTION_STATUS, status);
            values.put(DBConstants.VACHIS_INJECTION_PLACE, place);
            values.put(DBConstants.VACHIS_INJECTION_NOTE, note);
            long id = db.insert(DBConstants.TB_VACCINATION_HISTORY, null, values);
            result = id >= 0;

            // Update plan status
            if (result) {
                ContentValues planValues = new ContentValues();
                planValues.put(DBConstants.STATUS, DBConstants.STATUS_DELETE);
                String where = DBConstants.ID + "=" + planID;
                db.update(DBConstants.TB_VACCINATION_PLAN, planValues, where, null);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            db.endTransaction();
        }

        LogUtil.info("DBVacinations: addVaccinePlan => " + (result ? "SUCCESS" : "FAIL"));
        return result;
    }

    /**
     * Update vaccine plan for child
     *
     * @param data VaccinationsHistoryModel
     * @return boolean
     */
    public boolean updateVaccinePlan(VaccinationsHistoryModel data) {
        boolean result = false;
        SQLiteDatabase db = DBService.getWritableDatabase();

        db.beginTransaction();
        try {
            // Update item
            ContentValues values = new ContentValues();
            values.put(DBConstants.VACHIS_INJECTION_DATE, data.getInDate());
            values.put(DBConstants.VACHIS_INJECTION_STATUS, data.getInStatus());
            values.put(DBConstants.VACHIS_INJECTION_PLACE, data.getInPlace());
            values.put(DBConstants.VACHIS_INJECTION_NOTE, data.getInNote());
            values.put(DBConstants.UPDATED, Calendar.getInstance().getTimeInMillis());

            String where = DBConstants.ID + "=" + data.getId();
            int effects = db.update(DBConstants.TB_VACCINATION_HISTORY, values, where, null);
            result = effects > 0;

            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            db.endTransaction();
        }

        LogUtil.info("DBVacinations: updateVaccinePlan => " + (result ? "SUCCESS" : "FAIL"));
        return result;
    }

    /**
     * Delete vaccine plan for child
     *
     * @param data VaccinationsHistoryModel
     * @return boolean
     */
    public boolean deleteVaccinePlan(VaccinationsHistoryModel data) {
        boolean result = false;
        SQLiteDatabase db = DBService.getWritableDatabase();

        db.beginTransaction();
        try {
            // Delete plan from history table
            String where = DBConstants.ID + "=" + data.getId();
            int effects = db.delete(DBConstants.TB_VACCINATION_HISTORY, where, null);
            result = effects > 0;

            // Update plan status
            if (result) {
                ContentValues planValues = new ContentValues();
                planValues.put(DBConstants.STATUS, DBConstants.STATUS_NORMAL);
                String whereInPlan = "%s = %d AND %s = %d";
                whereInPlan = String.format(whereInPlan, DBConstants.VACPLAN_CHILD_ID, data.getChild().getId(),
                        DBConstants.VACPLAN_VACCINE_ID, data.getVaccineID());
                db.update(DBConstants.TB_VACCINATION_PLAN, planValues, whereInPlan, null);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            db.endTransaction();
        }

        LogUtil.info("DBVacinations: deleteVaccinePlan => " + (result ? "SUCCESS" : "FAIL"));
        return result;
    }
}
