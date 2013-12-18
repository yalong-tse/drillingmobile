package com.dreaming.drilling.utils;

import com.dreaming.drilling.bean.Workcontent;

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
			if (c.getHoledeep() != 0)
				result = c.getHoledeep();
		}
		return result;
	}

	/**
	 * 获取最大的一个孔深
	 * */
	public static float getMaxholedeep() {
		float result = 0;
		for (Workcontent c : GlobalConstants.list_workcontents) {
			if (c.getHoledeep() > result)
				result = c.getHoledeep();
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
}
