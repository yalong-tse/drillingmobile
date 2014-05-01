package com.dreaming.drilling.bean;

/**
 *  for 配置界面使用  合同列表  和  钻孔列表的信息
 */
public class SpinnerData {
	private Integer outerflag;  // 是否外协孔，1：外协孔；其余均为内部孔
	private String id;
	private String name;
	
	public SpinnerData() {}
	
	public SpinnerData(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public SpinnerData(String id, String name, Integer outerflag) {
		this.id = id;
		this.name = name;
		this.outerflag = outerflag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOuterflag() {
		return outerflag;
	}

	public void setOuterflag(Integer outerflag) {
		this.outerflag = outerflag;
	}
	
}
