package com.accenture.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.accenture.api.model.HistoryModel;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaPayloadUtils {

    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;
    
    @Autowired
    private KusinaStringUtils kusinaStringUtils;
    
    @Autowired
    private KusinaDateUtils kusinaDateUtils; 

    public String generatePayload(JSONObject jObj) {

        String param = jObj.get("payload").toString();
        String[] paramName = param.split(",");
        JSONObject airidObj = (JSONObject) jObj.get("userObj");
        Object airid = null;
        String id = null;
        String gte = null;
        String lte = null;
        String pageurl = null;
        Long max = null;
        

        if ("s_session".equalsIgnoreCase(param)) {
            return saveUserSession(jObj);
        } else if (Arrays.asList("c_session", "sessionTypeId").contains(param)) {
            Object nonce = jObj.get("nonce");
            return checkExistingSession(jObj.get("eid").toString(), (nonce != null ? jObj.get("nonce").toString() : null));
        } else if ("userTypeId".equalsIgnoreCase(param)) {
            return getUserId(jObj.get("eid").toString());
        } else if ("g_session".equalsIgnoreCase(param)) {
            return getUserSession(jObj);
        } else if ("allusers".equalsIgnoreCase(param)) {
            return getAllUser();
        } else if (Arrays.asList("users", "feedbacks").contains(param)) {
        	JSONObject userObj = (JSONObject) jObj.get("userObj");
        	String search = kusinaStringUtils.getModStr(userObj.get("search"));
        	String from = kusinaStringUtils.getModStr(userObj.get("from"));
        	String size = kusinaStringUtils.getModStr(userObj.get("size"));
            return "users".equalsIgnoreCase(param) ? getUser(from, size, search) : getFeedBacks(from, size, search);
        } else if ("announce".equalsIgnoreCase(param)) {
        	JSONObject announceObj = (JSONObject) jObj.get("announceObj");
        	String search = kusinaStringUtils.getModStr(announceObj.get("search"));
        	String from = kusinaStringUtils.getModStr(announceObj.get("from"));
        	String size = kusinaStringUtils.getModStr(announceObj.get("size"));
            return getAnnounce(from, size, search);
        } else if ("profile".equalsIgnoreCase(param)) {
        	JSONObject profileObj = (JSONObject) jObj.get("profileObj");
        	String search = kusinaStringUtils.getModStr(profileObj.get("search"));
        	String from = kusinaStringUtils.getModStr(profileObj.get("from"));
        	String size = kusinaStringUtils.getModStr(profileObj.get("size"));
            return getProfile(from, size, search);
        } else if ("all_airid".equalsIgnoreCase(param)) {
            return getAllAirid();
        } else if ("site_id".equalsIgnoreCase(param)) {
        	JSONObject profileObj = (JSONObject) jObj.get("profileObj");
        	String search = kusinaStringUtils.getModStr(profileObj.get("search"));
        	System.out.println("Search for site id: " + search);
            return getSiteId(search);
        } else if ("announcelive".equalsIgnoreCase(param)) {
        	JSONObject announceObj = (JSONObject) jObj.get("announceObj");
        	String search = kusinaStringUtils.getModStr(announceObj.get("search"));
        	String from = kusinaStringUtils.getModStr(announceObj.get("from"));
        	String size = kusinaStringUtils.getModStr(announceObj.get("size"));
            return getAnnounceLive(from, size, search);
        } else if ("announceall".equalsIgnoreCase(param)) {
        	JSONObject announceObj = (JSONObject) jObj.get("announceObj");
        	String now = kusinaStringUtils.getModStr(announceObj.get("now"));
            return getAnnounceAll(now);
        } else if ("histories".equalsIgnoreCase(param)) {
        	JSONObject historyObj = (JSONObject) jObj.get("historyObj");
        	String search = kusinaStringUtils.getModStr(historyObj.get("search"));
        	String from = kusinaStringUtils.getModStr(historyObj.get("from"));
        	String size = kusinaStringUtils.getModStr(historyObj.get("size"));
            return getHistories(from, size, search);
        } else if ("historyId".equalsIgnoreCase(param)) {
        	String _id = (String) jObj.get("id");
            return getHistoryId(_id);
        } else if ("insert".equalsIgnoreCase(param)) {
            return insertUserDetails(jObj);
        }  else if ("insertHistory".equalsIgnoreCase(param)) {
            return insertHistory(jObj);
        } else if ("postHistory".equalsIgnoreCase(param)) {
            return postHistory(jObj);
        } else if ("addFeedback".equalsIgnoreCase(param)) {
        	return addFeedbakcs(jObj);
        } else if ("addAnnouncement".equalsIgnoreCase(param)) {
        	return addAnnouncements(jObj);
        } else if ("editAnnouncement".equalsIgnoreCase(param)) {
        	return editAnnouncements(jObj);
        } else if ("addProfile".equalsIgnoreCase(param)) {
        	return addProfile(jObj);
        } else if ("editProfile".equalsIgnoreCase(param)) {
        	return editProfile(jObj);
        } else if ("readCount".equalsIgnoreCase(param)) {
        	JSONObject historyObj =  (JSONObject) jObj.get("historyObj");
        	String user = (String) historyObj.get("eid");
        	String now = (String) historyObj.get("now");
            return getTotalReadAnnounce(user, now);
        } else if ("readCountPerAnnounce".equalsIgnoreCase(param)) {
        	JSONObject historyObj =  (JSONObject) jObj.get("historyObj");
        	String announceId = (String) jObj.get("id");
        	String user = (String) historyObj.get("eid");
            return getTotalReadAnnouncePerUser(user, announceId);
        } else if ("readCountAll".equalsIgnoreCase(param)) {
           return getTotalAllReadAnnounce(jObj.get("id").toString());
        } else if ("announceDueDateCount".equalsIgnoreCase(param)) {
            return getTotalAnnounceDueDate();
        } else if ("update".equalsIgnoreCase(param)) {
            return updateUserDetails(jObj);
        } else if ("reports".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            String reportType = reportObj.get("reportType").toString();

            if (kusinaValidationUtils.isBySegmentName(reportType)) {
                if (reportType.equalsIgnoreCase("careerTracksBySegmentName")) {
                    return getReportBySegmentName(
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString());

                } else {
                    return getReportBySegmentName(
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString(),
                            kusinaValidationUtils.getFieldArgument(reportType));
                }
            } else {
                if (reportType.equalsIgnoreCase("pageCustomInfo")) {
                    return getPageCustomReport(
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString());
                } else {
                    return getHitsReports(
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportType,
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString());
                }

            }
        } else if ("AIBSPBVIreports".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            String reportType = reportObj.get("reportType").toString();
            JSONObject filterObj = null;
            String resultFilter = null;
            if(!reportType.equalsIgnoreCase("pageCustomInfo")) {
            	 filterObj = (JSONObject) jObj.get("resultFilter");
            	 resultFilter = kusinaStringUtils.getModStr(filterObj.get("resultset"));
            }
            String size = reportObj.get("size").toString();
            String search = reportObj.get("search").toString();
            
            if (kusinaValidationUtils.isBySegmentName(reportType)) {
                if (reportType.equalsIgnoreCase("careerTracksBySegmentName")) {
                    return getReportBySegmentNameAIBSPBVI(
                    		resultFilter,
                    		size,
                    		search,
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString());

                } else {
                    return getReportBySegmentNameAIBSPBVI(
                    		resultFilter,
                    		size,
                    		search,
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString(),
                            kusinaValidationUtils.getFieldArgumentAIBSPBVI(reportType));
                }
            } else {
                if (reportType.equalsIgnoreCase("pageCustomInfo")) {
                    return getPageCustomReportAIBSPBVI(
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString(),
                            reportObj.get("from").toString(),
                            reportObj.get("size").toString(),
                            reportObj.get("search").toString(),
                            reportType);
                } else {
                    return getHitsReportsAIBSPBVI(
                    		resultFilter,
                    		size,
                    		search,
                            reportObj.get("airid").toString(),
                            reportObj.get("id").toString(),
                            reportType,
                            reportObj.get("gte").toString(),
                            reportObj.get("lte").toString());
                }

            }
        } else if ("reportsOverviewPageMetrics".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            return getCustomReportsOverviewPageMetrics(Arrays.asList("\"" + airid + "\""), gte, lte, id);
        } else if ("reportsOverviewUserMetrics".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();

            return getCustomReportsOverviewUserMetrics(Arrays.asList("\"" + airid + "\""), gte, lte, id);
        } else if ("reportsOverviewReferrersMetrics".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();

            return getCustomReportsOverviewReferrersMetrics(Arrays.asList("\"" + airid + "\""), gte, lte, id);

        } else if ("reportsOverviewDownloadMetrics".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();

            return getCustomReportsOverviewDownloadMetrics(Arrays.asList("\"" + airid + "\""), gte, lte, id);

        } else if ("overviewReports".equalsIgnoreCase(param)) {
        	JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            String reportType = reportObj.get("reportType").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            String size = reportObj.get("size").toString();
            String search = reportObj.get("search").toString();
            String total = kusinaStringUtils.getModStr(reportObj.get("total"));
            String filterFor = kusinaStringUtils.getModStr(reportObj.get("filterFor"));
            JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
            String resultFilter = filterObj.get("resultset").toString();
            
	        return getCustomReportsOverviewMetrics(reportObj.get("airid").toString(), gte, lte, id, size, search, total, resultFilter, reportType, filterFor);
	          
        } else if ("events".equalsIgnoreCase(param)) {
        	JSONObject reportObj = (JSONObject) jObj.get("reportObj");   
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            String size = reportObj.get("size").toString();
            String search = reportObj.get("search").toString();
            String reportType = reportObj.get("reportType").toString();
            JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
            String resultFilter = filterObj.get("resultset").toString();
            
	        return getCustomReportsEvents(reportObj.get("airid").toString(), gte, lte, id, size, search, reportType, resultFilter);
	          
        } else if ("itfReports".equalsIgnoreCase(param)) {
        	JSONObject reportObj = (JSONObject) jObj.get("reportObj");   
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            String size = reportObj.get("size").toString();
            String search = reportObj.get("search").toString();
            String reportType = reportObj.get("reportType").toString();
            JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
            String resultFilter = filterObj.get("resultset").toString();
            
	        return getCustomReportsITF(reportObj.get("airid").toString(), gte, lte, id, size, search, reportType, resultFilter);
	          
        } else if ("uniquePageUrlList".equalsIgnoreCase(param)) {
            return getUniquePageUrlList();

        } else if ("visitorLogs".equalsIgnoreCase(param)) {
           // System.out.println("generatepayload for visitorlogs");
            return getVisitorLogs(jObj);
        } else if ("visitorLogsV2".equalsIgnoreCase(param)) {
            return getVisitorLogsV2(jObj);
        } else if ("pagesReport".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));

            return getCustomPagesReport(airid.toString(), id, gte, lte, kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")));
        } 
        	//PagesReport Revision for viewing parent results only.
        	else if ("pagesReportRevised".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));

            return getCustomPagesReportV2(airid.toString(), id, gte, lte, kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")));
        }
        	//PagesReportDetails Revision for viewing child results only.
        	else if ("pagesReportDetails".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            pageurl = reportObj.get("pageurl").toString();
            String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));

            return getCustomPagesReportDetails(airid.toString(), id, gte, lte, kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")), pageurl);
        }else if ("eventMetrics".equalsIgnoreCase(param)) {

        		 JSONObject reportObj = (JSONObject) jObj.get("reportObj");
                 airid = reportObj.get("airid").toString();
                 gte = reportObj.get("gte").toString();
                 lte = reportObj.get("lte").toString();
                 id = reportObj.get("id").toString();

            return getCustomReportsEventMetrics(airid.toString(), gte, lte, id);
        }else if ("usageReportRevised".equalsIgnoreCase(param)) {

            JSONObject reportObj = (JSONObject) jObj.get("reportObj");
            airid = reportObj.get("airid").toString();
            gte = reportObj.get("gte").toString();
            lte = reportObj.get("lte").toString();
            id = reportObj.get("id").toString();
            String to = reportObj.get("to").toString();
            String from = reportObj.get("from").toString();
            String size = reportObj.get("size").toString();
            String column = reportObj.get("column").toString();
            String sort = reportObj.get("sort").toString();
            String search = kusinaValidationUtils.modifyUsageSearch(reportObj.get("search"));
            String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));

            return getCustomReportUsageV2(
            		airid.toString(), 
            		id, 
            		gte, 
            		lte,
            		to,
            		from,
            		size,
            		column,
            		sort,
            		search,
            		kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")));
        } //PagesReportDetails Revision for viewing child results only.
        	else if ("usageDetails".equalsIgnoreCase(param)) {

                JSONObject reportObj = (JSONObject) jObj.get("reportObj");
                airid = reportObj.get("airid").toString();
                gte = reportObj.get("gte").toString();
                lte = reportObj.get("lte").toString();	
                id = reportObj.get("id").toString();
                String from = reportObj.get("from").toString();
                String to = reportObj.get("to").toString();
                String size = reportObj.get("size").toString();
                String column = reportObj.get("column").toString();
                String sort = reportObj.get("sort").toString();
                String search = kusinaValidationUtils.modifyUsageSearch(reportObj.get("search"));
                String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));
                String filterFor = reportObj.get("filterFor").toString();
                String filterMetrics = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filterMetrics"));
                
                return getCustomUsageReportDetails(
                		airid.toString(), 
                		id, 
                		gte, 
                		lte,
                		to,
                		from,
                		size,
                		column,
                		sort,
                		search,
                		filterFor,
                		filterMetrics,
                		kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")));
        }	else if ("reportTotal".equalsIgnoreCase(param)) {
       		 JSONObject reportObj = (JSONObject) jObj.get("reportObj");
                airid = kusinaStringUtils.getModStr(reportObj.get("airid"));
                gte = kusinaStringUtils.getModStr(reportObj.get("gte"));
                lte = kusinaStringUtils.getModStr(reportObj.get("lte"));
                id = kusinaStringUtils.getModStr(reportObj.get("id"));
                String filter = kusinaStringUtils.getModStr(reportObj.get("filter"));
                String filterFor = kusinaStringUtils.getModStr(reportObj.get("filterFor"));
                String filterMetrics = kusinaStringUtils.getModStr(reportObj.get("filterMetrics"));
                String reportType = kusinaStringUtils.getModStr(reportObj.get("reportType"));
                String search = kusinaStringUtils.getModStr(reportObj.get("search"));

           return getCustomReportsTotal(airid.toString(), gte, lte, id, filter, filterMetrics, filterFor, reportType, search);
       }   else if (kusinaValidationUtils.isValidPaginationType(param)) {	
    	   
    	   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
           airid = reportObj.get("airid").toString();
           gte = reportObj.get("gte").toString();
           lte = reportObj.get("lte").toString();
           id = reportObj.get("id").toString();
           String from = reportObj.get("from").toString();
           String size = reportObj.get("size").toString();
           String filter = kusinaStringUtils.getModStr(kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter")));
           String reportType = kusinaStringUtils.getModStr(reportObj.get("reportType"));
           String filterFor = kusinaStringUtils.getModStr(reportObj.get("filterFor"));
           String search = kusinaStringUtils.getModStr(reportObj.get("search"));
           
           return getCustomReportFilter(
        		search,
        		param,
        		reportType,
        		filterFor,
           		airid.toString(), 
           		id, 
           		gte, 
           		lte,
           		from,
           		size,
           		kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")));
     }	  else if ("usageReportPaginationChild".equalsIgnoreCase(param) || "pagesReportPaginationChild".equalsIgnoreCase(param)) {	
  	   
  	   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
         airid = reportObj.get("airid").toString();
         gte = reportObj.get("gte").toString();
         lte = reportObj.get("lte").toString();
         id = reportObj.get("id").toString();
         String from = reportObj.get("from").toString();
         String size = reportObj.get("size").toString();
         String filterFor = kusinaStringUtils.getModStr(reportObj.get("filterFor"));
         String filterMetrics = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filterMetrics"));
         String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));
         String search = kusinaStringUtils.getModStr(reportObj.get("search"));

         return getCustomReportFilterChild(
        		search,
        		param,
         		airid.toString(), 
         		id, 
         		gte, 
         		lte,
         		from,
         		size,
         		filterFor,
         		filterMetrics,
         		kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")));
   }	else if ("usageReportPaginationFilter".equalsIgnoreCase(param) || "pagesReportPaginationFilter".equalsIgnoreCase(param)) {
  	   
  	   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
  	   JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
  	   
         airid = reportObj.get("airid").toString();
         gte = reportObj.get("gte").toString();
         lte = reportObj.get("lte").toString();
         id = reportObj.get("id").toString();
         String from = reportObj.get("from").toString();
         String size = reportObj.get("size").toString();
         String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));
         String resultfilter = filterObj.get("resultset").toString();
         String search = reportObj.get("search").toString();
		
         return getCustomReportFilterPagination(
        		param,
         		airid.toString(), 
         		id, 
         		gte, 
         		lte,
         		from,
         		size,
         		search,
         		kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")),
         		resultfilter);
   }  else if ("usageChildPaginationFilter".equalsIgnoreCase(param) || "pagesChildPaginationFilter".equalsIgnoreCase(param)) {
  	   
  	   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
  	   JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
  	   
         airid = reportObj.get("airid").toString();
         gte = reportObj.get("gte").toString();
         lte = reportObj.get("lte").toString();
         id = reportObj.get("id").toString();
         String from = reportObj.get("from").toString();
         String size = reportObj.get("size").toString();
         String filter = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filter"));
         String filterMetrics = kusinaValidationUtils.modifyPagesFilter(reportObj.get("filterMetrics"));
         String resultfilter = filterObj.get("resultset").toString();
         String search = reportObj.get("search").toString();
         String filterFor = kusinaStringUtils.getModStr(reportObj.get("filterFor"));
        
         return getCustomReportChildFilterPagination(
        		param,
         		airid.toString(), 
         		id, 
         		gte, 
         		lte,
         		from,
         		size,
         		search,
         		filterMetrics,
         		kusinaValidationUtils.getFieldArgument(filter, filter.equalsIgnoreCase("Geography")),
         		resultfilter,
         		filterFor);
   }   else if ("visitorLogReportPagination".equalsIgnoreCase(param)) {	
	   
			   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
		       airid = reportObj.get("airid").toString();
		       gte = reportObj.get("gte").toString();
		       lte = reportObj.get("lte").toString();
		       id = reportObj.get("id").toString();
		       String from = reportObj.get("from").toString();
		       String size = reportObj.get("size").toString();
		
		       return getVisitorLogsFilter(
		       		airid.toString(), 
		       		id, 
		       		gte, 
		       		lte,
		       		from,
		       		size);
   } 	else if ("visitorLogReportPaginationFilter".equalsIgnoreCase(param)) {
  	   
  	   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
  	   JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
  	   
         airid = reportObj.get("airid").toString();
         gte = reportObj.get("gte").toString();
         lte = reportObj.get("lte").toString();
         id = reportObj.get("id").toString();
         String from = reportObj.get("from").toString();
         String size = reportObj.get("size").toString();
         String search = reportObj.get("search").toString();
         String reportType = reportObj.get("reportType").toString();
         String filter = reportObj.get("filter").toString();
         String resultfilter = filterObj.get("resultset").toString();
		
         return getVisitorLogFilterPagination(
         		airid.toString(), 
         		id, 
         		gte, 
         		lte,
         		from,
         		size,
         		search,
         		resultfilter,
         		reportType,
         		filter);
   } else if ("visitorReportTotal".equalsIgnoreCase(param)) {

 		 JSONObject reportObj = (JSONObject) jObj.get("reportObj");
	 		airid = reportObj.get("airid").toString();
	        gte = reportObj.get("gte").toString();
	        lte = reportObj.get("lte").toString();
	        id = reportObj.get("id").toString();
	        String from = reportObj.get("from").toString();
	        String size = reportObj.get("size").toString();
	        String search = reportObj.get("search").toString();

          return getVisitorLogsTotal(
        		airid.toString(), 
           		id, 
           		gte, 
           		lte,
           		from,
           		size,
           		search);
   }   else if ("visitorLogReportChildPagination".equalsIgnoreCase(param)) {
  	   
	   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
       airid = reportObj.get("airid").toString();
       gte = reportObj.get("gte").toString();
       lte = reportObj.get("lte").toString();
       id = reportObj.get("id").toString();
       String from = reportObj.get("from").toString();
       String size = reportObj.get("size").toString();
       String filterFor = reportObj.get("filterFor").toString();

       return getVisitorLogsFilterChild(
       		airid.toString(), 
       		id, 
       		gte, 
       		lte,
       		from,
       		size,
       		filterFor);
   } else if ("visitorLogReportChildPaginationFilter".equalsIgnoreCase(param)) {
  	   
  	   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
  	   JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
  	   
         airid = reportObj.get("airid").toString();
         gte = reportObj.get("gte").toString();
         lte = reportObj.get("lte").toString();
         id = reportObj.get("id").toString();
         String from = reportObj.get("from").toString();
         String size = reportObj.get("size").toString();
         String resultfilter = filterObj.get("resultset").toString();
         String search = reportObj.get("search").toString();
         String filterFor = reportObj.get("filterFor").toString();
        
         return getVisitorLogReportChildPaginationFilter(
         		airid.toString(), 
         		id, 
         		gte, 
         		lte,
         		from,
         		size,
         		search,
         		resultfilter,
         		filterFor
         		);
   } else if ("visitorReportTotalChild".equalsIgnoreCase(param)) {

		 JSONObject reportObj = (JSONObject) jObj.get("reportObj");
	 		airid = reportObj.get("airid").toString();
	        gte = reportObj.get("gte").toString();
	        lte = reportObj.get("lte").toString();
	        id = reportObj.get("id").toString();
	        String from = reportObj.get("from").toString();
	        String size = reportObj.get("size").toString();
	        String search = reportObj.get("search").toString();
	        String filterFor = reportObj.get("filterFor").toString();

        return getVisitorLogsTotalChild(
      		airid.toString(), 
         		id, 
         		gte, 
         		lte,
         		from,
         		size,
         		search,
         		filterFor);
   }  else if ("myTEReportPagination".equalsIgnoreCase(param)) {	
	   
		   JSONObject reportObj = (JSONObject) jObj.get("reportObj");
	       airid = reportObj.get("airid").toString();
	       gte = reportObj.get("gte").toString();
	       lte = reportObj.get("lte").toString();
	       id = reportObj.get("id").toString();
	       String from = reportObj.get("from").toString();
	       String size = reportObj.get("size").toString();
	
	       return getMyTEFilter(
	       		airid.toString(), 
	       		id, 
	       		gte, 
	       		lte,
	       		from,
	       		size);
   } 	else if ("myTEReportPaginationFilter".equalsIgnoreCase(param)) {
 
		 JSONObject reportObj = (JSONObject) jObj.get("reportObj");
		JSONObject filterObj = (JSONObject) jObj.get("resultFilter");
		 
		 airid = reportObj.get("airid").toString();
		 gte = reportObj.get("gte").toString();
		 lte = reportObj.get("lte").toString();
		 id = reportObj.get("id").toString();
		 String from = reportObj.get("from").toString();
		 String size = reportObj.get("size").toString();
		 String search = reportObj.get("search").toString();
		 String reportType = reportObj.get("reportType").toString();
		 String filter = reportObj.get("filter").toString();
		 String resultfilter = filterObj.get("resultset").toString();
		
		 return getMyTEFilterPagination(
		 		airid.toString(), 
		 		id, 
		 		gte, 
		 		lte,
		 		from,
		 		size,
		 		search,
		 		resultfilter,
		 		reportType,
		 		filter);
	} else if ("myTEReportTotal".equalsIgnoreCase(param)) {

		 JSONObject reportObj = (JSONObject) jObj.get("reportObj");
		 		airid = reportObj.get("airid").toString();
		        gte = reportObj.get("gte").toString();
		        lte = reportObj.get("lte").toString();
		        id = reportObj.get("id").toString();
		        String from = reportObj.get("from").toString();
		        String size = reportObj.get("size").toString();
		        String search = reportObj.get("search").toString();
	
	         return getMyTEReportTotal(
	       		airid.toString(), 
	          		id, 
	          		gte, 
	          		lte,
	          		from,
	          		size,
	          		search);
	} 

        if (!param.equalsIgnoreCase("user")) {
            if ((boolean) jObj.get("hasPayload") && !param.equalsIgnoreCase("appname")) {

                if (jObj.get("airid") != null) {
                    airid = jObj.get("airid");
                } else {
                    airid = airidObj.get("user_airid");
                }

                id = jObj.get("id").toString();
                gte = jObj.get("gte").toString();
                lte = jObj.get("lte").toString();
                max = (Long) jObj.get("max");

            }
        }

        switch (param) {
            case "appname":
                return getAppNameFilter(jObj);
            case "generationTime":
                return getAverageGenerationTime(gte, lte, airid, id);
            case "hitsCount":
                return getUniqueHitsCount(gte, lte, airid, id);
            case "visitorCount":
                return getUniqueVisitorCount(gte, lte, airid, id);
            case "visitsCount":
                return getUniqueVisitsCount(gte, lte, airid, id);
            case "topPageVisits":
                return getTopPageVisits(gte, lte, airid, id, max);
            case "topCountry":
                return getTopCountry(gte, lte, airid, id, max);
            case "topDevices":
                return getTopDevices(gte, lte, airid, id, max);
            case "topBrowser":
                return getTopBrowser(gte, lte, airid, id, max);
            case "visitOvertime":
                return getVisitsOverTime(gte, lte, airid, id, jObj.get("itv").toString(), jObj.get("tz").toString());

            case "user":
                return getUserByEid(jObj.get("eid").toString());

            case "uniquePageUrl":
                return getUniquePageUrl((String) jObj.get("pageUrlParam"), gte, lte, id, (String) airid);

            case "usageByUser":
                return getUsageByUser((String) airid, id, gte, lte);

            default:
                break;
        }
        return null;
    }



	//**************************************************************************
    public String getUserByEid(String eid) {

        String payload = "{\n"
                + "  \"query\":{\n"
                + "    \"match\":{\n"
                + "      \"user_eid\" : \"" + eid + "\"\n"
                + "    }\n"
                + "  }\n"
                + "\n"
                + "}";

        return payload;

    }

    public String getAppNameFilter(JSONObject o) {
        JSONObject obj = (JSONObject) o.get("userObj");
        String accessType = obj.get("user_access").toString();
        String payload = null;
        System.out.println("Access Type: "+ accessType);
        Object airid;
        Object id;

        if (o.get("airid") != null) {
            airid = o.get("airid");
            id = o.get("id");
        } else {
            airid = obj.get("user_airid");
            id = obj.get("user_id");
        }

        if ("Super Administrator".equalsIgnoreCase(accessType)) {
        	payload = "{\r\n" + 
        			"    \"size\": 1000,\r\n" + 
        			"    \"query\": {\r\n" + 
        			"        \"match_all\": {}\r\n" + 
        			"    },\r\n" + 
        			"    \"_source\": {\r\n" + 
        			"        \"excludes\": []\r\n" + 
        			"    },\r\n" + 
        			"    \"aggs\": {\r\n" + 
        			"        \"2\": {\r\n" + 
        			"            \"terms\": {\r\n" + 
        			"                \"field\": \"ID\",\r\n" + 
        			"                \"size\": 1000,\r\n" + 
        			"                \"order\": {\r\n" + 
        			"                    \"_count\": \"desc\"\r\n" + 
        			"                }\r\n" + 
        			"            },\r\n" + 
        			"            \"aggs\": {\r\n" + 
        			"                \"3\": {\r\n" + 
        			"                    \"terms\": {\r\n" + 
        			"                        \"field\": \"AIRID\",\r\n" + 
        			"                        \"size\": 1000,\r\n" + 
        			"                        \"order\": {\r\n" + 
        			"                            \"_count\": \"desc\"\r\n" + 
        			"                        }\r\n" + 
        			"                    },\r\n" + 
        			"                    \"aggs\": {\r\n" + 
        			"                        \"4\": {\r\n" + 
        			"                            \"terms\": {\r\n" + 
        			"                                \"field\": \"APPNAME.keyword\",\r\n" + 
        			"                                \"size\": 1000,\r\n" + 
        			"                                \"order\": {\r\n" + 
        			"                                    \"_count\": \"desc\"\r\n" + 
        			"                                }\r\n" + 
        			"                            }\r\n" + 
        			"                        }\r\n" + 
        			"                    }\r\n" + 
        			"                }\r\n" + 
        			"            }\r\n" + 
        			"        }\r\n" + 
        			"    }\r\n" + 
        			"}";
        } else {
        	payload = "{\r\n" + 
            		"    \"size\": 0,\r\n" + 
            		"    \"query\": {\r\n" + 
            		"        \"bool\": {\r\n" + 
            		"            \"must\": [\r\n" + 
            		"                {\r\n" + 
            		"                  \"terms\": {\r\n" + 
            		"                    \"AIRID\": "+ airid +"\r\n" +  
            		"                  }\r\n" + 
            		"                },{\r\n" + 
            		"                  \"terms\": {\r\n" + 
            		"                    \"ID\": " + id + "\r\n" + 
            		"                  }\r\n" + 
            		"                }\r\n" + 
            		"            ],\r\n" + 
            		"            \"must_not\": []\r\n" + 
            		"        }\r\n" + 
            		"    },\r\n" + 
            		"    \"_source\": {\r\n" + 
            		"        \"excludes\": []\r\n" + 
            		"    },\r\n" + 
            		"    \"aggs\": {\r\n" + 
            		"        \"2\": {\r\n" + 
            		"            \"terms\": {\r\n" + 
            		"                \"field\": \"ID\",\r\n" + 
            		"                \"size\": 100,\r\n" + 
            		"                \"order\": {\r\n" + 
            		"                    \"_count\": \"desc\"\r\n" + 
            		"                }\r\n" + 
            		"            },\r\n" + 
            		"            \"aggs\": {\r\n" + 
            		"                \"3\": {\r\n" + 
            		"                    \"terms\": {\r\n" + 
            		"                        \"field\": \"AIRID\",\r\n" + 
            		"                        \"size\": 100,\r\n" + 
            		"                        \"order\": {\r\n" + 
            		"                            \"_count\": \"desc\"\r\n" + 
            		"                        }\r\n" + 
            		"                    },\r\n" + 
            		"                    \"aggs\": {\r\n" + 
            		"                        \"4\": {\r\n" + 
            		"                            \"terms\": {\r\n" + 
            		"                                \"field\": \"APPNAME.keyword\",\r\n" + 
            		"                                \"size\": 100,\r\n" + 
            		"                                \"order\": {\r\n" + 
            		"                                    \"_count\": \"desc\"\r\n" + 
            		"                                }\r\n" + 
            		"                            }\r\n" + 
            		"                        }\r\n" + 
            		"                    }\r\n" + 
            		"                }\r\n" + 
            		"            }\r\n" + 
            		"        }\r\n" + 
            		"    }\r\n" + 
            		"}";
        }
        
        return payload;
    }

    public String getAverageGenerationTime(String gte, String lte, Object airid, String id) {

        String payload = "{\n"
                + "					  \"size\": 0,\n"
                + "					  \"query\": {\n"
                + "						\"bool\": {\n"
                + "						  \"must\": [\n"
                + "							{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "							{\n"
                + "							  \"range\": {\n"
                + "								\"@timestamp\": {\n"
                + "								  \"gte\": " + gte + ",\n"
                + "								  \"lte\": " + lte + ",\n"
                + "								  \"format\": \"epoch_millis\"\n"
                + "								}\n"
                + "							  }\n"
                + "							}\n"
                + "						  ],\n"
                + "						  \"must_not\": []\n"
                + "						}\n"
                + "					  },\n"
                + "					  \"_source\": {\n"
                + "						\"excludes\": []\n"
                + "					  },"
                + "\n"
                + "					  \"aggs\": {\n"
                + "						\"1\": {\n"
                + "						  \"avg\": {\n"
                + "							\"field\": \"GenerationTime\"\n"
                + "						  }\n"
                + "						}\n"
                + "					  }\n"
                + "					}";

        return payload;
    }

    public String getUniqueHitsCount(String gte, String lte, Object airid, String id) {

        String payload = "{\n"
                + "				  \"size\": 0,\n"
                + "				  \"query\": {\n"
                + "					\"bool\": {\n"
                + "					  \"must\": [\n"
                + "						{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "						{\n"
                + "						  \"range\": {\n"
                + "							\"@timestamp\": {\n"
                + "							  \"gte\": " + gte + ",\n"
                + "							  \"lte\": " + lte + ",\n"
                + "							  \"format\": \"epoch_millis\"\n"
                + "							}\n"
                + "						  }\n"
                + "						}\n"
                + "					  ],\n"
                + "					  \"must_not\": []\n"
                + "					}\n"
                + "				  },\n"
                + "				  \"_source\": {\n"
                + "					\"excludes\": []\n"
                + "				  },\n"
                + "				  \"aggs\": {\n"
                + "					\"1\": {\n"
                + "					  \"cardinality\": {\n"
                + "						\"field\": \"Hits.hash\"\n"
                + "					  }\n"
                + "					}\n"
                + "				  }\n"
                + "				}";

        return payload;

    }

    public String getUniqueVisitorCount(String gte, String lte, Object airid, String id) {

        String payload = "{\n"
                + "			  \"size\": 0,\n"
                + "			  \"query\": {\n"
                + "				\"bool\": {\n"
                + "				  \"must\": [\n"
                + "					{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "					{\n"
                + "					  \"range\": {\n"
                + "						\"@timestamp\": {\n"
                + "						  \"gte\": " + gte + ",\n"
                + "						  \"lte\": " + lte + ",\n"
                + "						  \"format\": \"epoch_millis\"\n"
                + "						}\n"
                + "					  }\n"
                + "					}\n"
                + "				  ],\n"
                + "				  \"must_not\": []\n"
                + "				}\n"
                + "			  },\n"
                + "			  \"_source\": {\n"
                + "				\"excludes\": []\n"
                + "			  },\n"
                + "			  \"aggs\": {\n"
                + "				\"1\": {\n"
                + "				  \"cardinality\": {\n"
                + "					\"field\": \"User.hash\"\n"
                + "				  }\n"
                + "				}\n"
                + "			  }\n"
                + "			}";
        return payload;

    }

    public String getUniqueVisitsCount(String gte, String lte, Object airid, String id) {

        String payload = "{\n"
                + "			  \"size\": 0,\n"
                + "			  \"query\": {\n"
                + "				\"bool\": {\n"
                + "				  \"must\": [\n"
                + "					{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "					{\n"
                + "					  \"range\": {\n"
                + "						\"@timestamp\": {\n"
                + "						  \"gte\": " + gte + ",\n"
                + "						  \"lte\": " + lte + ",\n"
                + "						  \"format\": \"epoch_millis\"\n"
                + "						}\n"
                + "					  }\n"
                + "					}\n"
                + "				  ],\n"
                + "				  \"must_not\": []\n"
                + "				}\n"
                + "			  },\n"
                + "			  \"_source\": {\n"
                + "				\"excludes\": []\n"
                + "			  },\n"
                + "			  \"aggs\": {\n"
                + "				\"1\": {\n"
                + "				  \"cardinality\": {\n"
                + "					\"field\": \"Visits.hash\"\n"
                + "				  }\n"
                + "				}\n"
                + "			  }\n"
                + "			}";
        return payload;

    }

    public String getTopPageVisits(String gte, String lte, Object airid, String id, Long max) {

        String payload = "{\n"
                + "				  \"size\": 0,\n"
                + "				  \"query\": {\n"
                + "					\"bool\": {\n"
                + "					  \"must\": [\n"
                + "						{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "						{\n"
                + "						  \"range\": {\n"
                + "							\"@timestamp\": {\n"
                + "							  \"gte\": " + gte + ",\n"
                + "							  \"lte\": " + lte + ",\n"
                + "							  \"format\": \"epoch_millis\"\n"
                + "							}\n"
                + "						  }\n"
                + "						}\n"
                + "					  ],\n"
                + "					  \"must_not\": []\n"
                + "					}\n"
                + "				  },\n"
                + "				  \"_source\": {\n"
                + "					\"excludes\": []\n"
                + "				  },\n"
                + "				  \"aggs\": {\n"
                + "					\"2\": {\n"
                + "					  \"terms\": {\n"
                + "						\"field\": \"PageTitle\",\n"
                + "						\"size\": " + max + ",\n"
                + "						\"order\": {\n"
                + "						  \"1\": \"desc\"\n"
                + "						}\n"
                + "					  },\n"
                + "					  \"aggs\": {\n"
                + "						\"1\": {\n"
                + "						  \"cardinality\": {\n"
                + "							\"field\": \"Visits.hash\"\n"
                + "						  }\n"
                + "						}\n"
                + "					  }\n"
                + "					}\n"
                + "				  }\n"
                + "				}";
        return payload;
    }

    public String getTopCountry(String gte, String lte, Object airid, String id, Long max) {

        String payload = "{\n"
                + "			  \"size\": 0,\n"
                + "			  \"query\": {\n"
                + "				\"bool\": {\n"
                + "				  \"must\": [\n"
                + "					{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "					{\n"
                + "					  \"range\": {\n"
                + "						\"@timestamp\": {\n"
                + "						  \"gte\": " + gte + ",\n"
                + "						  \"lte\": " + lte + ",\n"
                + "						  \"format\": \"epoch_millis\"\n"
                + "						}\n"
                + "					  }\n"
                + "					}\n"
                + "				  ],\n"
                + "				  \"must_not\": []\n"
                + "				}\n"
                + "			  },\n"
                + "			  \"_source\": {\n"
                + "				\"excludes\": []\n"
                + "			  },\n"
                + "			  \"aggs\": {\n"
                + "				\"2\": {\n"
                + "				  \"terms\": {\n"
                + "					\"field\": \"Country.keyword\",\n"
                + "					\"size\": " + max + ",\n"
                + "					\"order\": {\n"
                + "					  \"1\": \"desc\"\n"
                + "					}\n"
                + "				  },\n"
                + "				  \"aggs\": {\n"
                + "					\"1\": {\n"
                + "					  \"cardinality\": {\n"
                + "						\"field\": \"Visits.hash\"\n"
                + "					  }\n"
                + "					}\n"
                + "				  }\n"
                + "				}\n"
                + "			  }\n"
                + "			}";

        return payload;
    }

    public String getTopDevices(String gte, String lte, Object airid, String id, Long max) {

        String payload = "{\n"
                + "				  \"size\": 0,\n"
                + "				  \"query\": {\n"
                + "					\"bool\": {\n"
                + "					  \"must\": [\n"
                + "						{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "						{\n"
                + "						  \"range\": {\n"
                + "							\"@timestamp\": {\n"
                + "							  \"gte\": " + gte + ",\n"
                + "							  \"lte\": " + lte + ",\n"
                + "							  \"format\": \"epoch_millis\"\n"
                + "							}\n"
                + "						  }\n"
                + "						}\n"
                + "					  ],\n"
                + "					  \"must_not\": []\n"
                + "					}\n"
                + "				  },\n"
                + "				  \"_source\": {\n"
                + "					\"excludes\": []\n"
                + "				  },\n"
                + "				  \"aggs\": {\n"
                + "					\"2\": {\n"
                + "					  \"terms\": {\n"
                + "						\"field\": \"DeviceType.keyword\",\n"
                + "						\"size\": " + max + ",\n"
                + "						\"order\": {\n"
                + "						  \"1\": \"desc\"\n"
                + "						}\n"
                + "					  },\n"
                + "					  \"aggs\": {\n"
                + "						\"1\": {\n"
                + "						  \"cardinality\": {\n"
                + "							\"field\": \"Visits.hash\"\n"
                + "						  }\n"
                + "						}\n"
                + "					  }\n"
                + "					}\n"
                + "				  }\n"
                + "				}";
        return payload;

    }

    public String getTopBrowser(String gte, String lte, Object airid, String id, Long max) {

        String payload = "{\n"
                + "				  \"size\": 0,\n"
                + "				  \"query\": {\n"
                + "					\"bool\": {\n"
                + "					  \"must\": [\n"
                + "						{\n"
                + "							\"terms\": {\n"
                + "								\"AIRID\": " + airid + "\n"
                + "							}\n"
                + "						}, {\n"
                + "							\"term\": {\n"
                + "								\"ID\": \"" + id + "\"\n"
                + "							}\n"
                + "						},\n"
                + "						{\n"
                + "						  \"range\": {\n"
                + "							\"@timestamp\": {\n"
                + "							  \"gte\": " + gte + ",\n"
                + "							  \"lte\": " + lte + ",\n"
                + "							  \"format\": \"epoch_millis\"\n"
                + "							}\n"
                + "						  }\n"
                + "						}\n"
                + "					  ],\n"
                + "					  \"must_not\": []\n"
                + "					}\n"
                + "				  },\n"
                + "				  \"_source\": {\n"
                + "					\"excludes\": []\n"
                + "				  },\n"
                + "				  \"aggs\": {\n"
                + "					\"2\": {\n"
                + "					  \"terms\": {\n"
                + "						\"field\": \"Browser.keyword\",\n"
                + "						\"size\": " + max + ",\n"
                + "						\"order\": {\n"
                + "						  \"1\": \"desc\"\n"
                + "						}\n"
                + "					  },\n"
                + "					  \"aggs\": {\n"
                + "						\"1\": {\n"
                + "						  \"cardinality\": {\n"
                + "							\"field\": \"Visits.hash\"\n"
                + "						  }\n"
                + "						}\n"
                + "					  }\n"
                + "					}\n"
                + "				  }\n"
                + "				}";

        return payload;

    }

    public String getVisitsOverTime(String gte, String lte, Object airid, String id, String itv, String tz) {

        String payload = "{\n"
                + "	\"size\": 0,\n"
                + "	\"query\": {\n"
                + "		\"bool\": {\n"
                + "			\"must\": [{\n"
                + "					\"terms\": {\n"
                + "						\"AIRID\": " + airid + "\n"
                + "					}\n"
                + "				}, {\n"
                + "					\"term\": {\n"
                + "						\"ID\": \"" + id + "\"\n"
                + "					}\n"
                + "				}, {\n"
                + "					\"range\": {\n"
                + "						\"@timestamp\": {\n"
                + "							\"gte\": " + gte + ",\n"
                + "							\"lte\": " + lte + ",\n"
                + "							\"format\": \"epoch_millis\"\n"
                + "						}\n"
                + "					}\n"
                + "				}\n"
                + "			],\n"
                + "			\"must_not\": []\n"
                + "		}\n"
                + "	},\n"
                + "	\"_source\": {\n"
                + "		\"excludes\": []\n"
                + "	},\n"
                + "	\"aggs\": {\n"
                + "		\"2\": {\n"
                + "			\"date_histogram\": {\n"
                + "				\"field\": \"@timestamp\",\n"
                + "				\"interval\": \"" + itv + "\",\n"
                + "				\"time_zone\": \"" + tz + "\",\n"
                + "				\"min_doc_count\": 1,\n"
                + "				\"format\": \"M/d/yHH:mm:ssZZ\"\n"
                + "			},\n"
                + "			\"aggs\": {\n"
                + "				\"1\": {\n"
                + "					\"cardinality\": {\n"
                + "						\"field\": \"Visits.hash\"\n"
                + "					}\n"
                + "				},\n"
                + "				\"3\": {\n"
                + "					\"cardinality\": {\n"
                + "						\"field\": \"User.hash\"\n"
                + "					}\n"
                + "				}\n"
                + "			}\n"
                + "\n"
                + "		}}}";

        return payload;
    }

    //******************* Custom Reports
    public String getPageCustomReport(String airid, String id, String gte, String lte) {

        String payload = "{\n"
                + "\"size\": 1000,\n"
                + "\"sort\": [\n"
                + "    {\n"
                + "      \"CleanPageURL.keyword\": {\n"
                + "        \"order\": \"asc\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],"
                + "  \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [\n"
                + "       {\n"
                + "        \"query_string\": {\n"
                + "            \"analyze_wildcard\": true,\n"
                + "            \"query\": \"AIRID: \\\"" + airid + "\\\" AND ID: \\\"" + id + "\\\"  \"\n"
                + "          }\n"
                + "        },"
                + "        {\n"
                + "          \"range\": {\n"
                + "            \"@timestamp\": {\n"
                + "              \"gte\": " + gte + ",\n"
                + "              \"lte\": " + lte + ",\n"
                + "              \"format\": \"epoch_millis\"\n"
                + "            }\n"
                + "          }\n"
                + "        }\n"
                + "      ],\n"
                + "      \"must_not\": []\n"
                + "    }\n"
                + "  },\n"
                + "  \"_source\": {\n"
                + "    \"includes\": " + getReportColumns() + "\n"
                + "  }\n"
                + "}";

        return payload;

    }

    public String getHitsReports(String airid, String id, String includes, String gte, String lte) {

        String payload = "";

        // Hits by geography
        if (includes.equalsIgnoreCase("hitsByGeography")) {
            payload = "{\n"
                    + "  \"size\": 0,\n"
                    + "  \"query\": {\n"
                    + "    \"bool\": {\n"
                    + "      \"must\": [\n"
                    + "       {\n"
                    + "        \"query_string\": {\n"
                    + "            \"analyze_wildcard\": true,\n"
                    + "            \"query\": \"AIRID: \\\"" + airid + "\\\" AND ID: \\\"" + id + "\\\"\"\n"
                    + "          }\n"
                    + "        },        \n"
                    + "        {\n"
                    + "          \"range\": {\n"
                    + "            \"@timestamp\": {\n"
                    + "               \"gte\": " + gte + ",\n"
                    + "              \"lte\": " + lte + ",\n"
                    + "              \"format\": \"epoch_millis\"\n"
                    + "            }\n"
                    + "          }\n"
                    + "        }\n"
                    + "      ],\n"
                    + "      \"must_not\": []\n"
                    + "    }\n"
                    + "  },\n"
                    + "  \"aggs\": {\n"
                    + "    \"2\": {\n"
                    + "      \"terms\": {\n"
                    + "        \"field\": \"PageCustomVariable1Value.keyword\",\n"
                    + "     \"order\": {\n"
                    + "          \"_term\": \"asc\"\n"
                    + "        }"
                    + "      },\"aggs\": {\n"
                    + "        \"3\": {\n"
                    + "          \"terms\": {\n"
                    + "            \"field\": \"Geography.keyword\"\n"
                    + "          },\"aggs\": {\n"
                    + "            \"1\": {\n"
                    + "              \"cardinality\": {\n"
                    + "                \"field\": \"Hits.hash\"\n"
                    + "              }\n"
                    + "            }\n"
                    + "          }\n"
                    + "        }\n"
                    + "      }\n"
                    + "    }\n"
                    + "  }\n"
                    + "}";

        } else if (includes.equalsIgnoreCase("hitsByAsset")) {
            // Hits By Asset
            payload = "{\n"
                    + "  \"size\": 0,\n"
                    + "  \"query\": {\n"
                    + "    \"bool\": {\n"
                    + "      \"must\": [\n"
                    + "       {\n"
                    + "        \"query_string\": {\n"
                    + "            \"analyze_wildcard\": true,\n"
                    + "            \"query\": \"AIRID: \\\"" + airid + "\\\" AND ID: \\\"" + id + "\\\"\"\n"
                    + "          }\n"
                    + "        },        \n"
                    + "        {\n"
                    + "          \"range\": {\n"
                    + "            \"@timestamp\": {\n"
                    + "               \"gte\": " + gte + ",\n"
                    + "              \"lte\": " + lte + ",\n"
                    + "              \"format\": \"epoch_millis\"\n"
                    + "            }\n"
                    + "          }\n"
                    + "        }\n"
                    + "      ],\n"
                    + "      \"must_not\": []\n"
                    + "    }\n"
                    + "  },\n"
                    + "  \"aggs\": {\n"
                    + "    \"2\": {\n"
                    + "      \"terms\": {\n"
                    + "        \"field\": \"PageCustomVariable1Value.keyword\",\n"
                    + "     \"order\": {\n"
                    + "          \"_term\": \"asc\"\n"
                    + "        }"
                    + "      },\"aggs\": {\n"
                    + "        \"3\": {\n"
                    + "          \"terms\": {\n"
                    + "            \"field\": \"PageCustomVariable3Value.keyword\"\n"
                    + "          },\"aggs\": {\n"
                    + "            \"4\": {\n"
                    + "              \"terms\": {\n"
                    + "                \"field\": \"PageCustomVariable4Value.keyword\"\n"
                    + "              },\"aggs\": {\n"
                    + "                \"1\": {\n"
                    + "                  \"cardinality\": {\n"
                    + "                    \"field\": \"Hits.hash\"\n"
                    + "                  }\n"
                    + "                }\n"
                    + "              }\n"
                    + "            }\n"
                    + "          }\n"
                    + "        }\n"
                    + "      }\n"
                    + "    }\n"
                    + "  }\n"
                    + "}";
        }
        return payload;

    }

    public String getReportBySegmentName(String airid, String id, String gte, String lte, String arg) {

        String payload = "{\n"
                + "  \"size\": 0,\n"
                + "  \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [\n"
                + "       {\n"
                + "        \"query_string\": {\n"
                + "            \"analyze_wildcard\": true,\n"
                + "            \"query\": \"AIRID: \\\"" + airid + "\\\" AND ID: \\\"" + id + "\\\"\"\n"
                + "          }\n"
                + "        },"
                + "        {\n"
                + "          \"range\": {\n"
                + "            \"@timestamp\": {\n"
                + "              \"gte\": " + gte + ",\n"
                + "              \"lte\": " + lte + ",\n"
                + "              \"format\": \"epoch_millis\"\n"
                + "            }\n"
                + "          }\n"
                + "        }\n"
                + "      ],\n"
                + "      \"must_not\": []\n"
                + "    }\n"
                + "  },\n"
                + "  \"_source\": {\n"
                + "    \"excludes\": []\n"
                + "  },\n"
                + "  \"aggs\": {\n"
                + "    \"2\": {\n"
                + "      \"terms\": {\n"
                + "        \"field\": \"PageCustomVariable1Value.keyword\",\n"
                + "        \"size\": 1000,\n"
                + "\"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "      },\n"
                + "      \"aggs\": {\n"
                + "        \"3\": {\n"
                + "          \"terms\": {\n"
                + "            \"field\": \"" + arg + "\"\n"
                + "          },\n"
                + "          \"aggs\": {\n"
                + "            \"1\": {\n"
                + "              \"cardinality\": {\n"
                + "                \"field\": \"User.hash\"\n"
                + "              }\n"
                + "            }\n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}";

        return payload;

    }

    public String getReportBySegmentName(String airid, String id, String gte, String lte) {

        String payload = "{\n"
                + "  \"size\": 0,\n"
                + "  \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [\n"
                + "{\n"
                + "        \"query_string\": {\n"
                + "            \"analyze_wildcard\": true,\n"
                + "            \"query\": \"AIRID: \\\"" + airid + "\\\" AND ID: \\\"" + id + "\\\"\"\n"
                + "          }\n"
                + "        },"
                + "        {\n"
                + "          \"range\": {\n"
                + "            \"@timestamp\": {\n"
                + "              \"gte\": " + gte + ",\n"
                + "              \"lte\": " + lte + ",\n"
                + "              \"format\": \"epoch_millis\"\n"
                + "            }\n"
                + "          }\n"
                + "        }\n"
                + "      ],\n"
                + "      \"must_not\": []\n"
                + "    }\n"
                + "  },\n"
                + "  \"_source\": {\n"
                + "    \"excludes\": []\n"
                + "  },\n"
                + "  \"aggs\": {\n"
                + "    \"2\": {\n"
                + "      \"terms\": {\n"
                + "        \"field\": \"PageCustomVariable1Value.keyword\",\n"
                + "        \"size\": 1000,\n"
                + "     \"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "      },\n"
                + "      \"aggs\": {\n"
                + "        \"3\": {\n"
                + "          \"terms\": {\n"
                + "            \"field\": \"CareerTracks\"\n"
                + "          },\n"
                + "          \"aggs\": {\n"
                + "             \"4\": {\n"
                + "          \"terms\": {\n"
                + "            \"field\": \"PageCustomVariable4Value.keyword\"\n"
                + "          }, \"aggs\": {\n"
                + "            \"1\": {\n"
                + "              \"cardinality\": {\n"
                + "                \"field\": \"User.hash\"\n"
                + "              }\n"
                + "            },\n"
                + "            \"5\":{\n"
                + "              \"cardinality\": {\n"
                + "                \"field\": \"Hits.hash\"\n"
                + "              }\n"
                + "            }\n"
                + "          } \n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}\n"
                + "}";

        return payload;

    }

    public String getUniquePageUrlList() {

        String payload = "{\n"
                + "  \"size\": 0,\n"
                + "  \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [\n"
                + "        {\n"
                + "          \"wildcard\": {\n"
                + "            \"AIRID\": {\n"
                + "              \"value\": \"2700\"\n"
                + "            }\n"
                + "          }\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "    \n"
                + "  }, \n"
                + "  \"aggs\": {\n"
                + "    \"2\": {\n"
                + "      \"terms\": {\n"
                + "        \"field\": \"CleanPageURL.keyword\",\n"
                + "        \"size\": 1000,\n"
                + "     \"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}";

        return payload;
    }

    public String getUniquePageUrl(String pageUrl, String gte, String lte, String id, String airId) {

        String payload = "{\n"
                + "\"size\": 0,\n"
                + "\"query\": {\n"
                + "\"bool\": {\n"
                + "\"must\": [{\n"
                + "	\"wildcard\": {\n"
                + "		\"PageURL.keyword\": {\n"
                + "			\"value\": \"" + pageUrl + "\"\n"
                + "		}\n"
                + "	}\n"
                + "}, {\n"
                + "	\"range\": {\n"
                + "		\"@timestamp\": {\n"
                + "			\"gte\": " + gte + ",\n"
                + "			\"lte\": " + lte + ",\n"
                + "			\"format\": \"epoch_millis\"\n"
                + "		}\n"
                + "	}\n"
                + "}, {\n"
                + "	\"term\": {\n"
                + "		\"AIRID\": {\n"
                + "			\"value\": \"" + airId + "\"\n"
                + "		}\n"
                + "	}\n"
                + "}, {\n"
                + "	\"term\": {\n"
                + "		\"ID\": {\n"
                + "			\"value\": \"" + id + "\"\n"
                + "		}\n"
                + "	}\n"
                + "}\n"
                + "]\n"
                + "}\n"
                + "\n"
                + "},\n"
                + "\"aggs\": {\n"
                + "\"2\": {\n"
                + "\"terms\": {\n"
                + "\"field\": \"CleanPageURL.keyword\",\n"
                + "\"size\": 1000,\n"
                + "\"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "},\n"
                + "\"aggs\": {\n"
                + "\"3\": {\n"
                + "	\"terms\": {\n"
                + "		\"field\": \"Geography.keyword\"\n"
                + "	},\n"
                + "	\"aggs\": {\n"
                + "		\"1\": {\n"
                + "			\"avg\": {\n"
                + "				\"field\": \"GenerationTime\"\n"
                + "			}\n"
                + "		}\n"
                + "	}\n"
                + "}\n"
                + "}\n"
                + "\n"
                + "}\n"
                + "}\n"
                + "}";

        return payload;

    }

    public String getUsageByUser(String airid, String id, String gte, String lte) {

        String payload = "{\n"
                + "  \"size\": 0,\n"
                + "   \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [{\n"
                + "	      \"range\": {\n"
                + "		      \"@timestamp\": {\n"
                + "			      \"gte\": " + gte + ",\n"
                + "			      \"lte\": " + lte + ",\n"
                + "			      \"format\": \"epoch_millis\"\n"
                + "		      }\n"
                + "	      }\n"
                + "        },{\n"
                + "        	\"term\": {\n"
                + "        		\"AIRID\": {\n"
                + "        			\"value\": \"" + airid + "\"\n"
                + "        		}\n"
                + "        	}\n"
                + "        }, {\n"
                + "        	\"term\": {\n"
                + "        		\"ID\": {\n"
                + "        			\"value\": \"" + id + "\"\n"
                + "        		}\n"
                + "        	}\n"
                + "        }]\n"
                + "        }},\n"
                + "\"aggs\": {\n"
                + "    \"2\": {\n"
                + "      \"terms\": {\n"
                + "        \"field\": \"User\",\n"
                + "     \"size\": 1000,\n"
                + "     \"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "      },\n"
                + "      \"aggs\": {\n"
                + "        \"3\": {\n"
                + "          \"terms\": {\n"
                + "            \"field\": \"Visits\"\n"
                + "          },\"aggs\": {\n"
                + "            \"4\": {\n"
                + "              \"avg\": {\n"
                + "                \"field\": \"TotalElapseTimeOfVisit\"\n"
                + "              }\n"
                + "            },\n"
                + "            \"5\": {\n"
                + "              \"cardinality\": {\n"
                + "                \"field\": \"PageTitle\"\n"
                + "              }\n"
                + "            }\n"
                + "          }  \n"
                + "        },\n"
                + "        \"6\" : {\n"
                + "          \"cardinality\": {\n"
                + "            \"field\": \"Visits.hash\"\n"
                + "          }\n"
                + "        },\n"
                + "        \"7\":{\n"
                + "          \"cardinality\": {\n"
                + "            \"field\": \"Hits.hash\"\n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }"
                + "}";

        return payload;
    }

    public List<String> getReportColumns() {

        return Arrays.asList(
                "\"CleanPageURL\"",
                "\"PageCustomVariable1Value\"",
                "\"PageCustomVariable3Value\"",
                "\"PageCustomVariable2Value\"",
                "\"PageCustomVariable4Value\"",
                "\"User\"",
                "\"FirstActionTime\"",
                "\"Browser\"",
                "\"OperatingSystem\"",
                "\"DeviceType\"",
                "\"CareerLevel\"",
                "\"Geography\"",
                "\"CareerTracks\"");
    }

    // ******************
    public String saveUserSession(JSONObject o) {

        JSONObject sessionObj = (JSONObject) o.get("sessionObject");
        String payload = "{\n"
                + "\"nonce\" : \"" + sessionObj.get("nonce") + "\",\n"
                + "\"eid\" : \"" + sessionObj.get("eid") + "\",\n"
                + "\"authtime\" : " + sessionObj.get("authtime") + ",\n"
                + "\"expiration\" : " + sessionObj.get("exp") + "\n"
                + "}";

        return payload;

    }

    public String getUserSession(JSONObject o) {

        JSONObject oItem = (JSONObject) o.get("userSession");
        Long nowMillis = Long.parseLong(oItem.get("now").toString());
        nowMillis = nowMillis / 1000;

        String payload = "{\n"
                + "  \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [\n"
                + "        {\n"
                + "          \"query_string\": {\n"
                + "            \"analyze_wildcard\": true,\n"
                + "            \"query\": \"eid:\\\"" + oItem.get("eid") + "\\\" AND nonce:\\\"" + oItem.get("nonce") + "\\\"\"\n"
                + "          }\n"
                + "        },\n"
                + "        {\n"
                + "          \"range\": {\n"
                + "            \"expiration\": {\n"
                + "              \"gte\": " + nowMillis + ",\n"
                + "              \"format\": \"epoch_second\"\n"
                + "            }\n"
                + "          }\n"
                + "        }\n"
                + "      ],\n"
                + "      \"must_not\": []\n"
                + "    }\n"
                + "  },\n"
                + "  \"_source\": {\n"
                + "    \"excludes\": []\n"
                + "  },\n"
                + "  \"aggs\": {}\n"
                + "}";

        return payload;
    }

    public String checkExistingSession(String eid, String nonce) {
        String payload = "";
        if (StringUtils.hasText(nonce)) {
            payload = "{\n"
                    + "  \"query\": {\n"
                    + "    \"bool\": {\n"
                    + "      \"must\": [\n"
                    + "        {\n"
                    + "          \"query_string\": {\n"
                    + "            \"analyze_wildcard\": true,\n"
                    + "            \"query\": \"eid:\\\"" + eid + "\\\" AND nonce:\\\"" + nonce + "\\\"\"\n"
                    + "          }\n"
                    + "        }]}}}";
        } else {
            payload = "{\n"
                    + "  \"query\": {\n"
                    + "    \"bool\": {\n"
                    + "      \"must\": [\n"
                    + "        {\n"
                    + "          \"query_string\": {\n"
                    + "            \"analyze_wildcard\": true,\n"
                    + "            \"query\": \"eid:\\\"" + eid + "\\\" \"\n"
                    + "          }\n"
                    + "        }]}}}";
        }
        return payload;
    }

    public String getCustomReportsOverviewPageMetrics(Object airid, String gte, String lte, String id) {

        String payload = "{\n"
                + "  \"size\": 0, \n"
                + "  \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [{\n"
                + "	      \"range\": {\n"
                + "		      \"@timestamp\": {\n"
                + "			      \"gte\": " + gte + ",\n"
                + "			      \"lte\": " + lte + ",\n"
                + "			      \"format\": \"epoch_millis\"\n"
                + "		      }\n"
                + "	      }\n"
                + "        },{\n"
                + "        	\"terms\": {\n"
                + "        		\"AIRID\": " + airid + " \n"
                + "        	}\n"
                + "        }, {\n"
                + "        	\"term\": {\n"
                + "        		\"ID\": {\n"
                + "        			\"value\": \"" + id + "\"\n"
                + "        		}\n"
                + "        	}\n"
                + "        }]\n"
                + "        }},\n"
                + "  \"aggs\": {\n"
                + "    \"2\": {\n"
                + "      \"terms\": {\n"
                + "        \"field\": \"CleanPageURL.keyword\",\n"
                + "        \"size\": 1000,\n"
                + "         \"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "      },\n"
                + "      \"aggs\": {\n"
                + "        \"3\": {\n"
                + "          \"cardinality\": {\n"
                + "            \"field\": \"Hits.hash\"\n"
                + "          }\n"
                + "        },\n"
                + "        \"4\": {\n"
                + "          \"cardinality\": {\n"
                + "            \"field\": \"Visits.hash\"\n"
                + "          }\n"
                + "        },\n"
                + "        \"5\": {\n"
                + "          \"cardinality\": {\n"
                + "            \"field\": \"User.hash\"\n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}";

        return payload;
    }

    public String getCustomReportsOverviewUserMetrics(Object airid, String gte, String lte, String id) {

        String payload = "{\r\n"
                + "  \"size\": 0,\r\n"
                + "  \"query\": {\r\n"
                + "    \"bool\": {\r\n"
                + "      \"must\": [\r\n"
                + "        {\r\n"
                + "          \"range\": {\r\n"
                + "            \"@timestamp\": {\r\n"
                + "              \"gte\": " + gte + ",\r\n"
                + "              \"lte\": " + lte + ",\r\n"
                + "              \"format\": \"epoch_millis\"\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        },{\r\n"
                + "          \"terms\": {\r\n"
                + "            \"AIRID\": " + airid + " \r\n"
                + "          }\r\n"
                + "        },{\r\n"
                + "          \"term\": {\r\n"
                + "            \"ID\": {\r\n"
                + "              \"value\": \"" + id + "\" \r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        }\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  },\"aggs\": {\r\n"
                + "    \"2\": {\r\n"
                + "      \"terms\": {\r\n"
                + "        \"field\": \"User\",\r\n"
                + "         \"size\": 1000,\n"
                + "         \"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "      },\r\n"
                + "      \"aggs\": {\r\n"
                + "        \"3\": {\r\n"
                + "          \"terms\": {\r\n"
                + "            \"field\": \"CareerLevel\"\r\n"
                + "          },\r\n"
                + "          \"aggs\": {\r\n"
                + "            \"4\": {\r\n"
                + "              \"terms\": {\r\n"
                + "                \"field\": \"CareerTracks\"\r\n"
                + "              },\r\n"
                + "              \"aggs\": {\r\n"
                + "                \"5\": {\r\n"
                + "                  \"terms\": {\r\n"
                + "                    \"field\": \"Geography.keyword\"\r\n"
                + "                  },\r\n"
                + "                  \"aggs\": {\r\n"
                + "                    \"6\": {\r\n"
                + "                      \"terms\": {\r\n"
                + "                        \"field\": \"Country.keyword\"\r\n"
                + "                      }\r\n"
                + "                    }\r\n"
                + "                  }\r\n"
                + "                }\r\n"
                + "              }\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        },\r\n"
                + "        \"1\":{\r\n"
                + "          \"cardinality\": {\r\n"
                + "            \"field\": \"Visits.hash\"\r\n"
                + "          }\r\n"
                + "        }\r\n"
                + "      }\r\n"
                + "    }\r\n"
                + "  }\r\n"
                + "}";

        return payload;
    }

    public String getCustomPagesReport(String airid, String id, String gte, String lte, String filter) {

        String payload = "{\n"
                + "  \"size\": 0,\n"
                + "   \"query\": {\n"
                + "    \"bool\": {\n"
                + "      \"must\": [{\n"
                + "	      \"range\": {\n"
                + "		      \"@timestamp\": {\n"
                + "			      \"gte\": " + gte + ",\n"
                + "			      \"lte\": " + lte + ",\n"
                + "			      \"format\": \"epoch_millis\"\n"
                + "		      }\n"
                + "	      }\n"
                + "      },{ \n"
                + "        \"query_string\": {\n"
                + "            \"analyze_wildcard\": true,\n"
                + "            \"query\": \"AIRID:\\\"" + airid + "\\\" AND ID:\\\"" + id + "\\\"\"\n"
                + "        }\n"
                + "      }]\n"
                + "        }},\n"
                + "  \"aggs\": {\n"
                + "    \"1\": {\n"
                + "      \"terms\": {\n"
                + "        \"field\": \"PageURL.keyword\",\n"
                + "         \"size\": 500,\n"
                + "         \"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "      },\n"
                + "      \"aggs\": {\n"
                + "        \"2\": {\n"
                + "          \"terms\": {\n"
                + "            \"field\": \"" + filter + "\",\n"
                + "         \"size\": 50,\n"
                + "         \"order\": {\n"
                + "          \"_term\": \"asc\"\n"
                + "        }"
                + "          },\n"
                + "          \"aggs\": {\n"
                + "            \"3\": {\n"
                + "              \"terms\": {\n"
                + "                \"field\": \"Visits\"\n"
                + "              },\n"
                + "              \"aggs\": {\n"
                + "                \"4\": {\n"
                + "                  \"avg\": {\n"
                + "                    \"field\": \"TotalElapseTimeOfVisit\"\n"
                + "                  }\n"
                + "                },\n"
                + "                \"5\": {\n"
                + "                  \"cardinality\": {\n"
                + "                    \"field\": \"PageTitle\"\n"
                + "                  }\n"
                + "                }\n"
                + "              }  \n"
                + "            },\n"
                + "            \"6\" : {\n"
                + "              \"cardinality\": {\n"
                + "                \"field\": \"Visits.hash\"\n"
                + "              }\n"
                + "            },\n"
                + "            \"7\":{\n"
                + "              \"cardinality\": {\n"
                + "                \"field\": \"Hits.hash\"\n"
                + "              }\n"
                + "            }\n"
                + "          }\n"
                + "        }\n"
                + "      } \n"
                + "    }\n"
                + "  }\n"
                + "}";
        return payload;
    }

    
    public String getCustomPagesReportV2(String airid, String id, String gte, String lte, String filter) {

        String payload = "{\r\n" + 
        		"                  \"size\": 0,\r\n" + 
        		"                   \"query\": {\r\n" + 
        		"                    \"bool\": {\r\n" + 
        		"                      \"must\": [{\r\n" + 
        		"                	      \"range\": {\r\n" + 
        		"                		      \"@timestamp\": {\r\n" + 
        		"                			      \"gte\": " + gte + " ,\r\n" + 
        		"                			      \"lte\": " + lte + " ,\r\n" + 
        		"                			      \"format\": \"epoch_millis\"\r\n" + 
        		"                		      }\r\n" + 
        		"                	      }\r\n" + 
        		"                      },{ \r\n" + 
        		"                        \"query_string\": {\r\n" + 
        		"                            \"analyze_wildcard\": true,\r\n" + 
        		"                            \"query\": \"AIRID: "+ airid + " AND ID: " + id + "\"\r\n" + 
        		"                        }\r\n" + 
        		"                      }]\r\n" + 
        		"                        }},\r\n" + 
        		"                      \"aggs\": {\r\n" + 
        		"                        \"1\": {\r\n" + 
        		"                          \"terms\": {\r\n" + 
        		"                            \"field\": \"PageURL.keyword\",\r\n" + 
        		"                            \"size\": 500,\r\n" + 
        		"                            \"order\": {\r\n" + 
        		"                              \"_term\": \"asc\"\r\n" + 
        		"                            }\r\n" + 
        		"                          },\r\n" + 
        		"                         \"aggs\": {\r\n" + 
        		"                           \"2\": {\r\n" + 
        		"                             \"terms\": {\r\n" + 
        		"                               \"field\": \""+ filter +"\",\r\n" + 
        		"                               \"size\": 50,\r\n" + 
        		"                               \"order\": {\r\n" + 
        		"                                 \"_term\": \"asc\"\r\n" + 
        		"                               }\r\n" + 
        		"                             },\r\n" + 
        		"                             \"aggs\": {\r\n" + 
        		"                               \"3\": {\r\n" + 
        		"                                 \"terms\": {\r\n" + 
        		"                                   \"field\": \"Visits\"\r\n" + 
        		"                                 }\r\n" + 
        		"                               },\r\n" + 
        		"                               \"4\": {\r\n" + 
        		"                                \"cardinality\": {\r\n" + 
        		"                                  \"field\": \"Visits.hash\"\r\n" + 
        		"                                } \r\n" + 
        		"                               },\r\n" + 
        		"                               \"5\" : {\r\n" + 
        		"                                 \"cardinality\": {\r\n" + 
        		"                                   \"field\": \"Hits.hash\"\r\n" + 
        		"                                 }\r\n" + 
        		"                               },\r\n" + 
        		"                               \"6\" : {\r\n" + 
        		"                                 \"avg\": {\r\n" + 
        		"                                   \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
        		"                                 }\r\n" + 
        		"                               },\r\n" + 
        		"                               \"7\" : {\r\n" + 
        		"                                 \"cardinality\": {\r\n" + 
        		"                                   \"field\": \"PageTitle\"\r\n" + 
        		"                                 }\r\n" + 
        		"                               }\r\n" + 
        		"                             }\r\n" + 
        		"                           },\r\n" + 
        		"                           \"sum_visits\": {\r\n" + 
        		"                             \"sum_bucket\": {\r\n" + 
        		"                               \"buckets_path\": \"2>4\"\r\n" + 
        		"                             }\r\n" + 
        		"                           },\r\n" + 
        		"                           \"sum_hits\":{\r\n" + 
        		"                             \"sum_bucket\": {\r\n" + 
        		"                               \"buckets_path\": \"2>5\"\r\n" + 
        		"                             }\r\n" + 
        		"                           },\r\n" + 
        		"                           \"avg_page_visits\" : {\r\n" + 
        		"                             \"avg_bucket\": {\r\n" + 
        		"                               \"buckets_path\": \"2>7\"\r\n" + 
        		"                             }\r\n" + 
        		"                           },\r\n" + 
        		"                           \"avg_page_duration\" : {\r\n" + 
        		"                             \"avg_bucket\": {\r\n" + 
        		"                               \"buckets_path\": \"2>6\"\r\n" + 
        		"                             }\r\n" + 
        		"                           }\r\n" + 
        		"                         }\r\n" + 
        		"                        }\r\n" + 
        		"                      }\r\n" + 
        		"                }";
        
        return payload;
    }
    
    
    public String getCustomPagesReportDetails(String airid, String id, String gte, String lte, String filter, String pageurl) {

        String payload = "{\r\n" + 
        		"  \"size\": 0,\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"    \"must\": [{\r\n" + 
        		"      \"range\": {\r\n" + 
        		"          \"@timestamp\": {\r\n" + 
        		"              \"gte\": " + gte + " ,\r\n" + 
        		"              \"lte\": " + lte + " ,\r\n" + 
        		"              \"format\": \"epoch_millis\"\r\n" + 
        		"                		  }\r\n" + 
        		"                }\r\n" + 
        		"                },{ \r\n" + 
        		"                  \"query_string\": {\r\n" + 
        		"                  \"analyze_wildcard\": true,\r\n" + 
        		"                  \"query\": \"AIRID: " + airid + " AND ID: " + id + "\"\r\n" + 
        		"                }\r\n" + 
        		"              }],\r\n" + 
        		"      \"filter\": {\r\n" + 
        		"        \"term\": {\r\n" + 
        		"          \"PageURL.keyword\": \"" + pageurl + "\"\r\n" + 
        		"        }\r\n" + 
        		"      }\r\n" + 
        		"          }},\r\n" + 
        		"    \"aggs\" : {\r\n" + 
        		"      \"1\" : {\r\n" + 
        		"        \"terms\": {\r\n" + 
        		"          \"field\": \"" + filter + "\",\r\n" + 
        		"          \"size\": 50,\r\n" + 
        		"          \"order\": {\r\n" + 
        		"            \"_term\": \"asc\"\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        \"aggs\": {\r\n" + 
        		"          \"2\":{\r\n" + 
        		"            \"terms\": {\r\n" + 
        		"              \"field\": \"Visits\"\r\n" + 
        		"            },\r\n" + 
        		"            \"aggs\": {\r\n" + 
        		"              \"3\": {\r\n" + 
        		"                \"avg\": {\r\n" + 
        		"                  \"field\": \"TotalElapseTimeOfVisit\"   \r\n" + 
        		"                }\r\n" + 
        		"              },\r\n" + 
        		"              \"4\" : {\r\n" + 
        		"                \"cardinality\": {\r\n" + 
        		"                  \"field\": \"PageTitle\"\r\n" + 
        		"                }\r\n" + 
        		"              },\r\n" + 
        		"              \"5\" : {\r\n" + 
        		"                \"cardinality\": {\r\n" + 
        		"                  \"field\": \"Visits.hash\"\r\n" + 
        		"                }\r\n" + 
        		"              },\r\n" + 
        		"              \"6\" : {\r\n" + 
        		"                \"cardinality\": {\r\n" + 
        		"                  \"field\": \"Hits.hash\"\r\n" + 
        		"                }\r\n" + 
        		"              }\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"avg_page_duration\":{\r\n" + 
        		"            \"avg_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>3\"\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"avg_page_visit\" : {\r\n" + 
        		"            \"avg_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>4\"\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"sum_visits\":{\r\n" + 
        		"            \"sum_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>5\"\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"sum_hits\": {\r\n" + 
        		"            \"sum_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>6\"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"}";
        
        return payload;
    }
    
    public String getAllUser() {

        String payload = "{\r\n"
                + "  \"size\": 100,\r\n"
                + "  \"query\": {\r\n"
                + "    \"match_all\": {}\r\n"
                + "    \r\n"
                + "  }\r\n"
                + "}";
        return payload;

    }
    
    public String getUser(String from, String size, String search) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.convertSearchToParams(search);
    	if(search.equalsIgnoreCase("*")) {
    		 payload = "{\r\n" + 
            		"  \"from\": "+from+", \r\n" + 
            		"  \"size\": "+size+",\r\n" + 
            		"  \"query\": {\r\n" + 
            		"    \"match_all\": {}\r\n" + 
            		"  }\r\n" + 
            		"}";
    	}else {
    		 payload = "{\r\n" + 
    		 		"  \"from\": "+from+", \r\n" + 
    		 		"  \"size\": "+size+",\r\n" + 
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"          {\r\n" + 
    		 		"          \"query_string\": {\r\n" + 
    		 		"            \"analyze_wildcard\": true,\r\n" + 
    		 		"            \"query\": \""+searchQuery+"\",\r\n" + 
    		 		"            \"fields\": [\"user_eid\", \"user_access\", \"user_airid\", \"user_team\", \"access_expiration_date\", \"access_status\", \"created_date\" , \"last_update_date\", \"last_update_by\"]\r\n" + 
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	}
        return payload;
    }
    
    public String getAnnounce(String from, String size, String search) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.convertSearchToParams(search);
    	if(search.equalsIgnoreCase("*")) {
    		 payload = "{\r\n" + 
            		"  \"from\": "+from+", \r\n" + 
            		"  \"size\": "+size+",\r\n" + 
            		"  \"query\": {\r\n" + 
            		"    \"match_all\": {}\r\n" + 
            		"  }\r\n" + 
            		"}";
    	}else {
    		 payload = "{\r\n" + 
    		 		"  \"from\": "+from+", \r\n" + 
    		 		"  \"size\": "+size+",\r\n" + 
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"          {\r\n" + 
    		 		"          \"query_string\": {\r\n" + 
    		 		"            \"analyze_wildcard\": true,\r\n" + 
    		 		"            \"query\": \""+searchQuery+"\",\r\n" + 
    		 		"            \"fields\": [\"announcement_type\", \"announcement_due_date\", \"announcement_title\", \"announcement_content\", \"announcement_status\", \"announcement_created_date\" , \"announcement_last_updated_date\", \"announcement_last_updated_by\"]\r\n" + 
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	}
        return payload;
    }
    
    
    public String getAnnounceLive(String from, String size, String search) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.convertSearchToParams(search);
    	if(search.equalsIgnoreCase("*")) {
    		 payload = "{\r\n" + 
            		"  \"from\": "+from+", \r\n" + 
            		"  \"size\": "+size+",\r\n" + 
            		"	\"sort\": [\r\n" + 
            		"      {\r\n" + 
            		"        \"announcement_created_date\": {\r\n" + 
            		"          \"order\": \"desc\"\r\n" + 
            		"        }\r\n" + 
            		"      }\r\n" + 
            		"    ],\r\n" +
            		"  \"query\": {\r\n" + 
            		"    \"bool\": {\r\n" + 
            		"      \"must\": [\r\n" + 
            		"        {\r\n" + 
            		"          \"term\": {\r\n" + 
            		"            \"announcement_status\": {\r\n" + 
            		"              \"value\": \"live\"\r\n" + 
            		"            }\r\n" + 
            		"          }\r\n" + 
            		"        }\r\n" + 
            		"      ]\r\n" + 
            		"    }\r\n" +
            		"  }\r\n" + 
            		"}";
    	}else {
    		 payload = "{\r\n" + 
    		 		"  \"from\": "+from+", \r\n" + 
    		 		"  \"size\": "+size+",\r\n" +
    		 		" \"sort\": [\r\n" + 
    		 		"      {\r\n" + 
    		 		"        \"announcement_created_date\": {\r\n" + 
    		 		"          \"order\": \"desc\"\r\n" + 
    		 		"        }\r\n" + 
    		 		"      }\r\n" + 
    		 		"    ],\r\n" +
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"		{\r\n" + 
    		 		"          \"term\": {\r\n" + 
    		 		"            \"announcement_status\": {\r\n" + 
    		 		"              \"value\": \"live\"\r\n" + 
    		 		"            }\r\n" + 
    		 		"          }\r\n" + 
    		 		"        },\r\n" +
    		 		"          {\r\n" + 
    		 		"          \"query_string\": {\r\n" + 
    		 		"            \"analyze_wildcard\": true,\r\n" + 
    		 		"            \"query\": \""+searchQuery+"\",\r\n" + 
    		 		"            \"fields\": [\"announcement_type\", \"announcement_due_date\", \"announcement_title\", \"announcement_content\", \"announcement_status\", \"announcement_created_date\" , \"announcement_last_updated_date\", \"announcement_last_updated_by\"]\r\n" + 
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	}
        return payload;
    }
    
    public String getAnnounceAll(String now) {
    	
    	String payload = "{\r\n" +  
    				" \"sort\": [\r\n" + 
    				"    {\r\n" + 
    				"      \"announcement_created_date\": {\r\n" + 
    				"        \"order\": \"desc\"\r\n" + 
    				"      }\r\n" + 
    				"    }\r\n" + 
    				"  ],\r\n" +
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"        {\r\n" + 
    		 		"          \"range\": {\r\n" + 
    		 		"            \"announcement_due_date\": {\r\n" + 
    		 		"              \"gte\": "+now+"\r\n" + 
    		 		"            }\r\n" + 
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	
        return payload;
    }
    
    
    public String getHistories(String from, String size, String search) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.convertSearchToParams(search);
    	if(search.equalsIgnoreCase("*")) {
    		 payload = "{\r\n" + 
            		"  \"from\": "+from+", \r\n" + 
            		"  \"size\": "+size+",\r\n" + 
            		"  \"query\": {\r\n" + 
            		"    \"match_all\": {}\r\n" + 
            		"  }\r\n" + 
            		"}";
    	}else {
    		 payload = "{\r\n" + 
    		 		"  \"from\": "+from+", \r\n" + 
    		 		"  \"size\": "+size+",\r\n" + 
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"          {\r\n" + 
    		 		"          \"query_string\": {\r\n" + 
    		 		"            \"analyze_wildcard\": true,\r\n" + 
    		 		"            \"query\": \""+searchQuery+"\",\r\n" + 
    		 		"            \"fields\": [\"history_type\", \"history_user_eid\", \"history_doc_id\", \"history_action_type\", \"history_action_date\"]\r\n" + 
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	}
        return payload;
    }
    
    public String getFeedBacks(String from, String size, String search) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.convertSearchToParams(search);
    	if(search.equalsIgnoreCase("*")) {
    		 payload = "{\r\n" + 
            		"  \"from\": "+from+", \r\n" + 
            		"  \"size\": "+size+",\r\n" + 
            		"  \"query\": {\r\n" + 
            		"    \"match_all\": {}\r\n" + 
            		"  }\r\n" + 
            		"}";
    	}else {
    		 payload = "{\r\n" + 
    		 		"  \"from\": "+from+", \r\n" + 
    		 		"  \"size\": "+size+",\r\n" + 
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"          {\r\n" + 
    		 		"          \"query_string\": {\r\n" + 
    		 		"            \"analyze_wildcard\": true,\r\n" + 
    		 		"            \"query\": \""+searchQuery+"\",\r\n" + 
    		 		"            \"fields\": [\"user_eid\", \"user_airid\", \"rating_module\", \"rating_score\", \"rating_comment\", \"status\" , \"created_date\", \"last_update_date\", \"last_update_by\"]\r\n" + 
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	}
        return payload;
    }

    public String getUserId(String eid) {

        String payload = "{\r\n"
                + "  \"query\":{\r\n"
                + "    \"match\":{\r\n"
                + "      \"user_eid\" : \"" + eid + "\"\r\n"
                + "    }\r\n"
                + "  }\r\n"
                + "\r\n"
                + "}";

        return payload;
    }

    public String insertUserDetails(JSONObject o) {
    	JSONObject insertObj = (JSONObject) o.get("insertObj");
    	
    	String[] oldlist_airid = insertObj.get("airid").toString().split(",");
    	String[] oldlist_id = insertObj.get("id").toString().split(",");
    	
    	List<String> newlist_airid = new ArrayList<>();
    	List<String> newlist_id = new ArrayList<>();
    		
    	for(String airid : oldlist_airid) {
    		newlist_airid.add("\"" + airid + "\"");
    	}
    	
    	for(String id : oldlist_id) {
    		newlist_id.add("\"" + id + "\"");
    	}
    	
    	
    	String payload = "{	\r\n" + 
    			"    \"user_eid\" : \""+insertObj.get("eid2")+"\",\r\n" + 
    			"	\"user_access\" : \""+insertObj.get("access")+"\",\r\n" + 
    			"	\"user_team\" : \""+insertObj.get("team")+"\",\r\n" + 
    			"	\"user_id\" : "+newlist_id+",\r\n" + 
    			"	\"user_airid\" : "+newlist_airid+",\r\n" + 
    			"	\"access_expiration_date\" : \""+insertObj.get("expiry")+"\",\r\n" + 
    			"	\"access_status\" : \""+insertObj.get("status")+"\",\r\n" + 
    			"	\"created_date\" : \""+insertObj.get("createdDate")+"\",\r\n" + 
    			"	\"last_update_date\" : \""+insertObj.get("updatedDate")+"\",\r\n" + 
    			"	\"last_update_by\" : \""+insertObj.get("updatedBy")+"\"\r\n" + 
    			"}";
    	
    	return payload;
    }
    
    
    public String insertHistory(JSONObject o) {
    	HistoryModel hm =  (HistoryModel) o.get("insertObj");
    	System.out.println("THIS IS INSERT OBJ CONTENT with HISTORY MODEL: "+ hm.getActionType());
    	   	
    	String payload = "{\n" + 
    			"	\"history_type\": \""+hm.getType()+"\",\n" + 
    			"  \"history_user_eid\": \""+hm.getUserEid()+"\",\n" + 
    			"	\"history_doc_id\": \""+hm.getDocId()+"\",\n" + 
    			"	\"history_action_type\": \""+hm.getActionType()+"\",\n" + 
    			"	\"history_action_date\": \""+hm.getActionDate()+"\",\n" + 
    			"	\"history_due_date\": \""+hm.getDueDate()+"\"\n" + 
    			"}";
    	
    	return payload;
    }
    
    public String postHistory(JSONObject o) {
    	String payload = null;
    	List<HistoryModel> history_model =  (List<HistoryModel>) o.get("insertObj");
    	System.out.println("THIS IS INSERT OBJ CONTENT with HISTORY MODEL: "+ history_model);
    	   	
    	for(HistoryModel history: history_model) {
    		 payload = "{\n" + 
        			"	\"history_type\": \""+history.getType()+"\",\n" + 
        			"  \"history_user_eid\": \""+history.getUserEid()+"\",\n" + 
        			"	\"history_doc_id\": \""+history.getDocId()+"\",\n" + 
        			"	\"history_action_type\": \""+history.getActionType()+"\",\n" + 
        			"	\"history_action_date\": \""+history.getActionDate()+"\"\n" + 
        			"}";
    	}
    	return payload;
    }

    public String updateUserDetails(JSONObject o) {
   	JSONObject updateObj = (JSONObject) o.get("updateObj");
    	
    	String[] oldlist_airid = updateObj.get("airid").toString().split(",");
    	String[] oldlist_id = updateObj.get("id").toString().split(",");
    	
    	List<String> newlist_airid = new ArrayList<>();
    	List<String> newlist_id = new ArrayList<>();
    	
    	for(String airid : oldlist_airid) {
    		newlist_airid.add("\"" + airid + "\"");
    	}
    	
    	for(String id : oldlist_id) {
    		newlist_id.add("\"" + id + "\"");
    	}
    	System.out.println("THIS IS NEWLIST ID: "+ newlist_id);
    	System.out.println("THIS IS EID2:"+ updateObj.get("eid2"));
    	String payload = "{	\r\n" + 
    			"  \"doc\":{\r\n" +
    			"    \"user_eid\" : \""+updateObj.get("eid2")+"\",\r\n" + 
    			"	\"user_access\" : \""+updateObj.get("access")+"\",\r\n" + 
    			"	\"user_team\" : \""+updateObj.get("team")+"\",\r\n" + 
    			"	\"user_id\" : "+newlist_id+",\r\n" + 
    			"	\"user_airid\" : "+newlist_airid+",\r\n" + 
    			"	\"access_expiration_date\" : \""+updateObj.get("expiry")+"\",\r\n" + 
    			"	\"access_status\" : \""+updateObj.get("status")+"\",\r\n" + 
    			"	\"created_date\" : \""+updateObj.get("createdDate")+"\",\r\n" + 
    			"	\"last_update_date\" : \""+updateObj.get("updatedDate")+"\",\r\n" + 
    			"	\"last_update_by\" : \""+updateObj.get("updatedBy")+"\"\r\n" + 
    			"  }\r\n" +
    			"}";
    	return payload;
    }
    // *****************
    public String getCustomReportsOverviewReferrersMetrics(Object airid, String gte, String lte, String id) {

        String payload = "{\r\n"
                + "  \"size\": 0,\r\n"
                + "  \"query\": {\r\n"
                + "    \"bool\": {\r\n"
                + "      \"must\": [\r\n"
                + "        {\r\n"
                + "          \"terms\": {\r\n"
                + "            \"AIRID\": " + airid + "\r\n"
                + "          }\r\n"
                + "        },\r\n"
                + "        {\r\n"
                + "          \"term\": {\r\n"
                + "            \"ID\": {\r\n"
                + "              \"value\": " + id + "\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        },\r\n"
                + "        {\r\n"
                + "          \"range\": {\r\n"
                + "            \"@timestamp\": {\r\n"
                + "              \"gte\": " + gte + ",\r\n"
                + "              \"lte\": " + lte + "\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        }\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  }, \r\n"
                + "  \"aggs\": {\r\n"
                + "    \"1\": {\r\n"
                + "      \"terms\": {\r\n"
                + "        \"field\": \"RefererName\",\r\n"
                + "        \"size\": 500\r\n"
                + "      },\r\n"
                + "      \"aggs\": {\r\n"
                + "        \"2\": {\r\n"
                + "          \"terms\": {\r\n"
                + "            \"field\": \"User\"\r\n"
                + "          },\r\n"
                + "          \"aggs\": {\r\n"
                + "            \"3\": {\r\n"
                + "              \"terms\": {\r\n"
                + "                \"field\": \"Visits\"\r\n"
                + "              },\r\n"
                + "              \"aggs\": {\r\n"
                + "                \"4\": {\r\n"
                + "                  \"avg\": {\r\n"
                + "                    \"field\": \"TotalElapseTimeOfVisit\"\r\n"
                + "                  }\r\n"
                + "                },\r\n"
                + "                \"5\":{\r\n"
                + "                  \"cardinality\": {\r\n"
                + "                    \"field\": \"PageTitle\"\r\n"
                + "                  }\r\n"
                + "                }\r\n"
                + "              }\r\n"
                + "            },\r\n"
                + "            \"6\":{\r\n"
                + "              \"cardinality\": {\r\n"
                + "                \"field\": \"Visits.hash\"\r\n"
                + "              }\r\n"
                + "            },\r\n"
                + "            \"7\":{\r\n"
                + "              \"cardinality\": {\r\n"
                + "                \"field\": \"Hits.hash\"\r\n"
                + "              }\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        }\r\n"
                + "      } \r\n"
                + "    }\r\n"
                + "  }\r\n"
                + "}";

        return payload;
    }

    public String getCustomReportsOverviewDownloadMetrics(Object airid, String gte, String lte, String id) {

        String payload = "{\r\n"
                + "  \"size\": 0,\r\n"
                + "  \"query\": {\r\n"
                + "    \"bool\": {\r\n"
                + "      \"must\": [\r\n"
                + "        {\r\n"
                + "          \"terms\": {\r\n"
                + "            \"AIRID\": " + airid + "\r\n"
                + "          }\r\n"
                + "        },\r\n"
                + "        {\r\n"
                + "          \"term\": {\r\n"
                + "            \"ID\": {\r\n"
                + "              \"value\": " + id + "\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        }\r\n"
                + "      ],\r\n"
                + "      \"filter\": [\r\n"
                + "        {\r\n"
                + "          \"term\" : {\r\n"
                + "            \"ActionURLType\": \"3\"\r\n"
                + "          }\r\n"
                + "        },\r\n"
                + "        {\r\n"
                + "          \"range\": {\r\n"
                + "            \"@timestamp\": {\r\n"
                + "              \"gte\": " + gte + ",\r\n"
                + "              \"lte\": " + lte + "\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        }\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  },\r\n"
                + "  \"aggs\": {\r\n"
                + "    \"1\": {\r\n"
                + "      \"terms\": {\r\n"
                + "        \"field\": \"PageURL.keyword\",\r\n"
                + "        \"size\": 500\r\n"
                + "      },\r\n"
                + "      \"aggs\": {\r\n"
                + "        \"2\": {\r\n"
                + "          \"terms\": {\r\n"
                + "            \"field\": \"User\"\r\n"
                + "          },\r\n"
                + "          \"aggs\": {\r\n"
                + "            \"3\": {\r\n"
                + "              \"terms\": {\r\n"
                + "                \"field\": \"Visits\"\r\n"
                + "              },\r\n"
                + "              \"aggs\": {\r\n"
                + "                \"4\": {\r\n"
                + "                  \"avg\": {\r\n"
                + "                    \"field\": \"TotalElapseTimeOfVisit\"\r\n"
                + "                  }\r\n"
                + "                },\r\n"
                + "                \"5\":{\r\n"
                + "                  \"cardinality\": {\r\n"
                + "                    \"field\": \"PageTitle\"\r\n"
                + "                  }\r\n"
                + "                }\r\n"
                + "              }\r\n"
                + "            },\r\n"
                + "            \"6\":{\r\n"
                + "              \"cardinality\": {\r\n"
                + "                \"field\": \"Visits.hash\"\r\n"
                + "              }\r\n"
                + "            },\r\n"
                + "            \"7\":{\r\n"
                + "              \"cardinality\": {\r\n"
                + "                \"field\": \"Hits.hash\"\r\n"
                + "              }\r\n"
                + "            }\r\n"
                + "          }\r\n"
                + "        }\r\n"
                + "      } \r\n"
                + "    }\r\n"
                + "  }\r\n"
                + "}";

        return payload;
    }

    public String getVisitorLogs(JSONObject o) {
        JSONObject visitorobj = (JSONObject) o.get("visitorObject");
        
        String payload = "{\r\n" + 
        		"  \"size\": 0,\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"      \"must\": [\r\n" + 
        		"        {\r\n" + 
        		"          \"query_string\": {\r\n" + 
        		"            \"default_field\": \"AIRID\",\r\n" + 
        		"            \"query\": \"AIRID:" + visitorobj.get("airid") + " AND ID:" + visitorobj.get("id") + "\"\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"range\": {\r\n" + 
        		"            \"@timestamp\": {\r\n" + 
        		"              \"gte\": " + visitorobj.get("gte") + ",\r\n" + 
        		"              \"lte\": " + visitorobj.get("lte") + ",\r\n" + 
        		"              \"format\": \"epoch_millis\"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      ]\r\n" + 
        		"    }\r\n" + 
        		"  },\r\n" + 
        		"  \"aggs\": {\r\n" + 
        		"    \"1\": {\r\n" + 
        		"      \"terms\": {\r\n" + 
        		"        \"field\": \"User\",\r\n" + 
        		"        \"size\": 1,\r\n" + 
        		"		\"order\": {\r\n" +
        	    "     		\"_term\": \"asc\"\r\n" +
        	    "    		}\r\n" +
        		"      },\r\n" + 
        		"      \"aggs\": {\r\n" + 
        		"        \"2\": {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"field\": \"Country.keyword\"\r\n" + 
        		"          },\r\n" + 
        		"          \"aggs\": {\r\n" + 
        		"            \"3\": {\r\n" + 
        		"              \"terms\": {\r\n" + 
        		"                \"field\": \"Browser.keyword\"\r\n" + 
        		"              },\r\n" + 
        		"              \"aggs\": {\r\n" + 
        		"                \"4\": {\r\n" + 
        		"                  \"terms\": {\r\n" + 
        		"                    \"field\": \"OperatingSystem.keyword\"\r\n" + 
        		"                  },\r\n" + 
        		"                  \"aggs\": {\r\n" + 
        		"                    \"5\": {\r\n" + 
        		"                      \"terms\": {\r\n" + 
        		"                        \"field\": \"CleanPageURL.keyword\"\r\n" + 
        		"                      },\r\n" + 
        		"                      \"aggs\": {\r\n" + 
        		"                        \"6\": {\r\n" + 
        		"                          \"terms\": {\r\n" + 
        		"                            \"field\": \"QuickTimePlugin\"\r\n" + 
        		"                          },\r\n" + 
        		"                          \"aggs\": {\r\n" + 
        		"                            \"7\": {\r\n" + 
        		"                              \"terms\": {\r\n" + 
        		"                                \"field\": \"PDFPlugin\"\r\n" + 
        		"                              },\r\n" + 
        		"                              \"aggs\": {\r\n" + 
        		"                                \"8\": {\r\n" + 
        		"                                  \"terms\": {\r\n" + 
        		"                                    \"field\": \"RealPlayerPlugin\"\r\n" + 
        		"                                  },\r\n" + 
        		"                                  \"aggs\": {\r\n" + 
        		"                                    \"9\": {\r\n" + 
        		"                                      \"terms\": {\r\n" + 
        		"                                        \"field\": \"SilverlightPlugin\"\r\n" + 
        		"                                      },\r\n" + 
        		"                                      \"aggs\": {\r\n" + 
        		"                                        \"10\": {\r\n" + 
        		"                                          \"terms\": {\r\n" + 
        		"                                            \"field\": \"WindowsMediaPlugin\"\r\n" + 
        		"                                          },\r\n" + 
        		"                                          \"aggs\": {\r\n" + 
        		"                                            \"11\": {\r\n" + 
        		"                                              \"terms\": {\r\n" + 
        		"                                                \"field\": \"JavaPlugin\"\r\n" + 
        		"                                              },\r\n" + 
        		"                                              \"aggs\": {\r\n" + 
        		"                                                \"12\": {\r\n" + 
        		"                                                  \"terms\": {\r\n" + 
        		"                                                    \"field\": \"GearPlugin\"\r\n" + 
        		"                                                  },\r\n" + 
        		"                                                  \"aggs\": {\r\n" + 
        		"                                                    \"13\": {\r\n" + 
        		"                                                      \"terms\": {\r\n" + 
        		"                                                        \"field\": \"DirectorPlugin\"\r\n" + 
        		"                                                      },\r\n" + 
        		"                                                      \"aggs\": {\r\n" + 
        		"                                                        \"14\": {\r\n" + 
        		"                                                          \"terms\": {\r\n" + 
        		"                                                            \"field\": \"FlashPlugin\"\r\n" + 
        		"                                                          },\r\n" + 
        		"                                                          \"aggs\": {\r\n" + 
        		"                                                            \"15\": {\r\n" + 
        		"                                                              \"terms\": {\r\n" + 
        		"                                                                \"field\": \"RefererURL\"\r\n" + 
        		"                                                              },\r\n" + 
        		"                                                              \"aggs\": {\r\n" + 
        		"                                                                \"16\": {\r\n" + 
        		"                                                                  \"terms\": {\r\n" + 
        		"                                                                    \"field\": \"PageURL.keyword\"\r\n" + 
        		"                                                                  },\r\n" + 
        		"                                                                  \"aggs\": {\r\n" + 
        		"                                                                    \"17\": {\r\n" + 
        		"                                                                      \"terms\": {\r\n" + 
        		"                                                                        \"field\": \"@timestamp\"\r\n" + 
        		"                                                                      },\r\n" + 
        		"                                                                      \"aggs\": {\r\n" + 
        		"                                                                        \"18\": {\r\n" + 
        		"                                                                          \"terms\": {\r\n" + 
        		"                                                                            \"field\": \"PageTitle\"\r\n" + 
        		"                                                                          }\r\n" + 
        		"                                                                        }\r\n" + 
        		"                                                                      }\r\n" + 
        		"                                                                    }\r\n" + 
        		"                                                                  }\r\n" + 
        		"                                                                }\r\n" + 
        		"                                                              }\r\n" + 
        		"                                                            }\r\n" + 
        		"                                                          }\r\n" + 
        		"                                                        }\r\n" + 
        		"                                                      }\r\n" + 
        		"                                                    }\r\n" + 
        		"                                                  }\r\n" + 
        		"                                                }\r\n" + 
        		"                                              }\r\n" + 
        		"                                            }\r\n" + 
        		"                                          }\r\n" + 
        		"                                        }\r\n" + 
        		"                                      }\r\n" + 
        		"                                    }\r\n" + 
        		"                                  }\r\n" + 
        		"                                }\r\n" + 
        		"                              }\r\n" + 
        		"                            }\r\n" + 
        		"                          }\r\n" + 
        		"                        }\r\n" + 
        		"                      }\r\n" + 
        		"                    }\r\n" + 
        		"                  }\r\n" + 
        		"                }\r\n" + 
        		"              }\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"  }\r\n" + 
        		"}";

        return payload;
    }
    
    public String getVisitorLogsV2(JSONObject o) {
        JSONObject visitorobj = (JSONObject) o.get("visitorObject");
        
        String payload = "{\r\n" + 
        		"  \"size\": 0,\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"      \"must\": [\r\n" + 
        		"        {\r\n" + 
        		"          \"query_string\": {\r\n" + 
        		"            \"default_field\": \"AIRID\",\r\n" + 
        		"            \"query\": \"AIRID: "+visitorobj.get("airid")+" AND ID: "+visitorobj.get("id")+"\"\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"range\": {\r\n" + 
        		"            \"@timestamp\": {\r\n" + 
        		"              \"gte\": "+visitorobj.get("gte")+",\r\n" + 
        		"              \"lte\": "+visitorobj.get("lte")+",\r\n" + 
        		"              \"format\": \"epoch_millis\"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      ]\r\n" + 
        		"    }\r\n" + 
        		"  },\r\n" + 
        		"  \"aggs\": {\r\n" + 
        		"    \"1\": {  \r\n" + 
        		"      \"terms\": {\r\n" + 
        		"        \"field\": \"Visits\",\r\n" + 
        		"        \"size\": 100,\r\n" + 
        		"        \"order\": {\r\n" + 
        		"          \"_term\": \"desc\"\r\n" + 
        		"        }\r\n" + 
        		"      },\r\n" + 
        		"      \"aggs\": {\r\n" + 
        		"        \"2\": {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"field\": \"LastActionTime\"\r\n" + 
        		"          },\r\n" + 
        		"          \"aggs\": {\r\n" + 
        		"            \"3\": {\r\n" + 
        		"              \"terms\": {\r\n" + 
        		"                \"field\": \"User\"\r\n" + 
        		"              },\r\n" + 
        		"              \"aggs\": {\r\n" + 
        		"                \"4\": {\r\n" + 
        		"                  \"terms\": {\r\n" + 
        		"                    \"field\": \"Address\",\r\n" + 
        		"                    \"size\": 10\r\n" + 
        		"                  },\r\n" + 
        		"                  \"aggs\": {\r\n" + 
        		"                    \"5\": {\r\n" + 
        		"                      \"terms\": {\r\n" + 
        		"                        \"field\": \"Country.keyword\"\r\n" + 
        		"                      },\r\n" + 
        		"                      \"aggs\": {\r\n" + 
        		"                        \"6\": {\r\n" + 
        		"                          \"terms\": {\r\n" + 
        		"                            \"field\": \"Browser.keyword\"\r\n" + 
        		"                          },\r\n" + 
        		"                          \"aggs\": {\r\n" + 
        		"                            \"7\": {\r\n" + 
        		"                              \"terms\": {\r\n" + 
        		"                                \"field\": \"OperatingSystem.keyword\"\r\n" + 
        		"                              },\r\n" + 
        		"                              \"aggs\": {\r\n" + 
        		"                                \"8\": {\r\n" + 
        		"                                  \"terms\": {\r\n" + 
        		"                                    \"field\": \"CleanPageURL.keyword\"\r\n" + 
        		"                                  },\r\n" + 
        		"                                  \"aggs\": {\r\n" + 
        		"                                    \"9\": {\r\n" + 
        		"                                      \"terms\": {\r\n" + 
        		"                                        \"field\": \"RefererURL\"\r\n" + 
        		"                                      }, \r\n" + 
        		"                                      \"aggs\": {\r\n" + 
        		"                                        \"10\": {\r\n" + 
        		"                                          \"terms\": {\r\n" + 
        		"                                            \"field\": \"PageTitle\"\r\n" + 
        		"                                          },\r\n" + 
        		"                                          \"aggs\": {\r\n" + 
        		"                                            \"11\": {\r\n" + 
        		"                                              \"terms\": {\r\n" + 
        		"                                                \"field\": \"QuickTimePlugin\"\r\n" + 
        		"                                              },\r\n" + 
        		"                                              \"aggs\": {\r\n" + 
        		"                                                \"12\": {\r\n" + 
        		"                                                  \"terms\": {\r\n" + 
        		"                                                    \"field\": \"PDFPlugin\"\r\n" + 
        		"                                                  },\r\n" + 
        		"                                                  \"aggs\": {\r\n" + 
        		"                                                    \"13\": {\r\n" + 
        		"                                                      \"terms\": {\r\n" + 
        		"                                                        \"field\": \"RealPlayerPlugin\"\r\n" + 
        		"                                                      },\r\n" + 
        		"                                                      \"aggs\": {\r\n" + 
        		"                                                        \"14\": {\r\n" + 
        		"                                                          \"terms\": {\r\n" + 
        		"                                                            \"field\": \"SilverlightPlugin\"\r\n" + 
        		"                                                          },\r\n" + 
        		"                                                          \"aggs\": {\r\n" + 
        		"                                                            \"15\": {\r\n" + 
        		"                                                              \"terms\": {\r\n" + 
        		"                                                                \"field\": \"WindowsMediaPlugin\"\r\n" + 
        		"                                                              },\r\n" + 
        		"                                                              \"aggs\": {\r\n" + 
        		"                                                                \"16\": {\r\n" + 
        		"                                                                  \"terms\": {\r\n" + 
        		"                                                                    \"field\": \"JavaPlugin\"\r\n" + 
        		"                                                                  },\r\n" + 
        		"                                                                  \"aggs\": {\r\n" + 
        		"                                                                    \"17\": {\r\n" + 
        		"                                                                      \"terms\": {\r\n" + 
        		"                                                                        \"field\": \"GearPlugin\"\r\n" + 
        		"                                                                      },\r\n" + 
        		"                                                                      \"aggs\": {\r\n" + 
        		"                                                                        \"18\": {\r\n" + 
        		"                                                                          \"terms\": {\r\n" + 
        		"                                                                            \"field\": \"DirectorPlugin\"\r\n" + 
        		"                                                                          },\r\n" + 
        		"                                                                          \"aggs\": {\r\n" + 
        		"                                                                            \"19\": {\r\n" + 
        		"                                                                              \"terms\": {\r\n" + 
        		"                                                                                \"field\": \"FlashPlugin\"\r\n" + 
        		"                                                                              }\r\n" + 
        		"                                                                            }\r\n" + 
        		"                                                                          }\r\n" + 
        		"                                                                        }\r\n" + 
        		"                                                                      }\r\n" + 
        		"                                                                    }\r\n" + 
        		"                                                                  }\r\n" + 
        		"                                                                }\r\n" + 
        		"                                                              }\r\n" + 
        		"                                                            }\r\n" + 
        		"                                                          }\r\n" + 
        		"                                                        }\r\n" + 
        		"                                                      }\r\n" + 
        		"                                                    }\r\n" + 
        		"                                                  }\r\n" + 
        		"                                                }\r\n" + 
        		"                                              }\r\n" + 
        		"                                            }\r\n" + 
        		"                                          }\r\n" + 
        		"                                        }\r\n" + 
        		"                                      }\r\n" + 
        		"                                    }\r\n" + 
        		"                                  }\r\n" + 
        		"                                }\r\n" + 
        		"                              }\r\n" + 
        		"                            }\r\n" + 
        		"                          }\r\n" + 
        		"                        }\r\n" + 
        		"                      }\r\n" + 
        		"                    }\r\n" + 
        		"                  }\r\n" + 
        		"                }\r\n" + 
        		"              }\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"  }\r\n" + 
        		"}";

        return payload;
    }
    
    public String getCustomReportsEventMetrics(Object airid, String gte, String lte, String id) {

        String payload = "{\r\n" + 
        		"  \"size\": 0,\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"      \"must\": [\r\n" + 
        		"        {\r\n" + 
        		"          \"range\": {\r\n" + 
        		"            \"@timestamp\": {\r\n" + 
        		"              \"gte\": " + gte + " ,\r\n" + 
        		"              \"lte\": " + lte + " ,\r\n" + 
        		"              \"format\": \"epoch_millis\"\r\n" + 
        		"                		  }\r\n" + 
        		"                }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"query_string\": {\r\n" + 
        		"          \"analyze_wildcard\": true,\r\n" + 
        		"          \"query\": \"AIRID: " + airid + " AND ID: " + id + "\"\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      ]\r\n" + 
        		"    }\r\n" + 
        		"  },\r\n" + 
        		"  \"aggs\": {\r\n" + 
        		"    \"1\": {\r\n" + 
        		"      \"terms\": {\r\n" + 
        		"        \"field\": \"EventCategory\",\r\n" + 
        		"        \"order\": {\r\n" + 
        		"          \"_term\": \"asc\"\r\n" + 
        		"        }\r\n" + 
        		"      },\r\n" + 
        		"      \"aggs\": {\r\n" + 
        		"        \"2\": {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"field\": \"EventAction\",\r\n" + 
        		"            \"order\": {\r\n" + 
        		"              \"_term\": \"asc\"\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"aggs\": {\r\n" + 
        		"            \"3\": {\r\n" + 
        		"              \"terms\": {\r\n" + 
        		"                \"field\": \"EventName\",\r\n" + 
        		"                \"order\": {\r\n" + 
        		"                  \"_term\": \"asc\"\r\n" + 
        		"                }\r\n" + 
        		"              }\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"  }\r\n" + 
        		"}";

        return payload;
    }
    
    public String getCustomReportUsageV2(String airid, String id, String gte, String lte, String to, String from, String size, String column, String sort, String search, String filter) {

        String payload = "{\r\n" + 
        		"  \"size\": \"0\",\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"      \"must\": [\r\n" + 
        		"        {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"AIRID\": [" + airid + "]\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"term\": {\r\n" + 
        		"            \"ID\": {\r\n" + 
        		"              \"value\": " + id + "\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"range\": {\r\n" + 
        		"            \"@timestamp\": {\r\n" + 
        		"              \"gte\": " + gte + ",\r\n" + 
        		"              \"lte\": " + lte +"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"query_string\": {\r\n" + 
        		"            \"analyze_wildcard\": true,\r\n" + 
        		"            \"query\": \"*" + search + "*\", \r\n" + 
        		"            \"fields\": [\"User\", \"Visits\", \"Hits\", \"TotalElapseTimeOfVisit\", \"PageTitle\"]\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      ]\r\n" + 
        		"    }\r\n" + 
        		"  }, \r\n" + 
        		"  \"aggs\": {\r\n" + 
        		"    \"1\": {\r\n" + 
        		"      \"terms\": {\r\n" + 
        		"        \"field\": \"" + filter + "\"\r\n" + 
        		"      },\r\n" + 
        		"      \"aggs\": {\r\n" + 
        		"        \"2\": {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"field\": \"Visits\"\r\n" + 
        		"          },\r\n" + 
        		"          \"aggs\": {\r\n" + 
        		"            \"3\": {\r\n" + 
        		"              \"avg\": {\r\n" + 
        		"                \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
        		"              }\r\n" + 
        		"            },\r\n" + 
        		"            \"4\": {\r\n" + 
        		"              \"cardinality\": {\r\n" + 
        		"                \"field\": \"PageTitle\"\r\n" + 
        		"              }\r\n" + 
        		"            },\r\n" + 
        		"            \"5\":{\r\n" + 
        		"             \"cardinality\": {\r\n" + 
        		"               \"field\": \"Visits.hash\"\r\n" + 
        		"             } \r\n" + 
        		"            },\r\n" + 
        		"            \"6\":{\r\n" + 
        		"            \"cardinality\": {\r\n" + 
        		"              \"field\": \"Hits.hash\"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        \"avg_page_duration\":{\r\n" + 
        		"          \"avg_bucket\": {\r\n" + 
        		"            \"buckets_path\": \"2>3\"\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        \"avg_page_visits\":{\r\n" + 
        		"          \"sum_bucket\": {\r\n" + 
        		"            \"buckets_path\": \"2>4\"\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        \"sum_visits\":{\r\n" + 
        		"          \"sum_bucket\": {\r\n" + 
        		"            \"buckets_path\": \"2>5\"\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        \"sum_hits\":{\r\n" + 
        		"          \"sum_bucket\": {\r\n" + 
        		"            \"buckets_path\": \"2>6\"\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"  }\r\n" + 
        		"}";
        
        return payload;
    }
    
    public String getCustomUsageReportDetails(String airid, String id, String gte, String lte, String to, String from, String size, String column, String sort, String search, String filterFor, String filterMetrics, String filter) {

        String payload = "{\r\n" + 
        		"  \"size\": \"0\",\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"      \"must\": [\r\n" + 
        		"        {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"AIRID\": [\""+airid+"\"]\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"term\": {\r\n" + 
        		"            \"ID\": {\r\n" + 
        		"              \"value\": \""+id+"\"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"range\": {\r\n" + 
        		"            \"@timestamp\": {\r\n" + 
        		"              \"gte\": "+gte+",\r\n" + 
        		"              \"lte\": "+lte+"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"query_string\": {\r\n" + 
        		"            \"analyze_wildcard\": true,\r\n" + 
        		"            \"query\": \"*"+search+"*\", \r\n" + 
        		"            \"fields\": [\"User\", \"Visits\", \"Hits\", \"TotalElapseTimeOfVisit\", \"PageTitle\"]\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      ],\r\n" + 
        		"    \"filter\": {\r\n" + 
        		"      \"term\": {\r\n" + 
        		"        \""+filter+"\": \""+filterFor+"\"\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"  }\r\n" + 
        		"  },\r\n" + 
        		"  \"aggs\" : {\r\n" + 
        		"      \"1\" : {\r\n" + 
        		"        \"terms\": {\r\n" + 
        		"          \"field\": \""+filterMetrics+"\"\r\n" +
        		"        },\r\n" + 
        		"        \"aggs\": {\r\n" + 
        		"          \"2\":{\r\n" + 
        		"            \"terms\": {\r\n" + 
        		"              \"field\": \"Visits\"\r\n" + 
        		"            },\r\n" + 
        		"            \"aggs\": {\r\n" + 
        		"              \"3\": {\r\n" + 
        		"                \"avg\": {\r\n" + 
        		"                  \"field\": \"TotalElapseTimeOfVisit\"   \r\n" + 
        		"                }\r\n" + 
        		"              },\r\n" + 
        		"              \"4\" : {\r\n" + 
        		"                \"cardinality\": {\r\n" + 
        		"                  \"field\": \"PageTitle\"\r\n" + 
        		"                }\r\n" + 
        		"              },\r\n" + 
        		"              \"5\" : {\r\n" + 
        		"                \"cardinality\": {\r\n" + 
        		"                  \"field\": \"Visits.hash\"\r\n" + 
        		"                }\r\n" + 
        		"              },\r\n" + 
        		"              \"6\" : {\r\n" + 
        		"                \"cardinality\": {\r\n" + 
        		"                  \"field\": \"Hits.hash\"\r\n" + 
        		"                }\r\n" + 
        		"              }\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"avg_page_duration\":{\r\n" + 
        		"            \"avg_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>3\"\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"avg_page_visit\" : {\r\n" + 
        		"            \"avg_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>4\"\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"sum_visits\":{\r\n" + 
        		"            \"sum_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>5\"\r\n" + 
        		"            }\r\n" + 
        		"          },\r\n" + 
        		"          \"sum_hits\": {\r\n" + 
        		"            \"sum_bucket\": {\r\n" + 
        		"              \"buckets_path\": \"2>6\"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"}";
        
        return payload;
    }
    
    
    public String getCustomReportsTotal(String airid, String gte, String lte, String id, String filter, String filterMetrics, String filterFor, String reportType, String search) {
    	String payload = null;
    	String reportTypeConvert = null;
    	String searchQuery = kusinaStringUtils.searchQuery(reportType, search);
    	if(kusinaValidationUtils.isValidUsageType(reportType)) {
		    		if(filterMetrics.isEmpty() || filterMetrics.equalsIgnoreCase("")) {
			    		if(filterFor.isEmpty() || filterFor.equalsIgnoreCase("*")) {
			    			reportTypeConvert = kusinaValidationUtils.modifyPagesFilter(filter);
			    			if(search.equalsIgnoreCase("*")) {
			    				payload="{\r\n" + 
				        				" \"size\":0,\r\n"  +
				            			"  \"query\": {\r\n" + 
				            			"    \"bool\": {\r\n" + 
				            			"      \"must\": [\r\n" + 
				            			"        {\r\n" + 
				            			"          \"terms\": {\r\n" + 
				            			"            \"AIRID\": ["+airid+"]\r\n" + 
				            			"          }\r\n" + 
				            			"        },\r\n" + 
				            			"        {\r\n" + 
				            			"          \"term\": {\r\n" + 
				            			"            \"ID\": {\r\n" + 
				            			"              \"value\": "+id+"\r\n" + 
				            			"            }\r\n" + 
				            			"          }\r\n" + 
				            			"        },\r\n" + 
				            			"        {\r\n" + 
				            			"          \"range\": {\r\n" + 
				            			"            \"@timestamp\": {\r\n" + 
				            			"              \"gte\": "+gte+",\r\n" + 
				            			"              \"lte\": "+lte+"\r\n" + 
				            			"            }\r\n" + 
				            			"          }\r\n" + 
				            			"        }\r\n" + 
				            			"      ]\r\n" + 
				            			"    }\r\n" + 
				            			"  },\r\n" + 
				            			"  \"aggs\": {\r\n" + 
				            			"    \"totalcount\": {\r\n" + 
				            			"      \"cardinality\": {\r\n" + 
				            			"        \"field\": \""+reportTypeConvert+"\",\r\n" +
				            			"        \"precision_threshold\": 4000 \r\n" +
				            			"      }\r\n" + 
				            			"    }\r\n" + 
				            			"  }\r\n" + 
				            			"}";
			    			}else {
			    				payload="{\r\n" + 
				        				" \"size\":0,\r\n"  +
				            			"  \"query\": {\r\n" + 
				            			"    \"bool\": {\r\n" + 
				            			"      \"must\": [\r\n" + 
				            			"        {\r\n" + 
				            			"          \"terms\": {\r\n" + 
				            			"            \"AIRID\": ["+airid+"]\r\n" + 
				            			"          }\r\n" + 
				            			"        },\r\n" + 
				            			"        {\r\n" + 
				            			"          \"term\": {\r\n" + 
				            			"            \"ID\": {\r\n" + 
				            			"              \"value\": "+id+"\r\n" + 
				            			"            }\r\n" + 
				            			"          }\r\n" + 
				            			"        },\r\n" + 
				            			"        {\r\n" + 
				            			"          \"range\": {\r\n" + 
				            			"            \"@timestamp\": {\r\n" + 
				            			"              \"gte\": "+gte+",\r\n" + 
				            			"              \"lte\": "+lte+"\r\n" + 
				            			"            }\r\n" + 
				            			"          }\r\n" + 
				            			"        },\r\n" +
				            			"		 {\r\n" + 
				            			"          \"query_string\": {\r\n" + 
				            			"            \"analyze_wildcard\": true,\r\n" + 
				            			"            \"query\": \""+searchQuery+"\",\r\n" + 
				            			"            \"fields\": [\""+reportTypeConvert+"\"]\r\n" + 
				            			"          }\r\n" + 
				            			"        }\r\n " +
				            			"      ]\r\n" + 
				            			"    }\r\n" + 
				            			"  },\r\n" + 
				            			"  \"aggs\": {\r\n" + 
				            			"    \"totalcount\": {\r\n" + 
				            			"      \"cardinality\": {\r\n" + 
				            			"        \"field\": \""+reportTypeConvert+"\",\r\n" +
				            			"        \"precision_threshold\": 4000 \r\n" +
				            			"      }\r\n" + 
				            			"    }\r\n" + 
				            			"  }\r\n" + 
				            			"}";
			    			}
			    		}
		    	}else {
		    		reportTypeConvert = kusinaValidationUtils.modifyPagesFilter(filterMetrics);
		    		String fieldNames = kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics("usageChild"));
		    		if(search.equalsIgnoreCase("*")) {
		    			payload="{\r\n" + 
			    				" \"size\":0,\r\n"  +
			    				"  \"query\": {\r\n" + 
			    				"    \"bool\": {\r\n" + 
			    				"      \"must\": [\r\n" + 
			    				"        {\r\n" + 
			    				"          \"terms\": {\r\n" + 
			    				"            \"AIRID\": ["+airid+"]\r\n" + 
			    				"          }\r\n" + 
			    				"        },\r\n" + 
			    				"        {\r\n" + 
			    				"          \"term\": {\r\n" + 
			    				"            \"ID\": {\r\n" + 
			    				"              \"value\": "+id+"\r\n" + 
			    				"            }\r\n" + 
			    				"          }\r\n" + 
			    				"        },\r\n" + 
			    				"        {\r\n" + 
			    				"          \"range\": {\r\n" + 
			    				"            \"@timestamp\": {\r\n" + 
			    				"              \"gte\": "+gte+",\r\n" + 
			    				"              \"lte\": "+lte+"\r\n" + 
			    				"            }\r\n" + 
			    				"          }\r\n" + 
			    				"        }\r\n" + 
			    				"      ],\r\n" + 
			    				"      \"filter\": {\r\n" + 
			    				"        \"terms\": {\r\n" + 
			    				"          \""+kusinaValidationUtils.modifyPagesFilter(filter)+"\": [\r\n" + 
			    				"            \""+kusinaStringUtils.convertFilterToElasticQueryNames(filterFor)+"\"\r\n" + 
			    				"          ]\r\n" + 
			    				"        }\r\n" + 
			    				"      }\r\n" + 
			    				"    }\r\n" + 
			    				"  },\r\n" + 
			    				"  \"aggs\": {\r\n" + 
			    				"    \"totalcount\": {\r\n" + 
			    				"      \"cardinality\": {\r\n" + 
			    				"        \"field\": \""+reportTypeConvert+"\",\r\n" + 
			    				"		 \"precision_threshold\": 4000 \r\n" +
			    				"      }\r\n" + 
			    				"    }\r\n" + 
			    				"  }\r\n" + 
			    				"}";
		    		}else {
		    			payload="{\r\n" + 
			    				" \"size\":0,\r\n"  +
			    				"  \"query\": {\r\n" + 
			    				"    \"bool\": {\r\n" + 
			    				"      \"must\": [\r\n" + 
			    				"        {\r\n" + 
			    				"          \"terms\": {\r\n" + 
			    				"            \"AIRID\": ["+airid+"]\r\n" + 
			    				"          }\r\n" + 
			    				"        },\r\n" + 
			    				"        {\r\n" + 
			    				"          \"term\": {\r\n" + 
			    				"            \"ID\": {\r\n" + 
			    				"              \"value\": "+id+"\r\n" + 
			    				"            }\r\n" + 
			    				"          }\r\n" + 
			    				"        },\r\n" + 
			    				"        {\r\n" + 
			    				"          \"range\": {\r\n" + 
			    				"            \"@timestamp\": {\r\n" + 
			    				"              \"gte\": "+gte+",\r\n" + 
			    				"              \"lte\": "+lte+"\r\n" + 
			    				"            }\r\n" + 
			    				"          }\r\n" + 
			    				"        },\r\n" +
			    				"		{\r\n" + 
			    				"          \"query_string\": {\r\n" + 
			    				"            \"analyze_wildcard\": true,\r\n" + 
			    				"            \"query\": \""+searchQuery+"\",\r\n" + 
			    				"            \"fields\": "+fieldNames+"\r\n" + 
			    				"          }\r\n" + 
			    				"        }\r\n" +
			    				"      ],\r\n" + 
			    				"      \"filter\": {\r\n" + 
			    				"        \"terms\": {\r\n" + 
			    				"          \""+kusinaValidationUtils.modifyPagesFilter(filter)+"\": [\r\n" + 
			    				"            \""+kusinaStringUtils.convertFilterToElasticQueryNames(filterFor)+"\"\r\n" + 
			    				"          ]\r\n" + 
			    				"        }\r\n" + 
			    				"      }\r\n" + 
			    				"    }\r\n" + 
			    				"  },\r\n" + 
			    				"  \"aggs\": {\r\n" + 
			    				"    \"totalcount\": {\r\n" + 
			    				"      \"cardinality\": {\r\n" + 
			    				"        \"field\": \""+reportTypeConvert+"\",\r\n" + 
			    				"		 \"precision_threshold\": 4000 \r\n" +
			    				"      }\r\n" + 
			    				"    }\r\n" + 
			    				"  }\r\n" + 
			    				"}";
		    		}	
		    	}
    	}else if(kusinaValidationUtils.isValidPagesType(reportType)) {
    		if(filterMetrics.isEmpty() || filterMetrics.equalsIgnoreCase("")) {	
	    		if(filterFor.isEmpty() || filterFor.equalsIgnoreCase("*")) {
	    			if(search.equalsIgnoreCase("*")) {
	    				payload = "{\r\n" + 
		    					" \"size\":0,\r\n"  +
		        				"  \"query\": {\r\n" + 
		        				"    \"bool\": {\r\n" + 
		        				"      \"must\": [\r\n" + 
		        				"        {\r\n" + 
		        				"          \"terms\": {\r\n" + 
		        				"            \"AIRID\": ["+airid+"]\r\n" + 
		        				"          }\r\n" + 
		        				"        },\r\n" + 
		        				"        {\r\n" + 
		        				"          \"term\": {\r\n" + 
		        				"            \"ID\": {\r\n" + 
		        				"              \"value\": "+id+"\r\n" + 
		        				"            }\r\n" + 
		        				"          }\r\n" + 
		        				"        },\r\n" + 
		        				"        {\r\n" + 
		        				"          \"range\": {\r\n" + 
		        				"            \"@timestamp\": {\r\n" + 
		        				"              \"gte\": "+gte+",\r\n" + 
		        				"              \"lte\": "+lte+"\r\n" + 
		        				"            }\r\n" + 
		        				"          }\r\n" + 
		        				"        }\r\n" + 
		        				"      ]\r\n" + 
		        				"    }\r\n" + 	
		        				"  },\r\n" + 
		        				"  \"aggs\": {\r\n" + 
		        				"    \"totalcount\": {\r\n" + 
		        				"      \"cardinality\": {\r\n" + 
		        				"        \"field\": \"PageURL.keyword\",\r\n" + 
		        				"        \"precision_threshold\": 4000\r\n" + 
		        				"      }\r\n" + 
		        				"    }\r\n" + 
		        				"  }\r\n" + 
		        				"}";
	    			}else {
	    				payload = "{\r\n" + 
		    					" \"size\":0,\r\n"  +
		        				"  \"query\": {\r\n" + 
		        				"    \"bool\": {\r\n" + 
		        				"      \"must\": [\r\n" + 
		        				"        {\r\n" + 
		        				"          \"terms\": {\r\n" + 
		        				"            \"AIRID\": ["+airid+"]\r\n" + 
		        				"          }\r\n" + 
		        				"        },\r\n" + 
		        				"        {\r\n" + 
		        				"          \"term\": {\r\n" + 
		        				"            \"ID\": {\r\n" + 
		        				"              \"value\": "+id+"\r\n" + 
		        				"            }\r\n" + 
		        				"          }\r\n" + 
		        				"        },\r\n" + 
		        				"        {\r\n" + 
		        				"          \"range\": {\r\n" + 
		        				"            \"@timestamp\": {\r\n" + 
		        				"              \"gte\": "+gte+",\r\n" + 
		        				"              \"lte\": "+lte+"\r\n" + 
		        				"            }\r\n" + 
		        				"          }\r\n" + 
		        				"        },\r\n" +
		        				"		{\r\n" + 
		        				"          \"query_string\": {\r\n" + 
		        				"            \"analyze_wildcard\": true,\r\n" + 
		        				"            \"query\": \""+searchQuery+"\",\r\n" + 
		        				"            \"fields\": [\"PageURL.keyword\"]\r\n" + 
		        				"          }\r\n" + 
		        				"        }\r\n" +
		        				"      ]\r\n" + 
		        				"    }\r\n" + 	
		        				"  },\r\n" + 
		        				"  \"aggs\": {\r\n" + 
		        				"    \"totalcount\": {\r\n" + 
		        				"      \"cardinality\": {\r\n" + 
		        				"        \"field\": \"PageURL.keyword\",\r\n" + 
		        				"        \"precision_threshold\": 4000\r\n" + 
		        				"      }\r\n" + 
		        				"    }\r\n" + 
		        				"  }\r\n" + 
		        				"}";
	    			}
	    		}
    		}else {
    			if(search.equalsIgnoreCase("*")) {
    				payload = "{\r\n" + 
        					" \"size\":0,\r\n"  +
        					"  \"query\": {\r\n" + 
        					"    \"bool\": {\r\n" + 
        					"      \"must\": [\r\n" + 
        					"        {\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"AIRID\": ["+airid+"]\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ID\": {\r\n" + 
        					"              \"value\": "+id+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"range\": {\r\n" + 
        					"            \"@timestamp\": {\r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" + 
        					"      ],\r\n" + 
        					"      \"filter\": {\r\n" + 
        					"        \"terms\": {\r\n" + 
        					"          \"PageURL.keyword\": [\r\n" + 
        					"            \""+filterFor+"\"\r\n" + 
        					"          ]\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  },\r\n" + 
        					"  \"aggs\": {\r\n" + 
        					"    \"totalcount\": {\r\n" + 
        					"      \"cardinality\": {\r\n" + 
        					"        \"field\": \""+kusinaValidationUtils.modifyPagesFilter(filter)+"\",\r\n" + 
        					"        \"precision_threshold\": 4000\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  }\r\n" + 
        					"}";
    			}else {
    				payload = "{\r\n" + 
        					" \"size\":0,\r\n"  +
        					"  \"query\": {\r\n" + 
        					"    \"bool\": {\r\n" + 
        					"      \"must\": [\r\n" + 
        					"        {\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"AIRID\": ["+airid+"]\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ID\": {\r\n" + 
        					"              \"value\": "+id+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"range\": {\r\n" + 
        					"            \"@timestamp\": {\r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" +
        					"        {\r\n" + 
        					"          \"query_string\": {\r\n" + 
        					"            \"analyze_wildcard\": true,\r\n" + 
        					"            \"query\": \""+searchQuery+"\",\r\n" + 
        					"            \"fields\": [\""+kusinaValidationUtils.modifyPagesFilter(filter)+"\"]\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" +
        					"      ],\r\n" + 
        					"      \"filter\": {\r\n" + 
        					"        \"terms\": {\r\n" + 
        					"          \"PageURL.keyword\": [\r\n" + 
        					"            \""+filterFor+"\"\r\n" + 
        					"          ]\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  },\r\n" + 
        					"  \"aggs\": {\r\n" + 
        					"    \"totalcount\": {\r\n" + 
        					"      \"cardinality\": {\r\n" + 
        					"        \"field\": \""+kusinaValidationUtils.modifyPagesFilter(filter)+"\",\r\n" + 
        					"        \"precision_threshold\": 4000\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  }\r\n" + 
        					"}";
    			}
    			
    		}
    	//AibspBvi Metrics	
    	}else if(kusinaValidationUtils.isValidReportType(reportType)) {
    		if(filterMetrics.isEmpty() || filterMetrics.equalsIgnoreCase("")) {
	    		if(filterFor.isEmpty() || filterFor.equalsIgnoreCase("*")) {
	    			if(!reportType.equalsIgnoreCase("pageCustomInfo")) {
	    				if(search.equalsIgnoreCase("*")) {
	    					payload = "{ \r\n" + 
			        				" \"size\":0,\r\n"  +
			        				"  \"query\": { \r\n" + 
			        				"    \"bool\": { \r\n" + 
			        				"      \"must\": [ \r\n" + 
			        				"        { \r\n" + 
			        				"          \"terms\": { \r\n" + 
			        				"            \"AIRID\": ["+airid+"] \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"term\": { \r\n" + 
			        				"            \"ID\": { \r\n" + 
			        				"              \"value\": "+id+"\r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"range\": { \r\n" + 
			        				"            \"@timestamp\": { \r\n" + 
			        				"              \"gte\": "+gte+",\r\n" + 
			        				"              \"lte\": "+lte+" \r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        } \r\n" + 
			        				"      ] \r\n" + 
			        				"    } \r\n" + 
			        				"  },\r\n" + 
			        				"  \"aggs\": {\r\n" + 
			        				"    \"totalcount\": {\r\n" + 
			        				"      \"value_count\": {\r\n" + 
			        				"        \"field\": \"PageCustomVariable1Value.keyword\"\r\n" + 
			        				"      }\r\n" + 
			        				"    }\r\n" + 
			        				"  }\r\n" + 
			        				"}";
	    				}else {
	    					payload = "{ \r\n" + 
			        				" \"size\":0,\r\n"  +
			        				"  \"query\": { \r\n" + 
			        				"    \"bool\": { \r\n" + 
			        				"      \"must\": [ \r\n" + 
			        				"        { \r\n" + 
			        				"          \"terms\": { \r\n" + 
			        				"            \"AIRID\": ["+airid+"] \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"term\": { \r\n" + 
			        				"            \"ID\": { \r\n" + 
			        				"              \"value\": "+id+"\r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"range\": { \r\n" + 
			        				"            \"@timestamp\": { \r\n" + 
			        				"              \"gte\": "+gte+",\r\n" + 
			        				"              \"lte\": "+lte+" \r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" +
			        				"		{\r\n" + 
			        				"              \"query_string\": {\r\n" + 
			        				"                \"analyze_wildcard\": true,\r\n" + 
			        				"                \"query\": \""+searchQuery+"\",\r\n" + 
			        				"                \"fields\": "+kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics(reportType))+"\r\n" + 
			        				"              }\r\n" + 
			        				"        }\r\n" +
			        				"      ] \r\n" + 
			        				"    } \r\n" + 
			        				"  },\r\n" + 
			        				"  \"aggs\": {\r\n" + 
			        				"    \"totalcount\": {\r\n" + 
			        				"      \"value_count\": {\r\n" + 
			        				"        \"field\": \"PageCustomVariable1Value.keyword\"\r\n" + 
			        				"      }\r\n" + 
			        				"    }\r\n" + 
			        				"  }\r\n" + 
			        				"}";
	    				}
	    			}else {
	    				if(search.equalsIgnoreCase("*")) {
	    					payload = "{ \r\n" + 
			        				" \"size\":0,\r\n"  +
			        				"  \"query\": { \r\n" + 
			        				"    \"bool\": { \r\n" + 
			        				"      \"must\": [ \r\n" + 
			        				"        { \r\n" + 
			        				"          \"terms\": { \r\n" + 
			        				"            \"AIRID\": ["+airid+"] \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"term\": { \r\n" + 
			        				"            \"ID\": { \r\n" + 
			        				"              \"value\": "+id+"\r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"range\": { \r\n" + 
			        				"            \"@timestamp\": { \r\n" + 
			        				"              \"gte\": "+gte+",\r\n" + 
			        				"              \"lte\": "+lte+" \r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        } \r\n" + 
			        				"      ] \r\n" + 
			        				"    } \r\n" + 
			        				"  },\r\n" + 
			        				"  \"aggs\": {\r\n" + 
			        				"    \"totalcount\": {\r\n" + 
			        				"      \"value_count\": {\r\n" + 
			        				"        \"field\": \"CleanPageURL.keyword\"\r\n" + 
			        				"      }\r\n" + 
			        				"    }\r\n" + 
			        				"  }\r\n" + 
			        				"}";
	    				}else {
	    					payload = "{ \r\n" + 
			        				" \"size\":0,\r\n"  +
			        				"  \"query\": { \r\n" + 
			        				"    \"bool\": { \r\n" + 
			        				"      \"must\": [ \r\n" + 
			        				"        { \r\n" + 
			        				"          \"terms\": { \r\n" + 
			        				"            \"AIRID\": ["+airid+"] \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"term\": { \r\n" + 
			        				"            \"ID\": { \r\n" + 
			        				"              \"value\": "+id+"\r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" + 
			        				"        { \r\n" + 
			        				"          \"range\": { \r\n" + 
			        				"            \"@timestamp\": { \r\n" + 
			        				"              \"gte\": "+gte+",\r\n" + 
			        				"              \"lte\": "+lte+" \r\n" + 
			        				"            } \r\n" + 
			        				"          } \r\n" + 
			        				"        }, \r\n" +
			        				"		{\r\n" + 
			        				"              \"query_string\": {\r\n" + 
			        				"                \"analyze_wildcard\": true,\r\n" + 
			        				"                \"query\": \""+searchQuery+"\",\r\n" + 
			        				"                \"fields\": [\"CleanPageURL.keyword\"]\r\n" + 
			        				"          }\r\n" + 
			        				"        }\r\n" +
			        				"      ] \r\n" + 
			        				"    } \r\n" + 
			        				"  },\r\n" + 
			        				"  \"aggs\": {\r\n" + 
			        				"    \"totalcount\": {\r\n" + 
			        				"      \"value_count\": {\r\n" + 
			        				"        \"field\": \"CleanPageURL.keyword\"\r\n" + 
			        				"      }\r\n" + 
			        				"    }\r\n" + 
			        				"  }\r\n" + 
			        				"}";
	    				}
	    			}	
	    			
	    		}
    		}
    	}else if(kusinaValidationUtils.isValidOverviewType(reportType) || kusinaValidationUtils.isValidOverviewChildType(reportType)) {
    		String overviewFilter = kusinaValidationUtils.convertOverviewReportTypeToFilterField(reportType);
    		if(reportType.equalsIgnoreCase("pageOverview") || reportType.equalsIgnoreCase("userOverview") || reportType.equalsIgnoreCase("referrerOverview") || reportType.equalsIgnoreCase("referrerOverviewChild")) {
    			if(filterFor.isEmpty() || filterFor.equalsIgnoreCase("*")) {
    				if(reportType.equalsIgnoreCase("userOverview")) {
    					if(search.equalsIgnoreCase("*")) {
        					payload = "{\r\n" + 
                					" \"size\":0,\r\n"  +
                    				"  \"query\": {\r\n" + 
                    				"    \"bool\": {\r\n" + 
                    				"      \"must\": [\r\n" + 
                    				"        {\r\n" + 
                    				"          \"terms\": {\r\n" + 
                    				"            \"AIRID\": ["+airid+"]\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"term\": {\r\n" + 
                    				"            \"ID\": {\r\n" + 
                    				"              \"value\": "+id+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"range\": {\r\n" + 
                    				"            \"@timestamp\": {\r\n" + 
                    				"              \"gte\": "+gte+",\r\n" + 
                    				"              \"lte\": "+lte+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        }\r\n" + 
                    				"      ]\r\n" + 
                    				"    }\r\n" + 	
                    				"  },\r\n" + 
                    				"  \"aggs\": {\r\n" + 
                    				"    \"totalcount\": {\r\n" + 
                    				"      \"cardinality\": {\r\n" + 
                    				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                    				"        \"precision_threshold\": 4000\r\n" + 
                    				"      }\r\n" + 
                    				"    }\r\n" + 
                    				"  }\r\n" + 
                    				"}";
        				}else {
        					payload = "{\r\n" + 
                					" \"size\":0,\r\n"  +
                    				"  \"query\": {\r\n" + 
                    				"    \"bool\": {\r\n" + 
                    				"      \"must\": [\r\n" + 
                    				"        {\r\n" + 
                    				"          \"terms\": {\r\n" + 
                    				"            \"AIRID\": ["+airid+"]\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"term\": {\r\n" + 
                    				"            \"ID\": {\r\n" + 
                    				"              \"value\": "+id+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"range\": {\r\n" + 
                    				"            \"@timestamp\": {\r\n" + 
                    				"              \"gte\": "+gte+",\r\n" + 
                    				"              \"lte\": "+lte+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" +
                    				"		{\r\n" + 
                    				"          \"query_string\": {\r\n" + 
                    				"            \"analyze_wildcard\": true,\r\n" + 
                    				"            \"query\": \""+searchQuery+"\",\r\n" + 
                    				"            \"fields\": [\"User\", \"CareerLevel\", \"CareerTracks\", \"Geography.keyword\", \"Country.keyword\"]\r\n" + 
                    				"          }\r\n" + 
                    				"        }\r\n" +
                    				"      ]\r\n" + 
                    				"    }\r\n" + 	
                    				"  },\r\n" + 
                    				"  \"aggs\": {\r\n" + 
                    				"    \"totalcount\": {\r\n" + 
                    				"      \"cardinality\": {\r\n" + 
                    				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                    				"        \"precision_threshold\": 4000\r\n" + 
                    				"      }\r\n" + 
                    				"    }\r\n" + 
                    				"  }\r\n" + 
                    				"}";
        				}
    				} else if(reportType.equalsIgnoreCase("pageOverview")){
    					if(search.equalsIgnoreCase("*")) {
        					payload = "{\r\n" + 
                					" \"size\":0,\r\n"  +
                    				"  \"query\": {\r\n" + 
                    				"    \"bool\": {\r\n" + 
                    				"      \"must\": [\r\n" + 
                    				"        {\r\n" + 
                    				"          \"terms\": {\r\n" + 
                    				"            \"AIRID\": ["+airid+"]\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"term\": {\r\n" + 
                    				"            \"ID\": {\r\n" + 
                    				"              \"value\": "+id+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"range\": {\r\n" + 
                    				"            \"@timestamp\": {\r\n" + 
                    				"              \"gte\": "+gte+",\r\n" + 
                    				"              \"lte\": "+lte+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        }\r\n" + 
                    				"      ],\r\n" +
                    				"		\"filter\": {\r\n" + 
                    				"        \"term\": {\r\n" + 
                    				"          \"ActionURLType\": \"1\"\r\n" + 
                    				"        }\r\n" + 
                    				"      }\r\n" +
                    				"    }\r\n" + 	
                    				"  },\r\n" + 
                    				"  \"aggs\": {\r\n" + 
                    				"    \"totalcount\": {\r\n" + 
                    				"      \"cardinality\": {\r\n" + 
                    				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                    				"        \"precision_threshold\": 4000\r\n" + 
                    				"      }\r\n" + 
                    				"    }\r\n" + 
                    				"  }\r\n" + 
                    				"}";
        				}else {
        					payload = "{\r\n" + 
                					" \"size\":0,\r\n"  +
                    				"  \"query\": {\r\n" + 
                    				"    \"bool\": {\r\n" + 
                    				"      \"must\": [\r\n" + 
                    				"        {\r\n" + 
                    				"          \"terms\": {\r\n" + 
                    				"            \"AIRID\": ["+airid+"]\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"term\": {\r\n" + 
                    				"            \"ID\": {\r\n" + 
                    				"              \"value\": "+id+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"range\": {\r\n" + 
                    				"            \"@timestamp\": {\r\n" + 
                    				"              \"gte\": "+gte+",\r\n" + 
                    				"              \"lte\": "+lte+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" +
                    				"		{\r\n" + 
                    				"          \"query_string\": {\r\n" + 
                    				"            \"analyze_wildcard\": true,\r\n" + 
                    				"            \"query\": \""+searchQuery+"\",\r\n" + 
                    				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
                    				"          }\r\n" + 
                    				"        }\r\n" +
                    				"      ],\r\n" +
                    				"		\"filter\": {\r\n" + 
                    				"        \"term\": {\r\n" + 
                    				"          \"ActionURLType\": \"1\"\r\n" + 
                    				"        }\r\n" + 
                    				"      }\r\n" +
                    				"    }\r\n" + 	
                    				"  },\r\n" + 
                    				"  \"aggs\": {\r\n" + 
                    				"    \"totalcount\": {\r\n" + 
                    				"      \"cardinality\": {\r\n" + 
                    				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                    				"        \"precision_threshold\": 4000\r\n" + 
                    				"      }\r\n" + 
                    				"    }\r\n" + 
                    				"  }\r\n" + 
                    				"}";
        				}
    				} else {
    					if(search.equalsIgnoreCase("*")) {
        					payload = "{\r\n" + 
                					" \"size\":0,\r\n"  +
                    				"  \"query\": {\r\n" + 
                    				"    \"bool\": {\r\n" + 
                    				"      \"must\": [\r\n" + 
                    				"        {\r\n" + 
                    				"          \"terms\": {\r\n" + 
                    				"            \"AIRID\": ["+airid+"]\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"term\": {\r\n" + 
                    				"            \"ID\": {\r\n" + 
                    				"              \"value\": "+id+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"range\": {\r\n" + 
                    				"            \"@timestamp\": {\r\n" + 
                    				"              \"gte\": "+gte+",\r\n" + 
                    				"              \"lte\": "+lte+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        }\r\n" + 
                    				"      ]\r\n" + 
                    				"    }\r\n" + 	
                    				"  },\r\n" + 
                    				"  \"aggs\": {\r\n" + 
                    				"    \"totalcount\": {\r\n" + 
                    				"      \"cardinality\": {\r\n" + 
                    				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                    				"        \"precision_threshold\": 4000\r\n" + 
                    				"      }\r\n" + 
                    				"    }\r\n" + 
                    				"  }\r\n" + 
                    				"}";
        				}else {
        					payload = "{\r\n" + 
                					" \"size\":0,\r\n"  +
                    				"  \"query\": {\r\n" + 
                    				"    \"bool\": {\r\n" + 
                    				"      \"must\": [\r\n" + 
                    				"        {\r\n" + 
                    				"          \"terms\": {\r\n" + 
                    				"            \"AIRID\": ["+airid+"]\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"term\": {\r\n" + 
                    				"            \"ID\": {\r\n" + 
                    				"              \"value\": "+id+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" + 
                    				"        {\r\n" + 
                    				"          \"range\": {\r\n" + 
                    				"            \"@timestamp\": {\r\n" + 
                    				"              \"gte\": "+gte+",\r\n" + 
                    				"              \"lte\": "+lte+"\r\n" + 
                    				"            }\r\n" + 
                    				"          }\r\n" + 
                    				"        },\r\n" +
                    				"		{\r\n" + 
                    				"          \"query_string\": {\r\n" + 
                    				"            \"analyze_wildcard\": true,\r\n" + 
                    				"            \"query\": \""+searchQuery+"\",\r\n" + 
                    				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
                    				"          }\r\n" + 
                    				"        }\r\n" +
                    				"      ]\r\n" + 
                    				"    }\r\n" + 	
                    				"  },\r\n" + 
                    				"  \"aggs\": {\r\n" + 
                    				"    \"totalcount\": {\r\n" + 
                    				"      \"cardinality\": {\r\n" + 
                    				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                    				"        \"precision_threshold\": 4000\r\n" + 
                    				"      }\r\n" + 
                    				"    }\r\n" + 
                    				"  }\r\n" + 
                    				"}";
        				}
    				}
    			}else {
    				if(search.equalsIgnoreCase("*")) {
    					payload = "{\r\n" + 
            					" \"size\":0,\r\n"  +
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" + 
                				"      ],\r\n" +
                				"	\"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"RefererName\": \""+filterFor+"\"\r\n" + 
                				"        }\r\n" + 
                				"      }\r\n" +
                				"    }\r\n" + 	
                				"  },\r\n" + 
                				"  \"aggs\": {\r\n" + 
                				"    \"totalcount\": {\r\n" + 
                				"      \"cardinality\": {\r\n" + 
                				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                				"        \"precision_threshold\": 4000\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  }\r\n" + 
                				"}";
    				}else {
    					payload = "{\r\n" + 
            					" \"size\":0,\r\n"  +
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"		{\r\n" + 
                				"          \"query_string\": {\r\n" + 
                				"            \"analyze_wildcard\": true,\r\n" + 
                				"            \"query\": \""+searchQuery+"\",\r\n" + 
                				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" +
                				"      ],\r\n" +
                				"	\"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"RefererName\": \""+filterFor+"\"\r\n" + 
                				"        }\r\n" + 
                				"      }\r\n" +
                				"    }\r\n" + 	
                				"  },\r\n" + 
                				"  \"aggs\": {\r\n" + 
                				"    \"totalcount\": {\r\n" + 
                				"      \"cardinality\": {\r\n" + 
                				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                				"        \"precision_threshold\": 4000\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  }\r\n" + 
                				"}";
    				}
    			}	
    		}else if (reportType.equalsIgnoreCase("downloadOverview") || reportType.equalsIgnoreCase("downloadOverviewChild")) {
    			if(filterFor.isEmpty() || filterFor.equalsIgnoreCase("*")) {
    				if(search.equalsIgnoreCase("*")) {
    					payload = "{\r\n" + 
            					" \"size\":0,\r\n"  +
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" + 
                				"      ],\r\n" + 
                				"     \"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"ActionURLType\": \"3\"\r\n" + 
                				"        }\r\n" + 
                				"      } \r\n" +
                				"    }\r\n" + 	
                				"  },\r\n" + 
                				"  \"aggs\": {\r\n" + 
                				"    \"totalcount\": {\r\n" + 
                				"      \"cardinality\": {\r\n" + 
                				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                				"        \"precision_threshold\": 4000\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  }\r\n" + 
                				"}";
    				}else {
    					payload = "{\r\n" + 
            					" \"size\":0,\r\n"  +
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"       {\r\n" + 
                				"          \"query_string\": {\r\n" + 
                				"            \"analyze_wildcard\": true,\r\n" + 
                				"            \"query\": \""+searchQuery+"\",\r\n" + 
                				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" +
                				"      ],\r\n" + 
                				"     \"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"ActionURLType\": \"3\"\r\n" + 
                				"        }\r\n" + 
                				"      } \r\n" +
                				"    }\r\n" + 	
                				"  },\r\n" + 
                				"  \"aggs\": {\r\n" + 
                				"    \"totalcount\": {\r\n" + 
                				"      \"cardinality\": {\r\n" + 
                				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                				"        \"precision_threshold\": 4000\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  }\r\n" + 
                				"}";
    				}	
    			}else {
    				if(search.equalsIgnoreCase("*")) {
    					payload = "{\r\n" + 
            					" \"size\":0,\r\n"  +
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ActionURLType\": {\r\n" + 
                				"              \"value\": \"3\"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" + 
                				"      ],\r\n" + 
                				"     \"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"CleanPageURL.keyword\": \""+filterFor+"\"\r\n" + 
                				"        }\r\n" + 
                				"      } \r\n" +
                				"    }\r\n" + 	
                				"  },\r\n" + 
                				"  \"aggs\": {\r\n" + 
                				"    \"totalcount\": {\r\n" + 
                				"      \"cardinality\": {\r\n" + 
                				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                				"        \"precision_threshold\": 4000\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  }\r\n" + 
                				"}";
    				}else {
    					payload = "{\r\n" + 
            					" \"size\":0,\r\n"  +
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ActionURLType\": {\r\n" + 
                				"              \"value\": \"3\"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"		{\r\n" + 
                				"          \"query_string\": {\r\n" + 
                				"            \"analyze_wildcard\": true,\r\n" + 
                				"            \"query\": \""+searchQuery+"\",\r\n" + 
                				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" +
                				"      ],\r\n" + 
                				"     \"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"CleanPageURL.keyword\": \""+filterFor+"\"\r\n" + 
                				"        }\r\n" + 
                				"      } \r\n" +
                				"    }\r\n" + 	
                				"  },\r\n" + 
                				"  \"aggs\": {\r\n" + 
                				"    \"totalcount\": {\r\n" + 
                				"      \"cardinality\": {\r\n" + 
                				"        \"field\": \""+overviewFilter+"\",\r\n" + 
                				"        \"precision_threshold\": 4000\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  }\r\n" + 
                				"}";
    				}		
    			}
    		}
    	}else if(kusinaValidationUtils.isValidEventsType(reportType)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"terms\": {\r\n" + 
    					"            \"AIRID\": ["+airid+"]\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"term\": {\r\n" + 
    					"            \"ID\": {\r\n" + 
    					"              \"value\": "+id+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"range\": {\r\n" + 
    					"            \"@timestamp\": {\r\n" + 
    					"              \"gte\": "+gte+",\r\n" + 
    					"              \"lte\": "+lte+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"      ]\r\n" + 
    					"    }\r\n" +
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"cardinality\": {\r\n" + 
    					"        \"field\": \"EventAction\",\r\n" + 
    					"        \"precision_threshold\": 4000\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"terms\": {\r\n" + 
    					"            \"AIRID\": ["+airid+"]\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"term\": {\r\n" + 
    					"            \"ID\": {\r\n" + 
    					"              \"value\": "+id+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"range\": {\r\n" + 
    					"            \"@timestamp\": {\r\n" + 
    					"              \"gte\": "+gte+",\r\n" + 
    					"              \"lte\": "+lte+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"		{\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" + 
    					"            \"fields\": [\"EventAction\"]\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" +
    					"      ]\r\n" + 
    					"    }\r\n" +
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"cardinality\": {\r\n" + 
    					"        \"field\": \"EventAction\",\r\n" + 
    					"        \"precision_threshold\": 4000\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}	
    	} else if ("users".equalsIgnoreCase(reportType)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload="{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": []\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"cardinality\": {\r\n" + 
    					"        \"field\": \"user_eid\",\r\n" + 
    					"        \"precision_threshold\": 4000\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" + 
    					"            \"fields\": [\"user_eid\", \"user_access\", \"user_airid\", \"access_expiration_date\", \"access_status\", \"created_date\" , \"last_update_date\", \"last_update_by\"]\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"        ]\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"cardinality\": {\r\n" + 
    					"        \"field\": \"user_eid\",\r\n" + 
    					"        \"precision_threshold\": 4000\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}
    	} else if ("feedbacks".equalsIgnoreCase(reportType)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload="{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": []\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"user_eid\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" + 
    					"            \"fields\": [\"user_eid\", \"user_airid\", \"rating_module\", \"rating_score\", \"rating_comment\", \"status\" , \"created_date\", \"last_update_date\", \"last_update_by\"]\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"        ]\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"user_eid\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}
    	} else if ("announce".equalsIgnoreCase(reportType)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload="{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": []\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"announcement_type\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" + 
    					"            \"fields\": [\"announcement_type\", \"announcement_due_date\", \"announcement_title\", \"announcement_content\", \"announcement_status\", \"announcement_created_date\" , \"announcement_last_updated_date\", \"announcement_last_updated_by\"]\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"        ]\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"announcement_type\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}
    	} else if ("announcelive".equalsIgnoreCase(reportType)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload="{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"	   \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"term\": {\r\n" + 
    					"            \"announcement_status\": {\r\n" + 
    					"              \"value\": \"live\"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"      ]\r\n" +
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"announcement_type\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"		{\r\n" + 
    					"          \"term\": {\r\n" + 
    					"            \"announcement_status\": {\r\n" + 
    					"              \"value\": \"live\"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" +
    					"        {\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" + 
    					"            \"fields\": [\"announcement_type\", \"announcement_due_date\", \"announcement_title\", \"announcement_content\", \"announcement_status\", \"announcement_created_date\" , \"announcement_last_updated_date\", \"announcement_last_updated_by\"]\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"        ]\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"announcement_type\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}
    	} else if ("history".equalsIgnoreCase(reportType)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload="{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": []\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"history_type\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" + 
    					"            \"fields\": [\"history_type\", \"history_user_eid\", \"history_doc_id\", \"history_action_type\", \"history_action_date\"]\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"        ]\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"history_type\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}
    	} else if ("profile".equalsIgnoreCase(reportType)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload="{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": []\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"AIRID\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" + 
    					"            \"fields\": [\"AIRID\", \"ID\", \"APPNAME\", \"created_date\", \"last_update_date\", \"last_update_by\"]\r\n" + 
    					"          }\r\n" + 
    					"        }\r\n" + 
    					"        ]\r\n" + 
    					"    }\r\n" + 
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"value_count\": {\r\n" + 
    					"        \"field\": \"AIRID\"\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}
    	}  else if (kusinaValidationUtils.isValidITFType(reportType)) {
    		String itfFilter = kusinaValidationUtils.convertReportTypeToITFFilter(reportType);
    		if(search.equalsIgnoreCase("*")) {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"terms\": {\r\n" + 
    					"            \"AIRID\": ["+airid+"]\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"term\": {\r\n" + 
    					"            \"ID\": {\r\n" + 
    					"              \"value\": "+id+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"range\": {\r\n" + 
    					"            \"@timestamp\": {\r\n" + 
    					"              \"gte\": "+gte+",\r\n" + 
    					"              \"lte\": "+lte+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" +
    					"		{\r\n" + 
    					"	         \"match_phrase\": {\r\n" + 
    					"	           \"PageURL\": \""+itfFilter+"\"\r\n" + 
    					"	         }\r\n" + 
    					"	        }\r\n" +
    					"      ]\r\n" + 
    					"    }\r\n" +
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"cardinality\": {\r\n" + 
    					"        \"field\": \"User.hash\",\r\n" + 
    					"        \"precision_threshold\": 4000\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}else {
    			payload = "{\r\n" + 
    					" \"size\":0,\r\n" + 
    					"  \"query\": {\r\n" + 
    					"    \"bool\": {\r\n" + 
    					"      \"must\": [\r\n" + 
    					"        {\r\n" + 
    					"          \"terms\": {\r\n" + 
    					"            \"AIRID\": ["+airid+"]\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"term\": {\r\n" + 
    					"            \"ID\": {\r\n" + 
    					"              \"value\": "+id+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" + 
    					"        {\r\n" + 
    					"          \"range\": {\r\n" + 
    					"            \"@timestamp\": {\r\n" + 
    					"              \"gte\": "+gte+",\r\n" + 
    					"              \"lte\": "+lte+"\r\n" + 
    					"            }\r\n" + 
    					"          }\r\n" + 
    					"        },\r\n" +
    					"		{\r\n" + 
    					"	         \"match_phrase\": {\r\n" + 
    					"	           \"PageURL\": \""+itfFilter+"\"\r\n" + 
    					"	         }\r\n" + 
    					"	        }\r\n" +
    					"		{\r\n" + 
    					"          \"query_string\": {\r\n" + 
    					"            \"analyze_wildcard\": true,\r\n" + 
    					"            \"query\": \""+searchQuery+"\",\r\n" +
    					"          }\r\n" + 
    					"        }\r\n" +
    					"      ]\r\n" + 
    					"    }\r\n" +
    					"  },\r\n" + 
    					"  \"aggs\": {\r\n" + 
    					"    \"totalcount\": {\r\n" + 
    					"      \"cardinality\": {\r\n" + 
    					"        \"field\": \"User.hash\",\r\n" + 
    					"        \"precision_threshold\": 4000\r\n" + 
    					"      }\r\n" + 
    					"    }\r\n" + 
    					"  }\r\n" + 
    					"}";
    		}	
    	}
    	
    	return payload;
    }
    
    public String getCustomReportFilter(String search, String param, String reportType, String filterFor, String airid, String id, String gte, String lte, String from, String size, String filter) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.searchQuery(reportType, search);
    	String overviewFilter = kusinaValidationUtils.convertOverviewReportTypeToFilterField(reportType);
    	if("usageReportPagination".equalsIgnoreCase(param)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload = "{\r\n" + 
    	        		"  \"query\": {\r\n" + 
    	        		"    \"bool\": {\r\n" + 
    	        		"      \"must\": [\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"terms\": {\r\n" + 
    	        		"            \"AIRID\": ["+airid+"]\r\n" + 
    	        		"          }\r\n" + 
    	        		"        },\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"term\": {\r\n" + 
    	        		"            \"ID\": {\r\n" + 
    	        		"              \"value\": "+id+"\r\n" + 
    	        		"            }\r\n" + 
    	        		"          }\r\n" + 
    	        		"        },\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"range\": {\r\n" + 
    	        		"            \"@timestamp\": {\r\n" + 
    	        		"              \"gte\": "+gte+",\r\n" + 
    	        		"              \"lte\": "+lte+"\r\n" + 
    	        		"            }\r\n" + 
    	        		"          }\r\n" + 
    	        		"        },\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"query_string\": {\r\n" + 
    	        		"            \"analyze_wildcard\": true,\r\n" + 
    	        		"            \"query\": \"*\",\r\n" + 
    	        		"            \"fields\": [\"User\", \"CareerTracks\", \"CareerLevelDescription\", \"Geography.keyword\"]\r\n" + 
    	        		"          }\r\n" + 
    	        		"        }\r\n" + 
    	        		"      ]\r\n" + 
    	        		"    }\r\n" + 
    	        		"  },\r\n" + 
    	        		"  \"collapse\" : {\r\n" + 
    	        		"          \"field\" : \""+filter+"\"\r\n" + 
    	        		"  },\r\n" + 
    	        		"  \"from\": "+from+", \r\n" + 
    	        		"  \"size\": "+size+", \r\n" + 
    	        		"  \"sort\": [\r\n" + 
    	        		"    {\r\n" + 
    	        		"      \""+filter+"\": {\r\n" + 
    	        		"        \"order\": \"asc\"\r\n" + 
    	        		"      }\r\n" + 
    	        		"    }\r\n" + 
    	        		"  ]\r\n" + 
    	        		"}";
    		}else {
    			payload = "{\r\n" + 
    	        		"  \"query\": {\r\n" + 
    	        		"    \"bool\": {\r\n" + 
    	        		"      \"must\": [\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"terms\": {\r\n" + 
    	        		"            \"AIRID\": ["+airid+"]\r\n" + 
    	        		"          }\r\n" + 
    	        		"        },\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"term\": {\r\n" + 
    	        		"            \"ID\": {\r\n" + 
    	        		"              \"value\": "+id+"\r\n" + 
    	        		"            }\r\n" + 
    	        		"          }\r\n" + 
    	        		"        },\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"range\": {\r\n" + 
    	        		"            \"@timestamp\": {\r\n" + 
    	        		"              \"gte\": "+gte+",\r\n" + 
    	        		"              \"lte\": "+lte+"\r\n" + 
    	        		"            }\r\n" + 
    	        		"          }\r\n" + 
    	        		"        },\r\n" + 
    	        		"        {\r\n" + 
    	        		"          \"query_string\": {\r\n" + 
    	        		"            \"analyze_wildcard\": true,\r\n" + 
    	        		"            \"query\": \""+searchQuery+"\",\r\n" + 
    	        		"            \"fields\": [\""+filter+"\"]\r\n" + 
    	        		"          }\r\n" + 
    	        		"        }\r\n" + 
    	        		"      ]\r\n" + 
    	        		"    }\r\n" + 
    	        		"  },\r\n" + 
    	        		"  \"collapse\" : {\r\n" + 
    	        		"          \"field\" : \""+filter+"\"\r\n" + 
    	        		"  },\r\n" + 
    	        		"  \"from\": "+from+", \r\n" + 
    	        		"  \"size\": "+size+", \r\n" + 
    	        		"  \"sort\": [\r\n" + 
    	        		"    {\r\n" + 
    	        		"      \""+filter+"\": {\r\n" + 
    	        		"        \"order\": \"asc\"\r\n" + 
    	        		"      }\r\n" + 
    	        		"    }\r\n" + 
    	        		"  ]\r\n" + 
    	        		"}";
    		}
    		 
    	} else if ("pagesReportPagination".equalsIgnoreCase(param)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload = " {\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \"*\",\r\n" + 
        				"            \"fields\": [\"PageURL.keyword\"]\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \"PageURL.keyword\"\r\n" + 
        				"  },\r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \"PageURL.keyword\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
    		}else {
    			payload = " {\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \""+searchQuery+"\",\r\n" + 
        				"            \"fields\": [\"PageURL.keyword\"]\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \"PageURL.keyword\"\r\n" + 
        				"  },\r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \"PageURL.keyword\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
    		}
    	} else if ("AIBSPBVIreportsPagination".equalsIgnoreCase(param)) {
    		if(search.equalsIgnoreCase("*")) {    	
    			payload = "{\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \"*\",\r\n" + 
        				"            \"fields\": [\"PageCustomVariable1Value.keyword\"]\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \"Hits\"\r\n" + 
        				"  },\r\n" + 
        				"  \"_source\": false, \r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \"Hits\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
    		}else {
    			payload = "{\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \""+searchQuery+"\",\r\n" + 
        				"            \"fields\": "+kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics(reportType))+"\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \"Hits\"\r\n" + 
        				"  },\r\n" + 
        				"  \"_source\": false, \r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \"Hits\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
    		}
    	} else if ("overviewReportsPagination".equalsIgnoreCase(param)) {
    		if(!reportType.equalsIgnoreCase("downloadOverview")) {
    			if(reportType.equalsIgnoreCase("userOverview")) {
    				if(search.equalsIgnoreCase("*")) {
        				payload = "{\r\n" + 
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" + 
                				"      ]\r\n" + 
                				"    }\r\n" + 
                				"  },\r\n" + 
                				"  \"collapse\" : {\r\n" + 
                				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
                				"  },\r\n" + 
                				"  \"_source\": false, \r\n" + 
                				"  \"from\": "+from+",\r\n" + 
                				"  \"size\": "+size+",\r\n" + 
                				"  \"sort\": [\r\n" + 
                				"    {\r\n" + 
                				"      \""+overviewFilter+"\": {\r\n" + 
                				"        \"order\": \"asc\"\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  ]\r\n" + 
                				"}";
        			}else {
        				payload = "{\r\n" + 
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"	{\r\n" + 
                				"          \"query_string\": {\r\n" + 
                				"            \"analyze_wildcard\": true,\r\n" + 
                				"            \"query\": \""+searchQuery+"\",\r\n" + 
                				"            \"fields\": [\"User\", \"CareerLevel\", \"CareerTracks\", \"Geography.keyword\", \"Country.keyword\"]\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" +
                				"      ]\r\n" + 
                				"    }\r\n" + 
                				"  },\r\n" + 
                				"  \"collapse\" : {\r\n" + 
                				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
                				"  },\r\n" + 
                				"  \"_source\": false, \r\n" + 
                				"  \"from\": "+from+",\r\n" + 
                				"  \"size\": "+size+",\r\n" + 
                				"  \"sort\": [\r\n" + 
                				"    {\r\n" + 
                				"      \""+overviewFilter+"\": {\r\n" + 
                				"        \"order\": \"asc\"\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  ]\r\n" + 
                				"}";
        			}
    			}else if(reportType.equalsIgnoreCase("pageOverview")) {
    				if(search.equalsIgnoreCase("*")) {
        				payload = "{\r\n" + 
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" + 
                				"      ],\r\n" +
                				"      \"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"ActionURLType\": \"1\"\r\n" + 
                				"        }\r\n" + 
                				"      }\r\n" +
                				"    }\r\n" + 
                				"  },\r\n" + 
                				"  \"collapse\" : {\r\n" + 
                				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
                				"  },\r\n" + 
                				"  \"_source\": false, \r\n" + 
                				"  \"from\": "+from+",\r\n" + 
                				"  \"size\": "+size+",\r\n" + 
                				"  \"sort\": [\r\n" + 
                				"    {\r\n" + 
                				"      \""+overviewFilter+"\": {\r\n" + 
                				"        \"order\": \"asc\"\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  ]\r\n" + 
                				"}";
        			}else {
        				payload = "{\r\n" + 
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"	{\r\n" + 
                				"          \"query_string\": {\r\n" + 
                				"            \"analyze_wildcard\": true,\r\n" + 
                				"            \"query\": \""+searchQuery+"\",\r\n" + 
                				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" +
                				"      ],\r\n" +
                				"      \"filter\": {\r\n" + 
                				"        \"term\": {\r\n" + 
                				"          \"ActionURLType\": \"1\"\r\n" + 
                				"        }\r\n" + 
                				"      }\r\n" +
                				"    }\r\n" + 
                				"  },\r\n" + 
                				"  \"collapse\" : {\r\n" + 
                				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
                				"  },\r\n" + 
                				"  \"_source\": false, \r\n" + 
                				"  \"from\": "+from+",\r\n" + 
                				"  \"size\": "+size+",\r\n" + 
                				"  \"sort\": [\r\n" + 
                				"    {\r\n" + 
                				"      \""+overviewFilter+"\": {\r\n" + 
                				"        \"order\": \"asc\"\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  ]\r\n" + 
                				"}";
        			}
    			} else {
    				if(search.equalsIgnoreCase("*")) {
        				payload = "{\r\n" + 
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" + 
                				"      ]\r\n" +
                				"    }\r\n" + 
                				"  },\r\n" + 
                				"  \"collapse\" : {\r\n" + 
                				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
                				"  },\r\n" + 
                				"  \"_source\": false, \r\n" + 
                				"  \"from\": "+from+",\r\n" + 
                				"  \"size\": "+size+",\r\n" + 
                				"  \"sort\": [\r\n" + 
                				"    {\r\n" + 
                				"      \""+overviewFilter+"\": {\r\n" + 
                				"        \"order\": \"asc\"\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  ]\r\n" + 
                				"}";
        			}else {
        				payload = "{\r\n" + 
                				"  \"query\": {\r\n" + 
                				"    \"bool\": {\r\n" + 
                				"      \"must\": [\r\n" + 
                				"        {\r\n" + 
                				"          \"terms\": {\r\n" + 
                				"            \"AIRID\": ["+airid+"]\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"term\": {\r\n" + 
                				"            \"ID\": {\r\n" + 
                				"              \"value\": "+id+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" + 
                				"        {\r\n" + 
                				"          \"range\": {\r\n" + 
                				"            \"@timestamp\": {\r\n" + 
                				"              \"gte\": "+gte+",\r\n" + 
                				"              \"lte\": "+lte+"\r\n" + 
                				"            }\r\n" + 
                				"          }\r\n" + 
                				"        },\r\n" +
                				"	{\r\n" + 
                				"          \"query_string\": {\r\n" + 
                				"            \"analyze_wildcard\": true,\r\n" + 
                				"            \"query\": \""+searchQuery+"\",\r\n" + 
                				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
                				"          }\r\n" + 
                				"        }\r\n" +
                				"      ]\r\n" +
                				"    }\r\n" + 
                				"  },\r\n" + 
                				"  \"collapse\" : {\r\n" + 
                				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
                				"  },\r\n" + 
                				"  \"_source\": false, \r\n" + 
                				"  \"from\": "+from+",\r\n" + 
                				"  \"size\": "+size+",\r\n" + 
                				"  \"sort\": [\r\n" + 
                				"    {\r\n" + 
                				"      \""+overviewFilter+"\": {\r\n" + 
                				"        \"order\": \"asc\"\r\n" + 
                				"      }\r\n" + 
                				"    }\r\n" + 
                				"  ]\r\n" + 
                				"}";
        			}
    			}
    		}else {
    			if(search.equalsIgnoreCase("*")) {
    				payload = "{\r\n" + 
            				"  \"query\": {\r\n" + 
            				"    \"bool\": {\r\n" + 
            				"      \"must\": [\r\n" + 
            				"        {\r\n" + 
            				"          \"terms\": {\r\n" + 
            				"            \"AIRID\": ["+airid+"]\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        {\r\n" + 
            				"          \"term\": {\r\n" + 
            				"            \"ID\": {\r\n" + 
            				"              \"value\": "+id+"\r\n" + 
            				"            }\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        {\r\n" + 
            				"          \"range\": {\r\n" + 
            				"            \"@timestamp\": {\r\n" + 
            				"              \"gte\": "+gte+",\r\n" + 
            				"              \"lte\": "+lte+"\r\n" + 
            				"            }\r\n" + 
            				"          }\r\n" + 
            				"        }\r\n" + 
            				"      ],\r\n" +
            				"      \"filter\": {\r\n" + 
            				"        \"term\": {\r\n" + 
            				"          \"ActionURLType\": \"3\"\r\n" + 
            				"        }\r\n" + 
            				"      }\r\n" +
            				"    }\r\n" + 
            				"  },\r\n" + 
            				"  \"collapse\" : {\r\n" + 
            				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
            				"  },\r\n" + 
            				"  \"_source\": false, \r\n" + 
            				"  \"from\": "+from+",\r\n" + 
            				"  \"size\": "+size+",\r\n" + 
            				"  \"sort\": [\r\n" + 
            				"    {\r\n" + 
            				"      \""+overviewFilter+"\": {\r\n" + 
            				"        \"order\": \"asc\"\r\n" + 
            				"      }\r\n" + 
            				"    }\r\n" + 
            				"  ]\r\n" + 
            				"}";
    			}else {
    				payload = "{\r\n" + 
            				"  \"query\": {\r\n" + 
            				"    \"bool\": {\r\n" + 
            				"      \"must\": [\r\n" + 
            				"        {\r\n" + 
            				"          \"terms\": {\r\n" + 
            				"            \"AIRID\": ["+airid+"]\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        {\r\n" + 
            				"          \"term\": {\r\n" + 
            				"            \"ID\": {\r\n" + 
            				"              \"value\": "+id+"\r\n" + 
            				"            }\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        {\r\n" + 
            				"          \"range\": {\r\n" + 
            				"            \"@timestamp\": {\r\n" + 
            				"              \"gte\": "+gte+",\r\n" + 
            				"              \"lte\": "+lte+"\r\n" + 
            				"            }\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" +
            				"		{\r\n" + 
            				"          \"query_string\": {\r\n" + 
            				"            \"analyze_wildcard\": true,\r\n" + 
            				"            \"query\": \""+searchQuery+"\",\r\n" + 
            				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
            				"          }\r\n" + 
            				"        }\r\n" +
            				"      ],\r\n" +
            				"      \"filter\": {\r\n" + 
            				"        \"term\": {\r\n" + 
            				"          \"ActionURLType\": \"3\"\r\n" + 
            				"        }\r\n" + 
            				"      }\r\n" +
            				"    }\r\n" + 
            				"  },\r\n" + 
            				"  \"collapse\" : {\r\n" + 
            				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
            				"  },\r\n" + 
            				"  \"_source\": false, \r\n" + 
            				"  \"from\": "+from+",\r\n" + 
            				"  \"size\": "+size+",\r\n" + 
            				"  \"sort\": [\r\n" + 
            				"    {\r\n" + 
            				"      \""+overviewFilter+"\": {\r\n" + 
            				"        \"order\": \"asc\"\r\n" + 
            				"      }\r\n" + 
            				"    }\r\n" + 
            				"  ]\r\n" + 
            				"}";
    			}
    		}
    	} else if ("overviewReportsPaginationChild".equalsIgnoreCase(param)) {
    		if(reportType.equalsIgnoreCase("referrerOverviewChild")) {
    			System.out.println("THIS IS REFERRERCHILD PAYLOAD");
    			if(search.equalsIgnoreCase("*")) {
    				payload = "{\r\n" + 
        					"  \"query\": {\r\n" + 
        					"    \"bool\": {\r\n" + 
        					"      \"must\": [\r\n" + 
        					"        {\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"AIRID\": ["+airid+"]\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ID\": {\r\n" + 
        					"              \"value\": "+id+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"range\": {\r\n" + 
        					"            \"@timestamp\": {\r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" + 
        					"      ],\r\n" + 
        					"      \"filter\": {\r\n" + 
        					"        \"term\": {\r\n" + 
        					"          \"RefererName\": \""+filterFor+"\"\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  },\r\n" + 
        					"  \"collapse\" : {\r\n" + 
        					"          \"field\" : \"User\"\r\n" + 
        					"  },\r\n" + 
        					"  \"_source\": false, \r\n" + 
        					"  \"from\": "+from+",\r\n" + 
        					"  \"size\": "+size+",\r\n" + 
        					"  \"sort\": [\r\n" + 
        					"    {\r\n" + 
        					"      \"User\": {\r\n" + 
        					"        \"order\": \"asc\"\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  ]\r\n" + 
        					"}";
    			}else {
    				payload = "{\r\n" + 
        					"  \"query\": {\r\n" + 
        					"    \"bool\": {\r\n" + 
        					"      \"must\": [\r\n" + 
        					"        {\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"AIRID\": ["+airid+"]\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ID\": {\r\n" + 
        					"              \"value\": "+id+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"range\": {\r\n" + 
        					"            \"@timestamp\": {\r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" +
        					"		{\r\n" + 
        					"          \"query_string\": {\r\n" + 
        					"            \"analyze_wildcard\": true,\r\n" + 
        					"            \"query\": \""+searchQuery+"\",\r\n" + 
        					"            \"fields\": [\"User\"]\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" +
        					"      ],\r\n" + 
        					"      \"filter\": {\r\n" + 
        					"        \"term\": {\r\n" + 
        					"          \"RefererName\": \""+filterFor+"\"\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  },\r\n" + 
        					"  \"collapse\" : {\r\n" + 
        					"          \"field\" : \"User\"\r\n" + 
        					"  },\r\n" + 
        					"  \"_source\": false, \r\n" + 
        					"  \"from\": "+from+",\r\n" + 
        					"  \"size\": "+size+",\r\n" + 
        					"  \"sort\": [\r\n" + 
        					"    {\r\n" + 
        					"      \"User\": {\r\n" + 
        					"        \"order\": \"asc\"\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  ]\r\n" + 
        					"}";
    			}
    		}else if(reportType.equalsIgnoreCase("downloadOverviewChild")) {
    			System.out.println("THIS IS DOWNLOADCHILD PAYLOAD");
    			if(search.equalsIgnoreCase("*")) {
    				payload = "{\r\n" + 
        					"  \"query\": {\r\n" + 
        					"    \"bool\": {\r\n" + 
        					"      \"must\": [\r\n" + 
        					"        {\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"AIRID\": ["+airid+"]\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ID\": {\r\n" + 
        					"              \"value\": "+id+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ActionURLType\": {\r\n" + 
        					"              \"value\": \"3\"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"range\": {\r\n" + 
        					"            \"@timestamp\": {\r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" + 
        					"      ],\r\n" + 
        					"      \"filter\": {\r\n" + 
        					"        \"term\": {\r\n" + 
        					"          \"CleanPageURL.keyword\": \""+filterFor+"\"\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  },\r\n" + 
        					"  \"collapse\" : {\r\n" + 
        					"          \"field\" : \"User\"\r\n" + 
        					"  },\r\n" + 
        					"  \"_source\": false, \r\n" + 
        					"  \"from\": "+from+",\r\n" + 
        					"  \"size\": "+size+",\r\n" + 
        					"  \"sort\": [\r\n" + 
        					"    {\r\n" + 
        					"      \"User\": {\r\n" + 
        					"        \"order\": \"asc\"\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  ]\r\n" + 
        					"}";
    			}else {
    				payload = "{\r\n" + 
        					"  \"query\": {\r\n" + 
        					"    \"bool\": {\r\n" + 
        					"      \"must\": [\r\n" + 
        					"        {\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"AIRID\": ["+airid+"]\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ID\": {\r\n" + 
        					"              \"value\": "+id+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ActionURLType\": {\r\n" + 
        					"              \"value\": \"3\"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        {\r\n" + 
        					"          \"range\": {\r\n" + 
        					"            \"@timestamp\": {\r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" +
        					"	   {\r\n" + 
        					"          \"query_string\": {\r\n" + 
        					"            \"analyze_wildcard\": true,\r\n" + 
        					"            \"query\": \""+searchQuery+"\",\r\n" + 
        					"            \"fields\": [\"User\"]\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" +
        					"      ],\r\n" + 
        					"      \"filter\": {\r\n" + 
        					"        \"term\": {\r\n" + 
        					"          \"CleanPageURL.keyword\": \""+filterFor+"\"\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  },\r\n" + 
        					"  \"collapse\" : {\r\n" + 
        					"          \"field\" : \"User\"\r\n" + 
        					"  },\r\n" + 
        					"  \"_source\": false, \r\n" + 
        					"  \"from\": "+from+",\r\n" + 
        					"  \"size\": "+size+",\r\n" + 
        					"  \"sort\": [\r\n" + 
        					"    {\r\n" + 
        					"      \"User\": {\r\n" + 
        					"        \"order\": \"asc\"\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  ]\r\n" + 
        					"}";
    			}
    		} 		
    		
    	} else if("eventsPagination".equalsIgnoreCase(param)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload = "{\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \"*\",\r\n" + 
        				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
        				"  },\r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \""+overviewFilter+"\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
    		}else {
    			payload = "{\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \""+searchQuery+"\",\r\n" + 
        				"            \"fields\": [\""+overviewFilter+"\"]\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \""+overviewFilter+"\"\r\n" + 
        				"  },\r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \""+overviewFilter+"\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
    		}
    	} else if("itfReportsPagination".equalsIgnoreCase(param)) {
    		String itfFilter = kusinaValidationUtils.convertReportTypeToITFFilter(reportType);
    		if(search.equalsIgnoreCase("*")) {
				payload = "{\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" +
        				"		{\r\n" + 
        				"	         \"match_phrase\": {\r\n" + 
        				"	           \"PageURL\": \""+itfFilter+"\"\r\n" + 
        				"	         }\r\n" + 
        				"	        }\r\n" +
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \"User\"\r\n" + 
        				"  },\r\n" + 
        				"  \"_source\": false, \r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \"User\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
			}else {
				payload = "{\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": ["+airid+"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": "+id+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" +
        				"		{\r\n" + 
        				"	         \"match_phrase\": {\r\n" + 
        				"	           \"PageURL\": \""+itfFilter+"\"\r\n" + 
        				"	         }\r\n" + 
        				"	        },\r\n" +
        				"	     {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \""+searchQuery+"\",\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" +
        				"      ]\r\n" + 
        				"    }\r\n" + 
        				"  },\r\n" + 
        				"  \"collapse\" : {\r\n" + 
        				"          \"field\" : \"User\"\r\n" + 
        				"  },\r\n" + 
        				"  \"_source\": false, \r\n" + 
        				"  \"from\": "+from+",\r\n" + 
        				"  \"size\": "+size+",\r\n" + 
        				"  \"sort\": [\r\n" + 
        				"    {\r\n" + 
        				"      \"User\": {\r\n" + 
        				"        \"order\": \"asc\"\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"  ]\r\n" + 
        				"}";
			}
    	}
        return payload;
    }
    
    public String getCustomReportFilterChild(String search, String param, String airid, String id, String gte, String lte, String from, String size, String filterFor, String filterMetrics, String filter) {
    	String checkfilterFor =  kusinaStringUtils.convertFilterToElasticQueryNames(filterFor);
    	String payload = null;
    	String searchQuery = kusinaStringUtils.searchQuery(param, search);
    	String fieldNames = kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics("usageChild"));
    	if("usageReportPaginationChild".equalsIgnoreCase(param)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload = "{\r\n" + 
            			"  \"query\": {\r\n" + 
            			"    \"bool\": {\r\n" + 
            			"      \"must\": [\r\n" + 
            			"        {\r\n" + 
            			"          \"terms\": {\r\n" + 
            			"            \"AIRID\": ["+airid+"]\r\n" + 
            			"          }\r\n" + 
            			"        },\r\n" + 
            			"        {\r\n" + 
            			"          \"term\": {\r\n" + 
            			"            \"ID\": {\r\n" + 
            			"              \"value\": "+id+"\r\n" + 
            			"            }\r\n" + 
            			"          }\r\n" + 
            			"        },\r\n" + 
            			"        {\r\n" + 
            			"          \"range\": {\r\n" + 
            			"            \"@timestamp\": {\r\n" + 
            			"              \"gte\": "+gte+",\r\n" + 
            			"              \"lte\": "+lte+"\r\n" + 
            			"            }\r\n" + 
            			"          }\r\n" + 
            			"        },\r\n" + 
            			"        {\r\n" + 
            			"          \"query_string\": {\r\n" + 
            			"            \"analyze_wildcard\": true,\r\n" + 
            			"            \"query\": \"*\",\r\n" + 
            			"            \"fields\": [\""+filter+"\"]\r\n" + 
            			"          }\r\n" + 
            			"        }\r\n" + 
            			"      ],\r\n" + 
            			"      \"filter\": {\r\n" + 
            			"        \"terms\": {\r\n" + 
            			"          \""+filter+"\": [\r\n" + 
            			"            \""+checkfilterFor+"\"\r\n" + 
            			"          ]\r\n" + 
            			"        }\r\n" + 
            			"      }\r\n" + 
            			"    }\r\n" + 
            			"  },\r\n" + 
            			"  \"collapse\" : {\r\n" + 
            			"          \"field\" : \""+filterMetrics+"\"\r\n" + 
            			"  },\r\n" + 
            			"  \"from\": "+from+",\r\n" + 
            			"  \"size\": "+size+",\r\n" + 
            			"  \"sort\": [\r\n" + 
            			"    {\r\n" + 
            			"      \""+filterMetrics+"\": {\r\n" + 
            			"        \"order\": \"asc\"\r\n" + 
            			"      }\r\n" + 
            			"    }\r\n" + 
            			"  ]\r\n" + 
            			"}";    
    		}else {
    			payload = "{\r\n" + 
            			"  \"query\": {\r\n" + 
            			"    \"bool\": {\r\n" + 
            			"      \"must\": [\r\n" + 
            			"        {\r\n" + 
            			"          \"terms\": {\r\n" + 
            			"            \"AIRID\": ["+airid+"]\r\n" + 
            			"          }\r\n" + 
            			"        },\r\n" + 
            			"        {\r\n" + 
            			"          \"term\": {\r\n" + 
            			"            \"ID\": {\r\n" + 
            			"              \"value\": "+id+"\r\n" + 
            			"            }\r\n" + 
            			"          }\r\n" + 
            			"        },\r\n" + 
            			"        {\r\n" + 
            			"          \"range\": {\r\n" + 
            			"            \"@timestamp\": {\r\n" + 
            			"              \"gte\": "+gte+",\r\n" + 
            			"              \"lte\": "+lte+"\r\n" + 
            			"            }\r\n" + 
            			"          }\r\n" + 
            			"        },\r\n" + 
            			"        {\r\n" + 
            			"          \"query_string\": {\r\n" + 
            			"            \"analyze_wildcard\": true,\r\n" + 
            			"            \"query\": \""+searchQuery+"\",\r\n" + 
            			"            \"fields\": "+fieldNames+"\r\n" + 
            			"          }\r\n" + 
            			"        }\r\n" + 
            			"      ],\r\n" + 
            			"      \"filter\": {\r\n" + 
            			"        \"terms\": {\r\n" + 
            			"          \""+filter+"\": [\r\n" + 
            			"            \""+checkfilterFor+"\"\r\n" + 
            			"          ]\r\n" + 
            			"        }\r\n" + 
            			"      }\r\n" + 
            			"    }\r\n" + 
            			"  },\r\n" + 
            			"  \"collapse\" : {\r\n" + 
            			"          \"field\" : \""+filterMetrics+"\"\r\n" + 
            			"  },\r\n" + 
            			"  \"from\": "+from+",\r\n" + 
            			"  \"size\": "+size+",\r\n" + 
            			"  \"sort\": [\r\n" + 
            			"    {\r\n" + 
            			"      \""+filterMetrics+"\": {\r\n" + 
            			"        \"order\": \"asc\"\r\n" + 
            			"      }\r\n" + 
            			"    }\r\n" + 
            			"  ]\r\n" + 
            			"}";    
    		}
    	}else if("pagesReportPaginationChild".equalsIgnoreCase(param)) {
    		if(search.equalsIgnoreCase("*")) {
    			payload = "{\r\n" + 
             			"  \"query\": {\r\n" + 
             			"    \"bool\": {\r\n" + 
             			"      \"must\": [\r\n" + 
             			"        {\r\n" + 
             			"          \"terms\": {\r\n" + 
             			"            \"AIRID\": ["+airid+"]\r\n" + 
             			"          }\r\n" + 
             			"        },\r\n" + 
             			"        {\r\n" + 
             			"          \"term\": {\r\n" + 
             			"            \"ID\": {\r\n" + 
             			"              \"value\": "+id+"\r\n" + 
             			"            }\r\n" + 
             			"          }\r\n" + 
             			"        },\r\n" + 
             			"        {\r\n" + 
             			"          \"range\": {\r\n" + 
             			"            \"@timestamp\": {\r\n" + 
             			"              \"gte\": "+gte+",\r\n" + 
             			"              \"lte\": "+lte+"\r\n" + 
             			"            }\r\n" + 
             			"          }\r\n" + 
             			"        },\r\n" + 
             			"        {\r\n" + 
             			"          \"query_string\": {\r\n" + 
             			"            \"analyze_wildcard\": true,\r\n" + 
             			"            \"query\": \"*\",\r\n" + 
             			"            \"fields\": [\"PageURL.keyword\"]\r\n" + 
             			"          }\r\n" + 
             			"        }\r\n" + 
             			"      ],\r\n" + 
             			"      \"filter\": {\r\n" + 
             			"        \"terms\": {\r\n" + 
             			"          \"PageURL.keyword\": [\r\n" + 
             			"            \""+checkfilterFor+"\"\r\n" + 
             			"          ]\r\n" + 
             			"        }\r\n" + 
             			"      }\r\n" + 
             			"    }\r\n" + 
             			"  },\r\n" + 
             			"  \"collapse\" : {\r\n" + 
             			"          \"field\" : \""+filterMetrics+"\"\r\n" + 
             			"  },\r\n" + 
             			"  \"from\": "+from+",\r\n" + 
             			"  \"size\": "+size+",\r\n" + 
             			"  \"sort\": [\r\n" + 
             			"    {\r\n" + 
             			"      \""+filterMetrics+"\": {\r\n" + 
             			"        \"order\": \"asc\"\r\n" + 
             			"      }\r\n" + 
             			"    }\r\n" + 
             			"  ]\r\n" + 
             			"}";
    		}else {
    			payload = "{\r\n" + 
             			"  \"query\": {\r\n" + 
             			"    \"bool\": {\r\n" + 
             			"      \"must\": [\r\n" + 
             			"        {\r\n" + 
             			"          \"terms\": {\r\n" + 
             			"            \"AIRID\": ["+airid+"]\r\n" + 
             			"          }\r\n" + 
             			"        },\r\n" + 
             			"        {\r\n" + 
             			"          \"term\": {\r\n" + 
             			"            \"ID\": {\r\n" + 
             			"              \"value\": "+id+"\r\n" + 
             			"            }\r\n" + 
             			"          }\r\n" + 
             			"        },\r\n" + 
             			"        {\r\n" + 
             			"          \"range\": {\r\n" + 
             			"            \"@timestamp\": {\r\n" + 
             			"              \"gte\": "+gte+",\r\n" + 
             			"              \"lte\": "+lte+"\r\n" + 
             			"            }\r\n" + 
             			"          }\r\n" + 
             			"        },\r\n" + 
             			"        {\r\n" + 
             			"          \"query_string\": {\r\n" + 
             			"            \"analyze_wildcard\": true,\r\n" + 
             			"            \"query\": \""+searchQuery+"\",\r\n" + 
             			"            \"fields\": [\""+filter+"\"]\r\n" + 
             			"          }\r\n" + 
             			"        }\r\n" + 
             			"      ],\r\n" + 
             			"      \"filter\": {\r\n" + 
             			"        \"terms\": {\r\n" + 
             			"          \"PageURL.keyword\": [\r\n" + 
             			"            \""+checkfilterFor+"\"\r\n" + 
             			"          ]\r\n" + 
             			"        }\r\n" + 
             			"      }\r\n" + 
             			"    }\r\n" + 
             			"  },\r\n" + 
             			"  \"collapse\" : {\r\n" + 
             			"          \"field\" : \""+filterMetrics+"\"\r\n" + 
             			"  },\r\n" + 
             			"  \"from\": "+from+",\r\n" + 
             			"  \"size\": "+size+",\r\n" + 
             			"  \"sort\": [\r\n" + 
             			"    {\r\n" + 
             			"      \""+filterMetrics+"\": {\r\n" + 
             			"        \"order\": \"asc\"\r\n" + 
             			"      }\r\n" + 
             			"    }\r\n" + 
             			"  ]\r\n" + 
             			"}";
    		}
    		 
    	}
    	
    	   
        return payload;
    }
    public String getCustomReportFilterPagination(String param, String airid, String id, String gte, String lte, String from, String size, String search, String filter, String resultfilter) {
  	
    	String payload = null;
    	String convertSearch = kusinaStringUtils.convertSearchToELKQueryParam(search, filter);
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultfilter);
    	String fieldNames = kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics("usageParent"));
    	
    	if("usageReportPaginationFilter".equalsIgnoreCase(param)) {	
            		payload = "{\r\n" + 
            	        		"  \"query\": {\r\n" + 
            	        		"    \"bool\": {\r\n" + 
            	        		"      \"must\": [\r\n" + 
            	        		"        {\r\n" + 
            	        		"          \"terms\": {\r\n" + 
            	        		"            \"AIRID\": ["+airid+"]\r\n" + 
            	        		"          }\r\n" + 
            	        		"        },\r\n" + 
            	        		"        {\r\n" + 
            	        		"          \"term\": {\r\n" + 
            	        		"            \"ID\": {\r\n" + 
            	        		"              \"value\": "+id+"\r\n" + 
            	        		"            }\r\n" + 
            	        		"          }\r\n" + 
            	        		"        },\r\n" + 
            	        		"        {\r\n" + 
            	        		"          \"range\": {\r\n" + 
            	        		"            \"@timestamp\": {\r\n" + 
            	        		"              \"gte\": "+gte+",\r\n" + 
            	        		"              \"lte\": "+lte+"\r\n" + 
            	        		"            }\r\n" + 
            	        		"          }\r\n" + 
            	        		"        },\r\n" + 
            	        		"        {\r\n" + 
            	        		"          \"query_string\": {\r\n" + 
            	        		"            \"analyze_wildcard\": true,\r\n" + 
            	        		"            \"query\": \""+queryNames+"\",\r\n" + 
            	        		"            \"fields\": "+fieldNames+"\r\n" + 
            	        		"          }\r\n" + 
            	        		"        }\r\n" + 
            	        		"      ]\r\n" + 
            	        		"    }\r\n" + 
            	        		"  },\r\n" + 
            	        		"  \"aggs\": {\r\n" + 
            	        		"    \"1\": {\r\n" + 
            	        		"      \"terms\": {\r\n" + 
            	        		"        \"field\": \""+filter+"\",\r\n" +
            	        		"        \"order\": {\r\n" +
            	        	    "          \"_term\": \"asc\"\r\n" +
            	        	    "         },\r\n" +
            	        	    "         \"size\":"+size+"\r\n" +
            	        		"      },\r\n" + 
            	        		"      \"aggs\": {\r\n" + 
            	        		"        \"2\": {\r\n" + 
            	        		"          \"terms\": {\r\n" + 
            	        		"            \"field\": \"Visits\"\r\n" + 
            	        		"          },\r\n" + 
            	        		"          \"aggs\": {\r\n" + 
            	        		"            \"3\": {\r\n" + 
            	        		"              \"avg\": {\r\n" + 
            	        		"                \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
            	        		"              }\r\n" + 
            	        		"            },\r\n" + 
            	        		"            \"4\": {\r\n" + 
            	        		"              \"cardinality\": {\r\n" + 
            	        		"                \"field\": \"PageTitle\"\r\n" + 
            	        		"              }\r\n" + 
            	        		"            },\r\n" + 
            	        		"            \"5\":{\r\n" + 
            	        		"             \"cardinality\": {\r\n" + 
            	        		"               \"field\": \"Visits.hash\"\r\n" + 
            	        		"             }\r\n" + 
            	        		"            },\r\n" + 
            	        		"            \"6\":{\r\n" + 
            	        		"            \"cardinality\": {\r\n" + 
            	        		"              \"field\": \"Hits.hash\"\r\n" + 
            	        		"            }\r\n" + 
            	        		"          }\r\n" + 
            	        		"          }\r\n" + 
            	        		"        },\r\n" + 
            	        		"        \"avg_page_duration\":{\r\n" + 
            	        		"          \"avg_bucket\": {\r\n" + 
            	        		"            \"buckets_path\": \"2>3\"\r\n" + 
            	        		"          }\r\n" + 
            	        		"        },\r\n" + 
            	        		"        \"avg_page_visits\":{\r\n" + 
            	        		"          \"avg_bucket\": {\r\n" + 
            	        		"            \"buckets_path\": \"2>4\"\r\n" + 
            	        		"          }\r\n" + 
            	        		"        },\r\n" + 
            	        		"        \"sum_visits\":{\r\n" + 
            	        		"          \"sum_bucket\": {\r\n" + 
            	        		"            \"buckets_path\": \"2>5\"\r\n" + 
            	        		"          }\r\n" + 
            	        		"        },\r\n" + 
            	        		"        \"sum_hits\":{\r\n" + 
            	        		"          \"sum_bucket\": {\r\n" + 
            	        		"            \"buckets_path\": \"2>6\"\r\n" + 
            	        		"          }\r\n" + 
            	        		"        }\r\n" + 
            	        		"      }\r\n" + 
            	        		"    }\r\n" + 
            	        		"  }\r\n" + 
            	        		"}";
    	} else if("pagesReportPaginationFilter".equalsIgnoreCase(param)) {
        			payload="{\r\n" + 
            				"   \"query\": {\r\n" + 
            				"    \"bool\": {\r\n" + 
            				"      \"must\": [\r\n" + 
            				"        {\r\n" + 
            				"          \"terms\": {\r\n" + 
            				"            \"AIRID\": [\""+airid+"\"]\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        {\r\n" + 
            				"          \"term\": {\r\n" + 
            				"            \"ID\": {\r\n" + 
            				"              \"value\": \""+id+"\"\r\n" + 
            				"            }\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        {\r\n" + 
            				"          \"range\": {\r\n" + 
            				"            \"@timestamp\": {\r\n" + 
            				"              \"gte\": "+gte+",\r\n" + 
            				"              \"lte\": "+lte+"\r\n" + 
            				"            }\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        {\r\n" + 
            				"          \"query_string\": {\r\n" + 
            				"            \"analyze_wildcard\": true,\r\n" + 
            				"            \"query\": \""+queryNames+"\",\r\n" + 
            				"            \"fields\": [\"PageURL.keyword\"]\r\n" + 
            				"          }\r\n" + 
            				"        }\r\n" + 
            				"      ]\r\n" + 
            				"  }\r\n" + 
            				"  },\r\n" + 
            				"  \"aggs\": {\r\n" + 
            				"    \"1\": {\r\n" + 
            				"      \"terms\": {\r\n" + 
            				"        \"field\": \"PageURL.keyword\",\r\n" + 
            				"        \"size\": "+size+",\r\n" + 
            				"        \"order\": {\r\n" + 
            				"          \"_term\": \"asc\"\r\n" + 
            				"        }\r\n" + 
            				"      },\r\n" + 
            				"      \"aggs\": {\r\n" + 
            				"        \"2\": {\r\n" + 
            				"          \"terms\": {\r\n" + 
            				"            \"field\": \""+filter+"\"\r\n" + 
            				"          },\r\n" + 
            				"          \"aggs\": {\r\n" + 
            				"            \"3\":{\r\n" + 
            				"            \"terms\": {\r\n" + 
            				"              \"field\": \"Visits\"\r\n" + 
            				"            },\r\n" + 
            				"            \"aggs\": {\r\n" + 
            				"              \"4\": {\r\n" + 
            				"                \"avg\": {\r\n" + 
            				"                  \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
            				"                }\r\n" + 
            				"              },\r\n" + 
            				"              \"5\" : {\r\n" + 
            				"                \"cardinality\": {\r\n" + 
            				"                  \"field\": \"PageTitle\"\r\n" + 
            				"                }\r\n" + 
            				"              },\r\n" + 
            				"              \"6\" : {\r\n" + 
            				"                \"cardinality\": {\r\n" + 
            				"                  \"field\": \"Visits.hash\"\r\n" + 
            				"                }\r\n" + 
            				"              },\r\n" + 
            				"              \"7\" : {\r\n" + 
            				"                \"cardinality\": {\r\n" + 
            				"                  \"field\": \"Hits.hash\"\r\n" + 
            				"                }\r\n" + 
            				"              }\r\n" + 
            				"              \r\n" + 
            				"            }\r\n" + 
            				"          },\r\n" + 
            				"          \"sum_visits\": {  \r\n" + 
            				"             \"sum_bucket\": {  \r\n" + 
            				"               \"buckets_path\": \"3>6\"  \r\n" + 
            				"             }  \r\n" + 
            				"           },  \r\n" + 
            				"           \"sum_hits\":{  \r\n" + 
            				"             \"sum_bucket\": {  \r\n" + 
            				"               \"buckets_path\": \"3>7\"  \r\n" + 
            				"             }  \r\n" + 
            				"           },  \r\n" + 
            				"           \"avg_page_visits\" : {  \r\n" + 
            				"             \"avg_bucket\": {  \r\n" + 
            				"               \"buckets_path\": \"3>5\"  \r\n" + 
            				"             }  \r\n" + 
            				"           },  \r\n" + 
            				"           \"avg_page_duration\" : {  \r\n" + 
            				"             \"avg_bucket\": {  \r\n" + 
            				"               \"buckets_path\": \"3>4\"  \r\n" + 
            				"             }  \r\n" + 
            				"           }\r\n" + 
            				"        }\r\n" + 
            				"        },\r\n" + 
            				"        \"total_sum_visits\":{\r\n" + 
            				"          \"sum_bucket\": {\r\n" + 
            				"            \"buckets_path\": \"2>sum_visits\"\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        \"total_sum_hits\":{\r\n" + 
            				"          \"sum_bucket\": {\r\n" + 
            				"            \"buckets_path\": \"2>sum_hits\"\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        \"total_avg_page_visits\":{\r\n" + 
            				"          \"avg_bucket\": {\r\n" + 
            				"            \"buckets_path\": \"2>avg_page_visits\"\r\n" + 
            				"          }\r\n" + 
            				"        },\r\n" + 
            				"        \"total_avg_page_duration\":{\r\n" + 
            				"          \"avg_bucket\": {\r\n" + 
            				"            \"buckets_path\": \"2>avg_page_duration\"\r\n" + 
            				"          }\r\n" + 
            				"        }\r\n" + 
            				"      }\r\n" + 
            				"    }\r\n" + 
            				"  }\r\n" + 
            				"}";

    	}
    	return payload;
    }
    
    
    public String getCustomReportChildFilterPagination(String param, String airid, String id, String gte, String lte, String from, String size, String search, String filterMetrics, String filter, String resultfilter, String filterFor) {
    	String payload = null;
    	
    	System.out.println("THIS IS SEARCH TO MANIPULATE: "+search);
    	String convertSearch = kusinaStringUtils.convertSearchToELKQueryParam(search, filter);
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultfilter);
    	String fieldNames = kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics("usageChild"));
    	if("usageChildPaginationFilter".equalsIgnoreCase(param)) {
		       		 payload = "{\r\n" + 
		       		 		"  \"query\": {\r\n" + 
		       		 		"    \"bool\": {\r\n" + 
		       		 		"      \"must\": [\r\n" + 
		       		 		"        {\r\n" + 
		       		 		"          \"terms\": {\r\n" + 
		       		 		"            \"AIRID\": [\""+airid+"\"]\r\n" + 
		       		 		"          }\r\n" + 
		       		 		"        },\r\n" + 
		       		 		"        {\r\n" + 
		       		 		"          \"term\": {\r\n" + 
		       		 		"            \"ID\": {\r\n" + 
		       		 		"              \"value\": \""+id+"\"\r\n" + 
		       		 		"            }\r\n" + 
		       		 		"          }\r\n" + 
		       		 		"        },\r\n" + 
		       		 		"        {\r\n" + 
		       		 		"          \"range\": {\r\n" + 
		       		 		"            \"@timestamp\": {\r\n" + 
		       		 		"              \"gte\": "+gte+",\r\n" + 
		       		 		"              \"lte\": "+lte+"\r\n" + 
		       		 		"            }\r\n" + 
		       		 		"          }\r\n" + 
		       		 		"        },\r\n" + 
		       		 		"        {\r\n" + 
		       		 		"          \"query_string\": {\r\n" + 
		       		 		"            \"analyze_wildcard\": true,\r\n" + 
		       		 		"            \"query\": \""+queryNames+"\",\r\n" + 
		       		 		"            \"fields\": "+fieldNames+"\r\n" + 
		       		 		"          }\r\n" + 
		       		 		"        }\r\n" + 
		       		 		"      ]\r\n" + 
		       		 		"  }\r\n" + 
		       		 		"  },\r\n" + 
		       		 		"  \"aggs\" : {\r\n" + 
		       		 		"      \"1\" : {\r\n" + 
		       		 		"        \"terms\": {\r\n" + 
		       		 		"          \"field\": \""+filterMetrics+"\",\r\n" +
		       		 		"        \"order\": {\r\n" +
		   	        	    "          \"_term\": \"asc\"\r\n" +
		   	        	    "         },\r\n" +
		   	        	    "         \"size\":"+size+"\r\n" +
		       		 		"        },\r\n" + 
		       		 		"        \"aggs\": {\r\n" + 
		       		 		"          \"2\":{\r\n" + 
		       		 		"            \"terms\": {\r\n" + 
		       		 		"              \"field\": \"Visits\"\r\n" + 
		       		 		"            },\r\n" + 
		       		 		"            \"aggs\": {\r\n" + 
		       		 		"              \"3\": {\r\n" + 
		       		 		"                \"avg\": {\r\n" + 
		       		 		"                  \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
		       		 		"                }\r\n" + 
		       		 		"              },\r\n" + 
		       		 		"              \"4\" : {\r\n" + 
		       		 		"                \"cardinality\": {\r\n" + 
		       		 		"                  \"field\": \"PageTitle\"\r\n" + 
		       		 		"                }\r\n" + 
		       		 		"              },\r\n" + 
		       		 		"              \"5\" : {\r\n" + 
		       		 		"                \"cardinality\": {\r\n" + 
		       		 		"                  \"field\": \"Visits.hash\"\r\n" + 
		       		 		"                }\r\n" + 
		       		 		"              },\r\n" + 
		       		 		"              \"6\" : {\r\n" + 
		       		 		"                \"cardinality\": {\r\n" + 
		       		 		"                  \"field\": \"Hits.hash\"\r\n" + 
		       		 		"                }\r\n" + 
		       		 		"              }\r\n" + 
		       		 		"            }\r\n" + 
		       		 		"          },\r\n" + 
		       		 		"          \"avg_page_duration\":{\r\n" + 
		       		 		"            \"avg_bucket\": {\r\n" + 
		       		 		"              \"buckets_path\": \"2>3\"\r\n" + 
		       		 		"            }\r\n" + 
		       		 		"          },\r\n" + 
		       		 		"          \"avg_page_visit\" : {\r\n" + 
		       		 		"            \"avg_bucket\": {\r\n" + 
		       		 		"              \"buckets_path\": \"2>4\"\r\n" + 
		       		 		"            }\r\n" + 
		       		 		"          },\r\n" + 
		       		 		"          \"sum_visits\":{\r\n" + 
		       		 		"            \"sum_bucket\": {\r\n" + 
		       		 		"              \"buckets_path\": \"2>5\"\r\n" + 
		       		 		"            }\r\n" + 
		       		 		"          },\r\n" + 
		       		 		"          \"sum_hits\": {\r\n" + 
		       		 		"            \"sum_bucket\": {\r\n" + 
		       		 		"              \"buckets_path\": \"2>6\"\r\n" + 
		       		 		"            }\r\n" + 
		       		 		"          }\r\n" + 
		       		 		"        }\r\n" + 
		       		 		"      }\r\n" + 
		       		 		"    }\r\n" + 
		       		 		"}";
    	} else if("pagesChildPaginationFilter".equalsIgnoreCase(param)){
    			payload = "{\r\n" + 
        				"  \"query\": {\r\n" + 
        				"    \"bool\": {\r\n" + 
        				"      \"must\": [\r\n" + 
        				"        {\r\n" + 
        				"          \"terms\": {\r\n" + 
        				"            \"AIRID\": [\""+airid+"\"]\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"term\": {\r\n" + 
        				"            \"ID\": {\r\n" + 
        				"              \"value\": \""+id+"\"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"range\": {\r\n" + 
        				"            \"@timestamp\": {\r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        },\r\n" + 
        				"        {\r\n" + 
        				"          \"query_string\": {\r\n" + 
        				"            \"analyze_wildcard\": true,\r\n" + 
        				"            \"query\": \""+queryNames+"\",\r\n" + 
        				"            \"fields\": [\""+filter+"\"]\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      ],\r\n" + 
        				"      \"filter\": {\r\n" + 
        				"        \"terms\": {\r\n" + 
        				"          \"PageURL.keyword\": [\r\n" + 
        				"            \""+filterFor+"\"\r\n" + 
        				"          ]\r\n" + 
        				"        }\r\n" + 
        				"      }\r\n" + 
        				"  }\r\n" + 
        				"  },\r\n" + 
        				"  \"aggs\" : {\r\n" + 
        				"      \"1\" : {\r\n" + 
        				"        \"terms\": {\r\n" + 
        				"          \"field\": \""+filter+"\",\r\n" + 
        				"       	 \"order\": {\r\n" +
       	        	    "          	\"_term\": \"asc\"\r\n" +
       	        	    "         },\r\n" +
       	        	    "         \"size\":"+size+"\r\n" +
        				"        },\r\n" + 
        				"        \"aggs\": {\r\n" + 
        				"          \"2\":{\r\n" + 
        				"            \"terms\": {\r\n" + 
        				"              \"field\": \"Visits\"\r\n" + 
        				"            },\r\n" + 
        				"            \"aggs\": {\r\n" + 
        				"              \"3\": {\r\n" + 
        				"                \"avg\": {\r\n" + 
        				"                  \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
        				"                }\r\n" + 
        				"              },\r\n" + 
        				"              \"4\" : {\r\n" + 
        				"                \"cardinality\": {\r\n" + 
        				"                  \"field\": \"PageTitle\"\r\n" + 
        				"                }\r\n" + 
        				"              },\r\n" + 
        				"              \"5\" : {\r\n" + 
        				"                \"cardinality\": {\r\n" + 
        				"                  \"field\": \"Visits.hash\"\r\n" + 
        				"                }\r\n" + 
        				"              },\r\n" + 
        				"              \"6\" : {\r\n" + 
        				"                \"cardinality\": {\r\n" + 
        				"                  \"field\": \"Hits.hash\"\r\n" + 
        				"                }\r\n" + 
        				"              }\r\n" + 
        				"            }\r\n" + 
        				"          },\r\n" + 
        				"          \"avg_page_duration\":{\r\n" + 
        				"            \"avg_bucket\": {\r\n" + 
        				"              \"buckets_path\": \"2>3\"\r\n" + 
        				"            }\r\n" + 
        				"          },\r\n" + 
        				"          \"avg_page_visit\" : {\r\n" + 
        				"            \"avg_bucket\": {\r\n" + 
        				"              \"buckets_path\": \"2>4\"\r\n" + 
        				"            }\r\n" + 
        				"          },\r\n" + 
        				"          \"sum_visits\":{\r\n" + 
        				"            \"sum_bucket\": {\r\n" + 
        				"              \"buckets_path\": \"2>5\"\r\n" + 
        				"            }\r\n" + 
        				"          },\r\n" + 
        				"          \"sum_hits\": {\r\n" + 
        				"            \"sum_bucket\": {\r\n" + 
        				"              \"buckets_path\": \"2>6\"\r\n" + 
        				"            }\r\n" + 
        				"          }\r\n" + 
        				"        }\r\n" + 
        				"      }\r\n" + 
        				"    }\r\n" + 
        				"}";
    	}    	
    	return payload;
    }
    
    public String getVisitorLogsFilter(String airid, String id, String gte, String lte, String from, String size) {

        String payload = "{\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"      \"must\": [\r\n" + 
        		"        {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"AIRID\": ["+airid+"]\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"term\": {\r\n" + 
        		"            \"ID\": {\r\n" + 
        		"              \"value\": "+id+"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"range\": {\r\n" + 
        		"            \"@timestamp\": {\r\n" + 
        		"              \"gte\": "+gte+",\r\n" + 
        		"              \"lte\": "+lte+"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"query_string\": {\r\n" + 
        		"            \"analyze_wildcard\": true,\r\n" + 
        		"            \"query\": \"*\"\r\n" +  
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      ]\r\n" + 
        		"    }\r\n" + 
        		"  },\r\n" + 
        		"  \"collapse\" : {\r\n" + 
        		"          \"field\" : \"Visits\"\r\n" + 
        		"  },\r\n" + 
        		"  \"from\": "+from+", \r\n" + 
        		"  \"size\": "+size+", \r\n" + 
        		"  \"sort\": [\r\n" + 
        		"    {\r\n" + 
        		"      \"LastActionTime\": {\r\n" + 
        		"        \"order\": \"desc\"\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"  ]\r\n" + 
        		"}";
        
        
        return payload;
    }
    
    public String getVisitorLogFilterPagination(String airid, String id, String gte, String lte, String from, String size, String search, String resultfilter, String reportType, String filter) {
    	String payload = null;
    	System.out.println("THIS IS SEARCH TO MANIPULATE: "+search);
    	String convertSearch = kusinaStringUtils.convertSearchtoLowerCase(search);
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultfilter);
    	String fieldNames = kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics("usageParent"));
    	if(search.equalsIgnoreCase("*")) {
    		payload = "{\n" + 
    				"  \"query\": {\n" + 
    				"    \"bool\": {\n" + 
    				"      \"must\": [\n" + 
    				"        {\n" + 
    				"          \"terms\": {\n" + 
    				"            \"AIRID\": ["+airid+"]\n" + 
    				"          }\n" + 
    				"        },\n" + 
    				"        {\n" + 
    				"          \"term\": {\n" + 
    				"            \"ID\": {\n" + 
    				"              \"value\": "+id+"\n" + 
    				"            }\n" + 
    				"          }\n" + 
    				"        },\n" + 
    				"        {\n" + 
    				"          \"range\": {\n" + 
    				"            \"@timestamp\": {\n" + 
    				"              \"gte\": "+gte+",\n" + 
    				"              \"lte\": "+lte+"\n" + 
    				"            }\n" + 
    				"          }\n" + 
    				"        },\n" + 
    				"        {\n" + 
    				"          \"query_string\": {\n" + 
    				"            \"analyze_wildcard\": true,\n" + 
    				"            \"query\": \""+queryNames+"\"\r\n" + 
    				"          }\n" + 
    				"        }\n" + 
    				"      ]\n" + 
    				"    }\n" + 
    				"  },\n" + 
    				"    \"aggs\": {\n" + 
    				"    \"1\": {\n" + 
    				"      \"terms\": {\n" + 
    				"        \"field\": \"Visits\",\n" + 
    				"        	 \"size\": "+size+",\n" + 
    				"        	 \"order\": {\n" + 
    				"          	 	\"_term\": \"desc\"\n" + 
    				"        	 }\n" + 
    				"      },\n" + 
    				"      \"aggs\": {\n" + 
    				"        \"2\": {\n" + 
    				"          \"terms\": {\n" + 
    				"            \"field\": \"User\"\n" +
    				"          },\n" + 
    				"          \"aggs\": {\n" + 
    				"            \"3\": {\n" + 
    				"              \"terms\": {\n" + 
    				"                \"field\": \"Address\"\n" + 
    				"              }\n" + 
    				"            },\n" + 
    				"            \"4\": {\n" + 
    				"              \"terms\": {\n" + 
    				"                \"field\": \"LastActionTime\"\n" + 
    				"              }\n" + 
    				"            },\n" + 
    				"            \"5\": {\n" + 
    				"              \"terms\": {\n" + 
    				"                \"field\": \"Country.keyword\"\n" + 
    				"              }\n" + 
    				"            }\n" + 
    				"          }\n" + 
    				"        }\n" + 
    				"      }\n" + 
    				"    }\n" + 
    				"  }\n" + 
    				"}";    		
    	}else {
    		payload = "{\n" + 
    				"  \"query\": {\n" + 
    				"    \"bool\": {\n" + 
    				"      \"must\": [\n" + 
    				"        {\n" + 
    				"          \"terms\": {\n" + 
    				"            \"AIRID\": ["+airid+"]\n" + 
    				"          }\n" + 
    				"        },\n" + 
    				"        {\n" + 
    				"          \"term\": {\n" + 
    				"            \"ID\": {\n" + 
    				"              \"value\": "+id+"\n" + 
    				"            }\n" + 
    				"          }\n" + 
    				"        },\n" + 
    				"        {\n" + 
    				"          \"range\": {\n" + 
    				"            \"@timestamp\": {\n" + 
    				"              \"gte\": "+gte+",\n" + 
    				"              \"lte\": "+lte+"\n" + 
    				"            }\n" + 
    				"          }\n" + 
    				"        },\n" + 
    				"        {\n" + 
    				"          \"query_string\": {\n" + 
    				"            \"analyze_wildcard\": true,\n" + 
    				"            \"query\": \""+convertSearch+"\"\r\n" + 
    				"          }\n" + 
    				"        }\n" + 
    				"      ]\n" + 
    				"    }\n" + 
    				"  },\n" + 
    				"    \"aggs\": {\n" + 
    				"    \"1\": {\n" + 
    				"      \"terms\": {\n" + 
    				"        \"field\": \"Visits\"\n" + 
    				"      },\n" + 
    				"      \"aggs\": {\n" + 
    				"        \"2\": {\n" + 
    				"          \"terms\": {\n" + 
    				"            \"field\": \"User\",\n" +
    				"        	 \"size\": "+size+",\n" + 
    				"        	 \"order\": {\n" + 
    				"          	 	\"_term\": \"desc\"\n" + 
    				"        	 }\n" + 
    				"          },\n" + 
    				"          \"aggs\": {\n" + 
    				"            \"3\": {\n" + 
    				"              \"terms\": {\n" + 
    				"                \"field\": \"Address\"\n" + 
    				"              }\n" + 
    				"            },\n" + 
    				"            \"4\": {\n" + 
    				"              \"terms\": {\n" + 
    				"                \"field\": \"LastActionTime\"\n" + 
    				"              }\n" + 
    				"            },\n" + 
    				"            \"5\": {\n" + 
    				"              \"terms\": {\n" + 
    				"                \"field\": \"Country.keyword\"\n" + 
    				"              }\n" + 
    				"            }\n" + 
    				"          }\n" + 
    				"        }\n" + 
    				"      }\n" + 
    				"    }\n" + 
    				"  }\n" + 
    				"}";  
    	}
    	
    	return payload;
    	
    }
    
    public String getVisitorLogsTotal(String airid, String id, String gte, String lte, String from, String size, String search) {
    	String reportTypeConvert = null;
  	
    	String payload="{\r\n" + 
    			"  \"query\": {\r\n" + 
    			"    \"bool\": {\r\n" + 
    			"      \"must\": [\r\n" + 
    			"        {\r\n" + 
    			"          \"terms\": {\r\n" + 
    			"            \"AIRID\": ["+airid+"]\r\n" + 
    			"          }\r\n" + 
    			"        },\r\n" + 
    			"        {\r\n" + 
    			"          \"term\": {\r\n" + 
    			"            \"ID\": {\r\n" + 
    			"              \"value\": "+id+"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        },\r\n" + 
    			"        {\r\n" + 
    			"          \"range\": {\r\n" + 
    			"            \"@timestamp\": {\r\n" + 
    			"              \"gte\": "+gte+",\r\n" + 
    			"              \"lte\": "+lte+"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        }\r\n" + 
    			"      ]\r\n" + 
    			"    }\r\n" + 
    			"  },\r\n" + 
    			"  \"aggs\": {\r\n" + 
    			"    \"totalcount\": {\r\n" + 
    			"      \"cardinality\": {\r\n" + 
    			"        \"field\": \"Visits\"\r\n" + 
    			"      }\r\n" + 
    			"    }\r\n" + 
    			"  }\r\n" + 
    			"}";
    	
    	return payload;
    }
    
    public String getVisitorLogsFilterChild(String airid, String id, String gte, String lte, String from, String size, String filterFor) {
    	String checkfilterFor =  kusinaStringUtils.convertFilterToElasticQueryNames(filterFor);
    	String payload = "{\n" + 
    			"  \"query\": {\n" + 
    			"    \"bool\": {\n" + 
    			"      \"must\": [\n" + 
    			"        {\n" + 
    			"          \"terms\": {\n" + 
    			"            \"AIRID\": ["+airid+"]\n" + 
    			"          }\n" + 
    			"        },\n" + 
    			"        {\n" + 
    			"          \"term\": {\n" + 
    			"            \"ID\": {\n" + 
    			"              \"value\": "+id+"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        },\n" + 
    			"        {\n" + 
    			"          \"range\": {\n" + 
    			"            \"@timestamp\": {\n" + 
    			"              \"gte\": "+gte+",\n" + 
    			"              \"lte\": "+lte+"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        },\n" + 
    			"        {\n" + 
    			"          \"query_string\": {\n" + 
    			"            \"analyze_wildcard\": true,\n" + 
    			"            \"query\": \"*\",\n" + 
    			"            \"fields\": [\"CleanPageURL.keyword\"]\n" + 
    			"          }\n" + 
    			"        }\n" + 
    			"      ],\n" + 
    			"      \"filter\": {\n" + 
    			"        \"terms\": {\n" + 
    			"          \"Visits\": [\n" + 
    			"            \""+filterFor+"\"\n" + 
    			"          ]\n" + 
    			"        }\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  },\n" + 
    			"  \"collapse\" : {\n" + 
    			"          \"field\" : \"CleanPageURL.keyword\"\n" + 
    			"  },\n" + 
    			"  \"from\": "+from+",\n" + 
    			"  \"size\": "+size+",\n" + 
    			"  \"sort\": [\n" + 
    			"    {\n" + 
    			"      \"@timestamp\": {\n" + 
    			"        \"order\": \"desc\"\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  ]\n" + 
    			"}";   
        
        return payload;
    }
    
    public String getVisitorLogReportChildPaginationFilter(String airid, String id, String gte, String lte, String from, String size, String search, String resultfilter, String filterFor) {
    	String payload = null;
    	
    	System.out.println("THIS IS SEARCH TO MANIPULATE: "+search);
    	String convertSearch = kusinaStringUtils.convertSearchtoLowerCase(search);
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultfilter);
    	String fieldNames = kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics("usageChild"));
    	if(search.equalsIgnoreCase("*")) {
    		 payload = "{\n" + 
    		 		"  \"query\": {\n" + 
    		 		"    \"bool\": {\n" + 
    		 		"      \"must\": [\n" + 
    		 		"        {\n" + 
    		 		"          \"terms\": {\n" + 
    		 		"            \"AIRID\": ["+airid+"]\n" + 
    		 		"          }\n" + 
    		 		"        },\n" + 
    		 		"        {\n" + 
    		 		"          \"term\": {\n" + 
    		 		"            \"ID\": {\n" + 
    		 		"              \"value\": "+id+"\n" + 
    		 		"            }\n" + 
    		 		"          }\n" + 
    		 		"        },\n" + 
    		 		"        {\n" + 
    		 		"          \"range\": {\n" + 
    		 		"            \"@timestamp\": {\n" + 
    		 		"              \"gte\": "+gte+",\n" + 
    		 		"              \"lte\": "+lte+"\n" + 
    		 		"            }\n" + 
    		 		"          }\n" + 
    		 		"        },\n" + 
    		 		"        {\n" + 
    		 		"          \"query_string\": {\n" + 
    		 		"            \"analyze_wildcard\": true,\n" + 
    		 		"            \"query\": \""+queryNames+"\"\n" + 
    		 		"          }\n" + 
    		 		"        }\n" + 
    		 		"      ],\n" +
    		 		"      \"filter\": {\n" + 
        			"        \"terms\": {\n" + 
        			"          \"Visits\": [\n" + 
        			"            \""+filterFor+"\"\n" + 
        			"          ]\n" + 
        			"        }\n" + 
        			"      }\n" + 
    		 		"    }\n" + 
    		 		"  },\n" + 
    		 		"    \"aggs\": {\n" + 
    		 		"    \"1\": {\n" + 
    		 		"      \"terms\": {\n" + 
    		 		"        \"field\": \"Visits\",\n" + 
    		 		"        \"size\": 50\n" + 
    		 		"      },\n" + 
    		 		"      \"aggs\": {\n" + 
    		 		"        \"2\": {\n" + 
    		 		"          \"terms\": {\n" + 
    		 		"            \"field\": \"CleanPageURL.keyword\",\n" + 
    		 		"                 \"order\": {\n" + 
    		 		"                        \"_term\": \"desc\"\n" + 
    		 		"                 }\n" + 
    		 		"          },\n" + 
    		 		"          \"aggs\": {\n" + 
    		 		"            \"3\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"PageTitle\"\n" + 
    		 		"              }\n" + 
    		 		"            },\n" + 
    		 		"            \"4\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"Browser.keyword\"\n" + 
    		 		"              }\n" + 
    		 		"            },\n" + 
    		 		"            \"5\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"OperatingSystem.keyword\"\n" + 
    		 		"              }\n" + 
    		 		"            },\n" + 
    		 		"            \"6\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"LastActionTime\"\n" + 
    		 		"              }\n" + 
    		 		"            }\n" + 
    		 		"          }\n" + 
    		 		"        }\n" + 
    		 		"      }\n" + 
    		 		"    }\n" + 
    		 		"  }\n" + 
    		 		"}";
    		
    	}else {
    		payload = "{\n" + 
    		 		"  \"query\": {\n" + 
    		 		"    \"bool\": {\n" + 
    		 		"      \"must\": [\n" + 
    		 		"        {\n" + 
    		 		"          \"terms\": {\n" + 
    		 		"            \"AIRID\": ["+airid+"]\n" + 
    		 		"          }\n" + 
    		 		"        },\n" + 
    		 		"        {\n" + 
    		 		"          \"term\": {\n" + 
    		 		"            \"ID\": {\n" + 
    		 		"              \"value\": "+id+"\n" + 
    		 		"            }\n" + 
    		 		"          }\n" + 
    		 		"        },\n" + 
    		 		"        {\n" + 
    		 		"          \"range\": {\n" + 
    		 		"            \"@timestamp\": {\n" + 
    		 		"              \"gte\": "+gte+",\n" + 
    		 		"              \"lte\": "+lte+"\n" + 
    		 		"            }\n" + 
    		 		"          }\n" + 
    		 		"        },\n" + 
    		 		"        {\n" + 
    		 		"          \"query_string\": {\n" + 
    		 		"            \"analyze_wildcard\": true,\n" + 
    		 		"            \"query\": \""+convertSearch+"\"\n" + 
    		 		"          }\n" + 
    		 		"        }\n" + 
    		 		"      ]\n" + 
    		 		"    }\n" + 
    		 		"  },\n" + 
    		 		"    \"aggs\": {\n" + 
    		 		"    \"1\": {\n" + 
    		 		"      \"terms\": {\n" + 
    		 		"        \"field\": \"Visits\",\n" + 
    		 		"        \"size\": 50\n" + 
    		 		"      },\n" + 
    		 		"      \"aggs\": {\n" + 
    		 		"        \"2\": {\n" + 
    		 		"          \"terms\": {\n" + 
    		 		"            \"field\": \"CleanPageURL.keyword\",\n" + 
    		 		"                 \"order\": {\n" + 
    		 		"                        \"_term\": \"desc\"\n" + 
    		 		"                 }\n" + 
    		 		"          },\n" + 
    		 		"          \"aggs\": {\n" + 
    		 		"            \"3\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"PageTitle\"\n" + 
    		 		"              }\n" + 
    		 		"            },\n" + 
    		 		"            \"4\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"Browser.keyword\"\n" + 
    		 		"              }\n" + 
    		 		"            },\n" + 
    		 		"            \"5\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"OperatingSystem.keyword\"\n" + 
    		 		"              }\n" + 
    		 		"            },\n" + 
    		 		"            \"6\": {\n" + 
    		 		"              \"terms\": {\n" + 
    		 		"                \"field\": \"LastActionTime\"\n" + 
    		 		"              }\n" + 
    		 		"            }\n" + 
    		 		"          }\n" + 
    		 		"        }\n" + 
    		 		"      }\n" + 
    		 		"    }\n" + 
    		 		"  }\n" + 
    		 		"}";
    	}
    	
    	return payload;
    	
    }
    
    public String getVisitorLogsTotalChild(String airid, String id, String gte, String lte, String from, String size, String search, String filterFor) {
    	String reportTypeConvert = null;
  	
    	String payload= "{\n" + 
    			"  \"query\": {\n" + 
    			"    \"bool\": {\n" + 
    			"      \"must\": [\n" + 
    			"        {\n" + 
    			"          \"terms\": {\n" + 
    			"            \"AIRID\": ["+airid+"]\n" + 
    			"          }\n" + 
    			"        },\n" + 
    			"        {\n" + 
    			"          \"term\": {\n" + 
    			"            \"ID\": {\n" + 
    			"              \"value\": "+id+"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        },\n" + 
    			"        {\n" + 
    			"          \"range\": {\n" + 
    			"            \"@timestamp\": {\n" + 
    			"              \"gte\": "+gte+",\n" + 
    			"              \"lte\": "+lte+"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        }\n" + 
    			"      ],\n" + 
    			"      \"filter\": {\n" + 
    			"        \"terms\": {\n" + 
    			"          \"Visits\": [\n" + 
    			"            \""+filterFor+"\"\n" + 
    			"          ]\n" + 
    			"        }\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  },\n" + 
    			"  \"aggs\": {\n" + 
    			"    \"1\": {\n" + 
    			"      \"terms\": {\n" + 
    			"        \"field\": \"Visits\"\n" + 
    			"      },\n" + 
    			"      \"aggs\": {\n" + 
    			"        \"totalcount\": {\n" + 
    			"          \"cardinality\": {\n" + 
    			"            \"field\": \"CleanPageURL.keyword\"\n" + 
    			"          }\n" + 
    			"        }\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  }\n" + 
    			"}";
    	
    	return payload;
    }
    
    public String getMyTEFilter(String airid, String id, String gte, String lte, String from, String size) {

        String payload = "{\r\n" + 
        		"  \"query\": {\r\n" + 
        		"    \"bool\": {\r\n" + 
        		"      \"must\": [\r\n" + 
        		"        {\r\n" + 
        		"          \"terms\": {\r\n" + 
        		"            \"AIRID\": ["+airid+"]\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"term\": {\r\n" + 
        		"            \"ID\": {\r\n" + 
        		"              \"value\": "+id+"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"range\": {\r\n" + 
        		"            \"@timestamp\": {\r\n" + 
        		"              \"gte\": "+gte+",\r\n" + 
        		"              \"lte\": "+lte+"\r\n" + 
        		"            }\r\n" + 
        		"          }\r\n" + 
        		"        },\r\n" + 
        		"        {\r\n" + 
        		"          \"query_string\": {\r\n" + 
        		"            \"analyze_wildcard\": true,\r\n" + 
        		"            \"query\": \"*\"\r\n" + 
        		"          }\r\n" + 
        		"        }\r\n" + 
        		"      ]\r\n" + 
        		"    }\r\n" + 
        		"  },\r\n" + 
        		"  \"collapse\": {\r\n" + 
        		"    \"field\": \"Hits\"\r\n" + 
        		"  },\r\n" + 
        		"  \"from\": "+from+",\r\n" + 
        		"  \"size\": "+size+",\r\n" + 
        		"  \"sort\": [\r\n" + 
        		"    {\r\n" + 
        		"      \"Hits\": {\r\n" + 
        		"        \"order\": \"asc\"\r\n" + 
        		"      }\r\n" + 
        		"    }\r\n" + 
        		"  ]\r\n" + 
        		"} ";
        
        
        return payload;
    }
    
    public String getMyTEFilterPagination(String airid, String id, String gte, String lte, String from, String size, String search, String resultfilter, String reportType, String filter) {
    	String payload = null;
    	System.out.println("THIS IS SEARCH TO MANIPULATE: "+search);
    	String convertSearch = kusinaStringUtils.convertSearchtoLowerCase(search);
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultfilter);
    	String fieldNames = kusinaStringUtils.convertStringToElasticFieldQuery(kusinaValidationUtils.getFieldNameMetrics("usageParent"));
    	if(search.equalsIgnoreCase("*")) {
    		payload = "{\r\n" + 
    				"  \"query\": {\r\n" + 
    				"    \"bool\": {\r\n" + 
    				"      \"must\": [\r\n" + 
    				"        {\r\n" + 
    				"          \"terms\": {\r\n" + 
    				"            \"AIRID\": ["+airid+"]\r\n" + 
    				"          }\r\n" + 
    				"        },\r\n" + 
    				"        {\r\n" + 
    				"          \"term\": {\r\n" + 
    				"            \"ID\": {\r\n" + 
    				"              \"value\": "+id+"\r\n" + 
    				"            }\r\n" + 
    				"          }\r\n" + 
    				"        },\r\n" + 
    				"        {\r\n" + 
    				"          \"range\": {\r\n" + 
    				"            \"@timestamp\": {\r\n" + 
    				"              \"gte\": "+gte+",\r\n" + 
    				"              \"lte\": "+lte+"\r\n" + 
    				"            }\r\n" + 
    				"          }\r\n" + 
    				"        },\r\n" + 
    				"        {\r\n" + 
    				"          \"query_string\": {\r\n" + 
    				"            \"analyze_wildcard\": true,\r\n" + 
    				"            \"query\": \""+queryNames+"\"\r\n" +  
    				"          }\r\n" + 
    				"        }\r\n" + 
    				"      ]\r\n" + 
    				"    }\r\n" + 
    				"  },\r\n" + 
    				"    \"aggs\": {\r\n" + 
    				"    \"1\": {\r\n" + 
    				"      \"terms\": {\r\n" + 
    				"        \"field\": \"Hits\",\r\n" + 
    				"        \"size\": "+size+"\r\n" + 
    				"      },\r\n" + 
    				"      \"aggs\": {\r\n" + 
    				"        \"2\": {\r\n" + 
    				"          \"terms\": {\r\n" + 
    				"            \"field\": \"PageURL.keyword\"\r\n" + 
    				"          },\r\n" + 
    				"          \"aggs\": {\r\n" + 
    				"            \"3\": {\r\n" + 
    				"              \"terms\": {\r\n" + 
    				"                \"field\": \"Country.keyword\"\r\n" + 
    				"              }\r\n" + 
    				"            },\r\n" + 
    				"            \"4\": {\r\n" + 
    				"              \"terms\": {\r\n" + 
    				"                \"field\": \"GenerationTime\"\r\n" + 
    				"              }\r\n" + 
    				"            }\r\n" + 
    				"          }\r\n" + 
    				"        }\r\n" + 
    				"        \r\n" + 
    				"      }\r\n" + 
    				"    }\r\n" + 
    				"  }\r\n" + 
    				"}\r\n";
    	}else {
    		payload = "{\r\n" + 
    				"  \"query\": {\r\n" + 
    				"    \"bool\": {\r\n" + 
    				"      \"must\": [\r\n" + 
    				"        {\r\n" + 
    				"          \"terms\": {\r\n" + 
    				"            \"AIRID\": ["+airid+"]\r\n" + 
    				"          }\r\n" + 
    				"        },\r\n" + 
    				"        {\r\n" + 
    				"          \"term\": {\r\n" + 
    				"            \"ID\": {\r\n" + 
    				"              \"value\": "+id+"\r\n" + 
    				"            }\r\n" + 
    				"          }\r\n" + 
    				"        },\r\n" + 
    				"        {\r\n" + 
    				"          \"range\": {\r\n" + 
    				"            \"@timestamp\": {\r\n" + 
    				"              \"gte\": "+gte+",\r\n" + 
    				"              \"lte\": "+lte+"\r\n" + 
    				"            }\r\n" + 
    				"          }\r\n" + 
    				"        },\r\n" + 
    				"        {\r\n" + 
    				"          \"query_string\": {\r\n" + 
    				"            \"analyze_wildcard\": true,\r\n" + 
    				"            \"query\": \""+convertSearch+"\"\r\n" + 
    				"          }\r\n" + 
    				"        }\r\n" + 
    				"      ]\r\n" + 
    				"    }\r\n" + 
    				"  },\r\n" + 
    				"    \"aggs\": {\r\n" + 
    				"    \"1\": {\r\n" + 
    				"      \"terms\": {\r\n" + 
    				"        \"field\": \"LastActionTime\",\r\n" + 
    				"        \"size\": "+size+"\r\n" + 
    				"      },\r\n" + 
    				"      \"aggs\": {\r\n" + 
    				"        \"2\": {\r\n" + 
    				"          \"terms\": {\r\n" + 
    				"            \"field\": \"PageURL.keyword\"\r\n" + 
    				"          },\r\n" + 
    				"          \"aggs\": {\r\n" + 
    				"            \"3\": {\r\n" + 
    				"              \"terms\": {\r\n" + 
    				"                \"field\": \"Country.keyword\"\r\n" + 
    				"              }\r\n" + 
    				"            },\r\n" + 
    				"            \"4\": {\r\n" + 
    				"              \"terms\": {\r\n" + 
    				"                \"field\": \"GenerationTime\"\r\n" + 
    				"              }\r\n" + 
    				"            }\r\n" + 
    				"          }\r\n" + 
    				"        }\r\n" + 
    				"        \r\n" + 
    				"      }\r\n" + 
    				"    }\r\n" + 
    				"  }\r\n" + 
    				"}\r\n";
    	}
    	
    	return payload;
    	
    }
    
    public String getMyTEReportTotal(String airid, String id, String gte, String lte, String from, String size, String search) {
    	String reportTypeConvert = null;
  	
    	String payload="{\r\n" + 
    			"  \"query\": {\r\n" + 
    			"    \"bool\": {\r\n" + 
    			"      \"must\": [\r\n" + 
    			"        {\r\n" + 
    			"          \"terms\": {\r\n" + 
    			"            \"AIRID\": ["+airid+"]\r\n" + 
    			"          }\r\n" + 
    			"        },\r\n" + 
    			"        {\r\n" + 
    			"          \"term\": {\r\n" + 
    			"            \"ID\": {\r\n" + 
    			"              \"value\": "+id+"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        },\r\n" + 
    			"        {\r\n" + 
    			"          \"range\": {\r\n" + 
    			"            \"@timestamp\": {\r\n" + 
    			"              \"gte\": "+gte+",\r\n" + 
    			"              \"lte\": "+lte+"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        }\r\n" + 
    			"      ]\r\n" + 
    			"    }\r\n" + 
    			"  },\r\n" + 
    			"  \"aggs\": {\r\n" + 
    			"    \"totalcount\": {\r\n" + 
    			"      \"cardinality\": {\r\n" + 
    			"        \"field\": \"LastActionTime\"\r\n" + 
    			"      }\r\n" + 
    			"    }\r\n" + 
    			"  }\r\n" + 
    			"}";
    	
    	return payload;
    }
    
    //******************* Custom Reports for pageCustomInfo
    public String getPageCustomReportAIBSPBVI(String airid, String id, String gte, String lte, String from , String size, String search, String reportType) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.searchQuery(reportType, search);
        	if(search.equalsIgnoreCase("*")){
       		 payload = "{ \r\n" + 
               		"  \"from\": "+from+",  \r\n" + 
               		"  \"size\": "+size+", \r\n" + 
               		"  \"sort\": [ \r\n" + 
               		"      { \r\n" + 
               		"        \"CleanPageURL.keyword\": { \r\n" + 
               		"          \"order\": \"asc\" \r\n" + 
               		"        } \r\n" + 
               		"      } \r\n" + 
               		"    ], \r\n" + 
               		"    \"query\": { \r\n" + 
               		"      \"bool\": { \r\n" + 
               		"        \"must\": [ \r\n" + 
               		"          {\r\n" + 
               		"          \"terms\": {\r\n" + 
               		"            \"AIRID\": ["+airid+"]\r\n" + 
               		"          }\r\n" + 
               		"        },\r\n" + 
               		"        {\r\n" + 
               		"          \"term\": {\r\n" + 
               		"            \"ID\": {\r\n" + 
               		"              \"value\": "+id+"\r\n" + 
               		"            }\r\n" + 
               		"          }\r\n" + 
               		"        },\r\n" + 
               		"         {\r\n" + 
               		"          \"query_string\": {\r\n" + 
               		"            \"analyze_wildcard\": true,\r\n" + 
               		"            \"query\": \"*\",\r\n" + 
               		"            \"fields\": [\"CleanPageURL.keyword\"]\r\n" + 
               		"          }\r\n" + 
               		"        }, \r\n" + 
               		"          { \r\n" + 
               		"            \"range\": { \r\n" + 
               		"              \"@timestamp\": { \r\n" + 
               		"                 \"gte\": "+gte+", \r\n" + 
               		"                 \"lte\": "+lte+", \r\n" + 
               		"                \"format\": \"epoch_millis\" \r\n" + 
               		"              } \r\n" + 
               		"            } \r\n" + 
               		"          } \r\n" + 
               		"        ], \r\n" + 
               		"        \"must_not\": [] \r\n" + 
               		"      } \r\n" + 
               		"    }, \r\n" + 
               		"    \"_source\": { \r\n" + 
               		"      \"includes\": "+getReportColumns()+"\r\n" + 
               		"    } \r\n" + 
               		"}";
       	} else {
    		payload = "{ \r\n" + 
    				" \"size\": "+size+",\r\n" +
            		"  \"from\": "+from+",  \r\n" +  
            		"  \"sort\": [ \r\n" + 
            		"      { \r\n" + 
            		"        \"CleanPageURL.keyword\": { \r\n" + 
            		"          \"order\": \"asc\" \r\n" + 
            		"        } \r\n" + 
            		"      } \r\n" + 
            		"    ], \r\n" + 
            		"    \"query\": { \r\n" + 
            		"      \"bool\": { \r\n" + 
            		"        \"must\": [ \r\n" + 
            		"          {\r\n" + 
            		"          \"terms\": {\r\n" + 
            		"            \"AIRID\": ["+airid+"]\r\n" + 
            		"          }\r\n" + 
            		"        },\r\n" + 
            		"        {\r\n" + 
            		"          \"term\": {\r\n" + 
            		"            \"ID\": {\r\n" + 
            		"              \"value\": "+id+"\r\n" + 
            		"            }\r\n" + 
            		"          }\r\n" + 
            		"        },\r\n" + 
            		"         {\r\n" + 
            		"          \"query_string\": {\r\n" + 
            		"            \"analyze_wildcard\": true,\r\n" + 
            		"            \"query\": \""+searchQuery+"\",\r\n" + 
            		"            \"fields\": "+getReportColumns()+"\r\n" + 
            		"          }\r\n" + 
            		"        }, \r\n" + 
            		"          { \r\n" + 
            		"            \"range\": { \r\n" + 
            		"              \"@timestamp\": { \r\n" + 
            		"                 \"gte\": "+gte+", \r\n" + 
            		"                 \"lte\": "+lte+", \r\n" + 
            		"                \"format\": \"epoch_millis\" \r\n" + 
            		"              } \r\n" + 
            		"            } \r\n" + 
            		"          } \r\n" + 
            		"        ], \r\n" + 
            		"        \"must_not\": [] \r\n" + 
            		"      } \r\n" + 
            		"    }, \r\n" + 
            		"    \"_source\": { \r\n" + 
            		"      \"includes\": "+getReportColumns()+"\r\n" + 
            		"    } \r\n" + 
            		"}";
    	}
        
        return payload;

    }
    
    public String getReportBySegmentNameAIBSPBVI(String resultFilter, String size, String search, String airid, String id, String gte, String lte) {
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultFilter);
        String payload = "{\r\n" + 
                		"    \"query\": {\r\n" + 
                		"      \"bool\": {\r\n" + 
                		"        \"must\": [\r\n" + 
                		"          {\r\n" + 
                		"          \"terms\": {\r\n" + 
                		"            \"AIRID\": ["+airid+"]\r\n" + 
                		"          }\r\n" + 
                		"        },\r\n" + 
                		"        {\r\n" + 
                		"          \"term\": {\r\n" + 
                		"            \"ID\": {\r\n" + 
                		"              \"value\": "+id+"\r\n" + 
                		"            }\r\n" + 
                		"          }\r\n" + 
                		"        },\r\n" + 
                		"        {\r\n" + 
                		"          \"range\": {\r\n" + 
                		"            \"@timestamp\": {\r\n" + 
                		"              \"gte\": "+gte+",\r\n" + 
                		"              \"lte\": "+lte+"\r\n" + 
                		"            }\r\n" + 
                		"          }\r\n" + 
                		"        },\r\n" + 
                		"        {\r\n" + 
                		"          \"query_string\": {\r\n" + 
                		"            \"analyze_wildcard\": true,\r\n" + 
                		"            \"query\": \""+queryNames+"\",\r\n" + 
                		"            \"fields\": [\"Hits\"]\r\n" + 
                		"          }\r\n" + 
                		"        }\r\n" + 
                		"        \r\n" + 
                		"        ],\r\n" + 
                		"        \"must_not\": []\r\n" + 
                		"      }\r\n" + 
                		"    },\r\n" + 
                		"    \"_source\": {\r\n" + 
                		"      \"excludes\": []\r\n" + 
                		"    },\r\n" + 
                		"	\"aggs\": {\r\n" + 
                		"      \"0\": {\r\n" + 
                		"        \"terms\": {\r\n" + 
                		"          \"field\": \"Hits\",\r\n" + 
                		"          \"size\": "+size+",\r\n" + 
                		"          \"order\": {\r\n" + 
                		"            \"_term\": \"asc\"\r\n" + 
                		"          }\r\n" + 
                		"        }, \r\n" +
                		"    \"aggs\": {\r\n" + 
                		"      \"2\": {\r\n" + 
                		"        \"terms\": {\r\n" + 
                		"            \"field\": \"PageCustomVariable1Value.keyword\"\r\n" + 
                		"        },\r\n" + 
                		"        \"aggs\": {\r\n" + 
                		"          \"3\": {\r\n" + 
                		"            \"terms\": {\r\n" + 
                		"              \"field\": \"CareerTracks\"\r\n" + 
                		"            },\r\n" + 
                		"            \"aggs\": {\r\n" + 
                		"               \"4\": {\r\n" + 
                		"            \"terms\": {\r\n" + 
                		"              \"field\": \"PageCustomVariable4Value.keyword\"\r\n" + 
                		"                }, \r\n" + 
                		"                \"aggs\": {\r\n" + 
                		"                \"1\": {\r\n" + 
                		"                \"cardinality\": {\r\n" + 
                		"                  \"field\": \"User.hash\"\r\n" + 
                		"                  }\r\n" + 
                		"                },\r\n" + 
                		"                \"5\":{\r\n" + 
                		"                \"cardinality\": {\r\n" + 
                		"                  \"field\": \"Hits.hash\"\r\n" + 
                		"                }\r\n" + 
                		"              }\r\n" + 
                		"            } \r\n" + 
                		"            }\r\n" + 
                		"          }\r\n" + 
                		"        }\r\n" + 
                		"      }\r\n" + 
                		"    }\r\n" + 
                		"  }\r\n" +
                		"  }\r\n" +
                		"  }\r\n" +
                		"}";    	 
        return payload;

    }
    
    public String getReportBySegmentNameAIBSPBVI(String resultFilter, String size, String search, String airid, String id, String gte, String lte, String arg) {
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultFilter);
    	String payload = "{\r\n" + 
                		"      \"size\": 0,\r\n" + 
                		"      \"query\": {\r\n" + 
                		"        \"bool\": {\r\n" + 
                		"          \"must\": [\r\n" + 
                		"                            {\r\n" + 
                		"              \"terms\": {\r\n" + 
                		"                \"AIRID\": ["+airid+"]\r\n" + 
                		"              }\r\n" + 
                		"            },\r\n" + 
                		"            {\r\n" + 
                		"              \"term\": {\r\n" + 
                		"                \"ID\": {\r\n" + 
                		"                  \"value\": "+id+"\r\n" + 
                		"                }\r\n" + 
                		"              }\r\n" + 
                		"            },\r\n" + 
                		"            {\r\n" + 
                		"              \"range\": {\r\n" + 
                		"                \"@timestamp\": {\r\n" + 
                		"                  \"gte\": "+gte+",\r\n" + 
                		"                  \"lte\": "+lte+"\r\n" + 
                		"                }\r\n" + 
                		"              }\r\n" + 
                		"            },\r\n" + 
                		"            {\r\n" + 
                		"              \"query_string\": {\r\n" + 
                		"                \"analyze_wildcard\": true,\r\n" + 
                		"                \"query\": \""+queryNames+"\",\r\n" + 
                		"                \"fields\": [\"Hits\"]\r\n" + 
                		"              }\r\n" + 
                		"            }\r\n" + 
                		"          ],\r\n" + 
                		"          \"must_not\": []\r\n" + 
                		"        }\r\n" + 
                		"      },\r\n" + 
                		"      \"_source\": {\r\n" + 
                		"        \"excludes\": []\r\n" + 
                		"      },\r\n" + 
                		"	   \"aggs\": {\r\n" + 
                		"      \"0\": {\r\n" + 
                		"        \"terms\": {\r\n" + 
                		"          \"field\": \"Hits\",\r\n" + 
                		"          \"size\": "+size+",\r\n" + 
                		"          \"order\": {\r\n" + 
                		"            \"_term\": \"asc\"\r\n" + 
                		"          }\r\n" + 
                		"        },\r\n" +
                		"      \"aggs\": {\r\n" + 
                		"        \"2\": {\r\n" + 
                		"          \"terms\": {\r\n" + 
                		"            \"field\": \"PageCustomVariable1Value.keyword\"\r\n" + 
                		"          },\r\n" + 
                		"          \"aggs\": {\r\n" + 
                		"            \"3\": {\r\n" + 
                		"              \"terms\": {\r\n" + 
                		"                \"field\": \""+arg+"\"\r\n" + 
                		"              },\r\n" + 
                		"              \"aggs\": {\r\n" + 
                		"                \"1\": {\r\n" + 
                		"                  \"cardinality\": {\r\n" + 
                		"                    \"field\": \"User.hash\"\r\n" + 
                		"                  }\r\n" + 
                		"                }\r\n" + 
                		"              }\r\n" + 
                		"            }\r\n" + 
                		"          }\r\n" + 
                		"        }\r\n" + 
                		"      }\r\n" + 
                		"      }\r\n" + 
                		"      }\r\n" + 
                		"    }";
        return payload;
    }
    
    
    public String getHitsReportsAIBSPBVI(String resultFilter, String size, String search, String airid, String id, String includes, String gte, String lte) {
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultFilter);
        String payload = "";  
        
        // Hits by geography
        if (includes.equalsIgnoreCase("hitsByGeography")) {
            		payload = "{\r\n" + 
                    		"    \"size\": 0,\r\n" + 
                    		"    \"query\": {\r\n" + 
                    		"      \"bool\": {\r\n" + 
                    		"        \"must\": [\r\n" + 
                    		"          {\r\n" + 
                    		"              \"terms\": {\r\n" + 
                    		"                \"AIRID\": ["+airid+"]\r\n" + 
                    		"              }\r\n" + 
                    		"            },\r\n" + 
                    		"            {\r\n" + 
                    		"              \"term\": {\r\n" + 
                    		"                \"ID\": {\r\n" + 
                    		"                  \"value\": "+id+"\r\n" + 
                    		"                }\r\n" + 
                    		"              }\r\n" + 
                    		"            },\r\n" + 
                    		"            {\r\n" + 
                    		"              \"range\": {\r\n" + 
                    		"                \"@timestamp\": {\r\n" + 
                    		"                  \"gte\": "+gte+",\r\n" + 
                    		"                  \"lte\": "+lte+"\r\n" + 
                    		"                }\r\n" + 
                    		"              }\r\n" + 
                    		"            },\r\n" + 
                    		"            {\r\n" + 
                    		"              \"query_string\": {\r\n" + 
                    		"                \"analyze_wildcard\": true,\r\n" + 
                    		"                \"query\": \""+queryNames+"\",\r\n" + 
                    		"                \"fields\": [\"Hits\"]\r\n" + 
                    		"              }\r\n" + 
                    		"            }\r\n" + 
                    		"        ],\r\n" + 
                    		"        \"must_not\": []\r\n" + 
                    		"      }\r\n" + 
                    		"    },\r\n" + 
                    		"	\"aggs\": {\r\n" + 
                    		"        \"0\": {\r\n" + 
                    		"          \"terms\": {\r\n" + 
                    		"            \"field\": \"Hits\",\r\n" + 
                    		"            \"size\": "+size+",\r\n" + 
                    		"            \"order\": {\r\n" + 
                    		"              \"_term\": \"asc\"\r\n" + 
                    		"            }\r\n" + 
                    		"          },\r\n" + 
                    		"    \"aggs\": {\r\n" + 
                    		"      \"2\": {\r\n" + 
                    		"        \"terms\": {\r\n" + 
                    		"          \"field\": \"PageCustomVariable1Value.keyword\"\r\n" + 
                    		"        },\"aggs\": {\r\n" + 
                    		"          \"3\": {\r\n" + 
                    		"            \"terms\": {\r\n" + 
                    		"              \"field\": \"Geography.keyword\"\r\n" + 
                    		"            },\"aggs\": {\r\n" + 
                    		"              \"1\": {\r\n" + 
                    		"                \"cardinality\": {\r\n" + 
                    		"                  \"field\": \"Hits.hash\"\r\n" + 
                    		"                }\r\n" + 
                    		"              }\r\n" + 
                    		"            }\r\n" + 
                    		"          }\r\n" + 
                    		"        }\r\n" + 
                    		"      }\r\n" + 
                    		"    }\r\n" + 
                    		"    }\r\n" + 
                    		"    }\r\n" + 
                    		"  }";
        } else if (includes.equalsIgnoreCase("hitsByAsset")) {
            		payload = "{\r\n" + 
                    		"  \"size\": 0,\r\n" + 
                    		"  \"query\": {\r\n" + 
                    		"    \"bool\": {\r\n" + 
                    		"      \"must\": [\r\n" + 
                    		"       {\r\n" + 
                    		"              \"terms\": {\r\n" + 
                    		"                \"AIRID\": ["+airid+"]\r\n" + 
                    		"              }\r\n" + 
                    		"            },\r\n" + 
                    		"            {\r\n" + 
                    		"              \"term\": {\r\n" + 
                    		"                \"ID\": {\r\n" + 
                    		"                  \"value\": "+id+"\r\n" + 
                    		"                }\r\n" + 
                    		"              }\r\n" + 
                    		"            },\r\n" + 
                    		"            {\r\n" + 
                    		"              \"range\": {\r\n" + 
                    		"                \"@timestamp\": {\r\n" + 
                    		"                  \"gte\": "+gte+",\r\n" + 
                    		"                  \"lte\": "+lte+"\r\n" + 
                    		"                }\r\n" + 
                    		"              }\r\n" + 
                    		"            },\r\n" + 
                    		"            {\r\n" + 
                    		"              \"query_string\": {\r\n" + 
                    		"                \"analyze_wildcard\": true,\r\n" + 
                    		"                \"query\": \""+queryNames+"\",\r\n" + 
                    		"                \"fields\": [\"Hits\"]\r\n" + 
                    		"              }\r\n" + 
                    		"            }\r\n" + 
                    		"      ],\r\n" + 
                    		"      \"must_not\": []\r\n" + 
                    		"    }\r\n" + 
                    		"  },\r\n" + 
                    		"  \"aggs\": {\r\n" + 
                    		"        \"0\": {\r\n" + 
                    		"          \"terms\": {\r\n" + 
                    		"            \"field\": \"Hits\",\r\n" + 
                    		"            \"size\": "+size+",\r\n" + 
                    		"            \"order\": {\r\n" + 
                    		"              \"_term\": \"asc\"\r\n" + 
                    		"            }\r\n" + 
                    		"          },\r\n" +
                    		"  \"aggs\": {\r\n" + 
                    		"    \"2\": {\r\n" + 
                    		"      \"terms\": {\r\n" + 
                    		"        \"field\": \"PageCustomVariable1Value.keyword\"\r\n" +  
                    		"      },\"aggs\": {\r\n" + 
                    		"        \"3\": {\r\n" + 
                    		"          \"terms\": {\r\n" + 
                    		"            \"field\": \"PageCustomVariable3Value.keyword\"\r\n" + 
                    		"          },\"aggs\": {\r\n" + 
                    		"            \"4\": {\r\n" + 
                    		"              \"terms\": {\r\n" + 
                    		"                \"field\": \"PageCustomVariable4Value.keyword\"\r\n" + 
                    		"              },\"aggs\": {\r\n" + 
                    		"                \"1\": {\r\n" + 
                    		"                  \"cardinality\": {\r\n" + 
                    		"                    \"field\": \"Hits.hash\"\r\n" + 
                    		"                  }\r\n" + 
                    		"                }\r\n" + 
                    		"              }\r\n" + 
                    		"            }\r\n" + 
                    		"          }\r\n" + 
                    		"        }\r\n" + 
                    		"      }\r\n" + 
                    		"    }\r\n" + 
                    		"  }\r\n" + 
                    		"    }\r\n" + 
                    		"    }\r\n" + 
                    		"}";   		
         }
        return payload;
    }
    
    public String getCustomReportsOverviewMetrics(String airid, String gte, String lte, String id, String size, String search, String total, String resultFilter, String reportType, String filterFor) {
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultFilter);
    	String payload = null;
    	
    	if(!kusinaValidationUtils.isValidOverviewChildType(reportType)) {
    		//PageOverview
        	if(reportType.equalsIgnoreCase("pageOverview")) {
        			payload = "{\r\n" + 
                			"    \"query\": {\r\n" + 
                			"      \"bool\": {\r\n" + 
                			"        \"must\": [{\r\n" + 
                			"  	      \"range\": {\r\n" + 
                			"  		      \"@timestamp\": {\r\n" + 
                			"  			      \"gte\": "+gte+",\r\n" + 
                			"              	  \"lte\": "+lte+",\r\n" + 
                			"  			      \"format\": \"epoch_millis\"\r\n" + 
                			"  		      }\r\n" + 
                			"  	      }\r\n" + 
                			"          },{\r\n" + 
                			"          	\"terms\": {\r\n" + 
                			"          		\"AIRID\": [\""+airid+"\"]  \r\n" + 
                			"          	}\r\n" + 
                			"          }, {\r\n" + 
                			"          	\"term\": {\r\n" + 
                			"          		\"ID\": {\r\n" + 
                			"          			\"value\": \""+id+"\"\r\n" + 
                			"          		}\r\n" + 
                			"          	}\r\n" + 
                			"          },\r\n" + 
                			"          {\r\n" + 
                			"            \"query_string\": {\r\n" + 
                			"            \"analyze_wildcard\": true,\r\n" + 
                			"            \"query\": \""+queryNames+"\",\r\n" + 
                			"            \"fields\": [\"CleanPageURL.keyword\"]\r\n" + 
                			"          }\r\n" + 
                			"          }]\r\n" + 
                			"          }},\r\n" + 
                			"    \"aggs\": {\r\n" + 
                			"      \"2\": {\r\n" + 
                			"        \"terms\": {\r\n" + 
                			"          \"field\": \"CleanPageURL.keyword\",\r\n" + 
                			"          \"size\": "+size+",\r\n" + 
                			"           \"order\": {\r\n" + 
                			"            \"_term\": \"asc\"\r\n" + 
                			"          }\r\n" + 
                			"        },\r\n" + 
                			"        \"aggs\": {\r\n" + 
                			"          \"3\": {\r\n" + 
                			"            \"cardinality\": {\r\n" + 
                			"              \"field\": \"Hits.hash\"\r\n" + 
                			"            }\r\n" + 
                			"          },\r\n" + 
                			"          \"4\": {\r\n" + 
                			"            \"cardinality\": {\r\n" + 
                			"              \"field\": \"Visits.hash\"\r\n" + 
                			"            }\r\n" + 
                			"          },\r\n" + 
                			"          \"5\": {\r\n" + 
                			"            \"cardinality\": {\r\n" + 
                			"              \"field\": \"User.hash\"\r\n" + 
                			"            }\r\n" + 
                			"          }\r\n" + 
                			"        }\r\n" + 
                			"      }\r\n" + 
                			"    }\r\n" + 
                			"}";
        	//UserOverview
        	}else if(reportType.equalsIgnoreCase("userOverview")) {
        			payload = "{\r\n" + 
        					"  \"query\": {\r\n" + 
        					"    \"bool\": {\r\n" + 
        					"      \"must\": [\r\n" + 
        					"        {\r\n" + 
        					"          \"range\": {\r\n" + 
        					"            \"@timestamp\": {\r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+",\r\n" + 
        					"              \"format\": \"epoch_millis\"\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },{\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"AIRID\": ["+airid+"]\r\n" + 
        					"          }\r\n" + 
        					"        },{\r\n" + 
        					"          \"term\": {\r\n" + 
        					"            \"ID\": {\r\n" + 
        					"              \"value\": \""+id+"\" \r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"                {\r\n" + 
        					"            \"query_string\": {\r\n" + 
        					"            \"analyze_wildcard\": true,\r\n" + 
        					"            \"query\": \""+queryNames+"\",\r\n" + 
        					"            \"fields\": [\"User\"]\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" + 
        					"      ]\r\n" + 
        					"    }\r\n" + 
        					"  },\"aggs\": {\r\n" + 
        					"    \"2\": {\r\n" + 
        					"      \"terms\": {\r\n" + 
        					"        \"field\": \"User\",\r\n" + 
        					"         \"size\": "+size+",\r\n" + 
        					"         \"order\": {\r\n" + 
        					"          \"_term\": \"asc\"\r\n" + 
        					"        }\r\n" + 
        					"      },\r\n" + 
        					"      \"aggs\": {\r\n" + 
        					"        \"3\": {\r\n" + 
        					"          \"terms\": {\r\n" + 
        					"            \"field\": \"CareerLevel\"\r\n" + 
        					"          },\r\n" + 
        					"          \"aggs\": {\r\n" + 
        					"            \"4\": {\r\n" + 
        					"              \"terms\": {\r\n" + 
        					"                \"field\": \"CareerTracks\"\r\n" + 
        					"              },\r\n" + 
        					"              \"aggs\": {\r\n" + 
        					"                \"5\": {\r\n" + 
        					"                  \"terms\": {\r\n" + 
        					"                    \"field\": \"Geography.keyword\"\r\n" + 
        					"                  },\r\n" + 
        					"                  \"aggs\": {\r\n" + 
        					"                    \"6\": {\r\n" + 
        					"                      \"terms\": {\r\n" + 
        					"                        \"field\": \"Country.keyword\"\r\n" + 
        					"                      }\r\n" + 
        					"                    }\r\n" + 
        					"                  }\r\n" + 
        					"                }\r\n" + 
        					"              }\r\n" + 
        					"            }\r\n" + 
        					"          }\r\n" + 
        					"        },\r\n" + 
        					"        \"1\":{\r\n" + 
        					"          \"cardinality\": {\r\n" + 
        					"            \"field\": \"Visits.hash\"\r\n" + 
        					"          }\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"    }\r\n" + 
        					"  }\r\n" + 
        					"}";
        	}else if (reportType.equalsIgnoreCase("referrerOverview")) {
        		payload = "{  \r\n" + 
        				"  \"size\": 0, \r\n" + 
        				"   \"query\": {  \r\n" + 
        				"    \"bool\": {  \r\n" + 
        				"      \"must\": [  \r\n" + 
        				"        {  \r\n" + 
        				"          \"terms\": {  \r\n" + 
        				"            \"AIRID\": [\""+airid+"\"]  \r\n" + 
        				"          }  \r\n" + 
        				"        },  \r\n" + 
        				"        {  \r\n" + 
        				"          \"term\": {  \r\n" + 
        				"            \"ID\": {  \r\n" + 
        				"              \"value\": \""+id+"\"  \r\n" + 
        				"            }  \r\n" + 
        				"          }  \r\n" + 
        				"        },  \r\n" + 
        				"        {  \r\n" + 
        				"          \"range\": {  \r\n" + 
        				"            \"@timestamp\": {  \r\n" + 
        				"              \"gte\": "+gte+",\r\n" + 
        				"              \"lte\": "+lte+" \r\n" + 
        				"            }  \r\n" + 
        				"          }  \r\n" + 	
        				"        },  \r\n" + 
        				"        {  \r\n" + 
        				"          \"query_string\": {  \r\n" + 
        				"            \"analyze_wildcard\": true,  \r\n" + 
        				"            \"query\": \""+queryNames+"\",  \r\n" + 
        				"            \"fields\": [\"RefererURL\"]  \r\n" + 
        				"          }  \r\n" + 
        				"        }  \r\n" + 
        				"      ]  \r\n" + 
        				"  }  \r\n" + 
        				"  },  \r\n" + 
        				"  \"aggs\": {  \r\n" + 
        				"    \"1\": {  \r\n" + 
        				"      \"terms\": {  \r\n" + 
        				"        \"field\": \"RefererURL\",  \r\n" + 
        				"        \"size\": "+size+",  \r\n" + 
        				"        \"order\": {  \r\n" + 
        				"          \"_term\": \"asc\"  \r\n" + 
        				"        }  \r\n" + 
        				"      },  \r\n" + 
        				"      \"aggs\": {  \r\n" + 
        				"        \"2\": {  \r\n" + 
        				"          \"terms\": {  \r\n" + 
        				"            \"field\": \"User\"  \r\n" + 
        				"          },  \r\n" + 
        				"          \"aggs\": {  \r\n" + 
        				"            \"3\":{  \r\n" + 
        				"            \"terms\": {  \r\n" + 
        				"              \"field\": \"Visits\"  \r\n" + 
        				"            },  \r\n" + 
        				"            \"aggs\": {  \r\n" + 
        				"              \"4\": {  \r\n" + 
        				"                \"avg\": {  \r\n" + 
        				"                  \"field\": \"TotalElapseTimeOfVisit\"  \r\n" + 
        				"                }  \r\n" + 
        				"              },  \r\n" + 
        				"              \"5\" : {  \r\n" + 
        				"                \"cardinality\": {  \r\n" + 
        				"                  \"field\": \"PageTitle\"  \r\n" + 
        				"                }  \r\n" + 
        				"              },  \r\n" + 
        				"              \"6\" : {  \r\n" + 
        				"                \"cardinality\": {  \r\n" + 
        				"                  \"field\": \"Visits.hash\"  \r\n" + 
        				"                }  \r\n" + 
        				"              },  \r\n" + 
        				"              \"7\" : {  \r\n" + 
        				"                \"cardinality\": {  \r\n" + 
        				"                  \"field\": \"Hits.hash\"  \r\n" + 
        				"                }  \r\n" + 
        				"              }  \r\n" + 
        				"                \r\n" + 
        				"            }  \r\n" + 
        				"          },  \r\n" + 
        				"          \"sum_visits\": {    \r\n" + 
        				"             \"sum_bucket\": {    \r\n" + 
        				"               \"buckets_path\": \"3>6\"    \r\n" + 
        				"             }    \r\n" + 
        				"           },    \r\n" + 
        				"           \"sum_hits\":{    \r\n" + 
        				"             \"sum_bucket\": {    \r\n" + 
        				"               \"buckets_path\": \"3>7\"    \r\n" + 
        				"             }    \r\n" + 
        				"           },    \r\n" + 
        				"           \"avg_page_visits\" : {    \r\n" + 
        				"             \"avg_bucket\": {    \r\n" + 
        				"               \"buckets_path\": \"3>5\"    \r\n" + 
        				"             }    \r\n" + 
        				"           },    \r\n" + 
        				"           \"avg_page_duration\" : {    \r\n" + 
        				"             \"avg_bucket\": {    \r\n" + 
        				"               \"buckets_path\": \"3>4\"    \r\n" + 
        				"             }    \r\n" + 
        				"           }  \r\n" + 
        				"        }  \r\n" + 
        				"        },  \r\n" + 
        				"        \"total_sum_visits\":{  \r\n" + 
        				"          \"sum_bucket\": {  \r\n" + 
        				"            \"buckets_path\": \"2>sum_visits\"  \r\n" + 
        				"          }  \r\n" + 
        				"        },  \r\n" + 
        				"        \"total_sum_hits\":{  \r\n" + 
        				"          \"sum_bucket\": {  \r\n" + 
        				"            \"buckets_path\": \"2>sum_hits\"  \r\n" + 
        				"          }  \r\n" + 
        				"        },  \r\n" + 
        				"        \"total_avg_page_visits\":{  \r\n" + 
        				"          \"avg_bucket\": {  \r\n" + 
        				"            \"buckets_path\": \"2>avg_page_visits\"  \r\n" + 
        				"          }  \r\n" + 
        				"        },  \r\n" + 
        				"        \"total_avg_page_duration\":{  \r\n" + 
        				"          \"avg_bucket\": {  \r\n" + 
        				"            \"buckets_path\": \"2>avg_page_duration\"  \r\n" + 
        				"          }  \r\n" + 
        				"        }  \r\n" + 
        				"      }  \r\n" + 
        				"    }  \r\n" + 
        				"  }  \r\n" + 
        				"}";
        	} else if (reportType.equalsIgnoreCase("downloadOverview")) {
        			payload = "{  \r\n" + 
        					"  \"size\": 0, \r\n" + 
        					"   \"query\": {  \r\n" + 
        					"    \"bool\": {  \r\n" + 
        					"      \"must\": [  \r\n" + 
        					"        {  \r\n" + 
        					"          \"terms\": {  \r\n" + 
        					"            \"AIRID\": [\""+airid+"\"]  \r\n" + 
        					"          }  \r\n" + 
        					"        },  \r\n" + 
        					"        {  \r\n" + 
        					"          \"term\": {  \r\n" + 
        					"            \"ID\": {  \r\n" + 
        					"              \"value\": \""+id+"\"  \r\n" + 
        					"            }  \r\n" + 
        					"          }  \r\n" + 
        					"        },  \r\n" + 
        					"        {  \r\n" + 
        					"          \"range\": {  \r\n" + 
        					"            \"@timestamp\": {  \r\n" + 
        					"              \"gte\": "+gte+",\r\n" + 
        					"              \"lte\": "+lte+" \r\n" + 
        					"            }  \r\n" + 
        					"          }  \r\n" + 
        					"        },  \r\n" + 
        					"        {  \r\n" + 
        					"          \"query_string\": {  \r\n" + 
        					"            \"analyze_wildcard\": true,  \r\n" + 
        					"            \"query\": \""+queryNames+"\",  \r\n" + 
        					"            \"fields\": [\"CleanPageURL.keyword\"]  \r\n" + 
        					"          }  \r\n" + 
        					"        }  \r\n" + 
        					"      ],\r\n" + 
        					"      \"filter\": {\r\n" + 
        					"        \"term\": {\r\n" + 
        					"          \"ActionURLType\": \"3\"\r\n" + 
        					"        }\r\n" + 
        					"      }\r\n" + 
        					"  }  \r\n" + 
        					"  },  \r\n" + 
        					"  \"aggs\": {  \r\n" + 
        					"    \"1\": {  \r\n" + 
        					"      \"terms\": {  \r\n" + 
        					"        \"field\": \"CleanPageURL.keyword\",  \r\n" + 
        					"        \"size\": "+size+",  \r\n" + 
        					"        \"order\": {  \r\n" + 
        					"          \"_term\": \"asc\"  \r\n" + 
        					"        }  \r\n" + 
        					"      },  \r\n" + 
        					"      \"aggs\": {  \r\n" + 
        					"        \"2\": {  \r\n" + 
        					"          \"terms\": {  \r\n" + 
        					"            \"field\": \"User\"  \r\n" + 
        					"          },  \r\n" + 
        					"          \"aggs\": {  \r\n" + 
        					"            \"3\":{  \r\n" + 
        					"            \"terms\": {  \r\n" + 
        					"              \"field\": \"Visits\"  \r\n" + 
        					"            },  \r\n" + 
        					"            \"aggs\": {  \r\n" + 
        					"              \"4\": {  \r\n" + 
        					"                \"avg\": {  \r\n" + 
        					"                  \"field\": \"TotalElapseTimeOfVisit\"  \r\n" + 
        					"                }  \r\n" + 
        					"              },  \r\n" + 
        					"              \"5\" : {  \r\n" + 
        					"                \"cardinality\": {  \r\n" + 
        					"                  \"field\": \"PageTitle\"  \r\n" + 
        					"                }  \r\n" + 
        					"              },  \r\n" + 
        					"              \"6\" : {  \r\n" + 
        					"                \"cardinality\": {  \r\n" + 
        					"                  \"field\": \"Visits.hash\"  \r\n" + 
        					"                }  \r\n" + 
        					"              },  \r\n" + 
        					"              \"7\" : {  \r\n" + 
        					"                \"cardinality\": {  \r\n" + 
        					"                  \"field\": \"Hits.hash\"  \r\n" + 
        					"                }  \r\n" + 
        					"              }  \r\n" + 
        					"                \r\n" + 
        					"            }  \r\n" + 
        					"          },  \r\n" + 
        					"          \"sum_visits\": {    \r\n" + 
        					"             \"sum_bucket\": {    \r\n" + 
        					"               \"buckets_path\": \"3>6\"    \r\n" + 
        					"             }    \r\n" + 
        					"           },    \r\n" + 
        					"           \"sum_hits\":{    \r\n" + 
        					"             \"sum_bucket\": {    \r\n" + 
        					"               \"buckets_path\": \"3>7\"    \r\n" + 
        					"             }    \r\n" + 
        					"           },    \r\n" + 
        					"           \"avg_page_visits\" : {    \r\n" + 
        					"             \"avg_bucket\": {    \r\n" + 
        					"               \"buckets_path\": \"3>5\"    \r\n" + 
        					"             }    \r\n" + 
        					"           },    \r\n" + 
        					"           \"avg_page_duration\" : {    \r\n" + 
        					"             \"avg_bucket\": {    \r\n" + 
        					"               \"buckets_path\": \"3>4\"    \r\n" + 
        					"             }    \r\n" + 
        					"           }  \r\n" + 
        					"        }  \r\n" + 
        					"        },  \r\n" + 
        					"        \"total_sum_visits\":{  \r\n" + 
        					"          \"sum_bucket\": {  \r\n" + 
        					"            \"buckets_path\": \"2>sum_visits\"  \r\n" + 
        					"          }  \r\n" + 
        					"        },  \r\n" + 
        					"        \"total_sum_hits\":{  \r\n" + 
        					"          \"sum_bucket\": {  \r\n" + 
        					"            \"buckets_path\": \"2>sum_hits\"  \r\n" + 
        					"          }  \r\n" + 
        					"        },  \r\n" + 
        					"        \"total_avg_page_visits\":{  \r\n" + 
        					"          \"avg_bucket\": {  \r\n" + 
        					"            \"buckets_path\": \"2>avg_page_visits\"  \r\n" + 
        					"          }  \r\n" + 
        					"        },  \r\n" + 
        					"        \"total_avg_page_duration\":{  \r\n" + 
        					"          \"avg_bucket\": {  \r\n" + 
        					"            \"buckets_path\": \"2>avg_page_duration\"  \r\n" + 
        					"          }  \r\n" + 
        					"        }  \r\n" + 
        					"      }  \r\n" + 
        					"    }  \r\n" + 
        					"  }  \r\n" + 
        					"}";
        	}
    	}else {
    		//ReferrersOverview Child
    		System.out.println("This is child payload part 2");
    		if(reportType.equalsIgnoreCase("referrerOverviewChild")) {
    				payload = "{\r\n" + 
    						"  \"size\": 0, \r\n" + 
    						"  \"query\": {\r\n" + 
    						"    \"bool\": {\r\n" + 
    						"      \"must\": [\r\n" + 
    						"        {\r\n" + 
    						"          \"terms\": {\r\n" + 
    						"            \"AIRID\": [\""+airid+"\"]\r\n" + 
    						"          }\r\n" + 
    						"        },\r\n" + 
    						"        {\r\n" + 
    						"          \"term\": {\r\n" + 
    						"            \"ID\": {\r\n" + 
    						"              \"value\": \""+id+"\"\r\n" + 
    						"            }\r\n" + 
    						"          }\r\n" + 
    						"        },\r\n" + 
    						"        {\r\n" + 
    						"          \"range\": {\r\n" + 
    						"            \"@timestamp\": {\r\n" + 
    						"              \"gte\": "+gte+",\r\n" + 
    						"              \"lte\": "+lte+"\r\n" + 
    						"            }\r\n" + 
    						"          }\r\n" + 
    						"        },\r\n" + 
    						"        {\r\n" + 
    						"          \"query_string\": {\r\n" + 
    						"            \"analyze_wildcard\": true,\r\n" + 
    						"            \"query\": \""+queryNames+"\",\r\n" + 
    						"            \"fields\": [\"User\"]\r\n" + 
    						"          }\r\n" + 
    						"        }\r\n" + 
    						"      ],\r\n" + 
    						"      \"filter\": {\r\n" + 
    						"        \"term\": {\r\n" + 
    						"          \"RefererName\": \""+filterFor+"\"\r\n" + 
    						"        }\r\n" + 
    						"      }\r\n" + 
    						"  }\r\n" + 
    						"  },\r\n" + 
    						"  \"aggs\" : {\r\n" + 
    						"      \"1\" : {\r\n" + 
    						"        \"terms\": {\r\n" + 
    						"          \"field\": \"User\",\r\n" + 
    						"          \"size\":"+size+",\r\n" + 
    						"         \"order\": {\r\n" + 
    						"                \"_term\": \"asc\"\r\n" + 
    						"         }\r\n" + 
    						"        },\r\n" + 
    						"        \"aggs\": {\r\n" + 
    						"          \"2\":{\r\n" + 
    						"            \"terms\": {\r\n" + 
    						"              \"field\": \"Visits\"\r\n" + 
    						"            },\r\n" + 
    						"            \"aggs\": {\r\n" + 
    						"              \"3\": {\r\n" + 
    						"                \"avg\": {\r\n" + 
    						"                  \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
    						"                }\r\n" + 
    						"              },\r\n" + 
    						"              \"4\" : {\r\n" + 
    						"                \"cardinality\": {\r\n" + 
    						"                  \"field\": \"PageTitle\"\r\n" + 
    						"                }\r\n" + 
    						"              },\r\n" + 
    						"              \"5\" : {\r\n" + 
    						"                \"cardinality\": {\r\n" + 
    						"                  \"field\": \"Visits.hash\"\r\n" + 
    						"                }\r\n" + 
    						"              },\r\n" + 
    						"              \"6\" : {\r\n" + 
    						"                \"cardinality\": {\r\n" + 
    						"                  \"field\": \"Hits.hash\"\r\n" + 
    						"                }\r\n" + 
    						"              }\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"avg_page_duration\":{\r\n" + 
    						"            \"avg_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>3\"\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"avg_page_visit\" : {\r\n" + 
    						"            \"avg_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>4\"\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"sum_visits\":{\r\n" + 
    						"            \"sum_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>5\"\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"sum_hits\": {\r\n" + 
    						"            \"sum_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>6\"\r\n" + 
    						"            }\r\n" + 
    						"          }\r\n" + 
    						"        }\r\n" + 
    						"      }\r\n" + 
    						"    }\r\n" + 
    						"}\r\n" + 
    						"";
    		}else if(reportType.equalsIgnoreCase("downloadOverviewChild")) {
    				payload = "{\r\n" + 
    						"  \"size\": 0, \r\n" + 
    						"  \"query\": {\r\n" + 
    						"    \"bool\": {\r\n" + 
    						"      \"must\": [\r\n" + 
    						"        {\r\n" + 
    						"          \"terms\": {\r\n" + 
    						"            \"AIRID\": [\""+airid+"\"]\r\n" + 
    						"          }\r\n" + 
    						"        },\r\n" + 
    						"        {\r\n" + 
    						"          \"term\": {\r\n" + 
    						"            \"ID\": {\r\n" + 
    						"              \"value\": \""+id+"\"\r\n" + 
    						"            }\r\n" + 
    						"          }\r\n" + 
    						"        },\r\n" + 
    						"        {\r\n" + 
    						"          \"term\": {\r\n" + 
    						"            \"ActionURLType\": {\r\n" + 
    						"              \"value\": \"3\"\r\n" + 
    						"            }\r\n" + 
    						"          }\r\n" + 
    						"        },\r\n" + 
    						"        {\r\n" + 
    						"          \"range\": {\r\n" + 
    						"            \"@timestamp\": {\r\n" + 
    						"              \"gte\": "+gte+",\r\n" + 
    						"              \"lte\": "+lte+"\r\n" + 
    						"            }\r\n" + 
    						"          }\r\n" + 
    						"        },\r\n" + 
    						"        {\r\n" + 
    						"          \"query_string\": {\r\n" + 
    						"            \"analyze_wildcard\": true,\r\n" + 
    						"            \"query\": \""+queryNames+"\",\r\n" + 
    						"            \"fields\": [\"User\"]\r\n" + 
    						"          }\r\n" + 
    						"        }\r\n" + 
    						"      ],\r\n" + 
    						"      \"filter\": {\r\n" + 
    						"        \"term\": {\r\n" + 
    						"          \"CleanPageURL.keyword\": \""+filterFor+"\"\r\n" + 
    						"        }\r\n" + 
    						"      }\r\n" + 
    						"  }\r\n" + 
    						"  },\r\n" + 
    						"  \"aggs\" : {\r\n" + 
    						"      \"1\" : {\r\n" + 
    						"        \"terms\": {\r\n" + 
    						"          \"field\": \"User\",\r\n" + 
    						"          \"size\":"+size+",\r\n" + 
    						"         \"order\": {\r\n" + 
    						"                \"_term\": \"asc\"\r\n" + 
    						"         }\r\n" + 
    						"        },\r\n" + 
    						"        \"aggs\": {\r\n" + 
    						"          \"2\":{\r\n" + 
    						"            \"terms\": {\r\n" + 
    						"              \"field\": \"Visits\"\r\n" + 
    						"            },\r\n" + 
    						"            \"aggs\": {\r\n" + 
    						"              \"3\": {\r\n" + 
    						"                \"avg\": {\r\n" + 
    						"                  \"field\": \"TotalElapseTimeOfVisit\"\r\n" + 
    						"                }\r\n" + 
    						"              },\r\n" + 
    						"              \"4\" : {\r\n" + 
    						"                \"cardinality\": {\r\n" + 
    						"                  \"field\": \"PageTitle\"\r\n" + 
    						"                }\r\n" + 
    						"              },\r\n" + 
    						"              \"5\" : {\r\n" + 
    						"                \"cardinality\": {\r\n" + 
    						"                  \"field\": \"Visits.hash\"\r\n" + 
    						"                }\r\n" + 
    						"              },\r\n" + 
    						"              \"6\" : {\r\n" + 
    						"                \"cardinality\": {\r\n" + 
    						"                  \"field\": \"Hits.hash\"\r\n" + 
    						"                }\r\n" + 
    						"              }\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"avg_page_duration\":{\r\n" + 
    						"            \"avg_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>3\"\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"avg_page_visit\" : {\r\n" + 
    						"            \"avg_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>4\"\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"sum_visits\":{\r\n" + 
    						"            \"sum_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>5\"\r\n" + 
    						"            }\r\n" + 
    						"          },\r\n" + 
    						"          \"sum_hits\": {\r\n" + 
    						"            \"sum_bucket\": {\r\n" + 
    						"              \"buckets_path\": \"2>6\"\r\n" + 
    						"            }\r\n" + 
    						"          }\r\n" + 
    						"        }\r\n" + 
    						"      }\r\n" + 
    						"    }\r\n" + 
    						"}";
    				}
    	}
    	return payload;
    }
    
    
    public String getCustomReportsEvents(String airid, String gte, String lte, String id, String size, String search, String reportType, String resultFilter) {
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultFilter);
    	String	payload = "{ \r\n" + 
        			"  \"size\": 0, \r\n" + 
        			"  \"query\": { \r\n" + 
        			"    \"bool\": { \r\n" + 
        			"      \"must\": [ \r\n" + 
        			"        {\r\n" + 
        			"          \"terms\": {\r\n" + 
        			"            \"AIRID\": ["+airid+"]\r\n" + 
        			"          }\r\n" + 
        			"        },\r\n" + 
        			"        {\r\n" + 
        			"          \"term\": {\r\n" + 
        			"            \"ID\": {\r\n" + 
        			"              \"value\": "+id+"\r\n" + 
        			"            }\r\n" + 
        			"          }\r\n" + 
        			"        },\r\n" + 
        			"        {\r\n" + 
        			"          \"range\": {\r\n" + 
        			"            \"@timestamp\": {\r\n" + 
        			"              \"gte\": "+gte+",\r\n" + 
        			"              \"lte\": "+lte+"\r\n" + 
        			"            }\r\n" + 
        			"          }\r\n" + 
        			"        },\r\n" + 
        			"        {\r\n" + 
        			"          \"query_string\": {\r\n" + 
        			"            \"analyze_wildcard\": true,\r\n" + 
        			"            \"query\": \""+queryNames+"\",\r\n" + 
        			"            \"fields\": [\""+kusinaValidationUtils.modifyPagesFilter(reportType)+"\"]\r\n" + 
        			"          }\r\n" + 
        			"        } \r\n" + 
        			"      ] \r\n" + 
        			"    } \r\n" + 
        			"  }, \r\n" + 
        			"  \"aggs\": { \r\n" + 
        			"    \"1\": { \r\n" + 
        			"      \"terms\": { \r\n" + 
        			"        \"field\": \"EventCategory\", \r\n" + 
        			"		 \"size\": "+size+",\r\n" +
        			"        \"order\": { \r\n" + 
        			"          \"_term\": \"asc\" \r\n" + 
        			"        } \r\n" + 
        			"      }, \r\n" + 
        			"      \"aggs\": { \r\n" + 
        			"        \"2\": { \r\n" + 
        			"          \"terms\": { \r\n" + 
        			"            \"field\": \"EventAction\", \r\n" + 
        			"            \"order\": { \r\n" + 
        			"              \"_term\": \"asc\" \r\n" + 
        			"            } \r\n" + 
        			"          }, \r\n" + 
        			"          \"aggs\": { \r\n" + 
        			"            \"3\": { \r\n" + 
        			"              \"terms\": { \r\n" + 
        			"                \"field\": \"EventName\", \r\n" + 
        			"                \"order\": { \r\n" + 
        			"                  \"_term\": \"asc\" \r\n" + 
        			"                } \r\n" + 
        			"              } \r\n" + 
        			"            } \r\n" + 
        			"          } \r\n" + 
        			"        } \r\n" + 
        			"      } \r\n" + 
        			"    } \r\n" + 
        			"  } \r\n" + 
        			"}";
    	return payload;
    }
    
    
    public String getCustomReportsITF(String airid, String gte, String lte, String id, String size, String search, String reportType, String resultFilter) {
    	String queryNames = kusinaStringUtils.convertStringtoElasticQuery(resultFilter);
    	String itfFilter = kusinaValidationUtils.convertReportTypeToITFFilter(reportType);
    	String	payload = "{  \r\n" + 
    			"  \"size\": 0, \r\n" + 
    			"	  \"query\": {  \r\n" + 
    			"	    \"bool\": {  \r\n" + 
    			"	      \"must\": [  \r\n" + 
    			"	        {  \r\n" + 
    			"	          \"terms\": {  \r\n" + 
    			"	            \"AIRID\": [\""+airid+"\"]  \r\n" + 
    			"	          }  \r\n" + 
    			"	        },  \r\n" + 
    			"	        {  \r\n" + 
    			"	          \"term\": {  \r\n" + 
    			"	            \"ID\": {  \r\n" + 
    			"	              \"value\": \""+id+"\"  \r\n" + 
    			"	            }  \r\n" + 
    			"	          }  \r\n" + 
    			"	        },  \r\n" + 
    			"	        {  \r\n" + 
    			"	          \"range\": {  \r\n" + 
    			"	            \"@timestamp\": {  \r\n" + 
    			"	             \"gte\": "+gte+",\r\n" + 
    			"               \"lte\": "+lte+"\r\n" + 
    			"	            }  \r\n" + 
    			"	          }  \r\n" + 
    			"	        },\r\n" + 
    			"			{\r\n" + 
    			"	         \"match_phrase\": {\r\n" + 
    			"	           \"PageURL\": \""+itfFilter+"\"\r\n" + 
    			"	         }\r\n" + 
    			"	        },\r\n" +
    			"	        {\r\n" + 
    			"	          \"query_string\": {\r\n" + 
    			"	            \"analyze_wildcard\": true,\r\n" + 
    			"	            \"query\": \""+queryNames+"\"\r\n" + 
    			"	          }\r\n" + 
    			"	        }\r\n" + 
    			"	      ]  \r\n" + 
    			"	    }  \r\n" + 
    			"	  },\r\n" + 
    			"	 \"aggs\": {\r\n" + 
    			"	   \"1\": {\r\n" + 
    			"	     \"terms\": {\r\n" + 
    			"	       \"field\": \"User\"\r\n" + 
    			"	     },\r\n" + 
    			"	     \"aggs\": {\r\n" + 
    			"	       \"2\": {\r\n" + 
    			"	         \"terms\": {\r\n" + 
    			"	           \"field\": \"VisitCustomVariable2Value.keyword\"\r\n" + 
    			"	         },\r\n" + 
    			"	         \"aggs\": {\r\n" + 
    			"	           \"3\": {\r\n" + 
    			"	             \"terms\": {\r\n" + 
    			"	               \"field\": \"PageCustomVariable2Value.keyword\"\r\n" + 
    			"	             },\r\n" + 
    			"	             \"aggs\": {\r\n" + 
    			"	               \"4\": {\r\n" + 
    			"	                 \"terms\": {\r\n" + 
    			"	                   \"field\": \"Geography.keyword\"\r\n" + 
    			"	                 },\r\n" + 
    			"	                 \"aggs\": {\r\n" + 
    			"	                   \"5\": {\r\n" + 
    			"	                     \"terms\": {\r\n" + 
    			"	                       \"field\": \"Country.keyword\"\r\n" + 
    			"	                     },\r\n" + 
    			"	                     \"aggs\": {\r\n" + 
    			"	                       \"6\": {\r\n" + 
    			"	                         \"terms\": {\r\n" + 
    			"	                           \"field\": \"CareerLevel\"\r\n" + 
    			"	                         },\r\n" + 
    			"	                         \"aggs\": {\r\n" + 
    			"	                           \"7\": {\r\n" + 
    			"	                             \"terms\": {\r\n" + 
    			"	                               \"field\": \"CareerTracks\"\r\n" + 
    			"	                             },\r\n" + 
    			"	                             \"aggs\": {\r\n" + 
    			"	                               \"8\": {\r\n" + 
    			"	                                 \"cardinality\": {\r\n" + 
    			"	                                   \"field\": \"Visits.hash\",\r\n" + 
    			"	                                   \"precision_threshold\": 4000\r\n" + 
    			"	                                 }\r\n" + 
    			"	                               },\r\n" + 
    			"	                               \"9\":{\r\n" + 
    			"	                                 \"cardinality\": {\r\n" + 
    			"	                                   \"field\": \"Hits.hash\",\r\n" + 
    			"	                                   \"precision_threshold\": 4000\r\n" + 
    			"	                                 }\r\n" + 
    			"	                               },\r\n" +
    			"								   \"10\":{\r\n" + 
    			"	                                 \"sum\": {\r\n" + 
    			"	                                   \"field\": \"SumTimeSpent\"\r\n" + 
    			"	                                 }\r\n" + 
    			"	                               }\r\n" +
    			"	                             }\r\n" + 
    			"	                           }\r\n" + 
    			"	                         }\r\n" + 
    			"	                       }\r\n" + 
    			"	                     }\r\n" + 
    			"	                   }\r\n" + 
    			"	                 }\r\n" + 
    			"	               }\r\n" + 
    			"	             }\r\n" + 
    			"	           }\r\n" + 
    			"	         }\r\n" + 
    			"	       }\r\n" + 
    			"	     }\r\n" + 
    			"	   }\r\n" + 
    			"	 }\r\n" + 
    			"}";
    	return payload;
    }
    
    public String addFeedbakcs(JSONObject o) {
    	JSONObject insertObj = (JSONObject) o.get("insertObj");
    	
    	String payload = "{\r\n" + 
    			"      	\"user_eid\": \""+insertObj.get("eid")+"\",\r\n" + 
    			"      	\"user_airid\": \""+insertObj.get("airid")+"\",\r\n" + 
    			"      	\"user_id\": \""+insertObj.get("id")+"\",\r\n" + 
    			"		\"rating_module\": \""+insertObj.get("ratingModule")+"\",\r\n" + 
    			"		\"rating_score\": \""+insertObj.get("ratingScore")+"\",\r\n" + 
    			"		\"rating_comment\": \""+insertObj.get("ratingComment")+"\",\r\n" + 
    			"      	\"status\": \""+insertObj.get("status")+"\",\r\n" + 
    			"      	\"created_date\": \""+insertObj.get("createdDate")+"\",\r\n" + 
    			"      	\"last_update_date\": \""+insertObj.get("updatedDate")+"\",\r\n" + 
    			"      	\"last_update_by\": \""+insertObj.get("updatedBy")+"\"\r\n" + 
    			"}";
    	
    	return payload;
    }
    
    public String getTotalReadAnnounce(String name, String now) {
    	String payload = "{\n" + 
    			"  \"size\": 0,\n" + 
    			"  \"query\": {\n" + 
    			"    \"bool\": {\n" + 
    			"      \"must\": [\n" + 
    			"        {\n" + 
    			"          \"term\": {\n" + 
    			"            \"history_user_eid\": {\n" + 
    			"              \"value\": \""+name+"\"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        },\n" +
    			"		{\r\n" + 
    			"          \"term\": {\r\n" + 
    			"            \"history_action_type\": {\r\n" + 
    			"              \"value\": \"read\"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        },\r\n" +
    			"		{\r\n" + 
    			"          \"range\": {\r\n" + 
    			"            \"history_due_date\": {\r\n" + 
    			"              \"gte\": "+now+"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        }\r\n" +
    			"      ]\n" + 
    			"    }\n" + 
    			"  },\n" + 
    			"  \"aggs\": {\n" + 
    			"    \"readCount\": {\n" + 
    			"      \"value_count\": {\n" + 
    			"        \"field\": \"history_action_type\"\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  }\n" + 
    			"}";
    	
    	return payload;
    }
    
    
    public String getTotalReadAnnouncePerUser(String name, String id) {
    	String payload = "{\n" + 
    			"  \"size\": 0,\n" + 
    			"  \"query\": {\n" + 
    			"    \"bool\": {\n" + 
    			"      \"must\": [\n" + 
    			"        {\n" + 
    			"          \"term\": {\n" + 
    			"            \"history_user_eid\": {\n" + 
    			"              \"value\": \""+name+"\"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        },\n" +
    			"		{\r\n" + 
    			"          \"term\": {\r\n" + 
    			"            \"history_action_type\": {\r\n" + 
    			"              \"value\": \"read\"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        }\r\n" +
    			"      ],\n" +
    			"      \"filter\": {\r\n" + 
    			"        \"term\": {\r\n" + 
    			"          \"history_doc_id\": \""+id+"\"\r\n" + 
    			"        }\r\n" + 
    			"      }\r\n" +
    			"    }\n" + 
    			"  },\n" + 
    			"  \"aggs\": {\n" + 
    			"    \"readCount\": {\n" + 
    			"      \"value_count\": {\n" + 
    			"        \"field\": \"history_action_type\"\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  }\n" + 
    			"}";
    	
    	return payload;
    }
    public String getTotalAllReadAnnounce(String Id) {
    	String payload = "{\n" + 
    			"  \"size\": 0,\n" + 
    			"  \"query\": {\n" + 
    			"    \"bool\": {\n" + 
    			"      \"must\": [\n" + 
    			"        {\n" + 
    			"          \"term\": {\n" + 
    			"            \"history_doc_id\": {\n" + 
    			"              \"value\": \""+Id+"\"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        },\n" + 
    			"        {\n" + 
    			"          \"term\": {\n" + 
    			"            \"history_action_type\": {\n" + 
    			"              \"value\": \"read\"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        }\n" + 
    			"      ]\n" + 
    			"    }\n" + 
    			"  },\n" + 
    			"  \"aggs\": {\n" + 
    			"    \"readCountAll\": {\n" + 
    			"      \"value_count\": {\n" + 
    			"        \"field\": \"history_action_type\"\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  }\n" + 
    			"}";
    	
    	return payload;
    }

    public String getTotalAnnounceDueDate() {
    	String payload = "{\n" + 
    			"  \"size\": 0,\n" + 
    			"  \"query\": {\n" + 
    			"    \"bool\": {\n" + 
    			"      \"must\": [\n" + 
    			"		{\r\n" + 
    			"            \"term\": {\r\n" + 
    			"              \"announcement_status\": {\r\n" + 
    			"                \"value\": \"live\"\r\n" + 
    			"              }\r\n" + 
    			"            }\r\n" + 
    			"          },\r\n" +
    			"        {\n" + 
    			"          \"range\": {\n" + 
    			"            \"announcement_due_date\": {  \n" + 
    			"              \"gte\": "+kusinaDateUtils.getNowDateToMillis()+"\n" + 
    			"            }\n" + 
    			"          }\n" + 
    			"        }\n" + 
    			"      ]\n" + 
    			"    }\n" + 
    			"  },\n" + 
    			"  \"aggs\": {\n" + 
    			"    \"AnnounceCountBeforeDueDate\": {\n" + 
    			"      \"value_count\": {\n" + 
    			"        \"field\": \"announcement_type\"\n" + 
    			"      }\n" + 
    			"    }\n" + 
    			"  }\n" + 
    			"}";
    	
    	return payload;
    }
    
    public String addAnnouncements(JSONObject o) {
    	JSONObject insertObj = (JSONObject) o.get("insertObj");
    	
    	String payload = "{\r\n" + 
    			"		\"announcement_type\": \""+insertObj.get("type")+"\",\r\n" + 
    			"       \"announcement_due_date\": \""+insertObj.get("dueDate")+"\",\r\n" + 
    			"       \"announcement_title\": \""+insertObj.get("title")+"\",\r\n" + 
    			"		\"announcement_content\": \""+insertObj.get("content")+"\",\r\n" + 
    			"		\"announcement_status\": \""+insertObj.get("status")+"\",\r\n" + 
    			"		\"announcement_created_date\": \""+insertObj.get("createdDate")+"\",\r\n" + 
    			"       \"announcement_last_updated_date\": \""+insertObj.get("updatedDate")+"\",\r\n" + 
    			"       \"announcement_last_updated_by\": \""+insertObj.get("updatedBy")+"\"\r\n" + 
    			"} ";
    	
    	return payload;
    }
    
    public String editAnnouncements(JSONObject o) {
    	JSONObject insertObj = (JSONObject) o.get("insertObj");
    	
    	String payload = "{\n" + 
    			"  \"doc\":\n" + 
    			"    {\n" + 
    			"		\"announcement_type\": \""+insertObj.get("type")+"\",\r\n" + 
    			"       \"announcement_due_date\": \""+insertObj.get("dueDate")+"\",\r\n" + 
    			"       \"announcement_title\": \""+insertObj.get("title")+"\",\r\n" + 
    			"		\"announcement_content\": \""+insertObj.get("content")+"\",\r\n" + 
    			"		\"announcement_status\": \""+insertObj.get("status")+"\",\r\n" + 
    			"		\"announcement_created_date\": \""+insertObj.get("createdDate")+"\",\r\n" + 
    			"       \"announcement_last_updated_date\": \""+insertObj.get("updatedDate")+"\",\r\n" + 
    			"       \"announcement_last_updated_by\": \""+insertObj.get("updatedBy")+"\"\r\n" + 
    			"    } \n" + 
    			"}";
    	
    	return payload;
    }
    
    public String getHistoryId(String id) {
    	String payload = "{\r\n" + 
    			"  \"query\": {\r\n" + 
    			"    \"bool\": {\r\n" + 
    			"      \"must\": [\r\n" + 
    			"        {\r\n" + 
    			"          \"term\": {\r\n" + 
    			"            \"history_doc_id\": {\r\n" + 
    			"              \"value\": \""+id+"\"\r\n" + 
    			"            }\r\n" + 
    			"          }\r\n" + 
    			"        }\r\n" + 
    			"      ]\r\n" + 
    			"    }\r\n" + 
    			"  }\r\n" + 
    			"}";
    	return payload;
    }
    
    public String addProfile(JSONObject o) {
    	JSONObject insertObj = (JSONObject) o.get("insertObj");
    	
    	String payload = "{\r\n" + 
    			"	\"AIRID\": \""+insertObj.get("airid")+"\",\r\n" + 
    			"   \"ID\": \""+insertObj.get("idSite")+"\",\r\n" + 
    			"	\"APPNAME\": \""+insertObj.get("appName")+"\",\r\n" + 
    			"	\"created_date\": \""+insertObj.get("createdDate")+"\",\r\n" + 
    			"	\"last_update_date\": \""+insertObj.get("updatedDate")+"\",\r\n" + 
    			"	\"last_update_by\": \""+insertObj.get("updatedBy")+"\"\r\n" + 
    			"}";
    	
    	return payload;
    }
    
    public String editProfile(JSONObject o) {
    	JSONObject insertObj = (JSONObject) o.get("insertObj");
    	
    	String payload = "{\n" + 
    			"  \"doc\":\n" + 
    			"    {\n" + 
    			"		\"AIRID\": \""+insertObj.get("airid")+"\",\r\n" + 
    			"   	\"ID\": \""+insertObj.get("idSite")+"\",\r\n" + 
    			"		\"APPNAME\": \""+insertObj.get("appName")+"\",\r\n" + 
    			"		\"created_date\": \""+insertObj.get("createdDate")+"\",\r\n" + 
    			"		\"last_update_date\": \""+insertObj.get("updatedDate")+"\",\r\n" + 
    			"		\"last_update_by\": \""+insertObj.get("updatedBy")+"\"\r\n" + 
    			"    } \n" + 
    			"}";
    	
    	return payload;
    }
    
    public String getProfile(String from, String size, String search) {
    	String payload = null;
    	String searchQuery = kusinaStringUtils.convertSearchToParams(search);
    	if(search.equalsIgnoreCase("*")) {
    		 payload = "{\r\n" + 
            		"  \"from\": "+from+", \r\n" + 
            		"  \"size\": "+size+",\r\n" + 
            		"  \"query\": {\r\n" + 
            		"    \"match_all\": {}\r\n" + 
            		"  }\r\n" + 
            		"}";
    	}else {
    		 payload = "{\r\n" + 
    		 		"  \"from\": "+from+", \r\n" + 
    		 		"  \"size\": "+size+",\r\n" + 
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"          {\r\n" + 
    		 		"          \"query_string\": {\r\n" + 
    		 		"            \"analyze_wildcard\": true,\r\n" + 
    		 		"            \"query\": \""+searchQuery+"\",\r\n" + 
    		 		"            \"fields\": [\"AIRID\", \"ID\", \"APPNAME\", \"created_date\", \"last_update_date\", \"last_update_by\"]\r\n" + 
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	}
        return payload;
    }
    
    public String getAllAirid() {
    	String payload = null;
    	
    		 payload = "{\r\n" + 
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": []\r\n" + 
    		 		"    }\r\n" + 
    		 		"  },\r\n" + 
    		 		"    \"aggs\": {\r\n" + 
    		 		"    \"1\": {\r\n" + 
    		 		"      \"terms\": {\r\n" + 
    		 		"        \"field\": \"AIRID\",\r\n" + 
    		 		"        \"size\": 20000\r\n" + 
    		 		"      } \r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	
        return payload;
    }
    
    public String getSiteId(String search) {
    	String payload = null;
    	
    		 payload = "{\r\n" + 
    		 		"  \"query\": {\r\n" + 
    		 		"    \"bool\": {\r\n" + 
    		 		"      \"must\": [\r\n" + 
    		 		"        {\r\n" + 
    		 		"          \"query_string\": {\r\n" + 
    		 		"            \"analyze_wildcard\": true,\r\n" + 
    		 		"            \"query\": \""+search+"\"\r\n" +
    		 		"          }\r\n" + 
    		 		"        }\r\n" + 
    		 		"      ]\r\n" + 
    		 		"    }\r\n" + 
    		 		"  },\r\n" + 
    		 		"  \"aggs\": {\r\n" + 
    		 		"    \"1\": {\r\n" + 
    		 		"      \"terms\": {\r\n" + 
    		 		"            \"field\": \"ID\",\r\n" + 
    		 		"            \"size\": 5000\r\n" + 
    		 		"      }\r\n" + 
    		 		"    }\r\n" + 
    		 		"  }\r\n" + 
    		 		"}";
    	
        return payload;
    }
    
    
}
