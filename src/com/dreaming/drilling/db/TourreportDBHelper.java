package com.dreaming.drilling.db;

import java.math.BigDecimal;
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
			"othertime","holeaccidenttime","deviceaccidenttime","totaltime",
			"administrator","recorder","projectmanager","tourleader","status",
			"takeoverremark","instrumenttakeover","centralizer","antideviation","syncflag"};
	
	
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
		
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, this.columns,null, null, null, null, "tourreportid desc");
		
		while (cursor.moveToNext())
		{
			EntityTourreport entity = CursorToEntity(cursor);
			result.add(entity);
		}
		cursor.close();
		return result;
	}
	
	
	/**
	 * 分页获取班报，每页获取5条即可。
	 * */
	public List<EntityTourreport> getPagedTourreports(long currentpage, long pagesize)
	{
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		List<EntityTourreport> result = new ArrayList<EntityTourreport>();
		
		
		String sql = "select * from tourreport order by tourreportid desc";
		Cursor cursor = getSQLListPaged(sql, null, currentpage, pagesize);
		
		//Cursor cursor = getCursorListPaged(DBHelper.TOURREPORT_TABLE_NAME, this.columns, null, null, "tourreportid desc", currentpage, pagesize);
		
		while (cursor.moveToNext())
		{
			EntityTourreport entity = CursorToEntity(cursor);
			result.add(entity);
		}
		//Log.d("test", "22222222222222");
		cursor.close();
		return result;
	}
	
	
	/**
	 * 获取所有的未同步的班报
	 * */
	public List<EntityTourreport> getAllTourreportsNoSync()
	{
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		
		List<EntityTourreport> result = new ArrayList<EntityTourreport>();
		
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, this.columns,"syncflag=?", new String[]{"0"}, null, null, "tourreportid desc");
		
		Log.d("test", "111111111111111");
		while (cursor.moveToNext())
		{
			EntityTourreport entity = CursorToEntity(cursor);
			result.add(entity);
		}
		Log.d("test", "22222222222222");
		cursor.close();
		return result;
	}
	
	
	
	/**
	 * 获取最新的钻孔深度
	 * */
	public float getLastHoleDeep()
	{
		float result = 0;
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, this.columns,null, null, null, null, "tourreportid desc");
		//Log.d("test", "111111111111111");
		if (cursor.moveToNext())
		{
			EntityTourreport entity = CursorToEntity(cursor);
			result = entity.getCurrentdeep().floatValue();
		}
		
		cursor.close();
		return result;
		
	}
	
	/**
	 * 保存班报的方法
	 * 
	 * id 如何规定？  遗留一个问题
	 * 
	 * id 采用时间戳定义，方法返回该班报的ID，方便后续使用
	 * */
	public Long save(EntityTourreport entity)
	{
		SQLiteDatabase db= this.dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		Long result = System.currentTimeMillis();
		cv.put("tourreportid", result);
		cv.put("holenumber", entity.getHolenumber());
		cv.put("holeid", entity.getHoleid());
		cv.put("endtime", entity.getEndtime());
		cv.put("starttime", entity.getStarttime());
		cv.put("tourdate", entity.getTourdate());
		cv.put("administrator", entity.getAdministrator());
		cv.put("recorder", entity.getRecorder());
		cv.put("projectmanager", entity.getProjectmanager());
		cv.put("tourleader", entity.getTourleader());
		
		if(entity.getTourshift()!=null)
			cv.put("tourshift", entity.getTourshift().toString());
		
		if(entity.getTourcore()!=null)
			cv.put("tourcore", entity.getTourcore().toString());
		
		cv.put("status", entity.getStatus());
		if(entity.getLastdeep()!=null)
			cv.put("lastdeep", entity.getLastdeep().toString());
		if(entity.getCurrentdeep()!=null)
			cv.put("currentdeep", entity.getCurrentdeep().toString());
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
		cv.put("syncflag", 0); // 同步标识，0标识未与云端同步；1表示已经于云端同步
		
		db.insert(this.dbHelper.TOURREPORT_TABLE_NAME, null, cv);
		
		return result;
	}
	
	
	/**
	 * 
	 * 判断是否已经存在该班报
	 * 
	 * */
	public boolean findIsExists(String tourdate, String starttime,String endtime)
	{
		boolean result = false;
	
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, new String[]{"tourreportid","holenumber"}, 
				"tourdate=strftime('%Y-%m-%d',?) and starttime=? and endtime=?", new String[]{tourdate,starttime,endtime},
				null, null,null);
		
		if ((cursor != null) && (cursor.getCount() > 0))
		{
			result = true;
		}
		cursor.close();
		
		return result;
	}
	
	
	/**
	 * 
	 * 根据 tourdate, starttime, endtime
	 * 查询出已经存在的班报列表
	 * @param String tourdate
	 * @param String starttime
	 * @param String endtime
	 * @return List<EntityTourreport>
	 * 
	 * */
	public List<EntityTourreport> getAllTourreportsByTourdateAndTime(String tourdate, String starttime,String endtime)
	{
	
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		
		List<EntityTourreport> result = new ArrayList<EntityTourreport>();
		
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, this.columns,
				"tourdate=strftime('%Y-%m-%d',?) and starttime=strftime('%H:%M',?) and endtime=strftime('%H:%M',?)", new String[]{tourdate,starttime,endtime},
				null, null,"tourreportid desc");
		
		while (cursor.moveToNext())
		{
			EntityTourreport entity = CursorToEntity(cursor);
			result.add(entity);
		}
		
		cursor.close();
		
		return result;
		
	}
	
	
	/**
	 * 根据id 获取相应的班报
	 * 
	 * @param String tourreportid
	 * @return EntityTourreport entity
	 * 
	 * */
	public EntityTourreport getTourreportById(String tourreportid)
	{
	
		SQLiteDatabase db= this.dbHelper.getReadableDatabase();
		
		EntityTourreport result = null;
		
		Cursor cursor = db.query(DBHelper.TOURREPORT_TABLE_NAME, this.columns,
				"tourreportid=?", new String[]{tourreportid},
				null, null,null);
		
		while (cursor.moveToNext())
		{
			result = CursorToEntity(cursor);
		}
		
		cursor.close();
		
		return result;
		
	}

	/**
	 * 处理 从 Cursor 到  Entity 的转换问题
	 * */
	private EntityTourreport CursorToEntity(Cursor cursor)
	{
		
		EntityTourreport entity = new EntityTourreport();
		
		entity.setId(cursor.getString(cursor.getColumnIndex("tourreportid")));
		entity.setHolenumber(cursor.getString(cursor.getColumnIndex("holenumber")));
		entity.setTourdate(cursor.getString(cursor.getColumnIndex("tourdate")));
		entity.setStarttime(cursor.getString(cursor.getColumnIndex("starttime")));
		entity.setEndtime(cursor.getString(cursor.getColumnIndex("endtime")));
		
		if(cursor.getString(cursor.getColumnIndex("tourshift"))!=null && !cursor.getString(cursor.getColumnIndex("tourshift")).equalsIgnoreCase(""))
			entity.setTourshift(new BigDecimal(cursor.getString(cursor.getColumnIndex("tourshift"))));
		
		if(cursor.getString(cursor.getColumnIndex("tourcore"))!=null && !cursor.getString(cursor.getColumnIndex("tourcore")).equalsIgnoreCase(""))
			entity.setTourcore(new BigDecimal(cursor.getString(cursor.getColumnIndex("tourcore"))));
		
		if(cursor.getString(cursor.getColumnIndex("lastdeep")) !=null && !cursor.getString(cursor.getColumnIndex("lastdeep")).equalsIgnoreCase(""))
			entity.setLastdeep(new BigDecimal(cursor.getString(cursor.getColumnIndex("lastdeep"))));
		
		if(cursor.getString(cursor.getColumnIndex("currentdeep"))!=null && !cursor.getString(cursor.getColumnIndex("currentdeep")).equalsIgnoreCase(""))
			entity.setCurrentdeep(new BigDecimal(cursor.getString(cursor.getColumnIndex("currentdeep"))));
		
		entity.setTourdrillingtime(cursor.getString(cursor.getColumnIndex("tourdrillingtime")));
		entity.setTourauxiliarytime(cursor.getString(cursor.getColumnIndex("tourauxiliarytime")));
		entity.setHoleaccidenttime(cursor.getString(cursor.getColumnIndex("holeaccidenttime")));
		entity.setOthertime(cursor.getString(cursor.getColumnIndex("othertime")));
		entity.setDeviceaccidenttime(cursor.getString(cursor.getColumnIndex("deviceaccidenttime")));
		entity.setTotaltime(cursor.getString(cursor.getColumnIndex("totaltime")));
		
		
		entity.setAdministrator(cursor.getString(cursor.getColumnIndex("administrator")));
		entity.setRecorder(cursor.getString(cursor.getColumnIndex("recorder")));
		entity.setProjectmanager(cursor.getString(cursor.getColumnIndex("projectmanager")));
		entity.setTourleader(cursor.getString(cursor.getColumnIndex("tourleader")));
		entity.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
		entity.setTakeoverremark(cursor.getString(cursor.getColumnIndex("takeoverremark")));
		entity.setInstrumenttakeover(cursor.getString(cursor.getColumnIndex("instrumenttakeover")));
		entity.setCentralizer(cursor.getString(cursor.getColumnIndex("centralizer")));
		entity.setAntideviation(cursor.getString(cursor.getColumnIndex("antideviation")));
		if(cursor.getString(cursor.getColumnIndex("syncflag")).equalsIgnoreCase("1"))
		{
			entity.setSyncflag(1);
		}
		else
		{
			entity.setSyncflag(0);
		}
		
		return entity;
	}
	
	/**
	 * 
	 * 分页的基本方法
	 * 基于SQL语句的分页
	 * */
	public Cursor getSQLListPaged(String sql, String[] args, long currentpage,long pagesize) 
	{
		long first_result = (currentpage -1)* pagesize;
		long max_result = currentpage * pagesize;
		
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		StringBuilder sb = new StringBuilder(120);
		sb.append(sql);
		sb.append(" limit ");
		sb.append(first_result);
		sb.append(",");
		sb.append(max_result);
		
		return db.rawQuery(sb.toString(), args);
	}

	/**
	 * 更新同步标识的方法
	 * */
	public int updateSyncflag(String tourreportid,int flag)
	{
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("syncflag", flag);
		
		String[] args = {tourreportid};
		
		return db.update(DBHelper.TOURREPORT_TABLE_NAME, cv, "tourreportid=?", args);
		
	}
	
	
	/**
	 * 另外一种形式的分页
	 * */
/*	public Cursor getCursorListPaged(String tablename, String[] columns,String sql,String[] args, String orderby,long currentpage,long pagesize) 
	{
		
		long first_result = (currentpage -1)* pagesize;
		long max_result = currentpage * pagesize;
		
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		StringBuilder sb = new StringBuilder(120);
		if(sql !=null)
			sb.append(sql);
		else
			sb.append("1=1");
		sb.append(" limit ");
		sb.append(first_result);
		sb.append(",");
		sb.append(max_result);
		return  db.query(tablename, columns,sb.toString(),args,
				null, null,orderby);
	}*/
	
	
	
}
