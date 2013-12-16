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
	private Float upmore;
	private String corename;
	private String coregrade;
	private String corenumber;
	private Float corelength;
	private Float coreleftlength;
	private Float drillinglength;
	private String drillbit;
	private Float rotatespeed;
	private Float pumpquantity;
	private Float pumppressure;
	private Float holedeep;

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

	public Float getUpmore() {
		return upmore;
	}

	public void setUpmore(Float upmore) {
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

	public Float getCorelength() {
		return corelength;
	}

	public void setCorelength(Float corelength) {
		this.corelength = corelength;
	}

	public Float getCoreleftlength() {
		return coreleftlength;
	}

	public void setCoreleftlength(Float coreleftlength) {
		this.coreleftlength = coreleftlength;
	}

	public Float getDrillinglength() {
		return drillinglength;
	}

	public void setDrillinglength(Float drillinglength) {
		this.drillinglength = drillinglength;
	}

	public String getDrillbit() {
		return drillbit;
	}

	public void setDrillbit(String drillbit) {
		this.drillbit = drillbit;
	}

	public Float getRotatespeed() {
		return rotatespeed;
	}

	public void setRotatespeed(Float rotatespeed) {
		this.rotatespeed = rotatespeed;
	}

	public Float getPumpquantity() {
		return pumpquantity;
	}

	public void setPumpquantity(Float pumpquantity) {
		this.pumpquantity = pumpquantity;
	}

	public Float getPumppressure() {
		return pumppressure;
	}

	public void setPumppressure(Float pumppressure) {
		this.pumppressure = pumppressure;
	}

	public Float getHoledeep() {
		return holedeep;
	}

	public void setHoledeep(Float holedeep) {
		this.holedeep = holedeep;
	}

}
