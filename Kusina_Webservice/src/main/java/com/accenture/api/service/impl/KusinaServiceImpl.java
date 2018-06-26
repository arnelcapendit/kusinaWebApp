package com.accenture.api.service.impl;

import com.accenture.api.dao.VisitorDao;
import com.accenture.api.model.VisitorBean;
import com.accenture.api.service.KusinaService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaConstantUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaStringUtils;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author marlon.naraja
 */
@Service
public class KusinaServiceImpl implements KusinaService {

    private static final String KUSINA_SAVE_DELETE_SESSIONS = "/sessions";
    private static final String KUSINA_GET_SESSIONS = "/sessions/_search?pretty=true";
    private static final String KUSINA_USERS = "/users/_search?pretty=true";
    private static final String KUSINA_SETTINGS = "/settings/_search?pretty=true";
    private static final String KUSINA_PROJECT_PREFERENCES = "/project_preferences/_search?pretty=true";
    private static final String GET_SINGLE_USER_BY_EID = "/getSingleUserByEid";
    private static final String DELETE_USER_DETAILS = "/deleteUserDetails";
    private static final String INSERT_USER_DETAILS = "/insertUserDetails";
    private static final String UPDATE_USER_DETAILS = "/updateUserDetails";

    private static final String KUSINA_SAVE_DELETE_USERS = "/users";
    private static final String KUSINA_UPDATE_USERS = "/_update?pretty";

    @Autowired
    private KusinaConfigUtils kusinaConfigUtils;
    @Autowired
    private KusinaConstantUtils kusinaConstantUtils;
    @Autowired
    private KusinaElasticUtils kusinaElasticUtils;
    @Autowired
    private KusinaStringUtils kusinaStringUtils;

    @Autowired
    private VisitorDao visitorDao;

    @Override
    public JSONObject getUserByEid(String eid) {
        JSONObject o = new JSONObject();
        o.put("method", "GET");
        StringBuilder sb = new StringBuilder();
        sb.append("/");
//		@Comment: Spliting index with multiple types.
//      sb.append(kusinaConfigUtils.getAppKusinaPrefix());
        sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
        sb.append(KUSINA_USERS);
        o.put("uri", sb.toString());
        o.put("isKusina", true);
        o.put("hasPayload", true);
        o.put("eid", eid);
        o.put("payload", "user");

        JSONObject user = null;
        try {
            user = kusinaElasticUtils.sendRequest(o);
            if (user != null) {
                for (String s : kusinaConstantUtils.removeUserPropList()) {
                    user.remove(s);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        // validate hits;
        return user;
    }

    @Override
    public JSONObject getUserAirId(String eid) {

        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
            sb.append(KUSINA_USERS);

            o.put("uri", sb.toString());
            o.put("isKusina", true);
            o.put("hasPayload", true);
            o.put("eid", eid);
            o.put("payload", "user");

            resultSet = kusinaElasticUtils.sendRequest(o);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    @Override
    public JSONObject getAppSettings() {

        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaSettingsPrefix());
            sb.append(KUSINA_SETTINGS);

            o.put("uri", sb.toString());
            o.put("hasPayload", false);
            o.put("isKusina", true);
            o.put("payload", "");
            resultSet = kusinaElasticUtils.sendRequest(o);

            for (String s : kusinaConstantUtils.removeSettingList()) {
                resultSet.remove(s);
            }

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    @Override
    public JSONObject getAppPreferences() {

        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaPreferencesPrefix());
            sb.append(KUSINA_PROJECT_PREFERENCES);

            o.put("uri", sb.toString());
            o.put("hasPayload", false);
            o.put("isKusina", true);
            o.put("payload", "");

            resultSet = kusinaElasticUtils.sendRequest(o);

            for (String s : kusinaConstantUtils.removePreferencesList()) {
                resultSet.remove(s);
            }

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    @Override
    public JSONObject saveUserSession(JSONObject object) {

        JSONObject resultSet = null;
        JSONObject user = getUserByEid(object.get("eid").toString());
        if(user!=null) {
	        try {
	        	
	            JSONObject o = new JSONObject();
	            o.put("method", "POST");
	
	            StringBuilder sb = new StringBuilder();
	            sb.append("/");
	//  		@Comment: Spliting index with multiple types.
	//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
	            sb.append(kusinaConfigUtils.getAppKusinaSessionsPrefix());
	            sb.append(KUSINA_SAVE_DELETE_SESSIONS);
	
	            o.put("uri", sb.toString());
	            o.put("hasPayload", true);
	            o.put("isKusina", true);
	            o.put("payload", "s_session");
	            o.put("sessionObject", object);
	
	            resultSet = kusinaElasticUtils.sendRequest(o);
	            System.out.println("Save Session Status: " + resultSet.get("status").toString());
	        } catch (Exception ex) {
	            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	        }
        }
        return resultSet;
    }

    @Override
    public JSONObject getUserSession(JSONObject object) {

        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaSessionsPrefix());
            sb.append(KUSINA_GET_SESSIONS);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "g_session");
            o.put("userSession", object);

            resultSet = kusinaElasticUtils.sendRequest(o);

            if (resultSet == null) {
                resultSet = new JSONObject();
                resultSet.put("session", "expired");

            } else {
                resultSet = new JSONObject();
                resultSet.put("session", "active");
            }

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    @Override
    public String getSessionTypeId(String eid, String nonce) {

        String result = "";
        try {
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaSessionsPrefix());
            sb.append(KUSINA_GET_SESSIONS);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "sessionTypeId");
            o.put("eid", eid);
            o.put("nonce", nonce);

            result = (String) kusinaElasticUtils.sendRequest(o).get("_id");

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public boolean hasSession(String eid, String nonce) {

        try {
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaSessionsPrefix());
            sb.append(KUSINA_GET_SESSIONS);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "c_session");
            o.put("eid", eid);
            o.put("nonce", nonce);

            if (kusinaElasticUtils.sendRequest(o) != null) {
                return true;
            }

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean hasSession(String eid) {

        try {
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaSessionsPrefix());
            sb.append(KUSINA_GET_SESSIONS);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "c_session");
            o.put("eid", eid);

            if (kusinaElasticUtils.sendRequest(o) != null) {
                return true;
            }

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public void deleteSession(String id) {

        try {
            JSONObject o = new JSONObject();
            o.put("method", "DELETE");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaSessionsPrefix());
            sb.append(KUSINA_SAVE_DELETE_SESSIONS);
            sb.append("/");
            sb.append(id);

            o.put("uri", sb.toString());
            o.put("hasPayload", false);
            o.put("isKusina", true);
            o.put("payload", "d_session");

            String resultSet = kusinaElasticUtils.sendRequest(o).get("status").toString();
            System.out.println("Delete Session Status: " + resultSet);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public VisitorBean getVisitorSession(String eid) {

        return visitorDao.getVisitorSession(eid);
    }

    @Override
    public JSONObject getAllUsers() {

        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
            sb.append(KUSINA_USERS);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "allusers");

            resultSet = kusinaElasticUtils.sendRequest(o);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    @Override
    public String getUserTypeId(String eid) {

        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix());
            sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
            sb.append(KUSINA_USERS);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "userTypeId");
            o.put("eid", eid);

            return (String) kusinaElasticUtils.sendRequest(o).get("_id");

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean deleteUserDetails(String id) throws FileNotFoundException {

        try {
            JSONObject o = new JSONObject();
            o.put("method", "DELETE");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
            sb.append(KUSINA_SAVE_DELETE_USERS);
            sb.append("/");
            sb.append(id);

            o.put("uri", sb.toString());
            o.put("hasPayload", false);
            o.put("isKusina", true);
            o.put("payload", "d_user");

            try {
                String resultSet = kusinaElasticUtils.sendRequest(o).get("status").toString();
                if (resultSet != null) {
                    System.out.println("Delete User Status: " + resultSet);
                    return true;
                } else {
                    System.out.println("Delete User Status: null");
                    return false;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public JSONObject insertUserDetails(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//           sb.append(kusinaConfigUtils.getAppKusinaPrefix().replace("*", ""));
            sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
            sb.append(KUSINA_SAVE_DELETE_USERS);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "insert");
            o.put("insertObj", object);

            System.out.println("This is object for insert: " + object);

            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Insert User Status: " + result.get("status").toString());

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public JSONObject updateUserDetails(JSONObject object) {
    	System.out.println("User EID: " + object.get("_id"));
        JSONObject result = null;
        try {
            result = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//          sb.append(kusinaConfigUtils.getAppKusinaPrefix().replace("*", ""));
            sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
            sb.append(KUSINA_SAVE_DELETE_USERS);
            sb.append("/");
            sb.append(object.get("_id"));
            sb.append(KUSINA_UPDATE_USERS);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "update");
            o.put("updateObj", object);

            result = kusinaElasticUtils.sendRequest(o);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public JSONObject isUserAndSessionExists(JSONObject obj) {
        String eid = kusinaStringUtils.sanitizeString((String) obj.get("eid"));
        String nonce = kusinaStringUtils.sanitizeString((String) obj.get("nonce"));
        String now = kusinaStringUtils.sanitizeString((String) obj.get("now"));

        JSONObject ar = new JSONObject();
        JSONObject user = getUserByEid(eid);

        if (user != null) {
            if (hasSession(eid, nonce)) {
                JSONObject userSessionObj = new JSONObject();
                userSessionObj.put("eid", eid);
                userSessionObj.put("nonce", nonce);
                userSessionObj.put("now", now);

                JSONObject currentSession = getUserSession(userSessionObj);
                String strCurrentSession = (String) currentSession.get("session");

                if (strCurrentSession.equalsIgnoreCase("active")) {
                    ar.put("status", "session exists");
                } else {
                    String _id = getSessionTypeId(eid, nonce);
                    deleteSession(_id);
                    ar.put("status", "session expired");
                }

            } else {
                ar.put("status", "no session found");
            }
        } else {
            ar.put("status", "unauthorized user");
        }

        return ar;
    }
    

}
