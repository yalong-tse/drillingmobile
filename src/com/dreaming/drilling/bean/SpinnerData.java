package com.dreaming.drilling.bean;

/**
 *  for 配置界面使用  合同列表  和  钻孔列表的信息
 */
public class SpinnerData {
	private String id;
	private String name;
	
	public SpinnerData() {}
	
	public SpinnerData(String id, String name) {
		this.id = id;
		this.name = name;
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
	
}
