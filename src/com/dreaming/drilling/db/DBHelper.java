package com.dreaming.drilling.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB操作的基础类
 * 
 * tourreport 表结构设计
 * id
 * holenumber 孔号可能重复
 * holeid  孔id
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
 * mudamount 泥浆消耗量 ，郑州意见增加该字段
 * 
 * 
 * 
 * */
public class DBHelper extends SQLiteOpenHelper 
{

	// 数据库名称
	public static final String DB_NAME = "drilling";
	public static final int VERSION = 7;

	public static final String DB_CREATE_TOURREPORT = "CREATE TABLE IF NOT EXISTS  " +
			"tourreport ( _id INTEGEREGER PRIMARY KEY, tourreportid integer, holeid varchar(32), holenumber varchar(32),administrator VARCHAR(32),recorder varchar(32)," +
			" projectmanager varchar(32),tourleader varchar(32), tourdate date,starttime varchar(10) ,endtime varchar(10)," +
			" tourshift double, tourcore double, status integer,lastdeep double,currentdeep double, tourdrillingtime varchar(32)," +
			" tourauxiliarytime varchar(32),holeaccidenttime varchar(32),deviceaccidenttime varchar(32), othertime varchar(32),totaltime varchar(32)," +
			" takeoverremark varchar(200),instrumenttakeover varchar(100),centralizer float,antideviation varchar(32), syncflag integer);";
	
	public static final String DB_CREATE_WORKCONTENT = "create table if not exists workcontent(" +
			" _id integereager primary key, id integer ,tourreportid integer, content varchar(30), starttime varchar(10), endtime varchar(10)," +
			" upmore float,corename varchar(32),coregrade varchar(10),corenumber varchar(32),corelength float, coreleftlength float," + 
			" drillinglength float,drillbit varchar(20), rotatespeed float,pumpquantity float,pumppressure float,holedeep float,mudamount varchar(10));";
	
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
		paramSQLiteDatabase.execSQL(DB_CREATE_TOURREPORT);
		paramSQLiteDatabase.execSQL(DB_CREATE_WORKCONTENT);
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,int paramInt2) 
	{
		paramSQLiteDatabase.execSQL(DB_DROP_WORKCONTENT);
		paramSQLiteDatabase.execSQL(DB_DROP_TOURREPORT);
		onCreate(paramSQLiteDatabase);
	}
	
	
}
