package com.accenture.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.api.model.AnnounceModel;
import com.accenture.api.service.AnnounceService;
import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.HistoryService;
import com.accenture.api.utils.KusinaStringUtils;

/**
*
* @author 	arnel.m.capendit
* @created  5/2/2018
* @comment 	Webservice requests for announcements
*/
@RestController
@CrossOrigin(origins = "*")
public class AnnounceController {

	public static final String USER_RIGHTS_ADMIN = "Super Administrator";
	public static final String USER_RIGHTS_PROJ = "Project Administrator";
	public static final String GET_ANNOUNCE = "/rest/announce";
	public static final String GET_ANNOUNCE_LIVE = "/rest/announce/live";
	public static final String GET_ANNOUNCE_ALL_BEFORE_DUEDATE = "/rest/all/announce_duedate";
	public static final String GET_ANNOUNCE_BEFORE_DUEDATE = "/rest/announce_duedate";
	public static final String GET_NOTIFICATIONS = "/rest/notifications";
	public static final String ADD_ANNOUNCEMENT = "/rest/add_announcement";
	public static final String EDIT_ANNOUNCEMENT = "/rest/edit_announcement";
	public static final String DELETE_ANNOUNCEMENT = "/rest/delete_announcement";
	
	
	@Autowired
	public AnnounceService announceService;
	
	@Autowired
	public CustomReportService customReportService;
	
	@Autowired
	public HistoryService historyService; 
	
	@Autowired
    private KusinaStringUtils kusinaStringUtils;
	
	@GetMapping(GET_ANNOUNCE)
	public JSONObject getAnnounce(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		List<AnnounceModel> announcements = new ArrayList<>();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
			 //Webservice announcements
			 announcements =  announceService.getAnnounce(args);
			 result.put("announcements", announcements);
			 //Getting Total
			 JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
	         Long valueTotal = (Long) customReportTotal.get("value");
	         result.put("total", valueTotal); 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	
	@GetMapping(GET_ANNOUNCE_LIVE)
	public JSONObject getAnnounceLive(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		List<AnnounceModel> announcements = new ArrayList<>();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
			 //Webservice announcements
			 announcements =  announceService.getAnnounceLive(args);
			 result.put("announcements", announcements);
			 //Getting Total
			 JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
	         Long valueTotal = (Long) customReportTotal.get("value");
	         result.put("total", valueTotal); 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	
	@GetMapping(GET_ANNOUNCE_ALL_BEFORE_DUEDATE)
	public JSONObject getAnnounceBeforeDueDate(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		List<AnnounceModel> announcements = new ArrayList<>();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
			 //Webservice announcements
			 announcements =  announceService.getAnnounceAll(args);
			 result.put("announcements", announcements);
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	
	@GetMapping(GET_ANNOUNCE_BEFORE_DUEDATE)
	public JSONObject getAnnounceCountBeforeDueDate(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
	
			 JSONObject countAnnounceDueDate =  announceService.getAnnounceCountBeforeDueDate(args);
			 result.put("total", countAnnounceDueDate.get("value"));
			 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	@GetMapping(GET_NOTIFICATIONS)
	public JSONObject getNotifications(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
				
			 Long countAnnounceDueDate = (Long) announceService.getAnnounceCountBeforeDueDate(args).get("value");
			 Long countReadAnnounce = (Long) historyService.getReadAnnouncePerUser(args).get("value");
			 int total = countAnnounceDueDate.intValue() - countReadAnnounce.intValue();

			 result.put("total", total);		
			 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
	
		return result;
			
	}
	
	@RequestMapping(value = ADD_ANNOUNCEMENT, method = RequestMethod.POST)
	public JSONObject addAnnouncement(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("type") String type,
            @RequestParam("dueDate") String dueDate,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("status") String status,
            @RequestParam("createdDate") String createdDate,
            @RequestParam("updatedDate") String updatedDate,
            @RequestParam("updatedBy") String updatedBy) {

		type = kusinaStringUtils.sanitizeString(type);
		dueDate = kusinaStringUtils.sanitizeString(dueDate);
        title = kusinaStringUtils.sanitizeString(title);
        content = kusinaStringUtils.sanitizeString(content);
        status = kusinaStringUtils.sanitizeString(status);
        createdDate = kusinaStringUtils.sanitizeString(createdDate);
        updatedDate = kusinaStringUtils.sanitizeString(updatedDate);
        updatedBy = kusinaStringUtils.sanitizeString(updatedBy);
      

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        JSONObject insertObj = new JSONObject();

        insertObj.put("eid", eid);
        insertObj.put("type", type);
        insertObj.put("dueDate", dueDate);
        insertObj.put("title", title);
        insertObj.put("content", content);
        insertObj.put("status", status);
        insertObj.put("createdDate", createdDate);
        insertObj.put("updatedDate", updatedDate);
        insertObj.put("updatedBy", updatedBy);
        
        
        results = announceService.addAnnouncement(insertObj);

        return results;
    }
	
	@RequestMapping(value = EDIT_ANNOUNCEMENT, method = RequestMethod.POST)
	public JSONObject editAnnouncement(
			@RequestParam("id") String id,
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("type") String type,
            @RequestParam("dueDate") String dueDate,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("status") String status,
            @RequestParam("createdDate") String createdDate,
            @RequestParam("updatedDate") String updatedDate,
            @RequestParam("updatedBy") String updatedBy) {

		id = kusinaStringUtils.sanitizeString(id);
		type = kusinaStringUtils.sanitizeString(type);
		dueDate = kusinaStringUtils.sanitizeString(dueDate);
        title = kusinaStringUtils.sanitizeString(title);
        content = kusinaStringUtils.sanitizeString(content);
        status = kusinaStringUtils.sanitizeString(status);
        createdDate = kusinaStringUtils.sanitizeString(createdDate);
        updatedDate = kusinaStringUtils.sanitizeString(updatedDate);
        updatedBy = kusinaStringUtils.sanitizeString(updatedBy);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        JSONObject insertObj = new JSONObject();
        
        insertObj.put("id", id);
        insertObj.put("eid", eid);
        insertObj.put("type", type);
        insertObj.put("dueDate", dueDate);
        insertObj.put("title", title);
        insertObj.put("content", content);
        insertObj.put("status", status);
        insertObj.put("createdDate", createdDate);
        insertObj.put("updatedDate", updatedDate);
        insertObj.put("updatedBy", updatedBy);
        
        results = announceService.editAnnouncement(insertObj);

        return results;
    }
	
	@RequestMapping(value = DELETE_ANNOUNCEMENT, method = RequestMethod.DELETE)
	public JSONObject deleteAnnouncement(
			@RequestParam("id") String id,
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now) {

		id = kusinaStringUtils.sanitizeString(id);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        JSONObject insertObj = new JSONObject();
        
        insertObj.put("id", id);
        
        results = announceService.deleteAnnouncement(insertObj);

        return results;
    }

	
}
