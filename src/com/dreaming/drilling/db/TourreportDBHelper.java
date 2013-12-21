package com.dreaming.drilling.db;

import java.util.ArrayList;
import java.util.List;

import com.dreaming.drilling.bean.EntityTourreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 班报的数据库操作类
 * */
public class TourreportDBHelper extends DBOperation {

	private String[] columns = {"tourreportid","holenumber","tourdate",
			"starttime","endtime","tourshift","tourcore","lastdeep",
			"currentdeep","tourdrillingtime","tourauxiliarytime",
			"othertime","holeaccidenttime","deviceaccidenttime","totaltime"};
	
	
	public TourreportDBHelper(Context context) {
		super(context);
	}

	/**
	 * 获取所有的班报
	 * */
	public List<EntityTourreport> getAllTourreports()
	{
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		
		List<EntityTourreport> result = new ArrayList<EntityTourreport>();
		
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, this.columns,null, null, null, null, "tourreportid asc");
		
		Log.d("test", "111111111111111");
		while (cursor.moveToNext())
		{
			EntityTourreport entity = new EntityTourreport();
			
			entity.setId(cursor.getInt(cursor.getColumnIndex("tourreportid")));
			entity.setHolenumber(cursor.getString(cursor.getColumnIndex("holenumber")));
			entity.setTourdate(cursor.getString(cursor.getColumnIndex("tourdate")));
			entity.setStarttime(cursor.getString(cursor.getColumnIndex("starttime")));
			entity.setEndtime(cursor.getString(cursor.getColumnIndex("endtime")));
			entity.setTourshift(cursor.getFloat(cursor.getColumnIndex("tourshift")));
			entity.setTourcore(cursor.getFloat(cursor.getColumnIndex("tourcore")));
			entity.setLastdeep(cursor.getFloat(cursor.getColumnIndex("lastdeep")));
			entity.setCurrentdeep(cursor.getFloat(cursor.getColumnIndex("currentdeep")));
			entity.setTourdrillingtime(cursor.getString(cursor.getColumnIndex("tourdrillingtime")));
			entity.setTourauxiliarytime(cursor.getString(cursor.getColumnIndex("tourauxiliarytime")));
			entity.setHoleaccidenttime(cursor.getString(cursor.getColumnIndex("holeaccidenttime")));
			entity.setOthertime(cursor.getString(cursor.getColumnIndex("othertime")));
			entity.setDeviceaccidenttime(cursor.getString(cursor.getColumnIndex("deviceaccidenttime")));
			entity.setTotaltime(cursor.getString(cursor.getColumnIndex("totaltime")));
			
			result.add(entity);
		}
		Log.d("test", "22222222222222");
		return result;
	}
	
	
	/**
	 * 保存班报的方法
	 * 
	 * id 如何规定？  遗留一个问题
	 * */
	public void save(EntityTourreport entity)
	{
		SQLiteDatabase db= this.dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("tourreportid", System.currentTimeMillis());
		cv.put("holenumber", entity.getHolenumber());
		cv.put("starttime", entity.getStarttime());
		cv.put("endtime", entity.getEndtime());
		cv.put("tourdate", entity.getTourdate());
		cv.put("administrator", entity.getAdministrator());
		cv.put("recorder", entity.getRecorder());
		cv.put("projectmanager", entity.getProjectmanager());
		cv.put("tourleader", entity.getTourleader());
		cv.put("tourshift", entity.getTourshift());
		cv.put("tourcore", entity.getTourcore());
		cv.put("status", entity.getStatus());
		cv.put("lastdeep", entity.getLastdeep());
		cv.put("currentdeep", entity.getCurrentdeep());
		cv.put("tourdrillingtime ", entity.getTourdrillingtime());
		cv.put("tourauxiliarytime ", entity.getTourauxiliarytime());
		cv.put("holeaccidenttime", entity.getHoleaccidenttime());
		cv.put("deviceaccidenttime", entity.getDeviceaccidenttime());
		cv.put("othertime ", entity.getOthertime());
		cv.put("totaltime", entity.getTotaltime());
		cv.put("takeoverremark ", entity.getTakeoverremark());
		cv.put("instrumenttakeover", entity.getInstrumenttakeover());
		cv.put("centralizer", entity.getCentralizer());
		cv.put("antideviation",entity.getAntideviation());
		
		db.insert(this.dbHelper.TOURREPORT_TABLE_NAME, null, cv);
		
	}
	
	
	public boolean findIsExists()
	{
		boolean result = false;
		
		
		return result;
	}
	
	
	
}
