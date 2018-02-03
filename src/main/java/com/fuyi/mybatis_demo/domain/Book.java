package com.fuyi.mybatis_demo.domain;

public class Book {

	private Integer id;
	private String title;
	private String publish;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", publish=" + publish
				+ "]";
	}
	
}
