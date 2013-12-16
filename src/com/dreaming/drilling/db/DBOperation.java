package com.dreaming.drilling.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * 
 * 数据库操作的基础类
 * 
 * */
public class DBOperation {
	
	public DBHelper dbHelper;
	

	public DBOperation(Context context)
	{
		this.dbHelper = DBHelper.getInstance(context);
	}
	
	
	public SQLiteDatabase beginTransaction() 
	{
		SQLiteDatabase localSQLiteDatabase = this.dbHelper.getWritableDatabase();
		localSQLiteDatabase.beginTransaction();
		return localSQLiteDatabase;
	}

	public void clearDB() 
	{
		this.dbHelper.getWritableDatabase().delete("btc", null, null);
	}

	public void deleteTableData(String paramString) 
	{
		this.dbHelper.getWritableDatabase().delete(paramString, null, null);
	}

	public void endTransaction(SQLiteDatabase paramSQLiteDatabase) 
	{
		paramSQLiteDatabase.endTransaction();
	}
	
	
}
