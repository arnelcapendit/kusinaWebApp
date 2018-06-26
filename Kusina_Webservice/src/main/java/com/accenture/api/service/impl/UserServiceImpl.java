package com.accenture.api.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.api.service.UserService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;

/**    @comment User WebService version with ELK pagination approach ...
*	   @created 04/20/2018
*	   @author arnel.m.capendit
*/ 
@Service
public class UserServiceImpl implements UserService {
	 
		private static final String KUSINA_USERS = "/users/_search?pretty=true";
	
		@Autowired
	 	private KusinaConfigUtils kusinaConfigUtils; 
		
		@Autowired
		private KusinaElasticUtils kusinaElasticUtils;
	    
	    @Override
	    public JSONObject getUsers(JSONObject obj) {

	        JSONObject resultSet = null;
	        try {
	            resultSet = new JSONObject();
	            JSONObject o = new JSONObject();
	            o.put("method", "GET");

	            StringBuilder sb = new StringBuilder();
	            sb.append("/");
	            sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
	            sb.append(KUSINA_USERS);
	              
	            o.put("uri", sb.toString());
	            o.put("hasPayload", true);
	            o.put("isKusina", true);
	            o.put("userObj", obj);
	            o.put("payload", "users");

	            resultSet = kusinaElasticUtils.sendRequest(o);

	        } catch (Exception ex) {
	            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	        }

	        return resultSet;
	    }

}
