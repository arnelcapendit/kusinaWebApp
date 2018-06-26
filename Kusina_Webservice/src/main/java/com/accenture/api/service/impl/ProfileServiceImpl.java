package com.accenture.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.api.model.AirIdModel;
import com.accenture.api.model.AnnounceModel;
import com.accenture.api.model.ProfileModel;
import com.accenture.api.model.SiteIdModel;
import com.accenture.api.service.ProfileService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;

@Service
public class ProfileServiceImpl implements ProfileService{
	
	private static final String KUSINA_PROFILE = "/app-profiles/_search?pretty";
	
	@Autowired
	public KusinaConfigUtils kusinaConfigUtils;
	
	@Autowired
	public KusinaElasticUtils kusinaElasticUtils;
	
	@Autowired
	public KusinaExtractionUtils kusinaExtractionUtils;
	
	@Override
	public List<ProfileModel> getProfile(JSONObject object) {
		
        List<ProfileModel> am = new ArrayList<>() ;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append(KUSINA_PROFILE);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("profileObj", object);
            o.put("payload", "profile");
                    			 
            am =  kusinaExtractionUtils.getProfile(kusinaElasticUtils.sendRequest(o), object);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

		return  am;
	}
	
	@Override
	public List<AirIdModel> getAllAirid(JSONObject object) {
		
        List<AirIdModel> am = new ArrayList<>() ;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append(KUSINA_PROFILE);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("profileObj", object);
            o.put("payload", "all_airid");
                    			 
            am =  kusinaExtractionUtils.getAllAirid(kusinaElasticUtils.sendRequest(o), object);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

		return  am;
	}
	
	@Override
	public List<SiteIdModel> getSiteId(JSONObject object) {
		
        List<SiteIdModel> am = new ArrayList<>() ;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append(KUSINA_PROFILE);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("profileObj", object);
            o.put("payload", "site_id");
                    			 
            am =  kusinaExtractionUtils.getSiteId(kusinaElasticUtils.sendRequest(o), object);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

		return  am;
	}
		
	@Override
    public JSONObject getProfileExport(JSONObject object) {
        
        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");
           
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append(KUSINA_PROFILE);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("profileObj", object);
            o.put("payload", "profile");
            
            resultSet = (JSONObject) kusinaExtractionUtils.getProfileExport2(kusinaElasticUtils.sendRequest(o), object);
            
        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
	
	@Override
    public JSONObject addProfile(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append("/app-profiles");

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "addProfile");
            o.put("insertObj", object);

            System.out.println("This is object for addProfile: " + object);

            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Insert Profile Status: " + result.get("status").toString());

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
	
	@Override
    public JSONObject editProfile(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append("/app-profiles");
            sb.append("/");
            sb.append(object.get("id"));
            sb.append("/_update");

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "editProfile");
            o.put("insertObj", object);

            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Edit Profile Status: " + result.get("status").toString());

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
	
	@Override
    public JSONObject deleteProfile(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "DELETE");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append("/app-profiles");
            sb.append("/");
            sb.append(object.get("id"));

            o.put("uri", sb.toString());
            o.put("hasPayload", false);
            o.put("isKusina", true);
            o.put("payload", "deleteProfile");
//            o.put("insertObj", object);

            System.out.println("This is object for deleteProfile: " + object);

            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Delete Profile Status: " + result.get("status").toString());

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
