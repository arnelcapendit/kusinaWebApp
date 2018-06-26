package com.accenture.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class UserModel {

	@JsonPropertyOrder({
		"EID", 
		"ACCESS",
		"ID",
		"AIRID",
		"TEAM",
		"EXPIRY_DATE", 
		"STATUS", 
		"CREATED_DATE", 
		"LAST_UPDATED_DATE", 
		"LAST_UPDATED_BY"})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String eid;
	private String id;
	private String access;
	private String airid;
	private String team;
	private String expiryDate;
	private String status;
	private String createdDate;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
		
	@JsonGetter("ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonGetter("EID")
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	@JsonGetter("ACCESS")
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	
	@JsonGetter("AIRID")
	public String getAirid() {
		return airid;
	}
	public void setAirid(String airid) {
		this.airid = airid;
	}
	
	@JsonGetter("EXPIRY_DATE")
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
	
	@JsonGetter("LAST_UPDATED_DATE")
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@JsonGetter("LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@JsonGetter("TEAM")
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	

}

