package com.accenture.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.accenture.api.model.History;
import com.accenture.api.model.HistoryModel;

/**
*
* @author   arnel.m.capendit
* @created  5/3/2018
* @comment	Interface class for history services
*/
public interface HistoryService {

	/**
    *
    * @param object
    * @return
    */
	public List<HistoryModel> getHistories(JSONObject object);
	
	/**
    *
    * @param object
    * @return
    */
	public JSONObject getReadAnnouncePerUser(JSONObject object);
	
	/**
    *
    * @param object
    * @return
    */
	public JSONObject getReadAnnouncePerUserPerAnnounce(JSONObject object, String id);
	
	/**
    *
    * @param object
    * @return
    */
	public JSONObject getAllReadAnnounce(JSONObject object, String id);
	
	/**
    *
    * @param object
    * @return
    */
	public JSONObject saveHistory(JSONObject object);
	
	
	public JSONObject postHistory(HistoryModel historyModel);
	
	
	public JSONObject getHistoryAnnounceId(String id);
	
	public JSONObject deleteAnnouncement(String id);
}
