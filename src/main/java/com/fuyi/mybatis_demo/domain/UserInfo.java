package com.fuyi.mybatis_demo.domain;

import java.util.Date;

public class UserInfo {

	private Integer id;
	private Date jobDate;
	private String position;
	

	public Integer getId() {
		return id;
	}
	public Date getJobDate() {
		return jobDate;
	}
	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", jobDate=" + jobDate + ", position="
				+ position + "]";
	}
	
}
