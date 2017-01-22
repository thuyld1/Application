package com.android.mevabe.services;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vn.danduong.Constants;

public class DBService {
	private static String DB_PATH = "/data/data/com.vn.danduong/databases/";
	private static String DB_NAME = "danduong.db";
	private static String SCRIPT_NAME = "danduong.sql";

	private DatabaseHelper mDatabaseHelper;

	/********************* TABLE PRODUCT DETAILS ***********************/
	/*******************************************************************/

	/**
	 * Save product details to db
	 * 
	 * @param id
	 * @param details
	 */
	public void saveProductDetails(int id, String details) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

		db.beginTransaction();
		try {
			// delete old item
			String whereClause = DBConstants.ID + "=" + id;
			db.delete(DBConstants.TB_PRODUCT_DETAIL, whereClause, null);

			// Add new item
			ContentValues values = new ContentValues();
			values.put(DBConstants.ID, id);
			values.put(DBConstants.P_DETAILS, details);
			db.insert(DBConstants.TB_PRODUCT_DETAIL, null, values);

			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.i(Constants.TAG, e.getMessage());
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * Get product details from db
	 * 
	 * @param id
	 * @param details
	 */
	public Cursor getProductDetails(int id) {
		Log.i(Constants.TAG, "DB: getProductDetails: id = " + id);
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

		String selection = DBConstants.ID + "=" + id;
		return db.query(DBConstants.TB_PRODUCT_DETAIL,
				new String[] { DBConstants.P_DETAILS }, selection, null, null,
				null, null);
	}

	/************************* TABLE FAVORITE **************************/
	/*******************************************************************/

	/**
	 * Save favorite info
	 * 
	 * @param productId
	 * @param categoryId
	 * @param title
	 * @param address
	 * @param lat
	 * @param lon
	 */
	public long saveFavorite(int productId, int categoryId, String avatar,
			String title, String address, double lat, double lon) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		long id = 0;

		db.beginTransaction();
		try {
			// Add new item
			ContentValues values = new ContentValues();
			values.put(DBConstants.F_PRODUCT_ID, productId);
			values.put(DBConstants.F_CATEGORY_ID, categoryId);
			values.put(DBConstants.F_AVATAR, avatar);
			values.put(DBConstants.F_TITLE, title);
			values.put(DBConstants.F_ADDRESS, address);
			values.put(DBConstants.F_LAT, lat);
			values.put(DBConstants.F_LON, lon);
			id = db.insert(DBConstants.TB_FAVORITE, null, values);

			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.i(Constants.TAG, e.getMessage());
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		return id;
	}

	/**
	 * Check product exist from favorite table or not
	 * 
	 * @param id
	 *            product details id
	 */
	public Cursor isFavorite(int id) {
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

		String selection = DBConstants.F_PRODUCT_ID + "=" + id;
		return db.query(DBConstants.TB_FAVORITE,
				new String[] { DBConstants.ID }, selection, null, null, null,
				null);
	}

	/**
	 * Get favorite
	 * 
	 * @return
	 */
	public Cursor getFavorites() {
		return mDatabaseHelper.getReadableDatabase().query(
				DBConstants.TB_FAVORITE, null, null, null, null, null, null);
	}

	/**
	 * Delete favorite item
	 * 
	 * @param id
	 */
	public void deleteFavorite(long id) {
		String whereClause = DBConstants.ID + "=" + id;
		mDatabaseHelper.getWritableDatabase().delete(DBConstants.TB_FAVORITE,
				whereClause, null);
	}

	/**
	 * Delete favorite item
	 */
	public void deleteAllFavorite() {
		mDatabaseHelper.getWritableDatabase().delete(DBConstants.TB_FAVORITE,
				null, null);
	}

	/*************************** TABLE HISTORY *************************/
	/*******************************************************************/

	/**
	 * Save product details to db
	 * 
	 * @param productId
	 * @param categoryId
	 * @param details
	 *            product details string
	 * @param title
	 *            product name
	 */
	public long saveHistory(int productId, int categoryId, String details,
			String title) {
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		long id = 0;

		db.beginTransaction();
		try {
			// delete old item
			String whereClause = DBConstants.ID + "=" + productId;
			db.delete(DBConstants.TB_PRODUCT_DETAIL, whereClause, null);

			// Add new item
			ContentValues values = new ContentValues();
			values.put(DBConstants.ID, productId);
			values.put(DBConstants.P_DETAILS, details);
			db.insert(DBConstants.TB_PRODUCT_DETAIL, null, values);

			// Add new history item
			Calendar cal = Calendar.getInstance();
			cal.clear(Calendar.MILLISECOND);
			cal.clear(Calendar.SECOND);
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.HOUR);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			long time = cal.getTimeInMillis();

			// delete old item
			whereClause = DBConstants.H_PRODUCT_ID + "=" + productId;
			whereClause += " AND " + DBConstants.H_DATE + "=" + time;
			db.delete(DBConstants.TB_HISTORY, whereClause, null);

			values = new ContentValues();
			values.put(DBConstants.H_PRODUCT_ID, productId);
			values.put(DBConstants.H_CATEGORY_ID, categoryId);
			values.put(DBConstants.H_TITLE, title);
			values.put(DBConstants.H_DATE, time);
			id = db.insert(DBConstants.TB_HISTORY, null, values);

			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.i(Constants.TAG, e.getMessage());
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		return id;
	}

	/**
	 * Get list history items
	 * 
	 * @return
	 */
	public Cursor getHistory() {
		return mDatabaseHelper.getReadableDatabase().query(
				DBConstants.TB_HISTORY, null, null, null, null, null,
				DBConstants.H_DATE + " DESC");
	}

	/**
	 * Delete history item
	 * 
	 * @param id
	 */
	public void deleteHistory(long id) {
		String whereClause = DBConstants.ID + "=" + id;
		mDatabaseHelper.getWritableDatabase().delete(DBConstants.TB_HISTORY,
				whereClause, null);
	}

	/**
	 * Delete history item
	 */
	public void deleteAllHistory() {
		mDatabaseHelper.getWritableDatabase().delete(DBConstants.TB_HISTORY,
				null, null);
	}

	/********************************************** DB WORKING *********************************************/
	/*******************************************************************************************************/

	/**
	 * Constructor
	 * 
	 * @param context
	 *            Context
	 */
	public DBService(Context context) {
		mDatabaseHelper = new DatabaseHelper(context);
		try {
			mDatabaseHelper.createDataBase();
			mDatabaseHelper.openDataBase();
		} catch (Exception e) {
			// Log.e(Constants.TAG, e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * DatabaseHelper Class
	 * 
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
		 * */
		public void createDataBase() throws IOException {
			boolean dbExist = checkDataBase();
			Log.i(Constants.TAG, "dbExist : " + dbExist);

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
				String myPath = DB_PATH + DB_NAME;
				checkDB = SQLiteDatabase.openDatabase(myPath, null,
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
		// Log.i(Constants.TAG, "Copying database");
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
		// Log.i(Constants.TAG, "Database copied successfully");
		//
		// // Close the streams
		// myOutput.flush();
		// myOutput.close();
		// myInput.close();
		//
		// this.close();
		// Log.i(Constants.TAG, "DB exist: " + checkDataBase());
		//
		// }

		/**
		 * Execute SQL command in sql file
		 * 
		 * @param db
		 * @throws IOException
		 */
		public void executeSQL(SQLiteDatabase db) throws IOException {
			InputStream myInput = mHelperContext.getAssets().open(SCRIPT_NAME);
			StringBuilder sb = new StringBuilder();
			byte[] buffer = new byte[1024];
			int length = 0;
			String strFileContents;
			while ((length = myInput.read(buffer)) != -1) {
				strFileContents = new String(buffer, 0, length);
				sb.append(strFileContents);

			}

			String[] sqls = sb.toString().split("END");
			for (String sql : sqls) {
				db.execSQL(sql + ";");
			}
		}

		public void openDataBase() throws SQLException {
			close();

			// Open the database
			String myPath = DB_PATH + DB_NAME;
			mDatabase = SQLiteDatabase.openDatabase(myPath, null,
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
				Log.e(Constants.TAG, e.getLocalizedMessage());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

}
