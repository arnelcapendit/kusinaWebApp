package com.accenture.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.accenture.api.model.AirIdModel;
import com.accenture.api.model.ProfileModel;
import com.accenture.api.model.SiteIdModel;

/**
*
* @author   felix.m.macagaling
* @created  5/16/2018
* @comment	Interface class for profile service
*/
public interface ProfileService {
	
	public List<ProfileModel> getProfile(JSONObject args);
	
	public JSONObject addProfile(JSONObject insertObj);

	public JSONObject editProfile(JSONObject insertObj);

	public JSONObject deleteProfile(JSONObject insertObj);

	public JSONObject getProfileExport(JSONObject object);

	public List<AirIdModel> getAllAirid(JSONObject object);

	public List<SiteIdModel> getSiteId(JSONObject object);
	
}
