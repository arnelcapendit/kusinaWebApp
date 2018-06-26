package com.accenture.api.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.accenture.api.service.MyTEService;
import com.accenture.api.service.VisitorService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;
import com.accenture.api.utils.KusinaPayloadUtils;

@Service
public class MyTEServiceImpl implements MyTEService {
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
	public JSONObject getMyTEReports(JSONObject object) {
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
            o.put("payload", "myTEReportPagination");
            o.put("reportObj", object);
           
            
            //Getting Initial Filter values...
            JSONObject resultFilter = new JSONObject();
            resultFilter = kusinaExtractionUtils.getMyTE(kusinaElasticUtils.sendRequest(o));
            
            //Consuming and using filter names to return aggregated values...
            o.replace("payload", "myTEReportPaginationFilter");
            o.put("resultFilter", resultFilter);
            System.out.println("MyTE test if has resultFilter"+ resultFilter);
            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
            kusinaElasticUtils.sendRequest(o), "myTEReportPaginationFilter", object);
            
        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
	}

	@Override
	public JSONObject getMyTETotal(JSONObject object) {
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
            o.put("payload", "myTEReportTotal");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.getCustomReportTotal(kusinaElasticUtils.sendRequest(o));
            System.out.println("Total Result Set: "+resultSet);

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
	}


}
