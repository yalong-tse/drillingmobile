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
	private float drillinglength;
	private String drillbit;
	private float upleft;
	private String corename;
	private String coregrade;
	private String corenumber;
	
	private float corelength;
	private float coreleftlength;
	private float holedeep;
	private float pressure;
	private float rotatespeed;
	private float pump;
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
			workcontent.drillinglength = source.readFloat();
			workcontent.upleft = source.readFloat();
			workcontent.corelength = source.readFloat();
			workcontent.holedeep = source.readFloat();
			workcontent.pressure = source.readFloat();
			workcontent.rotatespeed = source.readFloat();
			workcontent.pump = source.readFloat();
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
		parcel.writeFloat(drillinglength);
		parcel.writeFloat(upleft);
		parcel.writeFloat(corelength);
		parcel.writeFloat(holedeep);
		parcel.writeFloat(pressure);
		parcel.writeFloat(rotatespeed);
		parcel.writeFloat(pump);
		
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
	public float getUpleft() {
		return upleft;
	}
	public void setUpleft(float upleft) {
		this.upleft = upleft;
	}
	public float getCorelength() {
		return corelength;
	}
	public void setCorelength(float corelength) {
		this.corelength = corelength;
	}
	public float getHoledeep() {
		return holedeep;
	}
	public void setHoledeep(float holedeep) {
		this.holedeep = holedeep;
	}
	public float getPressure() {
		return pressure;
	}
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	public float getRotatespeed() {
		return rotatespeed;
	}
	public void setRotatespeed(float rotatespeed) {
		this.rotatespeed = rotatespeed;
	}
	public float getPump() {
		return pump;
	}
	public void setPump(float pump) {
		this.pump = pump;
	}
	public float getDrillinglength() {
		return drillinglength;
	}
	public void setDrillinglength(float drillinglength) {
		this.drillinglength = drillinglength;
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
	public float getCoreleftlength() {
		return coreleftlength;
	}
	public void setCoreleftlength(float coreleftlength) {
		this.coreleftlength = coreleftlength;
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
	
	
}
