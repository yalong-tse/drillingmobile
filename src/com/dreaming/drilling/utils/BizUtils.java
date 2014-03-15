package com.dreaming.drilling.utils;

import com.dreaming.drilling.bean.Workcontent;
import com.dreaming.drilling.db.TourreportDBHelper;

/**
 * 
 * 业务需要的工具类
 * 
 * */
public class BizUtils {

	/**
	 * 获取上一次的（最新）的孔深
	 * */
	public static float getLastholedeep() {
		float result = 0;
		for (Workcontent c : GlobalConstants.list_workcontents) {
			if (Float.parseFloat(c.getHoledeep()) != 0)
				result = Float.parseFloat(c.getHoledeep());
		}

		
		return result;
	}

	/**
	 * 获取最大的一个孔深
	 * */
	public static float getMaxholedeep() {
		float result = 0;
		for (Workcontent c : GlobalConstants.list_workcontents) {
			if (Float.parseFloat(c.getHoledeep()) > result)
				result = Float.parseFloat(c.getHoledeep());
		}
		return result;
	}

	/**
	 * 根据分钟计算需要
	 * 
	 * 小时：分钟 样式
	 * 
	 * */
	public static String formatTimespan(long minutes) {
		String result = "00:00";

		String hour = "", minute = "";

		if (minutes > 0) {
			if (Math.round((minutes / 60)) < 10) {
				hour = "0" + Math.round((minutes / 60));
			}
			else
			{
				hour =  Math.round((minutes / 60)) + "";
			}
			
			if (Math.round(minutes % 60) < 10) {
				minute = "0" + Math.round(minutes % 60);
			}
			else
			{
				minute =  Math.round(minutes % 60) +"";
			}
			
			result = hour + ":" + minute;
		}

		return result;
	}

	/**
	 * 得到上一个工作内容的结束时间
	 * */
	public static String getLastTime()
	{
		String result = "00:00";
		if(GlobalConstants.list_workcontents.size()>0)
		{
			Workcontent w = GlobalConstants.list_workcontents.get(GlobalConstants.list_workcontents.size()-1);
			result = w.getEndtime();
		}
		else
		{
			if(GlobalConstants.tour_starttime!=null)
				result = GlobalConstants.tour_starttime;
			else
				result = "00:00";
		}
		return result;
	}
}
