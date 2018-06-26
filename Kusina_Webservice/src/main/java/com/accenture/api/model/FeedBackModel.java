package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class FeedBackModel {

	@JsonPropertyOrder({
		"EID", 
		"AIRID",
		"ID",
		"RATING_MODULE",
		"RATING_SCORE", 
		"COMMENT", 
		"STATUS", 
		"CREATED_DATE",
		"LAST_UPDATE_DATE",
		"LAST_UPDATE_BY"})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String eid;
	private String airid;
	private String id;
	private String ratingModule;
	private String ratingScore;
	private String comment;
	private String status;
	private String createdDate;
	private String lastUpdateDate;
	private String lastUpdateBy;
	
	@JsonGetter("EID")
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	@JsonGetter("AIRID")
	public String getAirid() {
		return airid;
	}
	public void setAirid(String airid) {
		this.airid = airid;
	}
	
	@JsonGetter("ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonGetter("RATING_MODULE")
	public String getRatingModule() {
		return ratingModule;
	}
	public void setRatingModule(String ratingModule) {
		this.ratingModule = ratingModule;
	}
	
	@JsonGetter("RATING_SCORE")
	public String getRatingScore() {
		return ratingScore;
	}
	public void setRatingScore(String ratingScore) {
		this.ratingScore = ratingScore;
	}
	
	@JsonGetter("COMMENT")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
