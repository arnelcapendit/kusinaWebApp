package com.accenture.api.service;

import com.accenture.api.model.VisitorBean;
import java.io.FileNotFoundException;
import org.json.simple.JSONObject;

/**
 *
 * @author marlon.naraja
 */
public interface KusinaService {

    /**
     *
     * @param eid
     * @return
     */
    public JSONObject getUserByEid(String eid);

    /**
     *
     * @param eid
     * @return
     */
    public JSONObject getUserAirId(String eid);

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
    public boolean hasSession(String eid, String nonce);

    /**
     *
     * @param eid
     * @return
     */
    public boolean hasSession(String eid);

    /**
     * Remove user session by id
     *
     * @param id
     */
    public void deleteSession(String id);

    /**
     *
     * @param eid
     * @return
     */
    VisitorBean getVisitorSession(String eid);

    // **************** arnel R3 - 12.12.17
    /**
     *
     * @param object
     * @return
     */
    public JSONObject getAllUsers();

    /**
     *
     * @param object
     * @return eid
     */
    public String getUserTypeId(String eid);

    /**
     *
     * @param object
     * @return boolean
     */
    public boolean deleteUserDetails(String id) throws FileNotFoundException;

    /**
     *
     * @param object
     * @return boolean
     */
    public JSONObject insertUserDetails(JSONObject object);

    /**
     *
     * @param object
     * @return boolean
     */
    public JSONObject updateUserDetails(JSONObject object);

    /**
     *
     * @param obj
     * @return
     */
    public JSONObject isUserAndSessionExists(JSONObject obj);

}
