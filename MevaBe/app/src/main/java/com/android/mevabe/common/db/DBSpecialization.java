package com.android.mevabe.common.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.model.Specialization;
import com.android.mevabe.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * DBSpecialization is working on filter_specialization table
 */
public class DBSpecialization {

    /**
     * Get list provinces
     *
     * @param selectedItems Set<String>
     * @return List<Specialization>
     */
    public List<Specialization> getSpecializations(Set<String> selectedItems) {
        LogUtil.debug("DBSpecialization: getSpecializations()");
        List<Specialization> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        Cursor cursor = db.query(DBConstants.TB_SPECIALIZATION,
                new String[]{DBConstants.SPEC_CODE, DBConstants.SPEC_TITLE}, null, null, null,
                null, DBConstants.SPEC_ORDER);

        // Parse data from DB
        Specialization row = null;
        // Check to set selected items
        if (selectedItems != null && selectedItems.size() > 0) {
            while (cursor.moveToNext()) {
                long code = cursor.getLong(0);
                String title = cursor.getString(1);
                row = new Specialization(code, title);
                row.setSelected(selectedItems.contains(String.valueOf(code)));
                result.add(row);
            }
        } else {
            while (cursor.moveToNext()) {
                long code = cursor.getLong(0);
                String title = cursor.getString(1);
                row = new Specialization(code, title);
                result.add(row);
            }
        }
        cursor.close();

        return result;
    }

}
