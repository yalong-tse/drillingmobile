package com.dreaming.drilling.bean;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 班报的实体类
 * 
 * 需要考虑的是 多个渠道填写班报的时候，ID的机制如何维护，可能会调整大平台的ID 产生机制
 * 
 *  * holenumber
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

 * 
 * */
public class EntityTourreport {
	
	private String id;
	private String holenumber;
	private String holeid;
	private String administrator;
	private String recorder;
	private String projectmanager;
	private String tourleader;
	
	// 矿区
	private String minearea;

	// 采用字符串
	private String tourdate;
	private String starttime;
	private String endtime;

	private BigDecimal tourshift;
	private BigDecimal tourcore;
	private Integer status;
	private BigDecimal lastdeep;
	private BigDecimal currentdeep;
	private String tourdrillingtime;
	private String tourauxiliarytime;
	private String holeaccidenttime;
	private String deviceaccidenttime;
	private String othertime;
	private String totaltime;
	private String takeoverremark;
	private String instrumenttakeover;
	private String centralizer;
	private String antideviation;
	private Integer syncflag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHolenumber() {
		return holenumber;
	}

	public void setHolenumber(String holenumber) {
		this.holenumber = holenumber;
	}

	public String getAdministrator() {
		return administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public String getProjectmanager() {
		return projectmanager;
	}

	public void setProjectmanager(String projectmanager) {
		this.projectmanager = projectmanager;
	}

	public String getTourleader() {
		return tourleader;
	}

	public void setTourleader(String tourleader) {
		this.tourleader = tourleader;
	}

	public String getTourdate() {
		return tourdate;
	}

	public void setTourdate(String tourdate) {
		this.tourdate = tourdate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getTourdrillingtime() {
		return tourdrillingtime;
	}

	public void setTourdrillingtime(String tourdrillingtime) {
		this.tourdrillingtime = tourdrillingtime;
	}

	public String getTourauxiliarytime() {
		return tourauxiliarytime;
	}

	public void setTourauxiliarytime(String tourauxiliarytime) {
		this.tourauxiliarytime = tourauxiliarytime;
	}

	public String getHoleaccidenttime() {
		return holeaccidenttime;
	}

	public void setHoleaccidenttime(String holeaccidenttime) {
		this.holeaccidenttime = holeaccidenttime;
	}

	public String getDeviceaccidenttime() {
		return deviceaccidenttime;
	}

	public void setDeviceaccidenttime(String deviceaccidenttime) {
		this.deviceaccidenttime = deviceaccidenttime;
	}

	public String getOthertime() {
		return othertime;
	}

	public void setOthertime(String othertime) {
		this.othertime = othertime;
	}

	public String getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}

	public String getTakeoverremark() {
		return takeoverremark;
	}

	public void setTakeoverremark(String takeoverremark) {
		this.takeoverremark = takeoverremark;
	}

	public String getInstrumenttakeover() {
		return instrumenttakeover;
	}

	public void setInstrumenttakeover(String instrumenttakeover) {
		this.instrumenttakeover = instrumenttakeover;
	}

	public String getCentralizer() {
		return centralizer;
	}

	public void setCentralizer(String centralizer) {
		this.centralizer = centralizer;
	}

	public String getAntideviation() {
		return antideviation;
	}

	public void setAntideviation(String antideviation) {
		this.antideviation = antideviation;
	}

	public String getMinearea() {
		return minearea;
	}

	public void setMinearea(String minearea) {
		this.minearea = minearea;
	}

	public String getHoleid() {
		return holeid;
	}

	public void setHoleid(String holeid) {
		this.holeid = holeid;
	}

	public Integer getSyncflag() {
		return syncflag;
	}

	public void setSyncflag(Integer syncflag) {
		this.syncflag = syncflag;
	}

	public BigDecimal getTourshift() {
		return tourshift;
	}

	public void setTourshift(BigDecimal tourshift) {
		this.tourshift = tourshift;
	}

	public BigDecimal getTourcore() {
		return tourcore;
	}

	public void setTourcore(BigDecimal tourcore) {
		this.tourcore = tourcore;
	}

	public BigDecimal getLastdeep() {
		return lastdeep;
	}

	public void setLastdeep(BigDecimal lastdeep) {
		this.lastdeep = lastdeep;
	}

	public BigDecimal getCurrentdeep() {
		return currentdeep;
	}

	public void setCurrentdeep(BigDecimal currentdeep) {
		this.currentdeep = currentdeep;
	}
	
	
}
