package com.accenture.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.api.service.MyTEService;
import com.accenture.api.utils.KusinaStringUtils;

/**
*
* @author    felix.m.macagaling
* @created   03-16-2018
*/
@RestController
@CrossOrigin(origins = "*")
public class MyTEController {
	private static final String MYTE = "/rest/custom_reports/myTeV2";
    
    
    @Autowired
    private KusinaStringUtils kusinaStringUtils;
    
    @Autowired
    private MyTEService myTEService;
    
    @RequestMapping(value=MYTE, method=RequestMethod.GET)    
    public JSONObject myTEReports(
    		HttpServletRequest request) {
    	 
    	JSONObject user = (JSONObject) request.getAttribute("user");
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        
        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
        
        //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	JSONObject customReportTotal = myTEService.getMyTETotal(args);
            JSONObject myTE = myTEService.getMyTEReports(args);
            Long valueTotal = (Long) customReportTotal.get("value");
            if(!args.get("search").toString().equalsIgnoreCase("*")) {
            	result.put("total", myTE.get("total"));
            }else {
            	result.put("total", valueTotal);
            }  
            result.put("MyTEReports", myTE.get("resultset"));
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	JSONObject customReportTotal = myTEService.getMyTETotal(args);
                JSONObject myTE = myTEService.getMyTEReports(args);
                Long valueTotal = (Long) customReportTotal.get("value");
                if(!args.get("search").toString().equalsIgnoreCase("*")) {
                	result.put("total", myTE.get("total"));
                }else {
                	result.put("total", valueTotal);
                }
                
                result.put("MyTEReports", myTE.get("resultset"));
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }     
        return result;
    }
}
