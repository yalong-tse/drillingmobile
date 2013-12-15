package com.dreaming.drilling.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB操作的基础类
 * 大盘信息存储到SQLITE DB 中
 * */
public class DBHelper extends SQLiteOpenHelper 
{

	public static final String DB_NAME = "drilling";
	public static final int VERSION = 2;

	public static final String DB_CREATE = "CREATE TABLE IF NOT EXISTS  tourreport ( _id INTEGEREGER PRIMARY KEY, id INTEGER,name VARCHAR(32),last DOUBLE,buy DOUBLE,sell DOUBLE ,high DOUBLE,low DOUBLE,vol DOUBLE,kind INTEGER,time VARCHAR(32),state INTEGER)";
	public static final String DB_DROP = "DROP TABLE IF EXISTS tourreport";
	public static final String BTC_TABLE_NAME = "tourreport";
	private static DBHelper mInstance;

	private DBHelper(Context paramContext) 
	{
		super(paramContext, DB_NAME, null, VERSION);
	}

	public static DBHelper getInstance(Context paramContext) 
	{
		try 
		{
			if (mInstance == null)
				mInstance = new DBHelper(paramContext);
			return mInstance;
		} 
		finally 
		{
			// localObject = finally;
			// throw localObject;
		}
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) 
	{
		paramSQLiteDatabase.execSQL(DB_CREATE);
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,int paramInt2) 
	{
		paramSQLiteDatabase.execSQL(DB_DROP);
		onCreate(paramSQLiteDatabase);
	}
}
