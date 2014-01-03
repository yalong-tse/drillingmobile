package com.dreaming.drilling.bean;

/**
 * 人员配组vo
 */
public class HoleDeployments {
	private String id;         // 云端人员id
	private String name;   // 云端人员姓名
	private String type;    // 云端人员类型：项目经理/机长/班长
	
	public HoleDeployments() {}

	public HoleDeployments(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
