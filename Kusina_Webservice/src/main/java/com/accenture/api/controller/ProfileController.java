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

import com.accenture.api.model.AirIdModel;
import com.accenture.api.model.AnnounceModel;
import com.accenture.api.model.ProfileModel;
import com.accenture.api.model.SiteIdModel;
import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.ProfileService;
import com.accenture.api.utils.KusinaStringUtils;

/**
*
* @author 	felix.m.macagaling
* @created  5/16/2018
* @comment 	Webservice requests for Profile
*/
@RestController
@CrossOrigin(origins = "*")
public class ProfileController {
	public static final String USER_RIGHTS_ADMIN = "Super Administrator";
	public static final String USER_RIGHTS_PROJ = "Project Administrator";
	public static final String GET_PROFILE = "/rest/profile";
	public static final String ADD_PROFILE = "/rest/add_profile";
	public static final String EDIT_PROFILE = "/rest/edit_profile";
	public static final String DELETE_PROFILE = "/rest/delete_profile";
	public static final String GET_ALL_AIRID = "/rest/get_all_airid";
	public static final String GET_SITE_ID = "/rest/get_site_id";
	
	
	@Autowired
	public ProfileService profileService;
	
	@Autowired
	public CustomReportService customReportService;
	
	@Autowired
    private KusinaStringUtils kusinaStringUtils;
	
	@GetMapping(GET_PROFILE)
	public JSONObject getAnnounce(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		List<ProfileModel> profile = new ArrayList<>();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
			 //Webservice announcements
			 profile =  profileService.getProfile(args);
			 result.put("profile", profile);
			 //Getting Total
			 JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
	         Long valueTotal = (Long) customReportTotal.get("value");
	         result.put("total", valueTotal); 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	@GetMapping(GET_ALL_AIRID)
	public JSONObject getAllAirid(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		List<AirIdModel> profile = new ArrayList<>();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
			 //Webservice announcements
			 profile =  profileService.getAllAirid(args);
			 result.put("profile", profile);
			 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	@GetMapping(GET_SITE_ID)
	public JSONObject getSiteId(HttpServletRequest request) {
		
		JSONObject user = (JSONObject) request.getAttribute("user");
		JSONObject args = (JSONObject) request.getAttribute("params");
		JSONObject result = new JSONObject();
		
		List<SiteIdModel> siteId = new ArrayList<>();
		
		 if (Arrays.asList(USER_RIGHTS_ADMIN, USER_RIGHTS_PROJ).contains(user.get("user_access").toString())) {
			 //Webservice announcements
			 siteId =  profileService.getSiteId(args);
			 result.put("site_id", siteId);
			 
		 } else {
	            result.put("status", "You don't have enough permission to access this service");
	     }
		
		return result;
	}
	
	
	@RequestMapping(value = ADD_PROFILE, method = RequestMethod.POST)
	public JSONObject addProfile(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("airid") String airid,
            @RequestParam("idSite") String idSite,
            @RequestParam("appName") String appName,
            @RequestParam("createdDate") String createdDate,
            @RequestParam("updatedDate") String updatedDate,
            @RequestParam("updatedBy") String updatedBy) {

		airid = kusinaStringUtils.sanitizeString(airid);
		idSite = kusinaStringUtils.sanitizeString(idSite);
		appName = kusinaStringUtils.sanitizeString(appName);
        createdDate = kusinaStringUtils.sanitizeString(createdDate);
        updatedDate = kusinaStringUtils.sanitizeString(updatedDate);
        updatedBy = kusinaStringUtils.sanitizeString(updatedBy);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        JSONObject insertObj = new JSONObject();

        insertObj.put("eid", eid);
        insertObj.put("airid", airid);
        insertObj.put("idSite", idSite);
        insertObj.put("appName", appName);
        insertObj.put("createdDate", createdDate);
        insertObj.put("updatedDate", updatedDate);
        insertObj.put("updatedBy", updatedBy);
        
        results = profileService.addProfile(insertObj);

        return results;
    }
	
	@RequestMapping(value = EDIT_PROFILE, method = RequestMethod.POST)
	public JSONObject editProfile(
			@RequestParam("id") String id,
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("airid") String airid,
            @RequestParam("idSite") String idSite,
            @RequestParam("appName") String appName,
            @RequestParam("createdDate") String createdDate,
            @RequestParam("updatedDate") String updatedDate,
            @RequestParam("updatedBy") String updatedBy) {

		id = kusinaStringUtils.sanitizeString(id);
		airid = kusinaStringUtils.sanitizeString(airid);
		idSite = kusinaStringUtils.sanitizeString(idSite);
        appName = kusinaStringUtils.sanitizeString(appName);
        createdDate = kusinaStringUtils.sanitizeString(createdDate);
        updatedDate = kusinaStringUtils.sanitizeString(updatedDate);
        updatedBy = kusinaStringUtils.sanitizeString(updatedBy);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        JSONObject insertObj = new JSONObject();
        
        insertObj.put("id", id);
        insertObj.put("eid", eid);
        insertObj.put("airid", airid);
        insertObj.put("idSite", idSite);
        insertObj.put("appName", appName);
        insertObj.put("createdDate", createdDate);
        insertObj.put("updatedDate", updatedDate);
        insertObj.put("updatedBy", updatedBy);
        
        results = profileService.editProfile(insertObj);

        return results;
    }
	
	@RequestMapping(value = DELETE_PROFILE, method = RequestMethod.DELETE)
	public JSONObject deleteProfile(
			@RequestParam("id") String id,
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now) {

		id = kusinaStringUtils.sanitizeString(id);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        JSONObject insertObj = new JSONObject();
        
        insertObj.put("id", id);
        
        results = profileService.deleteProfile(insertObj);

        return results;
    }
	
}
