package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class ProfileModel {
	@JsonPropertyOrder({
		"ID",
		"AIRID",
		"SITE_ID", 
		"APP_NAME",
		"CREATED_DATE", 
		"LAST_UPDATE_DATE", 
		"LAST_UPDATE_BY"})
	private String id;
	private String airid;
	private String idSite;
	private String appName;
	private String createdDate;
	private String lastUpdateDate;
	private String lastUpdateBy;
	
	

	
	@JsonGetter("ID")
	public String getId() {
		return id;
	}	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonGetter("AIRID")
	public String getAirid() {
		return airid;
	}
	public void setAirid(String airid) {
		this.airid = airid;
	}
	
	@JsonGetter("SITE_ID")
	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	
	@JsonGetter("APP_NAME")
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
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
