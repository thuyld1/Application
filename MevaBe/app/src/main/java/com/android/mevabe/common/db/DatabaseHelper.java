package com.android.mevabe.common.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.mevabe.common.AppConfig;
import com.android.mevabe.common.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.android.mevabe.common.AppConfig.DB_NAME;
import static com.android.mevabe.common.AppConfig.DB_PATH;

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

        super(context, DB_NAME, null, 1);
        this.mHelperContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        LogUtil.debug("dbExist : " + dbExist);

        if (dbExist) {
            // do nothing - database already exist
//            this.getWritableDatabase();
        } else {

            // By calling this method and empty database will be created
            // into the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
//            this.getReadableDatabase();

            copyDataBase();
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
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null,
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
     */
    public void copyDataBase() throws IOException {
        LogUtil.debug("Copying database");

        // Path to the just created empty db
        File out = new File(DB_PATH);
        out.mkdirs();
        out.delete();

        // Open your local db as the input stream
        InputStream myInput = mHelperContext.getAssets().open(DB_NAME);

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(DB_PATH);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1) {
            myOutput.write(buffer, 0, length);
        }
        LogUtil.debug("Database copied successfully");

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

        LogUtil.debug("DB exist: " + checkDataBase());

    }

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
        LogUtil.debug("CREATE DB SQL: ");
        for (String sql : sqls) {
            LogUtil.debug(sql);
            db.execSQL(sql + ";");
        }
    }

    public void openDataBase() throws SQLException {
        close();

        // Open the database
        mDatabase = SQLiteDatabase.openDatabase(DB_PATH, null,
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
//        try {

//            copyDataBase();
//            executeSQL(db);
//        } catch (Exception e) {
//            Log.e(AppConfig.LOG_TAG, e.getLocalizedMessage());
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}