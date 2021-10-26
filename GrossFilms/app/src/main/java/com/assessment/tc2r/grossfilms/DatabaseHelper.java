package com.assessment.tc2r.grossfilms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tc2r on 6/11/2017.
 * <p>
 * Description: I decided I wanted to store the information from the
 * movies in a SQLite Table. Because Why Not.
 *
 * Alternatively I might also store it in a Hosted MySQL Database.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "GROSSMOVIES.DB";
	public static final String TABLE_NAME = "MOVIES_TABLE";
	public static final String COL_1 = "RANK";
	public static final String COL_2 = "TITLE";
	public static final String COL_3 = "DIST";
	public static final String COL_4 = "GROSS";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create a table :D
		db.execSQL("create table "+ TABLE_NAME + " (RANK INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DIST TEXT, GROSS INTEGER)");

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1) {
		//  (ノ^_^)ノ┻━┻ ┬─┬ ノ( ^_^ノ)
		db.execSQL("DROP table IF EXIST "+ TABLE_NAME);
		// recreate table!
		onCreate(db);
	}


	public boolean insertData(String title, String dist, String gross) {
		// open our database to add content values!
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_2, title);
		contentValues.put(COL_3, dist);
		contentValues.put(COL_4, gross);

		// If successful return true.
		long result = db.insert(TABLE_NAME, null, contentValues);
		db.close();
		if (result == -1) {
			// There was an error.
			return false;
		}else{
			return true;
		}

	}


	public Cursor getAllData(){
		// Open our database and get the information contained within.
		SQLiteDatabase db = this.getWritableDatabase();

		// Select all data from @TABLE_NAME.
		Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);

		return result;

	}


	public boolean updateRow(String rank, String title, String dist, String gross) {
		// open our database to replace a row within the table!

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(COL_1, rank);
		contentValues.put(COL_2, title);
		contentValues.put(COL_3, dist);
		contentValues.put(COL_4, gross);
		db.update(TABLE_NAME, contentValues, "RANK = ?",new String[] { rank });
		db.close();
		return true;
	}

	public long getCount(){
		SQLiteDatabase db = this.getReadableDatabase();
		Long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
		db.close();
		return count;
	}


	public void dropTable() {
		//  (ノ^_^)ノ┻━┻
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// recreate table!
		onCreate(db);

	}


}
