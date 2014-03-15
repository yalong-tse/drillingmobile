package com.dreaming.drilling.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * workcontent 对象，方便在窗体之间传递的对象
 * 
 * */
public class Workcontent implements Parcelable {

	private Long id;
	private String starttime;
	private String endtime;
	private String type; 
	private String drillinglength;
	private String drillbit;
	private String upleft;
	private String corename;
	private String coregrade;
	private String corenumber;
	
	private String corelength;
	private String coreleftlength;
	private String holedeep;
	private String pressure;
	private String rotatespeed;
	private String pump;
	private Long tourreportid;
	
	public static final Parcelable.Creator<Workcontent> CREATOR = new Creator<Workcontent>()
	{
		@Override
		public Workcontent createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Workcontent workcontent = new Workcontent();
			workcontent.starttime = source.readString();
			workcontent.endtime = source.readString();
			workcontent.type = source.readString();
			workcontent.drillinglength = source.readString();
			workcontent.upleft = source.readString();
			workcontent.corelength = source.readString();
			workcontent.holedeep = source.readString();
			workcontent.pressure = source.readString();
			workcontent.rotatespeed = source.readString();
			workcontent.pump = source.readString();
			return workcontent;
		}

		@Override
		public Workcontent[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Workcontent[size];
		}
		
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// TODO Auto-generated method stub
		parcel.writeString(starttime);
		parcel.writeString(endtime);
		parcel.writeString(type);
		parcel.writeString(drillinglength);
		parcel.writeString(upleft);
		parcel.writeString(corelength);
		parcel.writeString(holedeep);
		parcel.writeString(pressure);
		parcel.writeString(rotatespeed);
		parcel.writeString(pump);
		
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDrillbit() {
		return drillbit;
	}
	public void setDrillbit(String drillbit) {
		this.drillbit = drillbit;
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
	
	public Long getTourreportid() {
		return tourreportid;
	}
	public void setTourreportid(Long tourreportid) {
		this.tourreportid = tourreportid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDrillinglength() {
		return drillinglength;
	}
	public void setDrillinglength(String drillinglength) {
		this.drillinglength = drillinglength;
	}
	public String getUpleft() {
		return upleft;
	}
	public void setUpleft(String upleft) {
		this.upleft = upleft;
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
	public String getHoledeep() {
		return holedeep;
	}
	public void setHoledeep(String holedeep) {
		this.holedeep = holedeep;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getRotatespeed() {
		return rotatespeed;
	}
	public void setRotatespeed(String rotatespeed) {
		this.rotatespeed = rotatespeed;
	}
	public String getPump() {
		return pump;
	}
	public void setPump(String pump) {
		this.pump = pump;
	}
	
	
}
