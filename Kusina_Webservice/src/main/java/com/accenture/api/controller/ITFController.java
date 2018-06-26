package com.accenture.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.ITFService;
import com.accenture.api.service.KusinaService;
import com.accenture.api.utils.KusinaStringUtils;
import com.accenture.api.utils.KusinaValidationUtils;

/**
 * 
 * @author arnel.m.capendit
 *
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/custom_reports")
public class ITFController {
	
	 	@Autowired
	    private CustomReportService customReportService;

	    @Autowired
	    private KusinaService kusinaService;

	    @Autowired
	    private KusinaStringUtils kusinaStringUtils;

	    @Autowired
	    private KusinaValidationUtils kusinaValidationUtils;
	    
	    @Autowired
	    private ITFService itfService;

	@GetMapping("/itf")
	public JSONObject getCustomReportsITF(HttpServletRequest request) {
	
		JSONObject user = (JSONObject) request.getAttribute("user");
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
		
        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        String reportType = args.get("reportType").toString();
        
        
        //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	//ITF service
        	if (kusinaValidationUtils.isValidITFType(reportType)) {
            JSONObject customReport = itfService.getCustomReportsITF(args);
            System.out.println("Result: " + customReport.get("resultset"));
            result.put(reportType, customReport.get("resultset"));	
           
            //Getting Total
            JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
        	Long valueTotal = (Long) customReportTotal.get("value");
            result.put("total", valueTotal);
        	} else {
              result.put("status", "invalid report type");
          }
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	//ITF service
            	if (kusinaValidationUtils.isValidITFType(reportType)) {
                JSONObject customReport = itfService.getCustomReportsITF(args);
                System.out.println("Result: " + customReport.get("resultset"));
                result.put(reportType, customReport.get("resultset"));	
               
                //Getting Total
                JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
            	Long valueTotal = (Long) customReportTotal.get("value");
                result.put("total", valueTotal);
            	} else {
                  result.put("status", "invalid report type");
              }
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }

        
		
		return result;
	}
	
}
