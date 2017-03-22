package com.android.mevabe.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.mevabe.common.utils.LogUtil;

public class DBService {
    private static DBService singleton;
    private DatabaseHelper mDatabaseHelper;

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
            LogUtil.error(e);
        }

        singleton = this;
    }


    /*********************************** DB WORKING ********************************/
    /*******************************************************************************/
    public static SQLiteDatabase getReadableDatabase() {
        return singleton.mDatabaseHelper.getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDatabase() {
        return singleton.mDatabaseHelper.getWritableDatabase();
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
