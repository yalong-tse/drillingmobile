package com.dreaming.drilling.db;

import java.util.ArrayList;
import java.util.List;

import com.dreaming.drilling.bean.Workcontent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 工作内容的保存方法
 * */
public class WorkcontentDBHelper extends DBOperation{

	private String[] columns = {"id","tourreportid","content","starttime",
			"endtime","upmore","corename","coregrade","corenumber",
			"corelength","coreleftlength","drillinglength",
			"drillbit","rotatespeed","pumpquantity","pumppressure","holedeep"};
	
	public WorkcontentDBHelper(Context context) {
		super(context);
	}
	
	

	public void save(Workcontent content)
	{
		SQLiteDatabase db= this.dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		cv.put("id", System.currentTimeMillis());
		cv.put("tourreportid", content.getTourreportid());
		cv.put("content", content.getType());
		cv.put("starttime", content.getStarttime());
		cv.put("endtime", content.getEndtime());
		cv.put("upmore", content.getUpleft());
		cv.put("corename",content.getCorename());
		cv.put("coregrade", content.getCoregrade());
		cv.put("corenumber", content.getCorenumber());
		cv.put("corelength",content.getCorelength());
		cv.put("coreleftlength", content.getCoreleftlength());
		cv.put("drillinglength", content.getDrillinglength());
		cv.put("drillbit",content.getDrillbit());
		cv.put("rotatespeed", content.getRotatespeed());
		cv.put("pumpquantity",content.getPump());
		cv.put("pumppressure", content.getPressure());
		cv.put("holedeep", content.getHoledeep());
		db.insert(this.dbHelper.WORKCONTENT_TABLE_NAME, null, cv);
		
	}
	
	public List<Workcontent> getWorkcontentByTourreportId(Long id)
	{
		
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		
		
		List<Workcontent> result = new ArrayList<Workcontent>();
		
		Cursor cursor = db.query(DBHelper.WORKCONTENT_TABLE_NAME, this.columns,
				"tourreportid=?", new String[]{id+""},
				null, null, "starttime asc");
		
		while (cursor.moveToNext())
		{
			
			Workcontent entity = new Workcontent();
			entity.setId(cursor.getLong(cursor.getColumnIndex("id")));
			entity.setTourreportid(cursor.getLong(cursor.getColumnIndex("tourreportid")));
			entity.setType(cursor.getString(cursor.getColumnIndex("content")));
			entity.setStarttime(cursor.getString(cursor.getColumnIndex("starttime")));
			entity.setEndtime(cursor.getString(cursor.getColumnIndex("endtime")));
			entity.setUpleft(cursor.getFloat(cursor.getColumnIndex("upmore")));
			entity.setCorename(cursor.getString(cursor.getColumnIndex("corename")));
			entity.setCoregrade(cursor.getString(cursor.getColumnIndex("coregrade")));
			entity.setCorenumber(cursor.getString(cursor.getColumnIndex("corenumber")));
			entity.setCorelength(cursor.getFloat(cursor.getColumnIndex("corelength")));
			entity.setCoreleftlength(cursor.getFloat(cursor.getColumnIndex("coreleftlength")));
			entity.setDrillinglength(cursor.getFloat(cursor.getColumnIndex("drillinglength")));
			entity.setDrillbit(cursor.getString(cursor.getColumnIndex("drillbit")));
			entity.setRotatespeed(cursor.getFloat(cursor.getColumnIndex("rotatespeed")));
			entity.setPump(cursor.getFloat(cursor.getColumnIndex("pumpquantity")));
			entity.setPressure(cursor.getFloat(cursor.getColumnIndex("pumppressure")));
			entity.setHoledeep(cursor.getFloat(cursor.getColumnIndex("holedeep")));
			result.add(entity);
		}
		
		cursor.close();
		
		return result;
		
		
	}
	
	
}
