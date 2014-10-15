package com.codefriday.bangkokunitrade.dataset;

public class ReportDetailEntry {
	private int id;
	private String topic;
	private String subject;
	private String detail;
	private String image;
	private String date;

	public ReportDetailEntry() {

	}
	
	public ReportDetailEntry(int id, String topic, String subject,
			String detail, String image, String date) {
		super();
		this.id = id;
		this.topic = topic;
		this.subject = subject;
		this.detail = detail;
		this.image = image;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
