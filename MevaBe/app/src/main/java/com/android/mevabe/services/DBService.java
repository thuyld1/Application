package com.android.mevabe.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.mevabe.common.AppConfig;
import com.android.mevabe.model.ProfileChildModel;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

public class DBService {
    private DatabaseHelper mDatabaseHelper;

    /********************* TABLE CHILDREN ***********************/
    /************************************************************/
    /**
     * Add child to DB
     *
     * @param myPro Profile
     * @param child ProfileChildModel
     */
    public void addChild(Profile myPro, ProfileChildModel child) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            // Add new item
            ContentValues values = new ContentValues();
            values.put(DBConstants.CHILD_PARENT_ID, myPro.getId());
            values.put(DBConstants.CHILD_ID, 0);
            values.put(DBConstants.CHILD_NAME, child.getName());
            values.put(DBConstants.CHILD_BIRTH, child.getDateOfBirth());
            values.put(DBConstants.CHILD_GENDER, child.getGender());
            values.put(DBConstants.CHILD_UPDATED, 0);
            db.insert(DBConstants.TB_CHILDREN, null, values);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(AppConfig.LOG_TAG, e.getMessage());
            e.printStackTrace();
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
        Log.i(AppConfig.LOG_TAG, "DB: getChildren: parentID = " + parentID);
        List<ProfileChildModel> result = new ArrayList<>();
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        String selection = DBConstants.CHILD_PARENT_ID + "=" + parentID;
        Cursor cursor = db.query(DBConstants.TB_CHILDREN,
                new String[]{DBConstants.ID, DBConstants.CHILD_NAME, DBConstants.CHILD_BIRTH, DBConstants
                        .CHILD_GENDER}, selection, null, null,
                null, null);

        // Parse data from DB
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
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
     * @param id int
     */
    public void deleteChild(int id) {
        String whereClause = DBConstants.ID + "=" + id;
        mDatabaseHelper.getWritableDatabase().delete(DBConstants.TB_CHILDREN,
                whereClause, null);
    }


    /********************************************** DB WORKING *********************************************/
    /*******************************************************************************************************/

    /**
     * Constructor
     *
     * @param context Context
     */
    public DBService(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
        try {
            mDatabaseHelper.createDataBase();
            mDatabaseHelper.openDataBase();
        } catch (Exception e) {
            // Log.e(AppConfig.LOG_TAG, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /**
     * Close DB connection
     */
    public void closeDB() {
        if (mDatabaseHelper != null) {
            mDatabaseHelper.close();
        }
    }


}
