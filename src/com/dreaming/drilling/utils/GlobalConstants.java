package com.dreaming.drilling.utils;

import java.util.ArrayList;
import java.util.List;

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
	
	// 工作内容
	public static List<Workcontent> list_workcontents = new ArrayList<Workcontent>();
	
	// 交接说明
	public static Takeovercontent takeover = new Takeovercontent();
	
	
	public static void remove_workcontent(Workcontent wc)
	{
		list_workcontents.remove(wc);
	}
	
}
