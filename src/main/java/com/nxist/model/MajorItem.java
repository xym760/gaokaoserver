package com.nxist.model;
/**
 * 自定义用于返回前台的json中需要的字段
 * @author xym760
 *
 */
public class MajorItem {
	private String majorName;
	private String majorCode;
	public MajorItem(String majorName,String majorCode) {
		this.majorName=majorName;
		this.majorCode=majorCode;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getMajorCode() {
		return majorCode;
	}
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}
}
