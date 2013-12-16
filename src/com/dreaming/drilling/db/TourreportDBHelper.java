package com.dreaming.drilling.db;

import java.util.ArrayList;
import java.util.List;

import com.dreaming.drilling.bean.EntityTourreport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 班报的数据库操作类
 * */
public class TourreportDBHelper extends DBOperation {

	private String[] columns = {"id","holenumber","tourdate",
			"starttime","endtime","tourshift","tourcore","lastdeep",
			"currentdeep","tourdrillingtime","tourauxiliarytime"};
	
	
	public TourreportDBHelper(Context context) {
		super(context);
	}

	/**
	 * 获取所有的班报
	 * */
	public List<EntityTourreport> getAllTourreports()
	{
		SQLiteDatabase  db= this.dbHelper.getReadableDatabase();
		
		List<EntityTourreport> result = new ArrayList<EntityTourreport>();
		
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, this.columns,null, null, null, null, "id asc");
		
		while (cursor.moveToNext())
		{
			EntityTourreport entity = new EntityTourreport();
			
			
		}
		
		
		return result;
		
	}
	
}
