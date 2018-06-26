package com.accenture.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.api.model.AnnounceModel;
import com.accenture.api.service.AnnounceService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;

@Service
public class AnnounceServiceImpl implements AnnounceService{
	
	private static final String KUSINA_ANNOUNCE = "/announcements/_search?pretty";

	@Autowired
	public KusinaConfigUtils kusinaConfigUtils;
	
	@Autowired
	public KusinaElasticUtils kusinaElasticUtils;
	
	@Autowired
	public KusinaExtractionUtils kusinaExtractionUtils;
	
	@Override
	public List<AnnounceModel> getAnnounce(JSONObject object) {
		
        List<AnnounceModel> am = new ArrayList<>() ;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
            sb.append(KUSINA_ANNOUNCE);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("announceObj", object);
            o.put("payload", "announce");
                    			 
            am =  kusinaExtractionUtils.getAnnounce(kusinaElasticUtils.sendRequest(o), object);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

		return  am;
	}

	@Override
	public List<AnnounceModel> getAnnounceLive(JSONObject object) {
		
        List<AnnounceModel> am = new ArrayList<>() ;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
            sb.append(KUSINA_ANNOUNCE);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("announceObj", object);
            o.put("payload", "announcelive");
                    			 
            am =  kusinaExtractionUtils.getAnnounceLive(kusinaElasticUtils.sendRequest(o), object);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

		return  am;
	}

	
	
	@Override
	public List<AnnounceModel> getAnnounceAll(JSONObject object) {
		
        List<AnnounceModel> am = new ArrayList<>() ;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
            sb.append(KUSINA_ANNOUNCE);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("announceObj", object);
            o.put("payload", "announceall");
                    			 
            am =  kusinaExtractionUtils.getAnnounce(kusinaElasticUtils.sendRequest(o), object);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

		return  am;
	}
	
	@Override
	public JSONObject getAnnounceCountBeforeDueDate(JSONObject object) {
		
		   JSONObject resultSet = null;
	        try {
	        	resultSet = new JSONObject();
	            JSONObject o = new JSONObject();
	            o.put("method", "GET");

	            StringBuilder sb = new StringBuilder();
	            sb.append("/");
	            sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
	            sb.append(KUSINA_ANNOUNCE);
	              
	            o.put("uri", sb.toString());
	            o.put("hasPayload", true);
	            o.put("isKusina", false);
	            o.put("announceObj", object);
	            o.put("payload", "announceDueDateCount");
	                    			 
	            resultSet =  kusinaExtractionUtils.getAnnounceCountBeforeDueDate(kusinaElasticUtils.sendRequest(o));

	        } catch (Exception ex) {
	            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		return resultSet;
	}
	
	@Override
    public JSONObject addAnnouncement(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
            sb.append("/announcements");

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "addAnnouncement");
            o.put("insertObj", object);

            System.out.println("This is object for addFeedback: " + object);

            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Insert Feedback Status: " + result.get("status").toString());

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
	
	@Override
    public JSONObject editAnnouncement(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
            sb.append("/announcements");
            sb.append("/");
            sb.append(object.get("id"));
            sb.append("/_update");

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "editAnnouncement");
            o.put("insertObj", object);

            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Delete History Status: " + result.get("status").toString());

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
	
	@Override
    public JSONObject deleteAnnouncement(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "DELETE");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
            sb.append("/announcements");
            sb.append("/");
            sb.append(object.get("id"));

            o.put("uri", sb.toString());
            o.put("hasPayload", false);
            o.put("isKusina", true);
            o.put("payload", "deleteAnnouncement");
//            o.put("insertObj", object);

            System.out.println("This is object for deleteFeedback: " + object);

            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Delete Announcement Status: " + result.get("status").toString());

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }


}
