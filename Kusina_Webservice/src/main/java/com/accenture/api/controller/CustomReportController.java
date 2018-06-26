/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.api.controller;

import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.KusinaService;
import com.accenture.api.utils.KusinaStringUtils;
import com.accenture.api.utils.KusinaValidationUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author marlon.naraja
 */
@RestController
@CrossOrigin(origins = "*")
public class CustomReportController {

    public static final String APP_CUSTOM_REPORTS = "/rest/custom_reports";
    public static final String APP_CUSTOM_REPORTSV2 = "/rest/custom_reportsV2";
    public static final String APP_CUSTOM_REPORTS_OVERVIEW = "/rest/custom_reports/overview";
    public static final String APP_CUSTOM_REPORTS_OVERVIEW_PAGE_METRICS = "/rest/custom_reports/overview/pagemetrics";
    public static final String APP_CUSTOM_REPORTS_OVERVIEW_USER_METRICS = "/rest/custom_reports/overview/usermetrics";
    public static final String APP_CUSTOM_REPORTS_OVERVIEW_REFERRERS_METRICS = "/rest/custom_reports/overview/referrersmetrics";
    public static final String APP_CUSTOM_REPORTS_OVERVIEW_DOWNLOAD_METRICS = "/rest/custom_reports/overview/downloadmetrics";
    public static final String APP_CUSTOM_REPORTS_USAGE = "/rest/custom_reports/usage";
    public static final String APP_CUSTOM_REPORTS_USAGEV2 = "/rest/custom_reports/usageV2";
    public static final String APP_CUSTOM_REPORTS_USAGEV3 = "/rest/custom_reports/usageV3";
    public static final String APP_CUSTOM_REPORTS_USAGE_DETAILS = "/rest/custom_reports/usage_details";
    public static final String APP_CUSTOM_REPORTS_USAGE_CHILD = "/rest/custom_reports/usage_child";
    public static final String APP_CUSTOM_REPORTS_MYTE = "/rest/custom_reports/myTe";
    public static final String APP_CUSTOM_REPORTS_MYTEV2 = "/rest/custom_reports/myTeV2";
    public static final String APP_CUSTOM_REPORTS_ONLOAD_MYTE = "/rest/custom_reports/onload/myTe";
    public static final String APP_CUSTOM_REPORTS_PAGES = "/rest/custom_reports/pages";
    public static final String APP_CUSTOM_REPORTS_PAGESV2 = "/rest/custom_reports/pagesV2";
    public static final String APP_CUSTOM_REPORTS_PAGESV3 = "/rest/custom_reports/pagesV3";
    public static final String APP_CUSTOM_REPORTS_PAGES_DETAILS = "/rest/custom_reports/pages_details";
    public static final String APP_CUSTOM_REPORTS_PAGES_CHILD = "/rest/custom_reports/pages_child";
    public static final String APP_CUSTOM_REPORTS_EVENT_METRICS = "/rest/custom_reports/event_metrics";
    public static final String APP_CUSTOM_REPORTS_EVENTS = "/rest/custom_reports/events";
    public static final String APP_CUSTOM_REPORTS_AIBSPBVI = "/rest/custom_reports/aibspbvi";
    
    
    
    @Autowired
    private CustomReportService customReportService;

    @Autowired
    private KusinaService kusinaService;

    @Autowired
    private KusinaStringUtils kusinaStringUtils;

    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;

    @RequestMapping(value = APP_CUSTOM_REPORTS, method = RequestMethod.GET)
    public JSONObject getCustomReports(HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        String reportType = args.get("reportType").toString();

        if (kusinaValidationUtils.isValidReportType(reportType)) {
            JSONObject customReport = customReportService.getCustomReports(args);
            result.put(reportType, customReport.get("resultset"));
        } else {
            result.put("status", "invalid report type");
        }

        return result;
    }

    @RequestMapping(value = APP_CUSTOM_REPORTS_OVERVIEW_PAGE_METRICS, method = RequestMethod.GET)
    public JSONObject getCustomReportsOverviewPageMetrics(
            HttpServletRequest request) {
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        JSONObject customReportsOverviewPageMetrics = customReportService.getCustomReportsOverviewPageMetrics(args);
        result.put("OverviewPageMetrics", customReportsOverviewPageMetrics);

        return result;
    }

    @RequestMapping(value = APP_CUSTOM_REPORTS_OVERVIEW_USER_METRICS, method = RequestMethod.GET)
    public JSONObject getCustomReportsOverviewUserMetrics(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        JSONObject customReportsOverviewUserMetrics = customReportService.getCustomReportsOverviewUserMetrics(args);
        result.put("OverviewUserMetrics", customReportsOverviewUserMetrics);

        return result;
    }

    @RequestMapping(value = APP_CUSTOM_REPORTS_ONLOAD_MYTE, method = RequestMethod.GET)
    public JSONObject getCustomReportsOnloadMyTE(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        JSONObject preferences = kusinaService.getAppPreferences();

        List<String> urlList = (List<String>) customReportService.getMyTEUniquePageUrlList().get("resultset");
        result.put("dropdown", urlList);

        args.put("pageUrlParam", kusinaStringUtils.getPageUrlParam(urlList.get(0), ""));

        result.put("dataTable", customReportService.getMyTEUniquePageUrl(args).get("resultset"));
        result.put("actionPreferences", preferences.get("custom_variable_1"));

        return result;
    }

    @RequestMapping(value = APP_CUSTOM_REPORTS_MYTE, method = RequestMethod.GET)
    public JSONObject getCustomReportsMyTE(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        args.put("pageUrlParam", kusinaStringUtils.getPageUrlParam(args.get("pageURL").toString(), args.get("action").toString()));

        result.put("dataTable", customReportService.getMyTEUniquePageUrl(args).get("resultset"));

        return result;
    }

    @RequestMapping(value = APP_CUSTOM_REPORTS_USAGE, method = RequestMethod.GET)
    public JSONObject getCustomReportsUsage(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        result.put("dataTable", customReportService.getUsageByUser(args).get("resultset"));

        return result;

    }
    
    //For modification of getCustomUsagePage creating parent 
    @RequestMapping(value = APP_CUSTOM_REPORTS_USAGEV2, method = RequestMethod.GET)
    public JSONObject getCustomReportsUsageV2(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
        	JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
            JSONObject customReport = customReportService.getCustomReportUsageV2(args);
            result.put("total", customReport.get("total"));
            result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));   
        } else {
            result.put("status", "invalid page report type");
        }

        return result;
    }
    
    
    //For modification of getCustomUsagePage and getting child data.
    @RequestMapping(value = APP_CUSTOM_REPORTS_USAGE_DETAILS, method = RequestMethod.GET)
    public JSONObject getCustomReportUsageDetails(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
            JSONObject customReport = customReportService.getCustomReportUsageDetails(args);
            result.put("total", customReport.get("total"));
            result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
        } else {
            result.put("status", "invalid page report type");
        }

        return result;
    }
    

    @RequestMapping(value = APP_CUSTOM_REPORTS_PAGES, method = RequestMethod.GET)
    public JSONObject getCustomReportPages(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
            JSONObject customReport = customReportService.getCustomReportPages(args);
            result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
        } else {
            result.put("status", "invalid page report type");
        }

        return result;
    }
    
    //For modification of getCustomReportPage and getting only PageURL as parent data.
    @RequestMapping(value = APP_CUSTOM_REPORTS_PAGESV2, method = RequestMethod.GET)
    public JSONObject getCustomReportPagesV2(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
            JSONObject customReport = customReportService.getCustomReportPagesV2(args);
            result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
        } else {
            result.put("status", "invalid page report type");
        }

        return result;
    }
    
  //For modification of getCustomReportPage and getting only PageURL details as child data.
    @RequestMapping(value = APP_CUSTOM_REPORTS_PAGES_DETAILS, method = RequestMethod.GET)
    public JSONObject getCustomReportPagesDetails(
            HttpServletRequest request) {

        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
            JSONObject customReport = customReportService.getCustomReportPagesDetails(args);
            result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
        } else {
            result.put("status", "invalid page report type");
        }

        return result;
    }
    
    
    // *****************
    
//    @RequestMapping(value = APP_CUSTOM_REPORTS_OVERVIEW_REFERRERS_METRICS, method = RequestMethod.GET)
//    public JSONObject getCustomReportsOverviewReferrersMetrics(
//            @RequestParam("airid") String airid,
//            @RequestParam("id") String id,
//            @RequestParam("gte") String gte,
//            @RequestParam("lte") String lte) {
//        JSONObject args = new JSONObject();
//        JSONObject result = new JSONObject();
//
//        args.put("id", id);
//        args.put("airid", airid);
//        args.put("gte", gte);
//        args.put("lte", lte);
//
//        JSONObject customReportsOverviewReferrersMetrics = customReportService.getCustomReportsOverviewReferrersMetrics(args);
//        result.put("OverviewReferrersMetrics", customReportsOverviewReferrersMetrics.get("resultset"));
//
//        return result;
//    }
//    
//    
//    @RequestMapping(value = APP_CUSTOM_REPORTS_OVERVIEW_DOWNLOAD_METRICS, method = RequestMethod.GET)
//    public JSONObject getCustomReportsOverviewDownloadMetrics(
//            @RequestParam("airid") String airid,
//            @RequestParam("id") String id,
//            @RequestParam("gte") String gte,
//            @RequestParam("lte") String lte) {
//        JSONObject args = new JSONObject();
//        JSONObject result = new JSONObject();
//
//        args.put("id", id);
//        args.put("airid", airid);
//        args.put("gte", gte);
//        args.put("lte", lte);
//
//        JSONObject customReportsOverviewDownloadMetrics = customReportService.getCustomReportsOverviewDownloadMetrics(args);
//        result.put("OverviewDownloadMetrics", customReportsOverviewDownloadMetrics.get("resultset"));
//
//        return result;
//    }
    
    @RequestMapping(value = APP_CUSTOM_REPORTS_OVERVIEW_REFERRERS_METRICS, method = RequestMethod.GET)
    public JSONObject getCustomReportsOverviewReferrersMetrics(
    		 HttpServletRequest request) {
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        JSONObject customReportsOverviewReferrersMetrics = customReportService.getCustomReportsOverviewReferrersMetrics(args);
        result.put("OverviewReferrersMetrics", customReportsOverviewReferrersMetrics);

        return result;
    }
    
    @RequestMapping(value = APP_CUSTOM_REPORTS_OVERVIEW_DOWNLOAD_METRICS, method = RequestMethod.GET)
    public JSONObject getCustomReportsOverviewDownloadMetrics(
    		 HttpServletRequest request) {
        
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        JSONObject customReportsOverviewDownloadMetrics = customReportService.getCustomReportsOverviewDownloadMetrics(args);
        result.put("OverviewDownloadMetrics", customReportsOverviewDownloadMetrics);

        return result;
    }
    
    
    @RequestMapping(value = APP_CUSTOM_REPORTS_EVENT_METRICS, method = RequestMethod.GET)
    public JSONObject getCustomReportsEventMetrics(
    		 HttpServletRequest request) {
        
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        JSONObject customReportsEventMetrics = customReportService.getCustomReportsEventMetrics(args);
        result.put("EventMetrics", customReportsEventMetrics);

        return result;
    }
    
/**    @comment This is a new approach for elastic search pagination for usage webservices...
*	   @created 3/1/2018
*	   @author arnel.m.capendit
*/		
    @RequestMapping(value = APP_CUSTOM_REPORTS_USAGEV3, method = RequestMethod.GET)
    public JSONObject getCustomReportsUsageV3(
            HttpServletRequest request) {

    	JSONObject user = (JSONObject) request.getAttribute("user");
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        
        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
      //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	if (kusinaValidationUtils.isValidUsageType(String.valueOf(args.get("reportType")))) {
            	//Usage service
                JSONObject customReport = customReportService.getCustomReportUsageV3(args);
                result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));     	
                //Getting total
            	JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
            	Long valueTotal = (Long) customReportTotal.get("value");
            	result.put("total", valueTotal);
            } else {
                result.put("status", "invalid usage report type");
            }
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	if (kusinaValidationUtils.isValidUsageType(String.valueOf(args.get("reportType")))) {
                	//Usage service
                    JSONObject customReport = customReportService.getCustomReportUsageV3(args);
                    result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));     	
                    //Getting total
                	JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                	Long valueTotal = (Long) customReportTotal.get("value");
                	result.put("total", valueTotal);
                } else {
                    result.put("status", "invalid usage report type");
                }
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }
      
        return result;
    }
	
    @RequestMapping(value = APP_CUSTOM_REPORTS_USAGE_CHILD, method = RequestMethod.GET)
    public JSONObject getCustomReportUsageChild(
            HttpServletRequest request) {

    	JSONObject user = (JSONObject) request.getAttribute("user");
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
        //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	if (kusinaValidationUtils.isValidUsageType(String.valueOf(args.get("reportType")))) {
            	//Usage child service
                JSONObject customReport = customReportService.getCustomReportUsageChild(args);
                result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
                //Getting total
                JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
            	Long valueTotal = (Long) customReportTotal.get("value");
                result.put("total", valueTotal);  
            } else {
                result.put("status", "invalid usage report type");
            }
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	if (kusinaValidationUtils.isValidUsageType(String.valueOf(args.get("reportType")))) {
                	//Usage child service
                    JSONObject customReport = customReportService.getCustomReportUsageChild(args);
                    result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
                    //Getting total
                    JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                	Long valueTotal = (Long) customReportTotal.get("value");
                    result.put("total", valueTotal);  
                } else {
                    result.put("status", "invalid usage report type");
                }
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        } 
        return result;
    }
 
/**    @comment This is a new approach for elastic search pagination for pages webservices...
*	   @created 3/16/2018
*	   @author arnel.m.capendit
*/		
    @RequestMapping(value = APP_CUSTOM_REPORTS_PAGESV3, method = RequestMethod.GET)
    public JSONObject getCustomReportsPagesV3(
            HttpServletRequest request) {

    	JSONObject user = (JSONObject) request.getAttribute("user");
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        
        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
      //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	 if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {	
              	//Pages service 
                  JSONObject customReport = customReportService.getCustomReportPagesV3(args);
                  result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
                 //Getting total 
                  JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
              	Long valueTotal = (Long) customReportTotal.get("value");
                  result.put("total", valueTotal);
              } else {
                  result.put("status", "invalid usage report type");
              }
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	 if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {	
                 	//Pages service 
                     JSONObject customReport = customReportService.getCustomReportPagesV3(args);
                     result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));
                    //Getting total 
                     JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                 	Long valueTotal = (Long) customReportTotal.get("value");
                     result.put("total", valueTotal);
                 } else {
                     result.put("status", "invalid usage report type");
                 }
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }
		
       
        return result;
    }
    
    @RequestMapping(value = APP_CUSTOM_REPORTS_PAGES_CHILD, method = RequestMethod.GET)
    public JSONObject getCustomReportPagesChild(
            HttpServletRequest request) {

    	JSONObject user = (JSONObject) request.getAttribute("user");
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();

        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
      //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
            	//Pages Child service
                JSONObject customReport = customReportService.getCustomReportPagesChild(args);
                result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));       
                //Getting Total
                JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
            	Long valueTotal = (Long) customReportTotal.get("value");
                result.put("total", valueTotal); 
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
                	//Pages Child service
                    JSONObject customReport = customReportService.getCustomReportPagesChild(args);
                    result.put(String.valueOf(args.get("reportType")), customReport.get("resultset"));       
                    //Getting Total
                    JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                	Long valueTotal = (Long) customReportTotal.get("value");
                    result.put("total", valueTotal);   
                } else {
                    result.put("status", "invalid usage report type");
                }
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }
      }
        return result;
    }
    
/**    @comment This is a new approach for elastic search pagination for aibspbvi webservices...
*	   @created 3/21/2018
*	   @author arnel.m.capendit
*/
    @RequestMapping(value = APP_CUSTOM_REPORTS_AIBSPBVI, method = RequestMethod.GET)
    public JSONObject getCustomReportsAIBSPBVI(HttpServletRequest request) {
    	
    	JSONObject user = (JSONObject) request.getAttribute("user");
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        String reportType = args.get("reportType").toString();

        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
      //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	//Aibsp service
            JSONObject customReport = customReportService.getCustomReportsAIBSPBVI(args);
            result.put(reportType, customReport.get("resultset"));	
           //Getting Total
            JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
        	Long valueTotal = (Long) customReportTotal.get("value");
            result.put("total", valueTotal);
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	if (kusinaValidationUtils.isValidReportType(reportType)) {
                	//Aibsp service
                    JSONObject customReport = customReportService.getCustomReportsAIBSPBVI(args);
                    result.put(reportType, customReport.get("resultset"));	
                   //Getting Total
                    JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                	Long valueTotal = (Long) customReportTotal.get("value");
                    result.put("total", valueTotal);
                } else {
                    result.put("status", "invalid report type");
                }
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }

        }
		
        return result;
    }
    
/**    @comment This is a new approach for elastic search pagination for overview webservices...
*	   @created 3/27/2018
*	   @author arnel.m.capendit
*/
    @RequestMapping(value = APP_CUSTOM_REPORTS_OVERVIEW, method = RequestMethod.GET)
    public JSONObject getCustomReportsOverview(HttpServletRequest request) {
        
    	JSONObject user = (JSONObject) request.getAttribute("user");
    	JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        String reportType = args.get("reportType").toString();
        
        String access = user.get("user_access").toString();
        JSONArray airidList = (JSONArray)user.get("user_airid");
        
      //Checking SuperAdmin rights
        if(access.equalsIgnoreCase("Super Administrator")) {
        	//Overview service
            JSONObject customReport = customReportService.getCustomReportsOverview(args);
            result.put(reportType, customReport.get("resultset"));
            //Getting Total
            JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
        	Long valueTotal = (Long) customReportTotal.get("value");
            result.put("total", valueTotal);      
        }else {
        	//Checking airids permission
            if(airidList.contains(args.get("airid"))) {
            	if (kusinaValidationUtils.isValidOverviewType(reportType) || kusinaValidationUtils.isValidOverviewChildType(reportType)) {
                	//Overview service
                    JSONObject customReport = customReportService.getCustomReportsOverview(args);
                    result.put(reportType, customReport.get("resultset"));
                    //Getting Total
                    JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                	Long valueTotal = (Long) customReportTotal.get("value");
                    result.put("total", valueTotal);        
                } else {
                    result.put("status", "invalid report type");
                }
            }else {
            	result.put("status", "You don't have access permission for this airid.");
            }
        }
        
        return result;
    }

/**    @comment This is a new approach for elastic search pagination for event webservices...
*	   @created 4/10/2018
*	   @author arnel.m.capendit
*/
        @RequestMapping(value = APP_CUSTOM_REPORTS_EVENTS, method = RequestMethod.GET)
        public JSONObject getCustomReportsEvents(HttpServletRequest request) {
            
        	JSONObject user = (JSONObject) request.getAttribute("user");
        	JSONObject args = (JSONObject) request.getAttribute("params");
            JSONObject result = new JSONObject();
            String reportType = args.get("reportType").toString();

            String access = user.get("user_access").toString();
            JSONArray airidList = (JSONArray)user.get("user_airid");
            
            //Checking SuperAdmin rights
            if(access.equalsIgnoreCase("Super Administrator")) {
            	if (kusinaValidationUtils.isValidEventsType(reportType)) {
                	//Events service
                    JSONObject customReport = customReportService.getCustomReportsEvents(args);
                    result.put(reportType, customReport.get("resultset"));
                    //Getting total
                    JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                	Long valueTotal = (Long) customReportTotal.get("value");
                    result.put("total", valueTotal);              
                } else {
                    result.put("status", "invalid report type");
                }
            }else {
            	//Checking airids permission
                if(airidList.contains(args.get("airid"))) {
                	if (kusinaValidationUtils.isValidEventsType(reportType)) {
                    	//Events service
                        JSONObject customReport = customReportService.getCustomReportsEvents(args);
                        result.put(reportType, customReport.get("resultset"));
                        //Getting total
                        JSONObject customReportTotal = customReportService.getCustomReportTotal(args);
                    	Long valueTotal = (Long) customReportTotal.get("value");
                        result.put("total", valueTotal);              
                    } else {
                        result.put("status", "invalid report type");
                    }
                }else {
                	result.put("status", "You don't have access permission for this airid.");
                }
            }
       
            return result;
        }

        @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
        public String sampleCode() {
        	return "Hello World";
        }
        

    
    
    
    

}
