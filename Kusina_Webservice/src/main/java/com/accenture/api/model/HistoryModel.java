package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/**
*
* @author 	arnel.m.capendit
* @created  5/3/2018
* @comment 	Webservice requests for histories
*/
public class HistoryModel {

//	@JsonPropertyOrder({
//		"ID",
//		"TYPE",
//		"USER",
//		"DOCID",
//		"ACTION_TYPE",
//		"ACTION_DATE"
//	})
	private String id;
	private String type;
	private String userEid;
	private String docId;
	private String actionType;
	private String actionDate;
	private String dueDate;
	
	
	public HistoryModel() {}
	
	
	public HistoryModel(String id, String type, String userEid, String docId, String actionType, String actionDate, String dueDate) {
		super();
		this.id = id;
		this.type = type;
		this.userEid = userEid;
		this.docId = docId;
		this.actionType = actionType;
		this.actionDate = actionDate;
		this.dueDate = dueDate;
	}
	
//	@JsonGetter("ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	@JsonGetter("TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
//	@JsonGetter("USER")
	public String getUserEid() {
		return userEid;
	}
	public void setUserEid(String userEid) {
		this.userEid = userEid;
	}
	
//	@JsonGetter("DOCID")
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
//	@JsonGetter("ACTION_TYPE")
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
//	@JsonGetter("ACTION_DATE")
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}


	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
