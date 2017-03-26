package com.android.mevabe.common.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.model.LocationDistrict;
import com.android.mevabe.common.model.LocationProvince;
import com.android.mevabe.common.model.ProfileChildModel;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static android.R.attr.key;
import static com.android.mevabe.common.db.DBConstants.LOC_PRO_SIMPLE;

/**
 * DBProfile is working on children table
 */
public class DBLocation {
    /**
     * Update location to DB
     *
     * @param child ProfileChildModel
     */
    public void updateLocation(ProfileChildModel child) {
        SQLiteDatabase db = DBService.getWritableDatabase();

        db.beginTransaction();
        try {
            // Update item
            ContentValues values = new ContentValues();
            values.put(DBConstants.CHILD_NAME, child.getName());
            values.put(DBConstants.CHILD_BIRTH, child.getDateOfBirth());
            values.put(DBConstants.CHILD_GENDER, child.getGender());
            values.put(DBConstants.UPDATED, Calendar.getInstance().getTimeInMillis());

            String whereClause = DBConstants.ID + " = " + child.getId();
            db.update(DBConstants.TB_CHILDREN, values, whereClause, null);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Get list provinces
     *
     * @return List<LocationProvince>
     */
    public List<LocationProvince> getProvinces(String key) {
        LogUtil.debug("DBLocation: getProvinces() key = " + key);
        List<LocationProvince> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        String selection = null;
        if (!StringUtils.isEmpty(key)) {
            String selectionFormat = "%s LIKE %s OR %s LIKE %s";
            selection = String.format(selectionFormat, DBConstants.LOC_PRO_TITLE, key, LOC_PRO_SIMPLE, key);
        }
        Cursor cursor = db.query(DBConstants.TB_LOCATION_PROVINCE,
                new String[]{DBConstants.LOC_PRO_CODE, DBConstants.LOC_PRO_TITLE}, selection, null, null,
                null, DBConstants.LOC_PRO_ORDER);

        // Parse data from DB
        LocationProvince row = null;
        while (cursor.moveToNext()) {
            long code = cursor.getLong(0);
            String title = cursor.getString(1);
            row = new LocationProvince(code, title);
            result.add(row);
        }
        cursor.close();

        return result;
    }

    /**
     * Get list districts
     *
     * @param provinceCode  long
     * @param selectedItems Set<String>
     * @return List<LocationDistrict>
     */
    public List<LocationDistrict> getDistricts(long provinceCode, Set<String> selectedItems) {
        LogUtil.debug("DBLocation: getProvinces() key = " + key);
        List<LocationDistrict> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();


        String selection = DBConstants.LOC_DIS_PCODE + "=" + provinceCode;
        Cursor cursor = db.query(DBConstants.TB_LOCATION_DISTRICT,
                new String[]{DBConstants.LOC_DIS_CODE, DBConstants.LOC_DIS_TITLE}, selection, null, null,
                null, DBConstants.LOC_DIS_TITLE);

        // Parse data from DB
        LocationDistrict row = null;

        // Check to set selected items
        if (selectedItems != null && selectedItems.size() > 0) {
            while (cursor.moveToNext()) {
                long code = cursor.getLong(0);
                String title = cursor.getString(1);
                row = new LocationDistrict(code, provinceCode, title);
                row.setSelected(selectedItems.contains(String.valueOf(code)));
                result.add(row);
            }
        } else {
            while (cursor.moveToNext()) {
                long code = cursor.getLong(0);
                String title = cursor.getString(1);
                row = new LocationDistrict(code, provinceCode, title);
                result.add(row);
            }
        }
        cursor.close();

        return result;
    }
}
