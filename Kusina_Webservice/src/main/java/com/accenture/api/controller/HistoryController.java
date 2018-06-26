package com.accenture.api.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.api.model.HistoryModel;
import com.accenture.api.model.testModel;
import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.HistoryService;

/**
*
* @author 	arnel.m.capendit
* @created  5/3/2018
* @comment 	Webservice requests for histories
*/
@RestController
@CrossOrigin(origins = "*")
public class HistoryController {

	public static final String USER_RIGHTS = "Super Administrator";
	public static final String GET_HISTORIES = "/rest/histories";
	public static final String GET_HISTORY_ID = "/rest/historyId";
	public static final String GET_READ_ANNOUNCE_PER_USER = "/rest/announce_read";
	public static final String GET_ALL_READ_ANNOUNCE = "/rest/all_announce_read";
	public static final String POST_READ_ANNOUNCE = "/rest/histories";
	public static final String POST_HISTORY = "/rest/post/histories";
	public static final String DELETE_HISTORY = "/rest/delete/histories";
	
	@Autowired
	public HistoryService historyService;
	
	@Autowired
	public CustomReportService customReportService;
	
	@Autowired
	public HistoryDaoService historyDaoService;
	
	@GetMapping(GET_HISTORIES)
	public JSONObject getHistories(HttpServletRequest request) {
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		List<HistoryModel> histories = new ArrayList<>();
		
		 if (USER_RIGHTS.equalsIgnoreCase(user.get("user_access").toString())) {
			 //Webservice announcements
			 histories =  historyService.getHistories(args);
			 result.put("histories", histories);
			 //Getting Total
			 JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
	         Long valueTotal = (Long) customReportTotal.get("value");
	         result.put("total", valueTotal); 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	
	@GetMapping(GET_READ_ANNOUNCE_PER_USER)
	public JSONObject getReadAnnouncePerUser(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		if (USER_RIGHTS.equalsIgnoreCase(user.get("user_access").toString())) {
			JSONObject countReadAnnounce = historyService.getReadAnnouncePerUser(args);
			result.put("total", countReadAnnounce.get("value"));
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	
	@PostMapping(POST_READ_ANNOUNCE)
	public JSONObject postHistoryAnnounce(HttpServletRequest request) {
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		//if (USER_RIGHTS.equalsIgnoreCase(user.get("user_access").toString())) {
			result.put("status", historyService.saveHistory(args).get("result"));		
		// } else {
	    //        result.put("status", "You don't have enough permission to access this service");
	    // }
		
		return result;
	}
	
	@PostMapping("/post/histories")
	public void postHistories(@RequestBody HistoryModel historyModel) {
		historyService.postHistory(historyModel);
	}
	
	
	@GetMapping("/history")
	public List<HistoryModel> retrieveAllHistory() {
		return historyDaoService.findAll();
	}
	
	@PostMapping("/postTest")
	public void postTest(@RequestBody testModel testmodel) {
		historyDaoService.addTestModel(testmodel);
	}
	
	@GetMapping("/getTest")
	public List<testModel> getTest() {
		return historyDaoService.getTestModel();
	}
	
	
	@GetMapping(GET_HISTORY_ID)
	public JSONObject getHistoryId(HttpServletRequest request) {
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		if (USER_RIGHTS.equalsIgnoreCase(user.get("user_access").toString())) {
			result.put("id", historyService.getHistoryAnnounceId(args.get("id").toString()).get("id").toString());		
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	
	@DeleteMapping(DELETE_HISTORY)
	public JSONObject deleteHistory(HttpServletRequest request) {
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		if (USER_RIGHTS.equalsIgnoreCase(user.get("user_access").toString())) {
			result = historyService.deleteAnnouncement(args.get("id").toString());
			result.put("status", result.get("status").toString());	
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
}
