package com.bjj.bjjdiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BjjDiaryDBHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "BjjDiary.db";
	public static final int DATABASE_VERSION = 2;
	
	public static final String KEY_ID = "_id";
	
	public static final String TABLE_EXERCISE = "EXERCISE";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";
	
	public static final String DATABASE_CREATE = "create table " +
			TABLE_EXERCISE + " (" + KEY_ID + 
			" integer primary key autoincrement, " +
			FIELD_NAME + " text," +
			FIELD_DESCRIPTION + " text);";
			
	
	public BjjDiaryDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	  db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
		onCreate(db);
	}

}
