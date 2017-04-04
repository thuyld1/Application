package com.android.mevabe.common.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.model.DoctorInfo;
import com.android.mevabe.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * DBLocation is working on filter_location_province and filter_location_district tables
 */
public class DBDoctors {


    /**
     * Get list favorite doctors
     *
     * @return List<DoctorInfo>
     */
    public List<DoctorInfo> getFavoriteDoctors() {
        LogUtil.debug("DBDoctors: getDoctorsFavorite()");
        List<DoctorInfo> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        Cursor cursor = db.query(DBConstants.TB_DOCTORS_FAVORITE,
                new String[]{DBConstants.DOC_FAV_CODE, DBConstants.DOC_FAV_NAME,
                        DBConstants.DOC_FAV_AVATAR, DBConstants.DOC_FAV_PHONE,
                        DBConstants.DOC_FAV_DES}, null, null, null,
                null, DBConstants.DOC_FAV_NAME);

        // Parse data from DB
        DoctorInfo row = null;
        int i = 0;
        while (cursor.moveToNext()) {
            i = 0;
            long code = cursor.getLong(i++);
            String name = cursor.getString(i++);
            String avatar = cursor.getString(i++);
            String phone = cursor.getString(i++);
            String des = cursor.getString(i++);
            row = new DoctorInfo(code, name, avatar, phone, des);
            row.setFavorite(true);
            result.add(row);
        }
        cursor.close();

        LogUtil.debug("DBDoctors: getDoctorsFavorite() size = " + result.size());
        return result;
    }


    /**
     * Save favorite info into DB
     *
     * @param info DoctorInfo
     * @return boolean
     */
    public boolean addFavorite(DoctorInfo info) {
        LogUtil.debug("DBDoctors: addFavorite() => doctor code = " + info.getCode());
        boolean result = false;
        SQLiteDatabase db = DBService.getWritableDatabase();

        db.beginTransaction();
        try {
            // Add new item
            ContentValues values = new ContentValues();
            values.put(DBConstants.DOC_FAV_CODE, info.getCode());
            values.put(DBConstants.DOC_FAV_NAME, info.getName());
            values.put(DBConstants.DOC_FAV_AVATAR, info.getAvatar());
            values.put(DBConstants.DOC_FAV_PHONE, info.getPhone());
            values.put(DBConstants.DOC_FAV_DES, info.getDes());
            result = db.insert(DBConstants.TB_DOCTORS_FAVORITE, null, values) > 0;

            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.error(e);
            result = false;
        } finally {
            db.endTransaction();
        }

        LogUtil.debug("DBDoctors: addFavorite() => result = " + result);
        return result;
    }

    /**
     * Save favorite info into DB
     *
     * @param info DoctorInfo
     * @return boolean
     */
    public boolean deleteFavorite(DoctorInfo info) {
        LogUtil.debug("DBDoctors: deleteFavorite() => doctor code = " + info.getCode());
        SQLiteDatabase db = DBService.getWritableDatabase();
        boolean result = false;

        db.beginTransaction();
        try {
            // Delete child item
            String whereClause = DBConstants.DOC_FAV_CODE + "=" + info.getCode();
            result = db.delete(DBConstants.TB_DOCTORS_FAVORITE, whereClause, null) > 0;

            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            db.endTransaction();
        }

        LogUtil.debug("DBDoctors: deleteFavorite() => result = " + result);
        return result;
    }

}
