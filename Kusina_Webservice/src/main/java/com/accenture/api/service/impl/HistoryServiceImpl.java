package com.accenture.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.api.model.History;
import com.accenture.api.model.HistoryModel;
import com.accenture.api.service.HistoryService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;

/**
*
* @author 	arnel.m.capendit
* @created  5/3/2018
* @comment 	Webservice requests for histories
*/
@Service
public class HistoryServiceImpl implements HistoryService {

	private static final String KUSINA_HISTORIES = "/histories/_search?pretty";
	private static final String POST_HISTORY = "/histories";
	
	@Autowired
	public KusinaConfigUtils configUtils;
	
	@Autowired
	public KusinaConfigUtils kusinaConfigUtils;
	
	@Autowired
	public KusinaElasticUtils kusinaElasticUtils;
	
	@Autowired
	public KusinaExtractionUtils kusinaExtractionUtils;
	
	@Override
	public List<HistoryModel> getHistories(JSONObject object) {
		
        List<HistoryModel> hm = new ArrayList<>() ;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append(KUSINA_HISTORIES);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("historyObj", object);
            o.put("payload", "histories");
                    			 
            hm =  kusinaExtractionUtils.getHistories(kusinaElasticUtils.sendRequest(o),object);

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

		return  hm;
	}

	@Override
	public JSONObject getReadAnnouncePerUser(JSONObject object) {
		
		JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append(KUSINA_HISTORIES);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("historyObj", object);
            o.put("payload", "readCount");
                    			 
            resultSet =  kusinaExtractionUtils.getReadAnnouncePerUser(kusinaElasticUtils.sendRequest(o));

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultSet;
	}
	
	@Override
	public JSONObject getReadAnnouncePerUserPerAnnounce(JSONObject object, String id) {
		
		JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append(KUSINA_HISTORIES);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("historyObj", object);
            o.put("id", id);
            o.put("payload", "readCountPerAnnounce");
                    			 
            resultSet =  kusinaExtractionUtils.getReadAnnouncePerUser(kusinaElasticUtils.sendRequest(o));

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultSet;
	}
	

	@Override
	public JSONObject getAllReadAnnounce(JSONObject object, String id) {
		
		JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append(KUSINA_HISTORIES);
              
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("historyObj", object);
            o.put("id", id);
            o.put("payload", "readCountAll");
                    			 
            resultSet =  kusinaExtractionUtils.getAllReadAnnounce(kusinaElasticUtils.sendRequest(o));

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultSet;
	}

	@Override
	public JSONObject saveHistory(JSONObject obj) {
		
		JSONObject result = null;
		HistoryModel hm;
        try {
        	hm = new HistoryModel();
        	hm.setType(obj.get("type").toString());
        	hm.setUserEid(obj.get("eid").toString());
        	hm.setDocId(obj.get("docId").toString());
        	hm.setActionType(obj.get("actionType").toString());
        	hm.setActionDate(obj.get("now").toString());
        	hm.setDueDate(obj.get("dueDate").toString());
        	
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append(POST_HISTORY);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "insertHistory");
            o.put("insertObj", hm);   
             
            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Insert History Status Success");

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		
		return result;
	}

	
	@Override
	public JSONObject postHistory(HistoryModel historyModel) {
		
		List<HistoryModel> _historyModel = new ArrayList<>();
		JSONObject result = null;
		HistoryModel hm;
        try {
        	_historyModel.add(historyModel);
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append(POST_HISTORY);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "postHistory");
            o.put("insertObj", _historyModel);   
             
            System.out.println("Insert ObjectList: "+ _historyModel);
            for(HistoryModel h: _historyModel) {
            	System.out.println(h.getType());
            	System.out.println(h.getActionDate());
            	System.out.println(h.getActionType());
            	System.out.println(h.getDocId());
            	System.out.println(h.getUserEid());
            }
            result = new JSONObject();
            result = kusinaElasticUtils.sendRequest(o);
            System.out.println("Insert History Status Success");

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		
		return result;
	}
	
	
	public JSONObject getHistoryAnnounceId(String id) {
		JSONObject result = null;

        try {
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append("/histories/_search?pretty");

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "historyId");
            o.put("id", id);

            result = new JSONObject();
            result.put("id", kusinaExtractionUtils.getHistoryAnnounceId(kusinaElasticUtils.sendRequest(o)));
            
        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		return result;
	}

	@Override
	public JSONObject deleteAnnouncement(String id) {

		JSONObject result = null;
        try {
            JSONObject o = new JSONObject();
            o.put("method", "DELETE");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
            sb.append("/histories");
            sb.append("/");
            sb.append(id);

            o.put("uri", sb.toString());
            o.put("hasPayload", false);
            o.put("isKusina", true);
            o.put("payload", "deleteAnnouncementHistories");

            result = new JSONObject();
            result = (JSONObject) kusinaElasticUtils.sendRequest(o);
            
            System.out.println("Delete histories completed.." + result.get("status"));

        } catch (Exception ex) {
            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		return result;
	}
}
