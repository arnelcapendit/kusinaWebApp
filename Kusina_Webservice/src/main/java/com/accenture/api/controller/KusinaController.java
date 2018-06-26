/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.api.controller;

import com.accenture.api.model.VisitorBean;
import com.accenture.api.service.KusinaService;
import com.accenture.api.utils.KusinaStringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author marlon.naraja
 */
@RestController
@CrossOrigin(origins = "*")
public class KusinaController {

    private static final String DEFAULT_VALUE = "";
    private static final String SESSION_EID = "sessionEID";

    private static final String APP_SAVE_SESSION = "/saveUserSession";
    
    private static final String LOGOUT = "/rest/logout";
    private static final String SET_POST_SESSION = "/rest/setPostSessionEid";
    private static final String SET_GET_SESSION = "/rest/setGetSessionEid";
    private static final String GET_SESSION = "/rest/getSessionEid";
    private static final String GET_USER_BY_EID = "/rest/getUserByEid";
    private static final String USER_APP_SETTINGS = "/rest/getAppSettings";
    private static final String USER_APP_PREFERENCES = "/rest/getAppPreferences";

    @Autowired
    private KusinaService kusinaService;

    @Autowired
    private KusinaStringUtils KusinaStringUtils;

    @RequestMapping(value = APP_SAVE_SESSION, method = RequestMethod.GET)
    public JSONObject saveUserSession(
            @RequestParam("eid") String eid,
            @RequestParam("nonce") String nonce,
            @RequestParam("authtime") String authtime,
            @RequestParam("exp") String exp) {

        JSONObject result;
        eid = KusinaStringUtils.sanitizeString(eid);
        nonce = KusinaStringUtils.sanitizeString(nonce);
        authtime = KusinaStringUtils.sanitizeString(authtime);
        exp = KusinaStringUtils.sanitizeString(exp);

        if (kusinaService.hasSession(eid)) {
            String _id = kusinaService.getSessionTypeId(eid, null);
            kusinaService.deleteSession(_id);
        }

        JSONObject o = new JSONObject();
        o.put("eid", eid);
        o.put("nonce", nonce);
        o.put("authtime", authtime);
        o.put("exp", exp);
        result = kusinaService.saveUserSession(o);

        return result;

    }

    @RequestMapping(value = USER_APP_SETTINGS, method = RequestMethod.GET)
    public JSONObject getAppSettings() {

        return kusinaService.getAppSettings();
    }

    @RequestMapping(value = USER_APP_PREFERENCES, method = RequestMethod.GET)
    public JSONObject getAppPreferences() {

        return kusinaService.getAppPreferences();
    }

    @RequestMapping(value = GET_USER_BY_EID, method = RequestMethod.GET)
    public JSONObject viewUserByEid(
            HttpServletRequest req,
            @RequestParam("eid") String eid) throws Exception {

        JSONObject user = kusinaService.getUserByEid(eid);

        return (JSONObject) user.get("user_airid");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = SET_GET_SESSION, method = RequestMethod.GET)
    public JSONObject setGetSession(@RequestParam(value = "eid") String eid, HttpServletRequest request, HttpSession session) {
        JSONObject retJson = new JSONObject();

        try {
            session.setAttribute(SESSION_EID, eid);
            retJson.put("status", "success");
        } catch (Exception e) {
            retJson.put("status", "failed - " + e.getMessage());
        }

        return retJson;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = SET_POST_SESSION, method = RequestMethod.POST)
    public JSONObject setSessionPOST(@RequestParam(value = "eid") String eid, HttpServletRequest request) {
        JSONObject retJson = new JSONObject();
        try {
            request.getSession().setAttribute(SESSION_EID, eid);
            retJson.put("status", "sucess");
        } catch (Exception e) {
            retJson.put("status", "failed - " + e.getMessage());
        }
        return retJson;
    }

    @RequestMapping(value = GET_SESSION, method = RequestMethod.GET, produces = "application/json")
    public VisitorBean getSessionEid(@RequestParam(value = "eid") String eid, HttpServletRequest request) {

        String sessiodEid = DEFAULT_VALUE;
        if (!ObjectUtils.isEmpty(request.getSession().getAttribute(SESSION_EID))) {
            sessiodEid = request.getSession().getAttribute(SESSION_EID).toString();
        }

        System.out.println("getSessionEID: " + sessiodEid);

        String ESO = eid == null ? DEFAULT_VALUE : eid;

        if (!StringUtils.isEmpty(ESO) || !StringUtils.isEmpty(sessiodEid)) {
            if (KusinaStringUtils.isValidEid(ESO)) {
                ESO = StringUtils.trimAllWhitespace(ESO);
                request.getSession().setAttribute(SESSION_EID, ESO);
                return kusinaService.getVisitorSession(ESO);
            } else {
                return kusinaService.getVisitorSession(StringUtils.trimAllWhitespace(sessiodEid));
            }

        } else {
            return kusinaService.getVisitorSession("nosession");
        }
    }

    @RequestMapping(value = LOGOUT, method = RequestMethod.GET)
    public void logout(
            @RequestParam(value = "eid") String eid,
            @RequestParam(value = "nonce") String nonce) {

        String _id = kusinaService.getSessionTypeId(eid, nonce);
        kusinaService.deleteSession(_id);
    }

}
