package com.accenture.api.service;

import org.json.simple.JSONObject;

/**    @comment User WebService version with ELK pagination approach ...
*	   @created 04/20/2018
*	   @author arnel.m.capendit
*/ 
public interface UserService {
	
	/**
    *
    * @param eid
    * @return
    */
	public JSONObject getUsers(JSONObject o);
	
	
}
