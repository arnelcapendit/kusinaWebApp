package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class AnnounceModel {
	
	@JsonPropertyOrder({
		"ID",
		"TYPE", 
		"DUE_DATE",
		"TITLE",
		"CONTENT",
		"STATUS", 
		"CREATED_DATE", 
		"LAST_UPDATE_DATE", 
		"LAST_UPDATE_BY",
		"READ_STATUS",
		"TOTAL_READ_COUNT"})
	private String id;
	private String type;
	private String dueDate;
	private String title;
	private String content;
	private String status;
	private String createdDate;
	private String lastUpdateDate;
	private String lastUpdateBy;
	private String totalReadCount;
	private String readStatus;
	
	
	@JsonGetter("READ_STATUS")
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	
	@JsonGetter("ID")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonGetter("TOTAL_READ_COUNT")
	public String getTotalReadCount() {
		return totalReadCount;
	}
	public void setTotalReadCount(String totalReadCount) {
		this.totalReadCount = totalReadCount;
	}
	
	@JsonGetter("TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@JsonGetter("DUE_DATE")
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	@JsonGetter("TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonGetter("CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonGetter("STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonGetter("CREATED_DATE")
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	@JsonGetter("LAST_UPDATE_DATE")
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@JsonGetter("LAST_UPDATE_BY")
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
	
	
	

}
