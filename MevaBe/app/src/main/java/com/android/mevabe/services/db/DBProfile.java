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
     */
    public void addChild(Profile myPro, ProfileChildModel child) {
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

            db.setTransactionSuccessful();
            child.setId(id);
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            db.endTransaction();
        }
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

        String selection = DBConstants.CHILD_PARENT_ID + "=" + parentID;
        Cursor cursor = db.query(DBConstants.TB_CHILDREN,
                new String[]{DBConstants.ID, DBConstants.CHILD_NAME, DBConstants.CHILD_BIRTH, DBConstants
                        .CHILD_GENDER}, selection, null, null,
                null, null);

        // Parse data from DB
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            long birth = cursor.getLong(2);
            int gender = cursor.getInt(3);


            ProfileChildModel child = new ProfileChildModel(id, name, birth, gender);
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
        String whereClause = DBConstants.ID + "=" + id;
        DBService.getWritableDatabase().delete(DBConstants.TB_CHILDREN,
                whereClause, null);
    }
}
