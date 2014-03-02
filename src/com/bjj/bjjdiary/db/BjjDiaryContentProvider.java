package com.bjj.bjjdiary.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class BjjDiaryContentProvider extends ContentProvider{
      
  public static final Uri EXERCISE_URI = Uri.parse("content://com.bjj/exercises");
  
  private static final int EXERCISES_ALLROWS = 100;
  private static final int EXERCISES_SINGLEROW = 200;
  
  private static final UriMatcher uriMatcher;
  
  static{
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI("com.bjj", "exercises", EXERCISES_ALLROWS);
    uriMatcher.addURI("com.bjj", "exercises/#", EXERCISES_SINGLEROW);
  }
  
  private BjjDiaryDBHelper dbHelper; 

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    
    switch(uriMatcher.match(uri)){
    case EXERCISES_SINGLEROW:
      String rowId = uri.getPathSegments().get(1);
      selection = BjjDiaryDBHelper.KEY_ID + "=" + rowId 
          + (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : "");
      default: break;
    }
    
    if(selection == null)
      selection = "1";
    
    int deleteCount = db.delete(BjjDiaryDBHelper.TABLE_EXERCISE, selection, selectionArgs);
    
    getContext().getContentResolver().notifyChange(uri, null);
    
    return deleteCount;
  }

  @Override
  public String getType(Uri uri) {
    switch(uriMatcher.match(uri)){
      case EXERCISES_ALLROWS:
      case EXERCISES_SINGLEROW:
        break;
      default: throw new IllegalArgumentException("Unsupported URI: " + uri);
    }
    
    if(uriMatcher.match(uri) < 200){
       return "vnd.android.cursor.dir/vnd.paad.elemental";
    }
    else{
      return "vnd.android.cursor.item/vnd.paad.elemental";
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    
    String nullColumnHack = null;
    long id = -1;
    
    switch(uriMatcher.match(uri)){
      case EXERCISES_ALLROWS:{
        id = db.insert(BjjDiaryDBHelper.TABLE_EXERCISE, nullColumnHack, values);
      }
      default: break;
    }
    
    if(id > -1){
      Uri insertedId = ContentUris.withAppendedId(EXERCISE_URI, id);
      
      getContext().getContentResolver().notifyChange(insertedId, null);
      
      return insertedId;
    }
    else{
      return null;
    }
  }

  @Override
  public boolean onCreate() {
    dbHelper = new BjjDiaryDBHelper(getContext(),BjjDiaryDBHelper.DATABASE_NAME, null, BjjDiaryDBHelper.DATABASE_VERSION);
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        
    switch(uriMatcher.match(uri)){
      case EXERCISES_ALLROWS:{
        queryBuilder.setTables(BjjDiaryDBHelper.TABLE_EXERCISE);
        break;
      }
      case EXERCISES_SINGLEROW:{
        Log.w("CONTENT_PROVIDER","Looking for single row");
        queryBuilder.setTables(BjjDiaryDBHelper.TABLE_EXERCISE);
        String rowId = uri.getPathSegments().get(1);
        queryBuilder.appendWhere(BjjDiaryDBHelper.KEY_ID + "=" + rowId);
        break;
      }
      default: break;
    }
    
    Cursor cursor = queryBuilder.query(db,  projection,  selection,  selectionArgs, null, null, sortOrder);
    return cursor;
  }

  @Override
  public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
    // TODO Auto-generated method stub
    return 0;
  }

}
