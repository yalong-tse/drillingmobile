package com.dreaming.drilling.bean;

import java.sql.Date;

/**
 * 班报的实体类
 * 
 * */
public class EntityTourreport {
	private Integer id;
	private String holenumber;
	private String administrator;
	private String recorder;
	private String projectmanager;
	private String tourleader;

	private Date tourdate;
	private Date starttime;
	private Date endtime;

	private Float tourshift;
	private Float tourcore;
	private Integer status;
	private Float lastdeep;
	private Float currentdeep;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Date getTourdate() {
		return tourdate;
	}

	public void setTourdate(Date tourdate) {
		this.tourdate = tourdate;
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

	public Float getTourshift() {
		return tourshift;
	}

	public void setTourshift(Float tourshift) {
		this.tourshift = tourshift;
	}

	public Float getTourcore() {
		return tourcore;
	}

	public void setTourcore(Float tourcore) {
		this.tourcore = tourcore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Float getLastdeep() {
		return lastdeep;
	}

	public void setLastdeep(Float lastdeep) {
		this.lastdeep = lastdeep;
	}

	public Float getCurrentdeep() {
		return currentdeep;
	}

	public void setCurrentdeep(Float currentdeep) {
		this.currentdeep = currentdeep;
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

}
