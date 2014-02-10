package com.dreaming.drilling.bean;

/**
 * 从云端返回的钻孔的详细信息
 * 
 * */
public class HoleDetail {

	private String minearea; // 云端返回的矿区信息
	private String actualdeep; // 远端返回的钻孔的实际深度
	private String holenumber; // 钻孔编号
	private String geologysituation; // 云端返回的地层情况的信息

	public HoleDetail(){}
	
	public HoleDetail(String $minearea,String $actualdeep,String $holenumber,String $geologysituation)
	{
		this.minearea = $minearea;
		this.actualdeep = $actualdeep;
		this.holenumber = $holenumber;
		this.geologysituation = $geologysituation;
		
	}
	public String getMinearea() {
		return minearea;
	}

	public void setMinearea(String minearea) {
		this.minearea = minearea;
	}

	public String getActualdeep() {
		return actualdeep;
	}

	public void setActualdeep(String actualdeep) {
		this.actualdeep = actualdeep;
	}

	public String getHolenumber() {
		return holenumber;
	}

	public void setHolenumber(String holenumber) {
		this.holenumber = holenumber;
	}

	public String getGeologysituation() {
		return geologysituation;
	}

	public void setGeologysituation(String geologysituation) {
		this.geologysituation = geologysituation;
	}

}
