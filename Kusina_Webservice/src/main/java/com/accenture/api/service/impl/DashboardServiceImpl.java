/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.api.service.impl;

import com.accenture.api.service.DashboardService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaConstantUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author marlon.naraja
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    private static final String WEBMETRIC_PIWIK = "/piwik/_search?pretty=true";
    private static final String AIRIDS = "/app-profiles/_search?pretty=true";
    

    @Autowired
    private KusinaConfigUtils kusinaConfigUtils;

    @Autowired
    private KusinaConstantUtils kusinaConstantUtils;

    @Autowired
    private KusinaElasticUtils kusinaElasticUtils;

    @Autowired
    private KusinaExtractionUtils kusinaExtractionUtils;

    @Override
    public JSONObject getVisualization(JSONObject object) {

        JSONObject resultSet = new JSONObject();
        try {
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);
            o.put("uri", sb.toString());
            o.put("isKusina", false);
            o.put("hasPayload", true);
            o.put("id", object.get("id"));
            o.put("gte", object.get("gte"));
            o.put("lte", object.get("lte"));
            o.put("itv", object.get("interval"));
            o.put("tz", object.get("user_timezone"));
            o.put("max", object.get("max"));
            o.put("payload", object.get("payload"));
            
            Object userAirid = object.get("airid");
            System.out.println("userAirid: " + userAirid);
            if (userAirid instanceof JSONArray) {
                o.put("airid", userAirid);
            } else {
                o.put("airid", Arrays.asList("\"" + userAirid + "\""));
                o.put("userObj", object);

            }
           
            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), 
                    object.get("payload").toString()
                    );

        } catch (Exception ex) {
            Logger.getLogger(DashboardServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    @Override
    public JSONObject getAllFilters(JSONObject object) {
    	String timezoneOffset = (String) object.get("user_timezone");
        JSONObject resultSet = new JSONObject();
        try {
            JSONObject o = new JSONObject();
            o.put("method", "POST");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);
            o.put("uri", sb.toString());
            o.put("isKusina", false);
            o.put("hasPayload", true);
            o.put("id", object.get("id"));
            o.put("gte", object.get("gte"));
            o.put("lte", object.get("lte"));
            o.put("itv", object.get("interval"));
            o.put("tz", object.get("user_timezone"));
            o.put("max", object.get("max"));

            Object userAirid = object.get("airid");
            System.out.println("userAirid: " + userAirid);
            if (userAirid instanceof JSONArray) {
                o.put("airid", userAirid);
            } else {
                o.put("airid", Arrays.asList("\"" + userAirid + "\""));
                o.put("userObj", object);

            }

            for (String s : kusinaConstantUtils.getVisualizationList()) {
                o.put("payload", s);
                resultSet.put(s, kusinaExtractionUtils.extractAggregationDashboard(
                		kusinaElasticUtils.sendRequest(o), s, timezoneOffset));
            }
        } catch (Exception ex) {
            Logger.getLogger(DashboardServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    @Override
    public JSONObject getAppFilter(JSONObject object) {

        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
            sb.append(AIRIDS);

            o.put("uri", sb.toString());
            o.put("isKusina", false);
            o.put("hasPayload", true);
            o.put("payload", "appname");

            Object userAirid = object.get("user_airid");
            
            if (userAirid instanceof JSONArray) {
                o.put("userObj", object);      
            } else {
                o.put("airid", Arrays.asList("\"" + userAirid + "\""));
                o.put("userObj", object);
            }
            
            Object userId = object.get("user_id");
            if (userId instanceof JSONArray) {
                o.put("userObj", object);      
            } else {
                o.put("id", Arrays.asList("\"" + userId + "\""));
                o.put("userObj", object);
            }

            resultSet = kusinaExtractionUtils.extractFilterDisplay(kusinaElasticUtils.sendRequest(o));
        } catch (Exception ex) {
            Logger.getLogger(DashboardServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

}
