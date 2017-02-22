package com.android.mevabe.services;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.mevabe.common.AppConfig;

import java.io.IOException;
import java.io.InputStream;

/**
 * DatabaseHelper Class
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mDatabase;
    private final Context mHelperContext;

    /**
     * Constructor Takes and keeps a reference of the passed context in
     * order to access to the application assets and resources.
     *
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, AppConfig.DB_NAME, null, 1);
        this.mHelperContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        Log.i(AppConfig.LOG_TAG, "dbExist : " + dbExist);

        if (dbExist) {
            // do nothing - database already exist
            this.getWritableDatabase();

        } else {

            // By calling this method and empty database will be created
            // into the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            // copyDataBase();
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(AppConfig.DB_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just
     * created empty database in the system folder, from where it can be
     * accessed and handled. This is done by transfering bytestream.
     * */
    // public void copyDataBase() throws IOException {
    // Log.i(AppConfig.LOG_TAG, "Copying database");
    //
    // // Open your local db as the input stream
    // InputStream myInput = mHelperContext.getAssets().open(DB_NAME);
    //
    // // Path to the just created empty db
    // String outFileName = DB_PATH + DB_NAME;
    // File out = new File(outFileName);
    // if (!out.exists())
    // out.createNewFile();
    // // Open the empty db as the output stream
    // OutputStream myOutput = new FileOutputStream(outFileName);
    //
    // // transfer bytes from the inputfile to the outputfile
    // byte[] buffer = new byte[1024];
    // int length;
    // while ((length = myInput.read(buffer)) != -1) {
    // myOutput.write(buffer, 0, length);
    // }
    // Log.i(AppConfig.LOG_TAG, "Database copied successfully");
    //
    // // Close the streams
    // myOutput.flush();
    // myOutput.close();
    // myInput.close();
    //
    // this.close();
    // Log.i(AppConfig.LOG_TAG, "DB exist: " + checkDataBase());
    //
    // }

    /**
     * Execute SQL command in sql file
     *
     * @param db
     * @throws IOException
     */
    public void executeSQL(SQLiteDatabase db) throws IOException {
        InputStream myInput = mHelperContext.getAssets().open(AppConfig.SCRIPT_NAME);
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int length = 0;
        String strFileContents;
        while ((length = myInput.read(buffer)) != -1) {
            strFileContents = new String(buffer, 0, length);
            sb.append(strFileContents);

        }

        String[] sqls = sb.toString().split("END");
        Log.i(AppConfig.LOG_TAG, "CREATE DB SQL: ");
        for (String sql : sqls) {
            Log.i(AppConfig.LOG_TAG, sql);
            db.execSQL(sql + ";");
        }
    }

    public void openDataBase() throws SQLException {
        close();

        // Open the database
        mDatabase = SQLiteDatabase.openDatabase(AppConfig.DB_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (mDatabase != null)
            mDatabase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            // copyDataBase();
            executeSQL(db);
        } catch (Exception e) {
            Log.e(AppConfig.LOG_TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}