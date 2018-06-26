package com.accenture.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.accenture.api.model.AnnounceModel;

/**
*
* @author   arnel.m.capendit
* @created  5/2/2018
* @comment	Interface class for announce services
*/
public interface AnnounceService {

	/**
    *
    * @param object
    * @return
    */
	public List<AnnounceModel> getAnnounce(JSONObject object);
	
	/**
    *
    * @param object
    * @return
    */
	public List<AnnounceModel> getAnnounceLive(JSONObject object);
	
	/**
    *
    * @param object
    * @return
    */
	public List<AnnounceModel> getAnnounceAll(JSONObject object);
	
	/**
    *
    * @param object
    * @return
    */
	public JSONObject getAnnounceCountBeforeDueDate(JSONObject object);

	public JSONObject addAnnouncement(JSONObject insertObj);

	public JSONObject editAnnouncement(JSONObject insertObj);

	public JSONObject deleteAnnouncement(JSONObject insertObj);
	
}
