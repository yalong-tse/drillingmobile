package com.dreaming.drilling.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB操作的基础类
 * 大盘信息存储到SQLITE DB 中
 * 
 * tourreport 表结构设计
 * id
 * holenumber
 * administrator 机长
 * recorder 记录员
 * projectmanager 项目经理
 * tourleader 本班班长
 * 
 * tourdate 本班日期
 * starttime  开始时间
 * endtime    结束时间
 * tourshift 本班进尺
 * tourcore  本班取心
 * 
 * status 状态
 * lastdeep  上一班孔深
 * currentdeep 当前孔深
 * 
 * tourdrillingtime  纯钻时长
 * tourauxiliarytime 辅助时长
 * holeaccidenttime 孔内事故时长
 * deviceaccidenttime 设备事故时长
 * othertime  其他时长
 * totaltime  合计时长
 * takeoverremark 交接说明
 * intrumenttakeover 钻具交接
 * centralizer 扶正器长
 * antideviation 防斜措施
 * *********************************************************************
 * workcontent 工作内容表设计
 * id
 * tourreportid
 * content 工作内容
 * starttime
 * endtime
 * 
 * upmore  上余
 * corename  岩心名称
 * coregrade   岩心等级
 * corenumber   岩心编号
 * corelength   取心长度
 * coreleftlength  残留岩心长度
 * drillinglength 钻进
 * drillbit  钻头规格
 * rotatespeed 转速
 * pumpquantity  泵量
 * pumppressure  泵压
 * holedeep  孔深
 * 
 * 
 * 
 * */
public class DBHelper extends SQLiteOpenHelper 
{

	public static final String DB_NAME = "drilling";
	public static final int VERSION = 2;

	public static final String DB_CREATE_TOURREPORT = "CREATE TABLE IF NOT EXISTS  tourreport ( _id INTEGEREGER PRIMARY KEY, id INTEGER,name VARCHAR(32),last DOUBLE,buy DOUBLE,sell DOUBLE ,high DOUBLE,low DOUBLE,vol DOUBLE,kind INTEGER,time VARCHAR(32),state INTEGER)";
	public static final String DB_CREATE_WORKCONTENT = "";
	public static final String DB_DROP_TOURREPORT = "DROP TABLE IF EXISTS tourreport";
	public static final String DB_DROP_WORKCONTENT = "Drop table if exists workcontent";
	public static final String TOURREPORT_TABLE_NAME = "tourreport";
	public static final String WORKCONTENT_TABLE_NAME = "workcontent";
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
		//paramSQLiteDatabase.execSQL();
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,int paramInt2) 
	{
		//paramSQLiteDatabase.execSQL(DB_DROP);
		onCreate(paramSQLiteDatabase);
	}
}
