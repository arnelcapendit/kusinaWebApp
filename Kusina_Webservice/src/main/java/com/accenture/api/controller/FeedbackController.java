package com.accenture.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.FeedbackService;
import com.accenture.api.service.KusinaService;
import com.accenture.api.utils.KusinaStringUtils;

@RestController
@CrossOrigin(origins = "*")
public class FeedbackController {
	private static final String USER_RIGHTS = "Super Administrator";
	private static final String ADD_FEEDBACKS = "/rest/addFeedback";
	private static final String GET_FEEDBACKS = "/rest/feedbacks";
	
	@Autowired
    private KusinaService mainservice;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private CustomReportService customReportService;
	
	@Autowired
    private KusinaStringUtils kusinaStringUtils;
	
	
	@RequestMapping(value = ADD_FEEDBACKS, method = RequestMethod.POST)
    public JSONObject addFeedbacks(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("airid") String airid,
            @RequestParam("id") String id,
            @RequestParam("ratingModule") String ratingModule,
            @RequestParam("ratingScore") String ratingScore,
            @RequestParam("ratingComment") String ratingComment,
            @RequestParam("status") String status,
            @RequestParam("createdDate") String createdDate,
            @RequestParam("updatedDate") String updatedDate,
            @RequestParam("updatedBy") String updatedBy) {

        eid = kusinaStringUtils.sanitizeString(eid);
        airid = kusinaStringUtils.sanitizeString(airid);
        id = kusinaStringUtils.sanitizeString(id);
        ratingModule = kusinaStringUtils.sanitizeString(ratingModule);
        ratingScore = kusinaStringUtils.sanitizeString(ratingScore);
        ratingComment = kusinaStringUtils.sanitizeString(ratingComment);
        status = kusinaStringUtils.sanitizeString(status);
        createdDate = kusinaStringUtils.sanitizeString(createdDate);
        updatedDate = kusinaStringUtils.sanitizeString(updatedDate);
        updatedBy = kusinaStringUtils.sanitizeString(updatedBy);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        JSONObject insertObj = new JSONObject();

        insertObj.put("eid", eid);
        insertObj.put("airid", airid);
        insertObj.put("id", id);
        insertObj.put("ratingModule", ratingModule);
        insertObj.put("ratingScore", ratingScore);
        insertObj.put("ratingComment", ratingComment);
        insertObj.put("status", status);
        insertObj.put("createdDate", createdDate);
        insertObj.put("updatedDate", updatedDate);
        insertObj.put("updatedBy", updatedBy);
        
        results = feedbackService.addFeedbacks(insertObj);

        return results;
    }
	
/**    @comment FeedBack WebService version with ELK pagination approach ...
*	   @created 04/25/2018
*	   @author arnel.m.capendit
*/     
	    @RequestMapping(value = GET_FEEDBACKS, method = RequestMethod.GET)
	    public JSONObject getFeedbacks(HttpServletRequest request) {
	    	
	    	JSONObject args = (JSONObject) request.getAttribute("params");
	    	JSONObject user = (JSONObject) request.getAttribute("user");
	        JSONObject result = new JSONObject();
	        
	        if (USER_RIGHTS.equalsIgnoreCase(user.get("user_access").toString())) {
	            //Feedback service
	        	result.put("feedbacks", feedbackService.getFeedbacks(args).get("resultset"));        
	        	//Getting total
	            JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
	        	Long valueTotal = (Long) customReportTotal.get("value");
	            result.put("total", valueTotal); 
	        } else {
	            result.put("status", "You don't have enough permission to access this service");
	        }
	       
	        return result;
	    }
}
