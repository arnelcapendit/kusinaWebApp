package com.accenture.api.utils;

import java.util.Arrays;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaValidationUtils {
    
     public boolean isAggregation(String reportType) {

        return Arrays.asList(
                "careerTracksBySegmentName",
                "careerLevelBySegmentName",
                "careerLevelDescriptionBySegmentName",
                "geographyBySegmentName",
                "hitsByAsset",
                "hitsByGeography").contains(reportType);
    }

    public boolean isBySegmentName(String reportType) {

        return Arrays.asList(
                "careerTracksBySegmentName",
                "careerLevelBySegmentName",
                "careerLevelDescriptionBySegmentName",	
                "geographyBySegmentName").contains(reportType);
    }

    public boolean isValidReportType(String reportType) {

        return Arrays.asList(
                "hitsByAsset",
                "hitsByGeography",
                "pageCustomInfo",
                "careerTracksBySegmentName",
                "careerLevelBySegmentName",
                "careerLevelDescriptionBySegmentName",
                "geographyBySegmentName").contains(reportType);
    }

    public boolean isValidExportReportType(String reportType) {
        return Arrays.asList(
                "hitsByAsset",
                "hitsByGeography",
                "pageCustomInfo",
                "careerTracksBySegmentName",
                "careerLevelBySegmentName",
                "careerLevelDescriptionBySegmentName",
                "geographyBySegmentName",
                "userMetrics",
                "pageMetrics",
                "referrersMetrics",
                "downloadMetrics",
                "userUsage",
                "visitorLogs",
                "user",
                "myTE",
                "careerLevel",
                "careerTracks",
                "geography",
                "events",
                "userUsage",
                "careerLevelDescriptionUsage",
                "careerTracksUsage",
                "geographyUsage",
                "userPages",
                "careerLevelPages",
                "careerTracksPages",
                "geographyPages",
                "userOverview",
                "pageOverview",
                "referrerOverview",
                "downloadOverview",
                "actionEvents",
                "users",
                "profile",
                "feedbacks",
                "itfFulfillment",
    			"itfNotification",
    			"itfComments").contains(reportType);

    }

    public String getFieldArgumentAIBSPBVI(String reportType) {
        return reportType.equalsIgnoreCase("careerLevelDescriptionBySegmentName") ? "CareerLevelDescription" : "Geography.keyword";
    }
    
    public String getFieldArgument(String reportType) {
        return reportType.equalsIgnoreCase("careerLevelBySegmentName") ? "CareerLevel" : "Geography.keyword";
    }
    
    public boolean isValidPagesType(String reportType) {

        return Arrays.asList(
                "userPages",
                "careerLevelPages",
                "careerTracksPages",
                "referrersMetrics", 		//For export validation only...
                "downloadMetrics",  		//For export validation only...
                "careerLevelDescription", 	//For parent description of careerlevel...	
                "geographyPages").contains(reportType);
    }
    
    public boolean isValidUsageType(String reportType) {

        return Arrays.asList(
                "userUsage",
                "careerLevelDescriptionUsage",
                "careerTracksUsage",
                "geographyUsage").contains(reportType);
    }
    
    public boolean isValidOverviewType(String reportType) {

        return Arrays.asList(
                "userOverview",
                "pageOverview",
                "referrerOverview",
                "downloadOverview").contains(reportType);
    }
    
    public boolean isValidITFType(String reportType) {
    	return Arrays.asList(
    			"itfFulfillment",
    			"itfNotification",
    			"itfComments"
    			).contains(reportType);
    }
    
    public boolean isValidOverviewChildType(String reportType) {

        return Arrays.asList(
                "referrerOverviewChild",
                "downloadOverviewChild").contains(reportType);
    }
    
    public boolean isValidPaginationType(String reportType) {

        return Arrays.asList(
                "usageReportPagination",
                "pagesReportPagination",
                "AIBSPBVIreportsPagination",
                "overviewReportsPagination",
                "overviewReportsPaginationChild",
                "eventsPagination",
                "itfReportsPagination").contains(reportType);
    }
    
    public boolean isValidEventsType(String reportType) {

        return Arrays.asList(
                "categoryEvents",
                "actionEvents",
                "nameEvents").contains(reportType);
    }
    
    
    public String modifyPagesFilter(Object str) {

        if (str != null) { 
        	if(isValidOverviewType(str.toString())) {
        		return convertOverviewReportTypeToFilterField(str.toString());
        	}else if(isValidITFType(str.toString())) {
        		return "User";
        	}
            switch (str.toString()) {
                case "user":
                    return "User";
                case "careerLevel":
                    return "CareerLevel";
                case "careerTracks":
                    return "CareerTracks";
                case "geography":
                    return "Geography.keyword";
                case "careerLevelDescription":
                	return "CareerLevelDescription";
                case "careerTrackDescription":
                	return "CareerTracksDescription.keyword";
                case "country":
                	return "Country.keyword";
                case "pageURL":
                	return "PageURL.keyword";
                case "pageCustomVariable1Value":
                	return "PageCustomVariable1Value.keyword";
                case "categoryEvents":
            		return "EventCategory";
            	case "actionEvents":
            		return "EventAction";
            	case "nameEvents":
            		return "EventName";
            	case "hits":
            		return "Hits";
                default:
    	    		break;
            }
        }
        return null;

    }
    
    public String getFieldArgument(String reportType, boolean hasKeyword) {

        return hasKeyword ? reportType + ".keyword" : reportType;
    }
    
    public String modifyUsageSearch(Object str) {
    	if(str != null) {
    		return str.toString();
    	} else {
    		return "*";
    	}
    }
    
    public String getFieldNameMetrics(String metrics) {
    	
    	switch(metrics) {
	    	case "usageParent":
	    		return "User,"
	    			 + "CareerTracks,"
	    			 + "CareerLevelDescription,"
	    			 + "Geography.keyword";
	    		
	    	case "usageChild":
	    		return "CareerTracksDescription.keyword,"
	    			 + "CareerLevel,"
	    			 + "Country.keyword";
	    	
	    	case "geographyBySegmentName":
	    		return "PageCustomVariable1Value.keyword,"
	    			 + "Geography.keyword";
	    				
	    	case "careerLevelDescriptionBySegmentName":
	    		return "PageCustomVariable1Value.keyword,"
	    			 + "CareerLevelDescription";
	    	
	    	case "careerTracksBySegmentName":
	    		return "PageCustomVariable1Value.keyword,"
	    			 + "CareerTracks,"
	    			 + "PageCustomVariable4Value.keyword";
	    	
	    	case "hitsByGeography":
	    		return "PageCustomVariable1Value.keyword,"
	    			 + "Geography.keyword";
	    	
	    	case "hitsByAsset":
	    		return "PageCustomVariable1Value.keyword,"
	    			 + "PageCustomVariable4Value.keyword,"
	    		     + "PageCustomVariable3Value.keyword";
	    		
	    	default: 
	    		break;
    	}
    	return null;	
    }
    
    public String convertOverviewReportTypeToFilterField(String report) {
    	switch(report) {
    	case "userOverview":
    		return "User";
    	case "pageOverview":
    		return "CleanPageURL.keyword";
    	case "referrerOverview":
    		return "RefererURL";
    	case "downloadOverview":
    		return "CleanPageURL.keyword";
    	case "referrerOverviewChild":
    		return "User";
    	case "downloadOverviewChild":
    		return "User";
    	case "categoryEvents":
    		return "EventCategory";
    	case "actionEvents":
    		return "EventAction";
    	case "nameEvents":
    		return "EventName";
    	default:
    		break;
    	}

    	return null;
    }
    
    public boolean isValidSearchQueryType(String reportType) {
    	return Arrays.asList(
    			"careerLevelDescriptionUsage",
    			"careerTracksUsage",
    			"careerTracksPages"
    			).contains(reportType);
    }
    
    public String convertReportTypeToITFFilter(String reportType) {
    	switch(reportType) {
    	case "itfFulfillment":
    		return "colabapi-service/activityfeedbystakeholder";
    	case "itfNotification":
    		return "colabapi-service/commondemandattributes";
    	case "itfComments":
    		return "colabapi-service/commententered";
    	default:
    		break;
    	}
    	return null;
    }
   
}
