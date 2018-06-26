	 package com.accenture.api.controller;


import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.api.service.VisitorService;
import com.accenture.api.utils.KusinaStringUtils;

/**
*
* @author    arnel.m.capendit
* @created   12-15-2017
*/
@RestController
@CrossOrigin(origins = "*")
public class VisitorController {
    
    private static final String VISITORLOGS = "/rest/visitorLogs";
    private static final String VISITORLOGSV2 = "/rest/visitorLogsV2";
    private static final String VISITORLOGSV3 = "/rest/visitorLogsV3";
    private static final String VISITORLOGSV3CHILD = "/rest/visitorLogs_child";
    
    
    @Autowired
    private KusinaStringUtils kusinaStringUtils;
    
    @Autowired
    private VisitorService visitorService;
    

    @RequestMapping(value= VISITORLOGS, method=RequestMethod.GET)
    public JSONObject visitorLog(
            @RequestParam("airid") String airid,
            @RequestParam("id") String id,
            @RequestParam("gte") String gte,
            @RequestParam("lte") String lte) {
        
        airid = kusinaStringUtils.sanitizeString(airid);
        id = kusinaStringUtils.sanitizeString(id);
        gte = kusinaStringUtils.sanitizeString(gte);
        lte = kusinaStringUtils.sanitizeString(lte);
        
        JSONObject obj = new JSONObject();
        obj.put("airid", airid);
        obj.put("id", id);
        obj.put("gte", gte);
        obj.put("lte", lte);	
        
        JSONObject result = new JSONObject();
        result.put("datatable", visitorService.getVisitorLogs(obj).get("resultset"));

        return result;
        
    }
    
    @RequestMapping(value = VISITORLOGSV2, method = RequestMethod.GET)
    public JSONObject getVisitorLogs(
    		 HttpServletRequest request) {
        
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        JSONArray visitorLogMetrics = (JSONArray) visitorService.getVisitorLogsV2(args).get("resultset");
        result.put("VisitorLogMetrics", visitorLogMetrics);

        return result;
    }
    
    @RequestMapping(value = VISITORLOGSV3, method = RequestMethod.GET)
    public JSONObject getVisitorLog(
    		HttpServletRequest request) {
    	 
    	JSONObject user = (JSONObject) request.getAttribute("user");
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        
        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
        	//Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	JSONObject customReportTotal = visitorService.getVisitorLogTotal(args);
            Long valueTotal = (Long) customReportTotal.get("value");
            args.put("total", valueTotal);
            JSONObject visitorLogMetrics = visitorService.getVisitorLogsV3(args);
            if(!args.get("search").toString().equalsIgnoreCase("*")) {
            	result.put("total", visitorLogMetrics.get("total"));
            }else {
            	result.put("total", valueTotal);
            }
            result.put("VisitorLogMetrics", visitorLogMetrics.get("resultset"));
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	JSONObject customReportTotal = visitorService.getVisitorLogTotal(args);
                Long valueTotal = (Long) customReportTotal.get("value");
                args.put("total", valueTotal);
                JSONObject visitorLogMetrics = visitorService.getVisitorLogsV3(args);
                if(!args.get("search").toString().equalsIgnoreCase("*")) {
                	result.put("total", visitorLogMetrics.get("total"));
                }else {
                	result.put("total", valueTotal);
                }
                result.put("VisitorLogMetrics", visitorLogMetrics.get("resultset"));
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }
        
        return result;
    }
    
    @RequestMapping(value = VISITORLOGSV3CHILD, method = RequestMethod.GET)
    public JSONObject getVisitorLogChild(
    		HttpServletRequest request) {
    	 
    	JSONObject user = (JSONObject) request.getAttribute("user");
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        
        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
        //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	JSONObject customReportTotal = visitorService.getVisitorLogTotalChild(args);
            JSONObject visitorLogMetrics = visitorService.getVisitorLogsV3Child(args);
            Long valueTotal = (Long) customReportTotal.get("value");
            
            if(!args.get("search").toString().equalsIgnoreCase("*")) {
            	result.put("total", visitorLogMetrics.get("total"));
            }else {
            	result.put("total", valueTotal);
            }
            
            result.put("VisitorLogMetrics", visitorLogMetrics.get("resultset"));
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	JSONObject customReportTotal = visitorService.getVisitorLogTotalChild(args);
                JSONObject visitorLogMetrics = visitorService.getVisitorLogsV3Child(args);
                Long valueTotal = (Long) customReportTotal.get("value");
                
                if(!args.get("search").toString().equalsIgnoreCase("*")) {
                	result.put("total", visitorLogMetrics.get("total"));
                }else {
                	result.put("total", valueTotal);
                }
                
                result.put("VisitorLogMetrics", visitorLogMetrics.get("resultset"));
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }         
        return result;
    }
    
    
    @RequestMapping(value= "/testArnel", method=RequestMethod.GET)
    public JSONObject visitorLog() {
 
        JSONObject obj = new JSONObject();
        obj.put("hey", "gumana");
             
        return obj;
        
    }
    
}