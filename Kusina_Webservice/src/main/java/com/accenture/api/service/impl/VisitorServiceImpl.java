package com.accenture.api.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.accenture.api.service.VisitorService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;
import com.accenture.api.utils.KusinaPayloadUtils;

@Service
public class VisitorServiceImpl implements VisitorService {
private static final String WEBMETRIC_PIWIK = "/piwik/_search?pretty=true";
    
    
    @Autowired
    private KusinaConfigUtils kusinaConfigUtils;
    
    @Autowired
    private KusinaPayloadUtils kusinaPayloadUtils;
    
    @Autowired
    private KusinaElasticUtils kusinaElasticUtils;
    
    @Autowired
    private KusinaExtractionUtils kusinaExtractionUtils;
    
    
    @Override
    public JSONObject getVisitorLogs(JSONObject object) {
        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");
            
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);
            
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "visitorLogs");
            o.put("visitorObject", object);
            
            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(kusinaElasticUtils.sendRequest(o), "visitorLogs");

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return resultSet;
    }

    @Override
    public JSONObject getVisitorLogsV2(JSONObject object) {
        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");
            
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);
            
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "visitorLogsV2");
            o.put("visitorObject", object);
            
            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(kusinaElasticUtils.sendRequest(o), "visitorLogsV2");

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return resultSet;
    }
    
    @Override
    public JSONObject getVisitorLogsV3(JSONObject object) {
        
        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "visitorLogReportPagination");
            o.put("reportObj", object);
           
            
            //Getting Initial Filter values...
            JSONObject resultFilter = new JSONObject();
            resultFilter = kusinaExtractionUtils.getVisitorLogsV3(kusinaElasticUtils.sendRequest(o));
            
            //Consuming and using filter names to return aggregated values...
            o.replace("payload", "visitorLogReportPaginationFilter");
            o.put("resultFilter", resultFilter);
            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
            kusinaElasticUtils.sendRequest(o), "visitorLogReportPaginationFilter", object);
            
        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    @Override
    public JSONObject getVisitorLogsExport(JSONObject object) {
        
        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "visitorLogReportPagination");
            o.put("reportObj", object);
           
            
            //Getting Initial Filter values...
            JSONObject resultFilter = new JSONObject();
            resultFilter = kusinaExtractionUtils.getVisitorLogsV3(kusinaElasticUtils.sendRequest(o));
            
            //Consuming and using filter names to return aggregated values...
            o.replace("payload", "visitorLogReportPaginationFilter");
            o.put("resultFilter", resultFilter);
            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
            kusinaElasticUtils.sendRequest(o), "visitorLogReportPaginationFilterExport", object);
            
        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    @Override
    public JSONObject getVisitorLogTotal(JSONObject object) {
    	JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "visitorReportTotal");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.getCustomReportTotal(kusinaElasticUtils.sendRequest(o));
            System.out.println("Total Result Set: "+resultSet);

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    @Override
    public JSONObject getVisitorLogsV3Child(JSONObject object) {
        
        JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "visitorLogReportChildPagination");
            o.put("reportObj", object);
           
            
            //Getting Initial Filter values...
            JSONObject resultFilter = new JSONObject();
            resultFilter = kusinaExtractionUtils.getVisitorLogsV3Child(kusinaElasticUtils.sendRequest(o));
            System.out.println("This is Result Filter: "+resultFilter);
            
            //Consuming and using filter names to return aggregated values...
            o.replace("payload", "visitorLogReportChildPaginationFilter");
            o.put("resultFilter", resultFilter);
            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
            kusinaElasticUtils.sendRequest(o), "visitorLogReportChildPaginationFilter", object);
            
        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    @Override
    public JSONObject getVisitorLogTotalChild(JSONObject object) {
    	JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "visitorReportTotalChild");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.getCustomReportTotalChild(kusinaElasticUtils.sendRequest(o));
            System.out.println("Total Result Set: "+resultSet);

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    
}