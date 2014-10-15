package com.codefriday.bangkokunitrade.dataset;

public class ReportlistEntry {
	private int id;
	private String topic;
	private String subject;
	private String date;

	public ReportlistEntry() {

	}
	
	public ReportlistEntry(int id, String topic, String subject, String date) {
		super();
		this.id = id;
		this.topic = topic;
		this.subject = subject;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
