package com.accenture.api.controller;

import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.KusinaService;
import com.accenture.api.service.UserService;
import com.accenture.api.utils.KusinaStringUtils;
import java.io.FileNotFoundException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private static final String USER_RIGHTS = "Super Administrator";
    private static final String GET_USERS = "/rest/getUsers";
    private static final String GET_ALL_USERS = "/rest/getAllUsers";
    private static final String GET_SINGLE_USER_BY_EID = "/rest/getSingleUserByEid";
    private static final String DELETE_USER_DETAILS = "/rest/deleteUserDetails";
    private static final String INSERT_USER_DETAILS = "/rest/insertUserDetails";
    private static final String UPDATE_USER_DETAILS = "/rest/updateUserDetails";
    private static final String GET_USER_ACCESS = "/rest/getUserAccess";

    @Autowired
    private KusinaService mainservice;

    @Autowired
    private KusinaStringUtils kusinaStringUtils;
    
    @Autowired
    private UserService userservice;
    
    @Autowired
    private CustomReportService customReportService;

    //Revised Version
    @RequestMapping(value = GET_ALL_USERS, method = RequestMethod.GET)
    public JSONObject getAllUsers(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now) {

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        userObject.put("eid", eid);
        userObject.put("nonce", nonce);
        userObject.put("now", now);

        String sessionCheck = mainservice.isUserAndSessionExists(userObject).get("status").toString();
        if (sessionCheck == "session exists") {
            JSONObject isUserAdmin = mainservice.getUserByEid(eid);

            if (isUserAdmin.get("user_access").equals(USER_RIGHTS)) {
                results.put("allusers", mainservice.getAllUsers().get("allusers"));
            } else {
                results.put("status", "You don't have enough permission to access this service");
            }
        } else {
            results.put("status", sessionCheck);
        }

        return results;
    }

    @RequestMapping(value = GET_SINGLE_USER_BY_EID, method = RequestMethod.GET)
    public JSONObject getSingleUserByEid(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("eidSearch") String eidSearch) {

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        userObject.put("eid", eid);
        userObject.put("nonce", nonce);
        userObject.put("now", now);

        String sessionCheck = mainservice.isUserAndSessionExists(userObject).get("status").toString();
        if (sessionCheck == "session exists") {
            JSONObject isUserAdmin = mainservice.getUserByEid(eid);

            if (isUserAdmin.get("user_access").equals(USER_RIGHTS)) {
                JSONObject userToSearch = mainservice.getUserByEid(eidSearch);
                if (userToSearch != null) {
                    results.put("userDetails", mainservice.getUserByEid(eidSearch));
                } else {
                    results.put("status", "User Not exists");
                }
            } else {
                results.put("status", "You don't have enough permission to access this service");
            }
        } else {
            results.put("status", sessionCheck);
        }

        return results;
    }

    @RequestMapping(value = DELETE_USER_DETAILS, method = RequestMethod.DELETE)
    public JSONObject deleteUserDetails(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("eidDelete") String eidDelete) throws FileNotFoundException {

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        userObject.put("eid", eid);
        userObject.put("nonce", nonce);
        userObject.put("now", now);

        String sessionCheck = mainservice.isUserAndSessionExists(userObject).get("status").toString();
        if (sessionCheck == "session exists") {
            JSONObject isUserAdmin = mainservice.getUserByEid(eid);

            if (isUserAdmin.get("user_access").equals(USER_RIGHTS)) {
                JSONObject userToSearch = mainservice.getUserByEid(eidDelete);
                if (userToSearch != null) {
                    String _id = mainservice.getUserTypeId(eidDelete);
                    if (mainservice.deleteUserDetails(_id)) {
                        results.put("status", "deleted");
                    } else {
                        results.put("status", "failed to delete");
                    }
                } else {
                    results.put("status", "No User Exists");
                }
            } else {
                results.put("status", "You don't have enough permission to access this service");
            }
        } else {
            results.put("status", sessionCheck);
        }

        return results;
    }

    @RequestMapping(value = INSERT_USER_DETAILS, method = RequestMethod.POST)
    public JSONObject insertUserDetails(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("eid2") String eid2,
            @RequestParam("id") String id,
            @RequestParam("team") String team,
            @RequestParam("airid") String airid,
            @RequestParam("access") String access,
            @RequestParam("expiry") String expiry,
            @RequestParam("status") String status,
            @RequestParam("createdDate") String createdDate,
            @RequestParam("updatedDate") String updatedDate,
            @RequestParam("updatedBy") String updatedBy) {

        eid2 = kusinaStringUtils.sanitizeString(eid2);
        id = kusinaStringUtils.sanitizeString(id);
        team = kusinaStringUtils.sanitizeString(team);
        airid = kusinaStringUtils.sanitizeString(airid);
        access = kusinaStringUtils.sanitizeString(access);
        expiry = kusinaStringUtils.sanitizeString(expiry);
        status = kusinaStringUtils.sanitizeString(status);
        createdDate = kusinaStringUtils.sanitizeString(createdDate);
        updatedDate = kusinaStringUtils.sanitizeString(updatedDate);
        updatedBy = kusinaStringUtils.sanitizeString(updatedBy);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        userObject.put("eid", eid);
        userObject.put("nonce", nonce);
        userObject.put("now", now);

        JSONObject insertObj = new JSONObject();

        insertObj.put("eid2", eid2);
        insertObj.put("id", id);
        insertObj.put("team", team);
        insertObj.put("airid", airid);
        insertObj.put("access", access);
        insertObj.put("expiry", expiry);
        insertObj.put("status", status);
        insertObj.put("createdDate", createdDate);
        insertObj.put("updatedDate", updatedDate);
        insertObj.put("updatedBy", updatedBy);

        String sessionCheck = mainservice.isUserAndSessionExists(userObject).get("status").toString();
        if (sessionCheck == "session exists") {
            JSONObject isUserAdmin = mainservice.getUserByEid(eid);

            if (isUserAdmin.get("user_access").equals(USER_RIGHTS)) {
                JSONObject user = mainservice.getUserByEid(eid2);

                if (user == null) {
                    results = mainservice.insertUserDetails(insertObj);
                    System.out.println("Inserting user are successfully completed");
                } else {
                    System.out.println("Inserting user are not valid");
                    results.put("status", "User Already Exists");
                }
            } else {
                results.put("status", "You don't have enough permission to access this service");
            }
        } else {
            results.put("status", sessionCheck);
        }

        return results;
    }

    @RequestMapping(value = UPDATE_USER_DETAILS, method = RequestMethod.POST)
    public JSONObject updateUserDetails(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("now") String now,
            @RequestParam("eid2") String eid2,
            @RequestParam("id") String id,
            @RequestParam("team") String team,
            @RequestParam("airid") String airid,
            @RequestParam("access") String access,
            @RequestParam("expiry") String expiry,
            @RequestParam("status") String status,
            @RequestParam("createdDate") String createdDate,
            @RequestParam("updatedDate") String updatedDate,
            @RequestParam("updatedBy") String updatedBy) throws FileNotFoundException {

        eid2 = kusinaStringUtils.sanitizeString(eid2);
        id = kusinaStringUtils.sanitizeString(id);
        team = kusinaStringUtils.sanitizeString(team);
        airid = kusinaStringUtils.sanitizeString(airid);
        access = kusinaStringUtils.sanitizeString(access);
        expiry = kusinaStringUtils.sanitizeString(expiry);
        status = kusinaStringUtils.sanitizeString(status);
        createdDate = kusinaStringUtils.sanitizeString(createdDate);
        updatedDate = kusinaStringUtils.sanitizeString(updatedDate);
        updatedBy = kusinaStringUtils.sanitizeString(updatedBy);

        JSONObject userObject = new JSONObject();
        JSONObject results = new JSONObject();
        userObject.put("eid", eid);
        userObject.put("nonce", nonce);
        userObject.put("now", now);

        JSONObject updateObj = new JSONObject();

        updateObj.put("eid2", eid2);
        updateObj.put("id", id);
        updateObj.put("team", team);
        updateObj.put("airid", airid);
        updateObj.put("access", access);
        updateObj.put("expiry", expiry);
        updateObj.put("status", status);
        updateObj.put("createdDate", createdDate);
        updateObj.put("updatedDate", updatedDate);
        updateObj.put("updatedBy", updatedBy);

        String sessionCheck = mainservice.isUserAndSessionExists(userObject).get("status").toString();
        if (sessionCheck.equalsIgnoreCase("session exists")) {
            JSONObject isUserAdmin = mainservice.getUserByEid(eid);

            if (isUserAdmin.get("user_access").equals(USER_RIGHTS)) {
                JSONObject userToSearch = mainservice.getUserByEid(eid2);
                if (userToSearch != null) {
                    String _id = mainservice.getUserTypeId(eid2);
                    System.out.println("User EID: " + _id);
                    updateObj.put("_id", _id);
                    results = mainservice.updateUserDetails(updateObj);
                } else {
                    results.put("status", "No User Exists");
                }
            } else {
                results.put("status", "You don't have enough permission to access this service");
            }
        } else {
            results.put("status", sessionCheck);
        }

        return results;
    }
    
    @RequestMapping(value=GET_USER_ACCESS, method=RequestMethod.GET)
    public JSONObject getUserAccess(HttpServletRequest request) {
		
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        
        JSONObject userToSearch = mainservice.getUserByEid((String) args.get("eid"));

        result.put("user_access", userToSearch.get("user_access"));
    	
    	
    	return result;
    	
    }
    
/**    @comment User WebService version with ELK pagination approach ...
*	   @created 04/20/2018
*	   @author arnel.m.capendit
*/     
    @RequestMapping(value = GET_USERS, method = RequestMethod.GET)
    public JSONObject getUsers(HttpServletRequest request) {
    	
    	JSONObject args = (JSONObject) request.getAttribute("params");
    	JSONObject user = (JSONObject) request.getAttribute("user");
        JSONObject result = new JSONObject();
        
        if (USER_RIGHTS.equalsIgnoreCase(user.get("user_access").toString())) {
            //User service
        	result.put("allusers", userservice.getUsers(args).get("resultset"));        
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
