package com.accenture.api.service;

import org.json.simple.JSONObject;
/**
*
* @author felix.m.macagaling
*/
public interface FeedbackService {

	JSONObject addFeedbacks(JSONObject object);
	
	JSONObject getFeedbacks(JSONObject object);
	
}
