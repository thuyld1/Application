package com.android.mevabe.services.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.model.ProfileChildModel;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * DBProfile is working on children table
 */
public class DBProfile {
    /**
     * Add child to DB
     *
     * @param myPro Profile
     * @param child ProfileChildModel
     * @return add child result
     */
    public boolean addChild(Profile myPro, ProfileChildModel child) {
        boolean result = true;
        SQLiteDatabase db = DBService.getWritableDatabase();

        db.beginTransaction();
        try {
            // Add new item
            ContentValues values = new ContentValues();
            values.put(DBConstants.CHILD_PARENT_ID, myPro.getId());
            values.put(DBConstants.CHILD_ID, 0);
            values.put(DBConstants.CHILD_NAME, child.getName());
            values.put(DBConstants.CHILD_BIRTH, child.getDateOfBirth());
            values.put(DBConstants.CHILD_GENDER, child.getGender());
            values.put(DBConstants.UPDATED, Calendar.getInstance().getTimeInMillis());
            long id = db.insert(DBConstants.TB_CHILDREN, null, values);

            // Add vaccinations plan for new child
            if (id >= 0) {
                addVaccinationsPlan(id);
            } else {
                result = false;
            }

            db.setTransactionSuccessful();
            child.setId(id);
        } catch (Exception e) {
            LogUtil.error(e);
            result = false;
        } finally {
            db.endTransaction();
        }

        return result;
    }

    /**
     * Add vaccinations plan for new child
     *
     * @param childID long
     */
    private void addVaccinationsPlan(long childID) {
        // Get list vaccines available
        LogUtil.debug("DBProfile: insertVaccinationsPlan() => childID = " + childID);
        SQLiteDatabase db = DBService.getReadableDatabase();
        String selection = DBConstants.STATUS + "=" + DBConstants.STATUS_NORMAL;
        Cursor cursor = db.query(DBConstants.TB_VACCINES,
                new String[]{DBConstants.ID}, selection, null, null,
                null, null);

        // Parse data from DB
        SQLiteDatabase dbWriter = DBService.getWritableDatabase();
        while (cursor.moveToNext()) {
            long vaccineID = cursor.getLong(0);

            // Add new item
            ContentValues values = new ContentValues();
            values.put(DBConstants.VACPLAN_CHILD_ID, childID);
            values.put(DBConstants.VACPLAN_VACCINE_ID, vaccineID);
            dbWriter.insert(DBConstants.TB_VACCINATION_PLAN, null, values);
        }
        cursor.close();
    }


    /**
     * Update child to DB
     *
     * @param child ProfileChildModel
     */
    public void updateChild(ProfileChildModel child) {
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
     * Get list child of parent
     *
     * @param parentID int
     * @return List<ProfileChildModel>
     */
    public List<ProfileChildModel> getMyChildren(String parentID) {
        LogUtil.debug("DBProfile: getChildren() => parentID = " + parentID);
        List<ProfileChildModel> result = new ArrayList<>();
        SQLiteDatabase db = DBService.getReadableDatabase();

        String selectionFormat = "%s = %s AND %s = %d";
        String selection = String.format(selectionFormat, DBConstants.CHILD_PARENT_ID, parentID, DBConstants.STATUS, DBConstants.STATUS_NORMAL);
        Cursor cursor = db.query(DBConstants.TB_CHILDREN,
                new String[]{DBConstants.ID, DBConstants.CHILD_NAME, DBConstants.CHILD_BIRTH, DBConstants
                        .CHILD_GENDER}, selection, null, null,
                null, null);

        // Parse data from DB
        ProfileChildModel child = null;
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            long birth = cursor.getLong(2);
            int gender = cursor.getInt(3);

            child = new ProfileChildModel(id, name, birth, gender);
            result.add(child);
        }
        cursor.close();

        return result;
    }


    /**
     * Delete child item by ID
     *
     * @param id long
     */
    public void deleteChild(long id) {
        LogUtil.debug("DBProfile: deleteChild() => childID = " + id);
        SQLiteDatabase db = DBService.getWritableDatabase();

        db.beginTransaction();
        try {
            // Delete child item
            ContentValues values = new ContentValues();
            values.put(DBConstants.STATUS, DBConstants.STATUS_DELETE);

            String whereClause = DBConstants.ID + "=" + id;
            db.update(DBConstants.TB_CHILDREN, values, whereClause, null);

            // Delete vaccinations information of child
            String vacPlanWhere = DBConstants.VACPLAN_CHILD_ID + "=" + id;
            db.delete(DBConstants.TB_VACCINATION_PLAN, vacPlanWhere, null);

            String vacHistoryWhere = DBConstants.VACHIS_CHILD_ID + "=" + id;
            db.update(DBConstants.TB_VACCINATION_HISTORY, values, vacHistoryWhere, null);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            db.endTransaction();
        }
    }
}
