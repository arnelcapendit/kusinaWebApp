package com.accenture.api.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.api.service.ITFService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;
import com.accenture.api.utils.KusinaValidationUtils;

/**
 * 
 * @author arnel.m.capendit
 *
 */
@Service
public class ITFServiceImpl implements ITFService {
	
	private static final String WEBMETRIC_PIWIK = "/piwik/_search?pretty=true";

	@Autowired
    private KusinaConfigUtils kusinaConfigUtils;

    @Autowired
    private KusinaElasticUtils kusinaElasticUtils;

    @Autowired
    private KusinaExtractionUtils kusinaExtractionUtils;

    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;

	@Override
	public JSONObject getCustomReportsITF(JSONObject obj) {
		
		 JSONObject resultSet = null;
	       try {

	           JSONObject o = new JSONObject();
	           o.put("method", "POST");

	           StringBuilder sb = new StringBuilder();
	           sb.append("/");
	           sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
	           sb.append(WEBMETRIC_PIWIK);

	           o.put("uri", sb.toString());
	           o.put("hasPayload", true);
	           o.put("isKusina", false);
	           o.put("payload", "itfReports");
	           o.put("reportObj", obj);
	           
	           String reportType = obj.get("reportType").toString();
	           JSONObject resultFilter = null;
	           
	 
	    	   o.replace("payload", "itfReportsPagination");
	           String filterKey = reportType;
	           resultFilter = kusinaExtractionUtils.getUsageReportFilterChild(kusinaElasticUtils.sendRequest(o),  filterKey);  
	           o.put("resultFilter", resultFilter);
	       
	           
	           o.replace("payload", "itfReports");
	           resultSet = kusinaElasticUtils.sendRequest(o);
	           
	          
               resultSet = kusinaExtractionUtils.getITFAggregations(resultSet);
               
	  
	       } catch (Exception ex) {
	           resultSet = null;
	           Logger.getLogger(ITFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	       }
	  
	   return resultSet;
	}

}
