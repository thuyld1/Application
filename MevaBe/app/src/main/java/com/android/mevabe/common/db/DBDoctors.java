package com.android.mevabe.common.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.model.DoctorFavorite;
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
     * @return List<DoctorFavorite>
     */
    public List<DoctorFavorite> getFavoriteDoctors() {
        LogUtil.debug("DBDoctors: getDoctorsFavorite()");
        List<DoctorFavorite> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        Cursor cursor = db.query(DBConstants.TB_DOCTORS_FAVORITE,
                new String[]{DBConstants.DOC_FAV_CODE, DBConstants.DOC_FAV_NAME,
                        DBConstants.DOC_FAV_AVATAR, DBConstants.DOC_FAV_PHONE,
                        DBConstants.DOC_FAV_DES}, null, null, null,
                null, DBConstants.DOC_FAV_NAME);

        // Parse data from DB
        DoctorFavorite row = null;
        int i = 0;
        while (cursor.moveToNext()) {
            i = 0;
            long code = cursor.getLong(i++);
            String name = cursor.getString(i++);
            String avatar = cursor.getString(i++);
            String phone = cursor.getString(i++);
            String des = cursor.getString(i++);
            row = new DoctorFavorite(code, name, avatar, phone, des);
            result.add(row);
        }
        cursor.close();

        return result;
    }

}
