package com.accenture.api.service;

import org.json.simple.JSONObject;
import  com.accenture.api.model.VisitorBean;

/**
 *
 * @author marlon.naraja
 */
public interface MainService {

    /**
     *
     * @param eid
     * @return
     */
    public JSONObject getUserByEid(String eid);

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getAllFilters(JSONObject object);

    /**
     *
     * @param eid
     * @return
     */
    public JSONObject getUserAirId(String eid);

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getAppFilter(JSONObject object);

    /**
     *
     * @return
     */
    public JSONObject getAppSettings();

    /**
     *
     * @return
     */
    public JSONObject getAppPreferences();
    
   /**
    * 
    * @param object
    * @return 
    */
    public JSONObject saveUserSession(JSONObject object);
    
    /**
     * 
     * @param object
     * @return 
     */
    public JSONObject getUserSession(JSONObject object);
    /**
     * 
     * @param eid
     * @param nonce
     * @return 
     */
    public String getSessionTypeId(String eid, String nonce);
    
    /**
     * Validate session by eid and nonce
     * 
     * @param eid
     * @param nonce
     * @return true if session exists otherwise false
     */
    public boolean isSessionExists(String eid, String nonce);
    
    /**
     * 
     * @param eid
     * @return 
     */
    public boolean isSessionExists(String eid);
    /**
     *  Remove user session by id
     * @param id 
     */
    public void deleteSession(String id);

    /**
     *
     * @param eid
     * @return
     */
    VisitorBean getVisitorSession(String eid);
    
    /**
     * 
     * @param object
     * @return 
     */
    public JSONObject getCustomReports(JSONObject object);
    
    /**
     * @author arnel.m.capendit
     * @date   11/23/2017
     * @param object
     * @return 
     */
    public JSONObject getCustomReportsOverviewPageMetrics (JSONObject object);
    
    /**
     * @author arnel.m.capendit
     * @date   11/23/2017
     * @param object
     * @return 
     */
    public JSONObject getCustomReportsOverviewUserMetrics (JSONObject object);
    
    /**
     * 
     * @return 
     */
    public JSONObject getMyTEUniquePageUrlList();
    /**
     * 
     * @param object
     * @return 
     */
    public JSONObject getMyTEUniquePageUrl(JSONObject object);
    /**
     * 
     * @param object
     * @return 
     */
    public JSONObject getUsageByUser(JSONObject object);
    /**
     * 
     * @param object
     * @return 
     */
    public JSONObject getVisualization(JSONObject object);
}
