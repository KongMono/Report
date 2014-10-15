package com.codefriday.bangkokunitrade.dataset;

public class ReportToppicEntry {
	private int id;
	private String topic;

	public ReportToppicEntry() {

	}
	
	public ReportToppicEntry(int id, String topic) {
		super();
		this.id = id;
		this.topic = topic;
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
	
	
}
