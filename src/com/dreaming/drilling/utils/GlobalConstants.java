package com.dreaming.drilling.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.dreaming.drilling.bean.SpinnerData;
import com.dreaming.drilling.bean.Takeovercontent;
import com.dreaming.drilling.bean.Workcontent;

/**
 * drilling全局变量
 * */
public class GlobalConstants {
	
	
	// share Preference 
	public static int MODE = android.app.Activity.MODE_PRIVATE;//
	
	public static final String PREFERENCE_NAME = "drilling";// preference 文件
	
	public static final String WORKCONTENT = "com.dreaming.drilling.workcontent.parcel";
	
	public static final String TOURREPORTID = "tourreport_id";
	
	// 工作内容
	public static List<Workcontent> list_workcontents = new ArrayList<Workcontent>();
	
	public static Workcontent the_workcontent = null;
	
	// 班报的开始时间，结束时间，以及班报日期
	public static String tour_starttime=null;
	public static String tour_endtime =null;
	public static Calendar tourdate = Calendar.getInstance(Locale.CHINA);
	
	// 交接说明
	public static Takeovercontent takeover = new Takeovercontent();
	
	
	// 当前的登录用户
	public static String userid; 
	
	public static void remove_workcontent(Workcontent wc)
	{
		list_workcontents.remove(wc);
	}
	
	
	public static List<SpinnerData> contractslist;
	
	public static List<SpinnerData> holelist;
	
	//public static String fetchurl; 
	
}
