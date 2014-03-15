package com.dreaming.drilling.bean;

import java.sql.Date;

/**
 * 工作内容的实体类
 * * tourreportid
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
 * */
public class EntityWorkcontent {

	private Integer id;
	private Integer tourreportid;
	private String content;
	private Date starttime;
	private Date endtime;
	private String upmore;
	private String corename;
	private String coregrade;
	private String corenumber;
	private String corelength;
	private String coreleftlength;
	private String drillinglength;
	private String drillbit;
	private String rotatespeed;
	private String pumpquantity;
	private String pumppressure;
	private String holedeep;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTourreportid() {
		return tourreportid;
	}

	public void setTourreportid(Integer tourreportid) {
		this.tourreportid = tourreportid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getUpmore() {
		return upmore;
	}

	public void setUpmore(String upmore) {
		this.upmore = upmore;
	}

	public String getCorename() {
		return corename;
	}

	public void setCorename(String corename) {
		this.corename = corename;
	}

	public String getCoregrade() {
		return coregrade;
	}

	public void setCoregrade(String coregrade) {
		this.coregrade = coregrade;
	}

	public String getCorenumber() {
		return corenumber;
	}

	public void setCorenumber(String corenumber) {
		this.corenumber = corenumber;
	}

	public String getCorelength() {
		return corelength;
	}

	public void setCorelength(String corelength) {
		this.corelength = corelength;
	}

	public String getCoreleftlength() {
		return coreleftlength;
	}

	public void setCoreleftlength(String coreleftlength) {
		this.coreleftlength = coreleftlength;
	}

	public String getDrillinglength() {
		return drillinglength;
	}

	public void setDrillinglength(String drillinglength) {
		this.drillinglength = drillinglength;
	}

	public String getDrillbit() {
		return drillbit;
	}

	public void setDrillbit(String drillbit) {
		this.drillbit = drillbit;
	}

	public String getRotatespeed() {
		return rotatespeed;
	}

	public void setRotatespeed(String rotatespeed) {
		this.rotatespeed = rotatespeed;
	}

	public String getPumpquantity() {
		return pumpquantity;
	}

	public void setPumpquantity(String pumpquantity) {
		this.pumpquantity = pumpquantity;
	}

	public String getPumppressure() {
		return pumppressure;
	}

	public void setPumppressure(String pumppressure) {
		this.pumppressure = pumppressure;
	}

	public String getHoledeep() {
		return holedeep;
	}

	public void setHoledeep(String holedeep) {
		this.holedeep = holedeep;
	}

	

}
