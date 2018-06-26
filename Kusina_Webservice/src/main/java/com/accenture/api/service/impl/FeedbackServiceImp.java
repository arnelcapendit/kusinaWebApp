package com.accenture.api.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accenture.api.service.FeedbackService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;

@Service
public class FeedbackServiceImp implements FeedbackService {
	
	private static final String KUSINA_FEEDBACKS = "/feedbacks/_search?pretty=true";
	
	@Autowired
    private KusinaConfigUtils kusinaConfigUtils;
	
	@Autowired
    private KusinaElasticUtils kusinaElasticUtils;
	
    @Override
    public JSONObject addFeedbacks(JSONObject object) {

        JSONObject result = null;

        try {

            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
//  		@Comment: Spliting index with multiple types.
//           sb.append(kusinaConfigUtils.getAppKusinaPrefix().replace("*", ""));
            sb.append(kusinaConfigUtils.getAppKusinaFeedbacksPrefix());
            sb.append("/feedbacks");

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", true);
            o.put("payload", "addFeedback");
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
	public JSONObject getFeedbacks(JSONObject obj) {
		 JSONObject resultSet = null;
	        try {
	            resultSet = new JSONObject();
	            JSONObject o = new JSONObject();
	            o.put("method", "GET");

	            StringBuilder sb = new StringBuilder();
	            sb.append("/");
	            sb.append(kusinaConfigUtils.getAppKusinaFeedbacksPrefix());
	            sb.append(KUSINA_FEEDBACKS);
	              
	            o.put("uri", sb.toString());
	            o.put("hasPayload", true);
	            o.put("isKusina", true);
	            o.put("userObj", obj);
	            o.put("payload", "feedbacks");
	            
	            resultSet = kusinaElasticUtils.sendRequest(o);

	        } catch (Exception ex) {
	            Logger.getLogger(KusinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	        }

	        return resultSet;
	}
}
