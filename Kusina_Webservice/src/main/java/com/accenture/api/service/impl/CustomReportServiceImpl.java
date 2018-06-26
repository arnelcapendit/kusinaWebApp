package com.accenture.api.service.impl;

import com.accenture.api.service.CustomReportService;
import com.accenture.api.utils.KusinaConfigUtils;
import com.accenture.api.utils.KusinaElasticUtils;
import com.accenture.api.utils.KusinaExtractionUtils;
import com.accenture.api.utils.KusinaValidationUtils;

import java.net.URLEncoder;
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
public class CustomReportServiceImpl implements CustomReportService {

    private static final String WEBMETRIC_PIWIK = "/piwik/_search?pretty=true";
    private static final String KUSINA_USERS = "/users/_search?pretty=true";
    private static final String KUSINA_FEEDBACKS = "/feedbacks/_search?pretty=true";
    private static final String KUSINA_ANNOUNCE = "/announcements/_search?pretty=true";
    private static final String KUSINA_HISTORY = "/histories/_search?pretty=true";
    private static final String KUSINA_PROFILE = "/app-profiles/_search?pretty";
    

    @Autowired
    private KusinaConfigUtils kusinaConfigUtils;

    @Autowired
    private KusinaElasticUtils kusinaElasticUtils;

    @Autowired
    private KusinaExtractionUtils kusinaExtractionUtils;

    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;

    @Override
    public JSONObject getCustomReports(JSONObject object) {

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
            o.put("payload", "reports");
            o.put("reportObj", object);

            resultSet = kusinaElasticUtils.sendRequest(o);
            String reportType = object.get("reportType").toString();
            if (kusinaValidationUtils.isAggregation(reportType)) {
                if (reportType.equalsIgnoreCase("careerTracksBySegmentName")) {
                    resultSet = kusinaExtractionUtils.extractCustomReportDisplay1(resultSet);
                } else if (reportType.equalsIgnoreCase("hitsByAsset")) {
                    resultSet = kusinaExtractionUtils.extractCustomReportDisplay4(resultSet);
                } else if (reportType.equalsIgnoreCase("hitsByGeography")) {
                    resultSet = kusinaExtractionUtils.extractCustomReportDisplay3(resultSet);
                } else {
                    resultSet = kusinaExtractionUtils.extractCustomReportDisplay2(resultSet, reportType);
                }
            } else if (reportType.equalsIgnoreCase("pageCustomInfo")) {
                resultSet = kusinaExtractionUtils.extractCustomReportDisplay5(resultSet);
            }

        } catch (Exception ex) {
            resultSet = null;
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    @Override
    public JSONObject getCustomReportsOverviewPageMetrics(JSONObject object) {

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
            o.put("payload", "reportsOverviewPageMetrics");
            o.put("reportObj", object);

            resultSet = kusinaExtractionUtils.customReportOverviewPageMetrics(kusinaElasticUtils.sendRequest(o));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    @Override
    public JSONObject getCustomReportsOverviewUserMetrics(JSONObject object) {

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
            o.put("payload", "reportsOverviewUserMetrics");
            o.put("reportObj", object);

            resultSet = kusinaExtractionUtils.customReportOverviewUserMetrics(kusinaElasticUtils.sendRequest(o));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    @Override
    public JSONObject getMyTEUniquePageUrlList() {

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
            o.put("payload", "uniquePageUrlList");

            resultSet.put("resultset", kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), "uniquePageUrlList"));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    @Override
    public JSONObject getMyTEUniquePageUrl(JSONObject object) {

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
            o.put("payload", "uniquePageUrl");
            o.put("id", object.get("id"));
            o.put("airid", object.get("airid"));
            o.put("gte", object.get("gte"));
            o.put("lte", object.get("lte"));
            o.put("pageUrlParam", object.get("pageUrlParam"));

            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(kusinaElasticUtils.sendRequest(o), "uniquePageUrl");

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    @Override
    public JSONObject getUsageByUser(JSONObject object) {

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
            o.put("payload", "usageByUser");
            o.put("id", object.get("id"));
            o.put("airid", object.get("airid"));
            o.put("gte", object.get("gte"));
            o.put("lte", object.get("lte"));

            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), "usageByUser");

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

  //UsageReport Revision for viewing parent results only. 
    @Override
    public JSONObject getCustomReportUsageV2(JSONObject object) {
        
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
            o.put("payload", "usageReportRevised");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), "usageReportRevised,"+
                    object.get("filter")+","+
                    object.get("to")+","+
                    object.get("from")+","+
                    object.get("size")+","+
                    object.get("search")+","+
                    object.get("column")+","+
                    object.get("sort"));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    
  //UsageReport Revision for viewing child results only. 
    @Override
    public JSONObject getCustomReportUsageDetails(JSONObject object) {
        
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
            o.put("payload", "usageDetails");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), "usageDetails,"+
                    object.get("filter")+","+
                    object.get("to")+","+
                    object.get("from")+","+
                    object.get("size")+","+
                    object.get("search")+","+
                    object.get("column")+","+
                    object.get("sort")+","+
                    object.get("filterFor"));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    @Override
    public JSONObject getCustomReportTotal(JSONObject object) {
    	JSONObject resultSet = null;
        try {
            resultSet = new JSONObject();
            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            if("users".equalsIgnoreCase(object.get("reportType").toString())) {
            	sb.append(kusinaConfigUtils.getAppKusinaUserPrefix());
                sb.append(KUSINA_USERS);
            }else if("feedbacks".equalsIgnoreCase(object.get("reportType").toString())){
            	sb.append(kusinaConfigUtils.getAppKusinaFeedbacksPrefix());
                sb.append(KUSINA_FEEDBACKS);
            }else if("announce".equalsIgnoreCase(object.get("reportType").toString())){
            	sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
                sb.append(KUSINA_ANNOUNCE);
            }else if("announcelive".equalsIgnoreCase(object.get("reportType").toString())){
            	sb.append(kusinaConfigUtils.getAppKusinaAnnouncePrefix());
                sb.append(KUSINA_ANNOUNCE);
            }else if("profile".equalsIgnoreCase(object.get("reportType").toString())){
            	sb.append(kusinaConfigUtils.getAppKusinaProfilesPrefix());
                sb.append(KUSINA_PROFILE);
            }else if("history".equalsIgnoreCase(object.get("reportType").toString())){
            	sb.append(kusinaConfigUtils.getAppKusinaHistoryPrefix());
                sb.append(KUSINA_HISTORY);
            }else {
            	sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
                sb.append(WEBMETRIC_PIWIK);
            }
            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "reportTotal");
            o.put("reportObj", object);
            

            resultSet = (JSONObject) kusinaExtractionUtils.getCustomReportTotal(kusinaElasticUtils.sendRequest(o));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    
    @Override
    public JSONObject getCustomReportPages(JSONObject object) {
        
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
            o.put("payload", "pagesReport");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), "pagesReport,"+object.get("filter"));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    
  //PagesReport Revision for viewing parent results only. 
    @Override
    public JSONObject getCustomReportPagesV2(JSONObject object) {
        
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
            o.put("payload", "pagesReportRevised");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), "pagesReportRevised,"+object.get("filter"));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
  //PagesReport Revision for viewing child results only. 
    @Override
    public JSONObject getCustomReportPagesDetails(JSONObject object) {
        
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
            o.put("payload", "pagesReportDetails");
            o.put("reportObj", object);

            resultSet = (JSONObject) kusinaExtractionUtils.extractAggregation(
                    kusinaElasticUtils.sendRequest(o), "pagesReportDetails,"+object.get("filter"));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    // ***************
     public JSONObject getCustomReportsOverviewReferrersMetrics(JSONObject object) {
        
        JSONObject resultSet = null;
        try {

            JSONObject o = new JSONObject();
            o.put("method", "GET");

            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
            sb.append(WEBMETRIC_PIWIK);

            o.put("uri", sb.toString());
            o.put("hasPayload", true);
            o.put("isKusina", false);
            o.put("payload", "reportsOverviewReferrersMetrics");
            o.put("reportObj", object);

            resultSet = kusinaExtractionUtils.customReportOverviewReferrersMetrics(kusinaElasticUtils.sendRequest(o));

        } catch (Exception ex) {
            Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;

   }
   
   
   public JSONObject getCustomReportsOverviewDownloadMetrics(JSONObject object) {
       
       JSONObject resultSet = null;
       try {

           JSONObject o = new JSONObject();
           o.put("method", "GET");

           StringBuilder sb = new StringBuilder();
           sb.append("/");
           sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
           sb.append(WEBMETRIC_PIWIK);

           o.put("uri", sb.toString());
           o.put("hasPayload", true);
           o.put("isKusina", false);
           o.put("payload", "reportsOverviewDownloadMetrics");
           o.put("reportObj", object);

           resultSet = kusinaExtractionUtils.customReportOverviewDownloadMetrics(kusinaElasticUtils.sendRequest(o));

       } catch (Exception ex) {
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;

  }

   public JSONObject getCustomReportsEventMetrics(JSONObject object) {
       
       JSONObject resultSet = null;
       try {

           JSONObject o = new JSONObject();
           o.put("method", "GET");

           StringBuilder sb = new StringBuilder();
           sb.append("/");
           sb.append(kusinaConfigUtils.getAppWebMetricsPrefix());
           sb.append(WEBMETRIC_PIWIK);

           o.put("uri", sb.toString());
           o.put("hasPayload", true);
           o.put("isKusina", false);
           o.put("payload", "eventMetrics");
           o.put("reportObj", object);

           resultSet = kusinaExtractionUtils.customReportEventMetrics(kusinaElasticUtils.sendRequest(o));

       } catch (Exception ex) {
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;

  }
   
   @Override
   public JSONObject getCustomReportUsageV3(JSONObject object) {
       
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
           o.put("payload", "usageReportPagination");
           o.put("reportObj", object);
          
           
           //Getting Initial Filter values...
           JSONObject resultFilter = new JSONObject();
           String filterKey = (String) object.get("filter");
           resultFilter = kusinaExtractionUtils.getReportFilter(kusinaElasticUtils.sendRequest(o),  filterKey);
           
           
           //Consuming and using filter names to return aggregated values...
           o.replace("payload", "usageReportPaginationFilter");
           o.put("resultFilter", resultFilter);
           resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
           kusinaElasticUtils.sendRequest(o), "usageReportPaginationFilter", object);
           
           
       } catch (Exception ex) {
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;
   }
 
   @Override
   public JSONObject getCustomReportUsageChild(JSONObject object) {
       
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
           o.put("payload", "usageReportPaginationChild");
           o.put("reportObj", object);
           
         //Getting Initial Filter child values...
           JSONObject resultFilter = new JSONObject();
           String filterKey = (String) object.get("filterMetrics");
           resultFilter = kusinaExtractionUtils.getUsageReportFilterChild(kusinaElasticUtils.sendRequest(o),  filterKey);
         
        
        //   Consuming and using filter child names to return aggregated values...
           o.replace("payload", "usageChildPaginationFilter");
           o.put("resultFilter", resultFilter);
           resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
           kusinaElasticUtils.sendRequest(o), "usageChildPaginationFilter", object);

       } catch (Exception ex) {
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;
   }

   @Override
   public JSONObject getCustomReportPagesV3(JSONObject object) {
       
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
           o.put("payload", "pagesReportPagination");
           o.put("reportObj", object);
          
           
           //Getting Initial Filter values...
           JSONObject resultFilter = new JSONObject();
           String filterKey = "pageURL";
           resultFilter = kusinaExtractionUtils.getReportFilter(kusinaElasticUtils.sendRequest(o), filterKey);
           
           
           //Consuming and using filter names to return aggregated values...
           o.replace("payload", "pagesReportPaginationFilter");
           o.put("resultFilter", resultFilter);
           resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
           kusinaElasticUtils.sendRequest(o), "pagesReportPaginationFilter", object);
           
           
       } catch (Exception ex) {
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;
   }
   
   @Override
   public JSONObject getCustomReportPagesChild(JSONObject object) {
       
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
           o.put("payload", "pagesReportPaginationChild");
           o.put("reportObj", object);
           
         //Getting Initial Filter child values...
           JSONObject resultFilter = new JSONObject();
           String filterKey = (String) object.get("filterMetrics");
           resultFilter = kusinaExtractionUtils.getUsageReportFilterChild(kusinaElasticUtils.sendRequest(o),  filterKey);
         
        
        //   Consuming and using filter child names to return aggregated values...
           o.replace("payload", "pagesChildPaginationFilter");
           o.put("resultFilter", resultFilter);
           resultSet = (JSONObject) kusinaExtractionUtils.extractAggregationMetrics(
           kusinaElasticUtils.sendRequest(o), "pagesChildPaginationFilter", object);

       } catch (Exception ex) {
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;
   }
   
   
   @Override
   public JSONObject getCustomReportsAIBSPBVI(JSONObject object) {

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
           o.put("payload", "AIBSPBVIreports");
           o.put("reportObj", object);
           String reportType = object.get("reportType").toString();
           JSONObject resultFilter = null;
           
           if(!reportType.equalsIgnoreCase("pageCustomInfo")) {
        	   //Getting Initial Filter child values...
        	   o.replace("payload", "AIBSPBVIreportsPagination");
               String filterKey = "hits";
               resultFilter = kusinaExtractionUtils.getUsageReportFilterChild(kusinaElasticUtils.sendRequest(o),  filterKey);  
               o.put("resultFilter", resultFilter);
           }
           
           o.replace("payload", "AIBSPBVIreports");
           resultSet = kusinaElasticUtils.sendRequest(o);
          
           if (kusinaValidationUtils.isAggregation(reportType)) {
               if (reportType.equalsIgnoreCase("careerTracksBySegmentName")) {
                   resultSet = kusinaExtractionUtils.extractCustomReportDisplay1(resultSet);
               } 
               	 else if (reportType.equalsIgnoreCase("hitsByAsset")) {
                   resultSet = kusinaExtractionUtils.extractCustomReportDisplay4(resultSet);
               } else if (reportType.equalsIgnoreCase("hitsByGeography")) {
                   resultSet = kusinaExtractionUtils.extractCustomReportDisplay3(resultSet);
               } else {
                   resultSet = kusinaExtractionUtils.extractCustomReportDisplay2(resultSet, reportType);
               }
           } 
           
           else if (reportType.equalsIgnoreCase("pageCustomInfo")) {
               resultSet = kusinaExtractionUtils.extractCustomReportDisplay5(resultSet);
           }

       } catch (Exception ex) {
           resultSet = null;
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;
   }
   
   @Override
   public JSONObject getCustomReportsOverview(JSONObject object) {

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
           o.put("payload", "overviewReports");
           o.put("reportObj", object);
           String reportType = object.get("reportType").toString();
           JSONObject resultFilter = null;
           String filterKey = null;
           
           //Getting Initial Filter parent values...
           if(kusinaValidationUtils.isValidOverviewType(reportType)) {
        	   o.replace("payload", "overviewReportsPagination");
               filterKey = reportType;
               resultFilter = kusinaExtractionUtils.getUsageReportFilterChild(kusinaElasticUtils.sendRequest(o),  filterKey);  
               o.put("resultFilter", resultFilter);   
           }else {
        	   System.out.println("This is child payload");
        	   o.replace("payload", "overviewReportsPaginationChild");
        	   filterKey = "user";
        	   resultFilter = kusinaExtractionUtils.getUsageReportFilterChild(kusinaElasticUtils.sendRequest(o),  filterKey);  
               o.put("resultFilter", resultFilter);      
           }
    	   
           o.replace("payload", "overviewReports");
           resultSet = kusinaElasticUtils.sendRequest(o);
           
           if(!kusinaValidationUtils.isValidOverviewChildType(reportType)) {
               if (reportType.equalsIgnoreCase("pageOverview")) {
            	   resultSet = kusinaExtractionUtils.customReportOverviewPageMetrics(resultSet);
               } else if (reportType.equalsIgnoreCase("userOverview")) {
            	   resultSet = kusinaExtractionUtils.customReportOverviewUserMetrics(resultSet);
               } else {
            	   resultSet = (JSONObject) kusinaExtractionUtils.customReportOverviewReferrersAndDownloadMetrics(resultSet, o);
               } 
           }else {
        	   JSONObject aggs = (JSONObject) resultSet.get("aggregations"); 
        	   	resultSet = (JSONObject) kusinaExtractionUtils.customReportOverviewReferrersAndDownloadMetricsChild(aggs, o);
           }
           

       } catch (Exception ex) {
           resultSet = null;
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;
   }
   
   
   @Override
   public JSONObject getCustomReportsEvents(JSONObject object) {

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
           o.put("payload", "events");
           o.put("reportObj", object);
           String reportType = object.get("reportType").toString();
           JSONObject resultFilter = null;
           
 
    	   o.replace("payload", "eventsPagination");
           String filterKey = reportType;
           resultFilter = kusinaExtractionUtils.getUsageReportFilterChild(kusinaElasticUtils.sendRequest(o),  filterKey);  
           o.put("resultFilter", resultFilter);
       
           
           o.replace("payload", "events");
           resultSet = kusinaElasticUtils.sendRequest(o);
           
           
           resultSet = kusinaExtractionUtils.customReportEvents(resultSet);

       } catch (Exception ex) {
           resultSet = null;
           Logger.getLogger(CustomReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resultSet;
   }
   
   
}
