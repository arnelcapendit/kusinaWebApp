package com.accenture.api.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.api.model.AirIdModel;
import com.accenture.api.model.AnnounceModel;
import com.accenture.api.model.AppNameFilter;
import com.accenture.api.model.EventsModel;
import com.accenture.api.model.FeedBackModel;
import com.accenture.api.model.HistoryModel;
import com.accenture.api.model.HitsModel;
import com.accenture.api.model.ITFModel;
import com.accenture.api.model.MyTEModel;
import com.accenture.api.model.OverviewDownloadMetrics;
import com.accenture.api.model.OverviewPageMetrics;
import com.accenture.api.model.OverviewReferrersMetrics;
import com.accenture.api.model.OverviewUserMetrics;
import com.accenture.api.model.PageCustomInfoModel;
import com.accenture.api.model.PagesModel;
import com.accenture.api.model.ProfileModel;
import com.accenture.api.model.SegmentModel;
import com.accenture.api.model.SiteIdModel;
import com.accenture.api.model.UserModel;
import com.accenture.api.model.UserUsageModel;
import com.accenture.api.model.VisitOverTimeFilter;
import com.accenture.api.model.VisitorLogsModel;
import com.accenture.api.model.VisualizationFilter;
import com.accenture.api.service.HistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaExtractionUtils {

    @Autowired
    private KusinaStringUtils kusinaStringUtils;

    @Autowired
    private KusinaDateUtils kusinaDateUtils;
    
    @Autowired
    private KusinaNumberFormat kusinaNumberFormat;
    
    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;
    
    @Autowired
    private HistoryService historyService;

    private DecimalFormat df = new DecimalFormat("###.##");

	private ObjectMapper mapper;

	private Object mainList1;

    public String getDocumentId(String response) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject mainObj = (JSONObject) parser.parse(response);
        JSONObject hitsObj = (JSONObject) mainObj.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        System.out.println("hits size: " + innerHits.size());
        String _id = null;
        if (innerHits.size() > 0) {
            JSONObject hit = (JSONObject) innerHits.get(0);
            _id = hit.get("_id").toString();
        }

        return _id;
    }

    public String getResult(String response) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject mainObj = (JSONObject) parser.parse(response);

        return mainObj.get("result").toString();
    }

    public JSONObject getHitsDocument(String response) throws ParseException {

        JSONParser parser = new JSONParser();

        JSONObject mainObj = (JSONObject) parser.parse(response);
        JSONObject hitsObj = (JSONObject) mainObj.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        System.out.println("hits size: " + innerHits.size());
        JSONObject doc = null;
        if (innerHits.size() > 0) {
            JSONObject hit = (JSONObject) innerHits.get(0);
            doc = (JSONObject) hit.get("_source");
        }
        return doc;
    }

    public JSONObject getAggregationDocument(String response) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject mainObj = (JSONObject) parser.parse(response);

        return mainObj;
    }

    public JSONObject getReportHitsDocument(String response) throws ParseException {
        JSONParser parser = new JSONParser();

        JSONObject mainObj = (JSONObject) parser.parse(response);
        JSONObject hitsObj = (JSONObject) mainObj.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        JSONObject responseObj = new JSONObject();
        JSONArray doc = new JSONArray();
        for (Object obj : innerHits) {
            JSONObject o = (JSONObject) obj;
            doc.add(o.get("_source"));
        }
        responseObj.put("resultset", doc);

        return responseObj;
    }
    
    
    public JSONObject extractFilterDisplay(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject two = (JSONObject) aggs.get("2");
        JSONArray two_buckets = (JSONArray) two.get("buckets");

        JSONObject finObject = new JSONObject();

        List<AppNameFilter> apL = new ArrayList<>();
        AppNameFilter ap;

        for (Object two_bucket : two_buckets) {
            JSONObject two_obj = (JSONObject) two_bucket;
            JSONObject three = (JSONObject) two_obj.get("3");
            JSONArray three_buckets = (JSONArray) three.get("buckets");
            for (Object three_bucket : three_buckets) {
                JSONObject three_obj = (JSONObject) three_bucket;
                JSONObject four = (JSONObject) three_obj.get("4");
                JSONArray four_buckets = (JSONArray) four.get("buckets");
                for (Object four_bucket : four_buckets) {
                    JSONObject four_obj = (JSONObject) four_bucket;
                    ap = new AppNameFilter();
                    ap.setAirId(three_obj.get("key").toString());
                    ap.setAppName(four_obj.get("key").toString());
                    ap.setId(two_obj.get("key").toString());
                    apL.add(ap);
                }
            }
        }

        try {
            String finStr = mapper.writeValueAsString(apL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("AppNameList", convertedObj);
            finObject.put("id", apL.get(0).getId());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finObject;
    }

    public Object getChartVisitOvertime(JSONObject aggs, String timezoneOffset) {

        ObjectMapper mapper = new ObjectMapper();
        JSONObject two = (JSONObject) aggs.get("2");
        JSONArray two_buckets = (JSONArray) two.get("buckets");

        List<VisitOverTimeFilter> visitOTL = new ArrayList<>();
        VisitOverTimeFilter vis;
        for (Object two_bucket : two_buckets) {

            JSONObject two_obj = (JSONObject) two_bucket;
            JSONObject one = (JSONObject) two_obj;
            JSONObject aggs_one = (JSONObject) two_obj.get("1");
            JSONObject aggs_three = (JSONObject) two_obj.get("3");

            vis = new VisitOverTimeFilter();

            vis.setKey((long) two_obj.get("key"));
            vis.setDoc_count((long) two_obj.get("doc_count"));
            vis.setKey_as_string(one.get("key_as_string").toString());
            vis.setKey_as_string(kusinaDateUtils.formatDate((long) two_obj.get("key"),timezoneOffset));
            vis.setUser((long) aggs_three.get("value"));
            vis.setVisits((long) aggs_one.get("value"));

            visitOTL.add(vis);	
        }

        try {
            String finStr = mapper.writeValueAsString(visitOTL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            return convertedObj;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Object getWebMetrics(JSONObject aggs) {

        JSONObject one = (JSONObject) aggs.get("1");

//        return one != null ? df.format(one.get("value")) : one.get("value");
        return one.get("value");
    }

    public Object getChartVisualization(JSONObject aggs) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject two = (JSONObject) aggs.get("2");
        JSONArray two_buckets = (JSONArray) two.get("buckets");

        List<VisualizationFilter> cvL = new ArrayList<>();
        VisualizationFilter vis;
        for (Object two_bucket : two_buckets) {

            JSONObject two_obj = (JSONObject) two_bucket;
            JSONObject one = (JSONObject) two_obj.get("1");
            vis = new VisualizationFilter();

            vis.setDoc_count((long) two_obj.get("doc_count"));
            vis.setHash((long) one.get("value"));
            vis.setKey(two_obj.get("key").toString());

            cvL.add(vis);
        }

        try {
            String finStr = mapper.writeValueAsString(cvL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            return convertedObj;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Object getUniquePageUrlList(JSONObject aggs) {

        JSONObject two = (JSONObject) aggs.get("2");
        JSONArray two_buckets = (JSONArray) two.get("buckets");

        List<String> appUrlList = new ArrayList<>();
        for (Object two_bucket : two_buckets) {
            JSONObject two_obj = (JSONObject) two_bucket;
            appUrlList.add((String) two_obj.get("key"));
        }
        return appUrlList;
    }

    public Object getUniquePageUrl(JSONObject aggs) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject two = (JSONObject) aggs.get("2");
        JSONArray two_buckets = (JSONArray) two.get("buckets");

        JSONObject finObject = new JSONObject();

        List<MyTEModel> myteList = new ArrayList<>();

        for (Object two_bucket : two_buckets) {
            JSONObject two_obj = (JSONObject) two_bucket;
            JSONObject three = (JSONObject) two_obj.get("3");
            JSONArray three_buckets = (JSONArray) three.get("buckets");
            for (Object three_bucket : three_buckets) {
                JSONObject three_obj = (JSONObject) three_bucket;
                JSONObject one = (JSONObject) three_obj.get("1");
                JSONArray one_buckets = (JSONArray) one.get("buckets");
                
                for (Object one_bucket : one_buckets) {
                	JSONObject one_obj = (JSONObject) three_bucket;
                	// Page URL
                    Object Visits = one_obj.get("key");
                    // Country
                    Object User = two_obj.get("key");
                    // Average General Time
                    Object Address = three_obj.get("key");
                    
                    
                    MyTEModel uum = new MyTEModel();
                    
                    uum.setPageUrl(Visits.toString());
                    uum.setGeography(User.toString());
                    uum.setAvgGenTime(Address.toString());
                    
                    myteList.add(uum); 
                	
                }
            }
        }

        try {
            String finStr = mapper.writeValueAsString(myteList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }

    public Object getUsageByUsers(JSONObject aggs) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject two = (JSONObject) aggs.get("2");
        JSONArray two_buckets = (JSONArray) two.get("buckets");

        JSONObject finObject = new JSONObject();
        List<UserUsageModel> uumL = new ArrayList<>();
        for (Object two_bucket : two_buckets) {
            JSONObject two_obj = (JSONObject) two_bucket;
            // Unique Visits Count
            JSONObject six = (JSONObject) two_obj.get("6");
            // Unique Hits Count
            JSONObject seven = (JSONObject) two_obj.get("7");

            // PageTitle
            JSONObject three = (JSONObject) two_obj.get("3");
            JSONArray three_buckets = (JSONArray) three.get("buckets");

            int size = three_buckets.size();
            double totalElapseTime = 0;
            int totalVisits = 0;
            JSONObject three_obj;
            for (Object three_bucket : three_buckets) {
                three_obj = (JSONObject) three_bucket;

                // TotalElapseTimePerVisit
                JSONObject four = (JSONObject) three_obj.get("4");
                // VisitPerPage
                JSONObject five = (JSONObject) three_obj.get("5");

                totalElapseTime += Double.parseDouble(four.get("value").toString());
                totalVisits += Integer.parseInt(five.get("value").toString());
            }
            uumL.add(new UserUsageModel(
                    two_obj.get("key").toString(),
                    six.get("value").toString(),
                    String.valueOf(df.format(totalElapseTime / size)),
                    String.valueOf(totalVisits / size),
                    seven.get("value").toString()));
        }

        try {
            String finStr = mapper.writeValueAsString(uumL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }

    public Object getCustomPages(JSONObject aggs, String filter) {
        ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        List<PagesModel> pmL = null;
        List<PagesModel> pmoL = null;
        List<PagesModel> mainList = new ArrayList<>();

        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;

            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");

            pmL = new ArrayList<>();
            pmoL = new ArrayList<>();

            int twoBuckeySize = 0;
            for (Object two_bucket : two_buckets) {

                JSONObject two_obj = (JSONObject) two_bucket;
                // Unique Visits Count
                JSONObject six = (JSONObject) two_obj.get("6");
                // Unique Hits Count
                JSONObject seven = (JSONObject) two_obj.get("7");

                // PageTitle
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONArray three_buckets = (JSONArray) three.get("buckets");

                int size = three_buckets.size();
                double totalElapseTime = 0;
                int totalVisits = 0;
                JSONObject three_obj;
                for (Object three_bucket : three_buckets) {
                    three_obj = (JSONObject) three_bucket;

                    // TotalElapseTimePerVisit
                    JSONObject four = (JSONObject) three_obj.get("4");
                    // VisitPerPage
                    JSONObject five = (JSONObject) three_obj.get("5");

                    totalElapseTime += Double.parseDouble(four.get("value").toString());
                    totalVisits += Integer.parseInt(five.get("value").toString());
                }

                PagesModel pm = new PagesModel();
                pm.setAvgPagesPerVisit(String.valueOf(totalVisits / size));
                pm.setAvgVisit(String.valueOf(df.format(totalElapseTime / size)));
                pm.setVisits(six.get("value").toString());
                pm.setHits(seven.get("value").toString());

                if (filter.equalsIgnoreCase("user")) {
                    pm.setUser(two_obj.get("key").toString());
                } else if (filter.equalsIgnoreCase("careerLevel")) {
                    pm.setCareerLevel(two_obj.get("key").toString());
                } else if (filter.equalsIgnoreCase("careerTracks")) {
                    pm.setCareerTracks(two_obj.get("key").toString());
                } else if (filter.equalsIgnoreCase("geography")) {
                    pm.setGeography(two_obj.get("key").toString());
                }

                if (twoBuckeySize <= 50) {
                    pmL.add(pm);
                } else {
                    pmoL.add(pm);
                }
                twoBuckeySize++;
            }// 2 buckets for loop 

            int otherListSize = pmoL.size();

            // limit subrecord into 50 rows
            if (otherListSize > 0) {

                int otherAvgTotalElapseTime = 0;
                double otherAvgTotalVisits = 0;
                int otherSumOfVisits = 0;
                int otherSumOfHits = 0;

                for (PagesModel page : pmoL) {
                    otherAvgTotalElapseTime += Integer.parseInt(page.getAvgPagesPerVisit());
                    otherAvgTotalVisits += Double.parseDouble(page.getAvgVisit());
                    otherSumOfVisits += Integer.parseInt(page.getVisits());
                    otherSumOfHits += Integer.parseInt(page.getHits());
                }

                PagesModel otherPM = new PagesModel();
                otherPM.setAvgPagesPerVisit(String.valueOf(otherAvgTotalElapseTime / otherListSize));
                otherPM.setAvgVisit(String.valueOf(df.format(otherAvgTotalVisits / otherListSize)));
                otherPM.setHits(String.valueOf(otherSumOfHits));
                otherPM.setVisits(String.valueOf(otherSumOfVisits));
                if (filter.equalsIgnoreCase("user")) {
                    otherPM.setUser("Others");
                } else if (filter.equalsIgnoreCase("careerLevel")) {
                    otherPM.setCareerLevel("Others");
                } else if (filter.equalsIgnoreCase("careerTracks")) {
                    otherPM.setCareerTracks("Others");
                } else if (filter.equalsIgnoreCase("geography")) {
                    otherPM.setGeography("Others");
                }

                pmL.add(otherPM);
            }

            int avgTotalElapseTime = 0;
            double avgTotalVisits = 0;
            int sumOfVisits = 0;
            int sumOfHits = 0;

            int listSize = pmL.size();

            for (PagesModel page : pmL) {
                avgTotalElapseTime += Integer.parseInt(page.getAvgPagesPerVisit());
                avgTotalVisits += Double.parseDouble(page.getAvgVisit());
                sumOfVisits += Integer.parseInt(page.getVisits());
                sumOfHits += Integer.parseInt(page.getHits());
            }

            PagesModel mainPM = new PagesModel();
            mainPM.setAvgPagesPerVisit(String.valueOf(avgTotalElapseTime / listSize));
            mainPM.setAvgVisit(String.valueOf(df.format(avgTotalVisits / listSize)));
            mainPM.setHits(String.valueOf(sumOfHits));
            mainPM.setVisits(String.valueOf(sumOfVisits));
            mainPM.setPageURL(one_obj.get("key").toString());
            mainPM.setPageList(pmL);
            mainList.add(mainPM);

        }// 1 for loop buckets

        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    //For getCustomPages modification for viewing parent data only...
    public Object getCustomPagesV2(JSONObject aggs, String filter) {
        ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<PagesModel> pmL = null;
        List<PagesModel> mainList = new ArrayList<>();

        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
           
           // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visits = (JSONObject) one_obj.get("avg_page_visits");	
            
            
            PagesModel pm = new PagesModel();
            pm.setAvgPagesPerVisit(avg_page_visits.get("value").toString());
            pm.setAvgVisit(avg_page_duration.get("value").toString());
            pm.setVisits(sum_visits.get("value").toString());
            pm.setHits(sum_hits.get("value").toString());
            pm.setPageURL(one_obj.get("key").toString());
            
            mainList.add(pm);
            
        }

        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    
  //For getCustomPages modification for viewing child data only...
    public Object getCustomPagesDetails(JSONObject aggs, String filter) {
        ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<PagesModel> pmL = null;
        List<PagesModel> mainList = new ArrayList<>();

        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
           
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visit = (JSONObject) one_obj.get("avg_page_visit");	
            
            
            PagesModel pm = new PagesModel();
            pm.setAvgPagesPerVisit(avg_page_visit.get("value").toString());
            pm.setAvgVisit(avg_page_duration.get("value").toString());
            pm.setVisits(sum_visits.get("value").toString());
            pm.setHits(sum_hits.get("value").toString());
           
            
            if (filter.equalsIgnoreCase("user")) {
                pm.setUser(one_obj.get("key").toString());
            } else if (filter.equalsIgnoreCase("careerLevel")) {
                pm.setCareerLevel(one_obj.get("key").toString());
            } else if (filter.equalsIgnoreCase("careerTracks")) {
                pm.setCareerTracks(one_obj.get("key").toString());
            } else if (filter.equalsIgnoreCase("geography")) {
                pm.setGeography(one_obj.get("key").toString());
            }

            mainList.add(pm);
            
        }

        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    //For UsageByUser modification for viewing parent data only...
    public Object getCustomUsageV2(JSONObject aggs, String filter, String to, String from, String size, String search, String column, String sort) {
        ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<UserUsageModel> mainList = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat(".##");
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
          
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visits = (JSONObject) one_obj.get("avg_page_visits");	
            
            
            UserUsageModel uum = new UserUsageModel();
            uum.setAvgPagesPerVisit(avg_page_visits.get("value").toString());
            uum.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            //uum.setAvgVisit( df2.format(Double.parseDouble(avg_page_duration.get("value").toString())));
            uum.setVisits(sum_visits.get("value").toString());
            uum.setHits(sum_hits.get("value").toString());
            
            switch(filter) {
            case "user":
            	uum.setUser(one_obj.get("key").toString());
            	break;
            case "careerTracks":
            	uum.setCareerTracks(one_obj.get("key").toString());
            	break;
            case "careerLevelDescription":
            	uum.setCareerLevelDescription(one_obj.get("key").toString());
            	break;
            case "geography":
            	uum.setGeography(one_obj.get("key").toString());
            	break;
            default:
            	break;
            }
            mainList.add(uum);   
        }
        
        //Sorting Lists Model Results 
        if(column.equalsIgnoreCase("user")) {
        	if(sort.equalsIgnoreCase("asc")) {
        		System.out.println("SORTED BY USER ASC...");
        		Collections.sort(mainList, UserUsageModel.UserComparatorAsc);
        	}else {	
        		System.out.println("SORTED BY USER DESC...");
        		Collections.sort(mainList, UserUsageModel.UserComparatorDesc);
        	}
       } else if(column.equalsIgnoreCase("visits")) {
    	   if(sort.equalsIgnoreCase("asc")) {
       		System.out.println("SORTED BY VISITS ASC...");
       		Collections.sort(mainList, UserUsageModel.VisitsComparatorAsc);
	       	}else {	
	       		System.out.println("SORTED BY VISITS DESC...");
	       		Collections.sort(mainList, UserUsageModel.VisitsComparatorDesc);
	       	}
       }  else if(column.equalsIgnoreCase("aveVisits")) {
    	   if(sort.equalsIgnoreCase("asc")) {
          		System.out.println("SORTED BY AVE VISITS ASC...");
          		Collections.sort(mainList, UserUsageModel.AveVisitComparatorAsc);
   	       	}else {	
   	       		System.out.println("SORTED BY AVE VISITS DESC...");
   	       		Collections.sort(mainList, UserUsageModel.AveVisitComparatorDesc);
   	       	}
       }  else if(column.equalsIgnoreCase("avePageVisits")) {
    	   if(sort.equalsIgnoreCase("asc")) {
         		System.out.println("SORTED BY AVE PAGE VISITS ASC...");
         		Collections.sort(mainList, UserUsageModel.AvePageVisitComparatorAsc);
  	       	}else {	
  	       		System.out.println("SORTED BY AVE PAGE VISITS DESC...");
  	       		Collections.sort(mainList, UserUsageModel.AvePageVisitComparatorDesc);
  	        }
      }   else if(column.equalsIgnoreCase("hits")) {
    	  	if(sort.equalsIgnoreCase("asc")) {
    		System.out.println("SORTED BY HITS ASC...");
    		Collections.sort(mainList, UserUsageModel.HitsComparatorAsc);
	       	}else {	
	       		System.out.println("SORTED BY HITS DESC...");
	       		Collections.sort(mainList, UserUsageModel.HitsComparatorDesc);
	        }
      } 
       
        //Checking size request
        List<UserUsageModel> uumList = new ArrayList<UserUsageModel>();
        int toIndex = Integer.parseInt(to);
        int fromIndex = Integer.parseInt(from);
        int sizeIndex = Integer.parseInt(size);
        if((toIndex > mainList.size()) || (sizeIndex > mainList.size())) {
        	uumList.addAll(mainList);
        } else {
        	if(search.equalsIgnoreCase("*")) {
        		uumList = mainList.subList(fromIndex, toIndex);	
        	}else {
        		fromIndex = 0;
        		toIndex = 1; 
        		uumList = mainList.subList(fromIndex, toIndex);
        	}
        }
        
        try {
            String finStr = mapper.writeValueAsString(uumList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
          
            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    
    //For getCustomUsage modification for viewing child data only...
    public Object getCustomUsageDetails(JSONObject aggs, String filter, String to, String from, String size, String search, String column, String sort) {
        ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<UserUsageModel> mainList = new ArrayList<>();
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
           
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visit = (JSONObject) one_obj.get("avg_page_visit");	
            
            
            UserUsageModel uum = new UserUsageModel();
            uum.setAvgPagesPerVisit(avg_page_visit.get("value").toString());
            uum.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            uum.setVisits(sum_visits.get("value").toString());
            uum.setHits(sum_hits.get("value").toString());
           
            
            if (filter.equalsIgnoreCase("careerLevelDescription")) {
            	uum.setCareerLevel(one_obj.get("key").toString());	
            } else if (filter.equalsIgnoreCase("careerTracks")) {
            	uum.setCareerTrackDescription(one_obj.get("key").toString());
            } else if (filter.equalsIgnoreCase("geography")) {
            	uum.setCountry(one_obj.get("key").toString());
            }

            mainList.add(uum);
            
        }
        
      //Sorting Lists Model Results 
        if(column.equalsIgnoreCase("user")) {
        	if(sort.equalsIgnoreCase("asc")) {
        		System.out.println("SORTED BY USER ASC...");
        		Collections.sort(mainList, UserUsageModel.UserComparatorAsc);
        	}else {	
        		System.out.println("SORTED BY USER DESC...");
        		Collections.sort(mainList, UserUsageModel.UserComparatorDesc);
        	}
       } else if(column.equalsIgnoreCase("careerTrackDescription")) {
    	   if(sort.equalsIgnoreCase("asc")) {
          		System.out.println("SORTED BY CAREERTRACKDESCRIPTION ASC...");
          		Collections.sort(mainList, UserUsageModel.CareerTrackDescComparatorAsc);
   	       	}else {	
   	       		System.out.println("SORTED BY CAREERTRACKDESCRIPTION DESC...");
   	       		Collections.sort(mainList, UserUsageModel.CareerTrackDescComparatorDesc);
   	       	}
       } else if(column.equalsIgnoreCase("careerLevel")) {
    	   if(sort.equalsIgnoreCase("asc")) {
         		System.out.println("SORTED BY CAREERLEVEL ASC...");
         		Collections.sort(mainList, UserUsageModel.CareerLevelComparatorAsc);
  	       	}else {	
  	       		System.out.println("SORTED BY CAREERLEVEL DESC...");
  	       		Collections.sort(mainList, UserUsageModel.CareerLevelComparatorDesc);
  	       	}
      } else if(column.equalsIgnoreCase("country")) {
   	   		if(sort.equalsIgnoreCase("asc")) {
    		System.out.println("SORTED BY COUNTRY ASC...");
    		Collections.sort(mainList, UserUsageModel.CountryComparatorAsc);
	       	}else {	
	       		System.out.println("SORTED BY COUNTRY DESC...");
	       		Collections.sort(mainList, UserUsageModel.CountryComparatorDesc);
	       	}
      } else if(column.equalsIgnoreCase("visits")) {
    	   if(sort.equalsIgnoreCase("asc")) {
       		System.out.println("SORTED BY VISITS ASC...");
       		Collections.sort(mainList, UserUsageModel.VisitsComparatorAsc);
	       	}else {	
	       		System.out.println("SORTED BY VISITS DESC...");
	       		Collections.sort(mainList, UserUsageModel.VisitsComparatorDesc);
	       	}
       }  else if(column.equalsIgnoreCase("aveVisits")) {
    	   if(sort.equalsIgnoreCase("asc")) {
          		System.out.println("SORTED BY AVE VISITS ASC...");
          		Collections.sort(mainList, UserUsageModel.AveVisitComparatorAsc);
   	       	}else {	
   	       		System.out.println("SORTED BY AVE VISITS DESC...");
   	       		Collections.sort(mainList, UserUsageModel.AveVisitComparatorDesc);
   	       	}
       }  else if(column.equalsIgnoreCase("avePageVisits")) {
    	   if(sort.equalsIgnoreCase("asc")) {
         		System.out.println("SORTED BY AVE PAGE VISITS ASC...");
         		Collections.sort(mainList, UserUsageModel.AvePageVisitComparatorAsc);
  	       	}else {	
  	       		System.out.println("SORTED BY AVE PAGE VISITS DESC...");
  	       		Collections.sort(mainList, UserUsageModel.AvePageVisitComparatorDesc);
  	        }
      }   else if(column.equalsIgnoreCase("hits")) {
    	  	if(sort.equalsIgnoreCase("asc")) {
    		System.out.println("SORTED BY HITS ASC...");
    		Collections.sort(mainList, UserUsageModel.HitsComparatorAsc);
	       	}else {	
	       		System.out.println("SORTED BY HITS DESC...");
	       		Collections.sort(mainList, UserUsageModel.HitsComparatorDesc);
	        }
      }
        
      //Checking size request
        List<UserUsageModel> uumList = new ArrayList<UserUsageModel>();
        int toIndex = Integer.parseInt(to);
        int fromIndex = Integer.parseInt(from);
        int sizeIndex = Integer.parseInt(size);
        if((toIndex > mainList.size()) || (sizeIndex > mainList.size())) {
        	uumList.addAll(mainList);
        } else {
        	if(search.equalsIgnoreCase("*")) {
        		uumList = mainList.subList(fromIndex, toIndex);	
        	}else {
        		fromIndex = 0;
        		toIndex = 1; 
        		uumList = mainList.subList(fromIndex, toIndex);
        	}
        }

        try {
            String finStr = mapper.writeValueAsString(uumList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    public Object getPagesByX(JSONObject aggs, String filter) {

        ObjectMapper mapper = new ObjectMapper();
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();
        List<PagesModel> pmL = new ArrayList<>();

        for (Object one_bucket : one_buckets) {

            JSONObject one_object = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_object.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");

            for (Object two_bucket : two_buckets) {
                JSONObject two_obj = (JSONObject) two_bucket;
                // Unique Visits Count
                JSONObject six = (JSONObject) two_obj.get("6");
                // Unique Hits Count
                JSONObject seven = (JSONObject) two_obj.get("7");

                // PageTitle
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONArray three_buckets = (JSONArray) three.get("buckets");

                int size = three_buckets.size();
                double totalElapseTime = 0;
                int totalVisits = 0;
                JSONObject three_obj;

                for (Object three_bucket : three_buckets) {
                    three_obj = (JSONObject) three_bucket;

                    // TotalElapseTimePerVisit
                    JSONObject four = (JSONObject) three_obj.get("4");
                    // VisitPerPage
                    JSONObject five = (JSONObject) three_obj.get("5");

                    totalElapseTime += Double.parseDouble(four.get("value").toString());
                    totalVisits += Integer.parseInt(five.get("value").toString());
                }

                PagesModel pagesModel = new PagesModel();
                pagesModel.setAvgPagesPerVisit(filter);
                pagesModel.setAvgVisit(filter);
                pagesModel.setHits(filter);
                pagesModel.setVisits(filter);
                pagesModel.setPageURL(one_object.get("key").toString());

                if (filter.equalsIgnoreCase("user")) {
                    pagesModel.setUser(filter);
                } else if (filter.equalsIgnoreCase("careerLevel")) {
                    pagesModel.setCareerLevel(filter);
                } else if (filter.equalsIgnoreCase("careerTracks")) {
                    pagesModel.setCareerTracks(filter);
                } else if (filter.equalsIgnoreCase("geography")) {
                    pagesModel.setGeography(filter);
                }

                pmL.add(pagesModel);
//                uumL.add(new UserUsageModel(
//                        two_obj.get("key").toString(),
//                        six.get("value").toString(),
//                        String.valueOf(df.format(totalElapseTime / size)),
//                        String.valueOf(totalVisits / size),
//                        seven.get("value").toString()));
            }
        }

        try {
            String finStr = mapper.writeValueAsString(pmL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }

    public Object extractAggregation(JSONObject o, String payload) {
        JSONObject aggs = (JSONObject) o.get("aggregations");  

        String[] params = payload.split(",");

        switch (params[0]) {

            case "generationTime":
            case "hitsCount":
            case "visitorCount":
            case "visitsCount":
                return getWebMetrics(aggs);

            case "topPageVisits":
            case "topCountry":
            case "topDevices":
            case "topBrowser":
                return getChartVisualization(aggs);

//            case "visitOvertime":
//                return getChartVisitOvertime(aggs);

            case "uniquePageUrlList":
                return getUniquePageUrlList(aggs);

//            case "uniquePageUrl":
//                return getUniquePageUrl(aggs);
//                
            case "visitorLogsV2":
            	return getVisitorLogsV2(aggs);
            	
            case "visitorLogsV3":
            	return getVisitorLogsV3(aggs);
            	
//            case "visitorLogs":
//            	return getVisitorLogs(aggs);
            	
            case "usageReportRevised":
            	return getCustomUsageV2(aggs, params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
            
            case "usageDetails":
            	return getCustomUsageDetails(aggs, params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
            	
            case "usageByUser":
                return getUsageByUsers(aggs);

            case "pagesReportRevised":
                return getCustomPagesV2(aggs, params[1]);
            
            case "pagesReportDetails":
                return getCustomPagesDetails(aggs, params[1]);
                
            case "pagesReport":
                return getCustomPages(aggs, params[1]);
    
            default:
                break;

        }

        return aggs;
    }
    
    public Object extractAggregationDashboard(JSONObject o, String payload, String timezoneOffset) {
        JSONObject aggs = (JSONObject) o.get("aggregations");  

        String[] params = payload.split(",");

        switch (params[0]) {

            case "generationTime":
            case "hitsCount":
            case "visitorCount":
            case "visitsCount":
                return getWebMetrics(aggs);

            case "topPageVisits":
            case "topCountry":
            case "topDevices":
            case "topBrowser":
                return getChartVisualization(aggs);

            case "visitOvertime":
                return getChartVisitOvertime(aggs,timezoneOffset);

            case "uniquePageUrlList":
                return getUniquePageUrlList(aggs);
    
            default:
                break;

        }

        return aggs;
    }
    
    public Object extractAggregationMetrics(JSONObject o, String payload, JSONObject objParams) {
        JSONObject aggs = (JSONObject) o.get("aggregations");  
        
        switch (payload) {
        
            case "usageReportPaginationFilter":
            	return getCustomUsagePagination(aggs, objParams);
            	
            case "usageChildPaginationFilter":
            	return getCustomUsageChildPagination(aggs, objParams);
            	
            case "pagesReportPaginationFilter":
            	return getCustomPagesPagination(aggs, objParams);
            	
            case "pagesChildPaginationFilter":
            	return getCustomPagesChildPagination(aggs, objParams);
            	
            case "visitorLogReportPaginationFilter":
            	return getVisitorLogsV3Pagination(aggs, objParams);
            	
            case "visitorLogReportPaginationFilterExport":
            	return getVisitorLogsV3PaginationExport(aggs, objParams);
            	
            case "visitorLogReportChildPaginationFilter":
            	return getVisitorLogsV3ChildPagination(aggs, objParams);
            	
            case "myTEReportPaginationFilter":
            	return getMyTEPagination(aggs, objParams);
            default:
                break;

        }

        return aggs;
    }

    public JSONObject customReportOverviewPageMetrics(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject two = (JSONObject) aggs.get("2");

        JSONArray two_buckets = (JSONArray) two.get("buckets");

        JSONObject finObject = new JSONObject();

        List<OverviewPageMetrics> opml = new ArrayList<>();

        for (Object two_bucket : two_buckets) {
            JSONObject two_obj = (JSONObject) two_bucket;
            JSONObject three = (JSONObject) two_obj.get("3");
            JSONObject four = (JSONObject) two_obj.get("4");
            JSONObject five = (JSONObject) two_obj.get("5");

            OverviewPageMetrics opm = new OverviewPageMetrics();

            //Getting Hits for unique visits
            opm.setHits(kusinaStringUtils.getModStr(three.get("value")));
            //Getting PageURL
            opm.setPageurl(kusinaStringUtils.getModStr(two_obj.get("key")));
            //Getting User eid
            opm.setUser(kusinaStringUtils.getModStr(five.get("value")));
            //Getting Visits Count
            opm.setVisits(kusinaStringUtils.getModStr(four.get("value")));

            opml.add(opm);
        }

        try {
            String finStr = mapper.writeValueAsString(opml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("total", opml.size());
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }

    public JSONObject customReportOverviewUserMetrics(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject two = (JSONObject) aggs.get("2");

        JSONArray two_buckets = (JSONArray) two.get("buckets");

        JSONObject finObject = new JSONObject();

        List<OverviewUserMetrics> ouml = new ArrayList<>();

        for (Object two_bucket : two_buckets) {
            JSONObject two_obj = (JSONObject) two_bucket;
            JSONObject three = (JSONObject) two_obj.get("3");

            JSONObject one = (JSONObject) two_obj.get("1");

            JSONArray three_buckets = (JSONArray) three.get("buckets");
            for (Object three_bucket : three_buckets) {
                JSONObject three_obj = (JSONObject) three_bucket;
                JSONObject four = (JSONObject) three_obj.get("4");
                JSONArray four_buckets = (JSONArray) four.get("buckets");
                for (Object four_bucket : four_buckets) {
                    JSONObject four_obj = (JSONObject) four_bucket;
                    JSONObject five = (JSONObject) four_obj.get("5");
                    JSONArray five_buckets = (JSONArray) five.get("buckets");
                    for (Object five_bucket : five_buckets) {
                        JSONObject five_obj = (JSONObject) five_bucket;
                        JSONObject six = (JSONObject) five_obj.get("6");
                        JSONArray six_buckets = (JSONArray) six.get("buckets");
                        for (Object six_bucket : six_buckets) {
                            JSONObject six_obj = (JSONObject) six_bucket;

                            OverviewUserMetrics oum = new OverviewUserMetrics();

                            // Getting Username
                            oum.setUser(kusinaStringUtils.getModStr(two_obj.get("key")));
                            // Getting CareerLevel
                            oum.setCareerLevel(kusinaStringUtils.getModStr(three_obj.get("key")));
                            // Getting CareerTracks
                            oum.setCareerTracks(kusinaStringUtils.getModStr(four_obj.get("key")));
                            // Getting Geography
                            oum.setGeography(kusinaStringUtils.getModStr(five_obj.get("key")));
                            // Getting Country
                            oum.setCountry(kusinaStringUtils.getModStr(six_obj.get("key")));
                            // Getting Visits
                            oum.setVisits(kusinaStringUtils.getModStr(one.get("value")));

                            ouml.add(oum);
                        }
                    }
                }

            }
        }

        try {
            String finStr = mapper.writeValueAsString(ouml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("total", ouml.size());
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }

    public JSONObject extractCustomReportDisplay1(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        
        JSONObject zero = (JSONObject) aggs.get("0");
        JSONArray zero_buckets = (JSONArray) zero.get("buckets");
        JSONObject finObject = new JSONObject();

        List<SegmentModel> smL = new ArrayList<>();
        SegmentModel sm;

        for(Object zero_bucket : zero_buckets) {
        	JSONObject zero_obj = (JSONObject) zero_bucket;
        	JSONObject two = (JSONObject) zero_obj.get("2");
        	JSONArray two_buckets = (JSONArray) two.get("buckets");
            for (Object two_bucket : two_buckets) {
                JSONObject two_obj = (JSONObject) two_bucket;
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONArray three_buckets = (JSONArray) three.get("buckets");
                for (Object three_bucket : three_buckets) {
                    JSONObject three_obj = (JSONObject) three_bucket;
                    JSONObject four = (JSONObject) three_obj.get("4");
                    JSONArray four_buckets = (JSONArray) four.get("buckets");
                    for (Object four_bucket : four_buckets) {
                        JSONObject four_obj = (JSONObject) four_bucket;
                        sm = new SegmentModel();
                        // Segment Name
                        sm.setSegmentName(kusinaStringUtils.getModStr(two_obj.get("key")));
                        // Career Tracks
                        sm.setCareerTracks(kusinaStringUtils.getModStr(three_obj.get("key")));
                        // Industry Name
                        sm.setIndustryName(kusinaStringUtils.getModStr(four_obj.get("key")));
                        // Unique Visitor's Count
                        JSONObject one = (JSONObject) four_obj.get("1");
                        sm.setUniqueVisitorCount(kusinaStringUtils.getModStr(one.get("value")));
                        // Hits
                        JSONObject five = (JSONObject) four_obj.get("5");
                        sm.setHits(kusinaStringUtils.getModStr(five.get("value")));

                        smL.add(sm);
                    }
                }
            }
        }
        
        try {
            String finStr = mapper.writeValueAsString(smL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("total", smL.size());
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finObject;
    }

    public JSONObject extractCustomReportDisplay2(JSONObject o, String reportType) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject zero = (JSONObject) aggs.get("0");
        JSONArray zero_buckets = (JSONArray) zero.get("buckets");

        JSONObject finObject = new JSONObject();

        List<SegmentModel> smL = new ArrayList<>();
        SegmentModel sm;

        for(Object zero_bucket : zero_buckets) {
        	JSONObject zero_obj = (JSONObject) zero_bucket;
        	JSONObject two = (JSONObject) zero_obj.get("2");
        	JSONArray two_buckets = (JSONArray) two.get("buckets");
	        for (Object two_bucket : two_buckets) {
	            JSONObject two_obj = (JSONObject) two_bucket;
	            JSONObject three = (JSONObject) two_obj.get("3");
	            JSONArray three_buckets = (JSONArray) three.get("buckets");
	            for (Object three_bucket : three_buckets) {
	                JSONObject three_obj = (JSONObject) three_bucket;
	                JSONObject one = (JSONObject) three_obj.get("1");
	                sm = new SegmentModel();
	                // Segment Name
	                sm.setSegmentName(kusinaStringUtils.getModStr(two_obj.get("key")));
	                // Geography/CareerLevel
	
	                if ("geographyBySegmentName".equalsIgnoreCase(reportType)) {
	                    sm.setGeography(kusinaStringUtils.getModStr(three_obj.get("key")));
	                } else {
	                    sm.setCareerLevel(kusinaStringUtils.getModStr(three_obj.get("key")));
	                }
	                // Unique Visitor's Count
	                sm.setUniqueVisitorCount(kusinaStringUtils.getModStr(one.get("value")));
	
	                smL.add(sm);
	            }
	        }
        }
        try {
            String finStr = mapper.writeValueAsString(smL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("total", smL.size());
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finObject;
    }

    public JSONObject extractCustomReportDisplay3(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject zero = (JSONObject) aggs.get("0");
        JSONArray zero_buckets = (JSONArray) zero.get("buckets");

        JSONObject finObject = new JSONObject();

        List<HitsModel> hmL = new ArrayList<>();
        HitsModel hm;

        for(Object zero_bucket : zero_buckets) {
        	JSONObject zero_obj = (JSONObject) zero_bucket;
        	JSONObject two = (JSONObject) zero_obj.get("2");
        	JSONArray two_buckets = (JSONArray) two.get("buckets");
	        for (Object two_bucket : two_buckets) {
	            JSONObject two_obj = (JSONObject) two_bucket;
	            JSONObject three = (JSONObject) two_obj.get("3");
	            JSONArray three_buckets = (JSONArray) three.get("buckets");
	            for (Object three_bucket : three_buckets) {
	                JSONObject three_obj = (JSONObject) three_bucket;
	                JSONObject one = (JSONObject) three_obj.get("1");
	                hm = new HitsModel();
	                // Segment Name
	                hm.setSegmentName(kusinaStringUtils.getModStr(two_obj.get("key")));
	                // Geography
	                hm.setGeography(kusinaStringUtils.getModStr(three_obj.get("key")));
	                // Unique Visitor's Count
	                hm.setHits(kusinaStringUtils.getModStr(one.get("value")));
	
	                hmL.add(hm);
	            }
	        }
        }
        try {
            String finStr = mapper.writeValueAsString(hmL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("total", hmL.size());
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }

    public JSONObject extractCustomReportDisplay4(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        
        JSONObject zero = (JSONObject) aggs.get("0");
        JSONArray zero_buckets = (JSONArray) zero.get("buckets");

        JSONObject finObject = new JSONObject();

        List<HitsModel> hmL = new ArrayList<>();
        HitsModel hm;
        
        for(Object zero_bucket : zero_buckets) {
        	JSONObject zero_obj = (JSONObject) zero_bucket;
        	JSONObject two = (JSONObject) zero_obj.get("2");
        	JSONArray two_buckets = (JSONArray) two.get("buckets");
        for (Object two_bucket : two_buckets) {
            JSONObject two_obj = (JSONObject) two_bucket;
            JSONObject three = (JSONObject) two_obj.get("3");
            JSONArray three_buckets = (JSONArray) three.get("buckets");
            for (Object three_bucket : three_buckets) {
                JSONObject three_obj = (JSONObject) three_bucket;
                JSONObject four = (JSONObject) three_obj.get("4");
                JSONArray four_buckets = (JSONArray) four.get("buckets");
                for (Object four_bucket : four_buckets) {
                    JSONObject four_obj = (JSONObject) four_bucket;
                    hm = new HitsModel();
                    // Segment Name
                    hm.setSegmentName(kusinaStringUtils.getModStr(two_obj.get("key")));
                    // Industry Name
                    hm.setIndustryName(kusinaStringUtils.getModStr(three_obj.get("key")));
                    // Asset Type
                    hm.setAssetType(kusinaStringUtils.getModStr(four_obj.get("key")));
                    // Hits Count
                    JSONObject one = (JSONObject) four_obj.get("1");
                    hm.setHits(kusinaStringUtils.getModStr(one.get("value")));

                    hmL.add(hm);
                }
            }
        }
        }
        try {
            String finStr = mapper.writeValueAsString(hmL);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("total", hmL.size());
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }

    public JSONObject extractCustomReportDisplay5(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        List<PageCustomInfoModel> pcimList = new ArrayList<>();
        JSONArray hitsArray = (JSONArray) o.get("resultset");

        JSONObject finObject = new JSONObject();
        for (Object hitObj : hitsArray) {

            JSONObject hit = (JSONObject) hitObj;

            pcimList.add(new PageCustomInfoModel(
                    kusinaStringUtils.getModStr(hit.get("CleanPageURL")),
                    kusinaStringUtils.getModStr(hit.get("FirstActionTime")),
                    kusinaStringUtils.getModStr(hit.get("User")),
                    kusinaStringUtils.getModStr(hit.get("Browser")),
                    kusinaStringUtils.getModStr(hit.get("OperatingSystem")),
                    kusinaStringUtils.getModStr(hit.get("DeviceType")),
                    kusinaStringUtils.getModStr(hit.get("Geography")),
                    kusinaStringUtils.getModStr(hit.get("PageCustomVariable1Value")),
                    kusinaStringUtils.getModStr(hit.get("PageCustomVariable3Value")),
                    kusinaStringUtils.getModStr(hit.get("PageCustomVariable2Value")),
                    kusinaStringUtils.getModStr(hit.get("PageCustomVariable4Value")),
                    kusinaStringUtils.getModStr(hit.get("CareerTracks")),
                    kusinaStringUtils.getModStr(hit.get("CareerLevel")))
            );

        }

        try {
            String finStr = mapper.writeValueAsString(pcimList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("total", pcimList.size());
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }

    public JSONObject getAllUsersList(String response) throws ParseException {
    	ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        JSONObject mainObj = (JSONObject) parser.parse(response);
        JSONObject hitsObj = (JSONObject) mainObj.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        JSONObject finObject = new JSONObject();
        List<UserModel> uml = new ArrayList<>();
        //List<String> arryList = new ArrayList<>();
        
        UserModel um;
           
        for (Object obj : innerHits) {
            JSONObject o = (JSONObject) obj;
            //doc.add(o.get("_source"));
            JSONObject _sourceObj = (JSONObject) o.get("_source");
            
            um = new UserModel();
            //Getting Eid
            um.setEid(kusinaStringUtils.getModStr(_sourceObj.get("user_eid")));
            //Getting User Access
            um.setAccess(kusinaStringUtils.getModStr(_sourceObj.get("user_access")));
            //Getting User Team
            um.setTeam(kusinaStringUtils.getModStr(_sourceObj.get("user_team")));
            //Getting User ID
            um.setId(kusinaStringUtils.getModStr(_sourceObj.get("user_id").toString().replace("\"", "").replace("[", "").replace("]", "")));
            //Getting AirID
            um.setAirid(kusinaStringUtils.getModStr(_sourceObj.get("user_airid").toString().replace("\"", "").replace("[", "").replace("]", "")));
            //Getting Expiration Date
            um.setExpiryDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("access_expiration_date").toString())));
            //Getting Status
            um.setStatus(kusinaStringUtils.getModStr(_sourceObj.get("access_status")));
            //Getting Created Date
            um.setCreatedDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("created_date").toString())));
            //Getting Last Update Date
            um.setLastUpdatedDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("last_update_date").toString())));
            //Getting Last Update By
            um.setLastUpdatedBy(kusinaStringUtils.getModStr(_sourceObj.get("last_update_by")));
            
            uml.add(um);
        }
       
        try {
            String finStr = mapper.writeValueAsString(uml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
            finObject.put("total", uml.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }	
    
    
    public JSONObject getFeedBacks(String response) throws ParseException {
    	ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        JSONObject mainObj = (JSONObject) parser.parse(response);
        JSONObject hitsObj = (JSONObject) mainObj.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        JSONObject finObject = new JSONObject();
        List<FeedBackModel> fml = new ArrayList<>();
        //List<String> arryList = new ArrayList<>();
        
        FeedBackModel fm;
           
        for (Object obj : innerHits) {
            JSONObject o = (JSONObject) obj;
            //doc.add(o.get("_source"));
            JSONObject _sourceObj = (JSONObject) o.get("_source");
            
            fm = new FeedBackModel();
            //Getting Eid
            fm.setEid(kusinaStringUtils.getModStr(_sourceObj.get("user_eid")));
            //Getting AirID
            fm.setAirid(kusinaStringUtils.getModStr(_sourceObj.get("user_airid").toString().replace("\"", "").replace("[", "").replace("]", "")));
            //Getting ID
            fm.setId(kusinaStringUtils.getModStr(_sourceObj.get("user_id")));
            //Getting Rating Module
            fm.setRatingModule(kusinaStringUtils.getModStr(_sourceObj.get("rating_module")));
            //Getting Rating Score
            fm.setRatingScore(kusinaStringUtils.getModStr(_sourceObj.get("rating_score")));
            //Getting Rating Comment
            fm.setComment(kusinaStringUtils.getModStr(_sourceObj.get("rating_comment")));
            //Getting Status
            fm.setStatus(kusinaStringUtils.getModStr(_sourceObj.get("status")));
            //Getting Created Date
            fm.setCreatedDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("created_date").toString())));
            //Getting Last Update Date
            fm.setLastUpdateDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("last_update_date").toString())));
            //Getting Last Update By
            fm.setLastUpdateBy(kusinaStringUtils.getModStr(_sourceObj.get("last_update_by")));
            
            fml.add(fm);
        }
       
        try {
            String finStr = mapper.writeValueAsString(fml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }	


    // ********************8
    public JSONObject customReportOverviewDownloadMetrics(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();

        List<OverviewDownloadMetrics> main_odml = new ArrayList<>();
        List<OverviewDownloadMetrics> odml = new ArrayList<>();
        OverviewDownloadMetrics ord;

        for (Object one_bucket : one_buckets) {

            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            odml = new ArrayList<>();
            for (Object two_bucket : two_buckets) {
                JSONObject two_obj = (JSONObject) two_bucket;
                JSONObject six = (JSONObject) two_obj.get("6");
                JSONObject seven = (JSONObject) two_obj.get("7");
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONArray three_buckets = (JSONArray) three.get("buckets");

                int size = three_buckets.size();
                double totalElapseTime = 0;
                int totalVisits = 0;

                JSONObject three_obj;
                for (Object three_bucket : three_buckets) {
                    three_obj = (JSONObject) three_bucket;
                    JSONObject four = (JSONObject) three_obj.get("4");
                    JSONObject five = (JSONObject) three_obj.get("5");

                    totalElapseTime += Double.parseDouble(four.get("value").toString());
                    totalVisits += Integer.parseInt(five.get("value").toString());
                }

                odml.add(new OverviewDownloadMetrics(
                        one_obj.get("key").toString(),
                        two_obj.get("key").toString(),
                        six.get("value").toString(),
                        String.valueOf(df.format(totalElapseTime / size)),
                        String.valueOf(totalVisits / size),
                        seven.get("value").toString()
                ));

            }

            int avgTotalElapseTime = 0;
            double avgTotalVisits = 0;
            int sumOfVisits = 0;
            int sumOfHits = 0;
            int listSize = odml.size();
            for (OverviewDownloadMetrics ordl_page : odml) {
                avgTotalElapseTime += Integer.parseInt(ordl_page.getAvgPagesPerVisit());
                avgTotalVisits += Double.parseDouble(ordl_page.getAvgVisit());
                sumOfVisits += Integer.parseInt(ordl_page.getVisits());
                sumOfHits += Integer.parseInt(ordl_page.getHits());
            }

            OverviewDownloadMetrics Odml = new OverviewDownloadMetrics();
            Odml.setPageurl(one_obj.get("key").toString());
            Odml.setAvgPagesPerVisit(String.valueOf(avgTotalElapseTime / listSize));
            Odml.setAvgVisit(String.valueOf(df.format(avgTotalVisits / listSize)));
            Odml.setHits(String.valueOf(sumOfHits));
            Odml.setVisits(String.valueOf(sumOfVisits));

            Odml.setDownloadMetricsModel(odml);
            main_odml.add(Odml);

        }

        try {
            String finStr = mapper.writeValueAsString(main_odml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }

    public JSONObject getCustomReportTotal(JSONObject o) {
         JSONObject aggs = (JSONObject) o.get("aggregations");
         JSONObject totalCount = (JSONObject) aggs.get("totalcount");
    	
  
    	return totalCount;
    }
    
    public JSONObject getCustomReportTotalChild(JSONObject o) {
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        
        JSONObject totalCount = new JSONObject();
        
        for (Object one_bucket : one_buckets) {
        	JSONObject one_obj = (JSONObject) one_bucket;
        	JSONObject Count = (JSONObject) one_obj.get("totalcount");
        	
        	totalCount = Count;
        }
		return totalCount;
   
   }
    
    
    public JSONObject customReportOverviewReferrersMetrics(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();

        List<OverviewReferrersMetrics> main_orml = new ArrayList<>();
        List<OverviewReferrersMetrics> orml = new ArrayList<>();
        OverviewReferrersMetrics orm;

        for (Object one_bucket : one_buckets) {

            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            orml = new ArrayList<>();
            for (Object two_bucket : two_buckets) {
                JSONObject two_obj = (JSONObject) two_bucket;
                JSONObject six = (JSONObject) two_obj.get("6");
                JSONObject seven = (JSONObject) two_obj.get("7");
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONArray three_buckets = (JSONArray) three.get("buckets");

                int size = three_buckets.size();
                double totalElapseTime = 0;
                int totalVisits = 0;

                JSONObject three_obj;
                for (Object three_bucket : three_buckets) {
                    three_obj = (JSONObject) three_bucket;
                    JSONObject four = (JSONObject) three_obj.get("4");
                    JSONObject five = (JSONObject) three_obj.get("5");

                    totalElapseTime += Double.parseDouble(four.get("value").toString());
                    totalVisits += Integer.parseInt(five.get("value").toString());
                }

                orml.add(new OverviewReferrersMetrics(
                        one_obj.get("key").toString(),
                        two_obj.get("key").toString(),
                        six.get("value").toString(),
                        String.valueOf(df.format(totalElapseTime / size)),
                        String.valueOf(totalVisits / size),
                        seven.get("value").toString()
                ));

            }

            int avgTotalElapseTime = 0;
            double avgTotalVisits = 0;
            int sumOfVisits = 0;
            int sumOfHits = 0;
            int listSize = orml.size();
            for (OverviewReferrersMetrics orml_page : orml) {
                avgTotalElapseTime += Integer.parseInt(orml_page.getAvgPagesPerVisit());
                avgTotalVisits += Double.parseDouble(orml_page.getAvgVisit());
                sumOfVisits += Integer.parseInt(orml_page.getVisits());
                sumOfHits += Integer.parseInt(orml_page.getHits());
            }

            OverviewReferrersMetrics Orml = new OverviewReferrersMetrics();
            Orml.setReferrerName(one_obj.get("key").toString());
            Orml.setAvgPagesPerVisit(String.valueOf(avgTotalElapseTime / listSize));
            Orml.setAvgVisit(String.valueOf(df.format(avgTotalVisits / listSize)));
            Orml.setHits(String.valueOf(sumOfHits));
            Orml.setVisits(String.valueOf(sumOfVisits));

            Orml.setReferrersMetricsModel(orml);
            main_orml.add(Orml);

        }

        try {
            String finStr = mapper.writeValueAsString(main_orml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
//    public JSONObject getVisitorLogs(JSONObject aggs) {
//        ObjectMapper mapper = new ObjectMapper();
//        JSONObject one = (JSONObject) aggs.get("1");
//        JSONArray one_buckets = (JSONArray) one.get("buckets");
//
//        JSONObject finObject = new JSONObject();
//        List<VisitorLogsModel> vlm = new ArrayList<>();
//
//        for (Object one_bucket : one_buckets) {
//            JSONObject one_object = (JSONObject) one_bucket;
//            JSONObject two = (JSONObject) one_object.get("2");
//            JSONArray two_buckets = (JSONArray) two.get("buckets");
//            for (Object two_bucket : two_buckets) {
//                JSONObject two_object = (JSONObject) two_bucket;
//                JSONObject three = (JSONObject) two_object.get("3");
//                JSONArray three_buckets = (JSONArray) three.get("buckets");
//                for (Object three_bucket : three_buckets) {
//                    JSONObject three_object = (JSONObject) three_bucket;
//                    JSONObject four = (JSONObject) three_object.get("4");
//                    JSONArray four_buckets = (JSONArray) four.get("buckets");
//                    for (Object four_bucket : four_buckets) {
//                        JSONObject four_object = (JSONObject) four_bucket;
//                        JSONObject five = (JSONObject) four_object.get("5");
//                        JSONArray five_buckets = (JSONArray) five.get("buckets");
//                        for (Object five_bucket : five_buckets) {
//                            JSONObject five_object = (JSONObject) five_bucket;
//                            JSONObject six = (JSONObject) five_object.get("6");
//                            JSONArray six_buckets = (JSONArray) six.get("buckets");
//                            for (Object six_bucket : six_buckets) {
//                                JSONObject six_object = (JSONObject) six_bucket;
//                                JSONObject seven = (JSONObject) six_object.get("7");
//                                JSONArray seven_buckets = (JSONArray) seven.get("buckets");
//                                for (Object seven_bucket : seven_buckets) {
//                                    JSONObject seven_object = (JSONObject) seven_bucket;
//                                    JSONObject eight = (JSONObject) seven_object.get("8");
//                                    JSONArray eight_buckets = (JSONArray) eight.get("buckets");
//                                    for (Object eight_bucket : eight_buckets) {
//                                        JSONObject eight_object = (JSONObject) eight_bucket;
//                                        JSONObject nine = (JSONObject) eight_object.get("9");
//                                        JSONArray nine_buckets = (JSONArray) nine.get("buckets");
//                                        for (Object nine_bucket : nine_buckets) {
//                                            JSONObject nine_object = (JSONObject) nine_bucket;
//                                            JSONObject ten = (JSONObject) nine_object.get("10");
//                                            JSONArray ten_buckets = (JSONArray) ten.get("buckets");
//                                            for (Object ten_bucket : ten_buckets) {
//                                                JSONObject ten_object = (JSONObject) ten_bucket;
//                                                JSONObject eleven = (JSONObject) ten_object.get("11");
//                                                JSONArray eleven_buckets = (JSONArray) eleven.get("buckets");
//                                                for (Object eleven_bucket : eleven_buckets) {
//                                                    JSONObject eleven_object = (JSONObject) eleven_bucket;
//                                                    JSONObject twelve = (JSONObject) eleven_object.get("12");
//                                                    JSONArray twelve_buckets = (JSONArray) twelve.get("buckets");
//                                                    for (Object twelve_bucket : twelve_buckets) {
//                                                        JSONObject twelve_object = (JSONObject) twelve_bucket;
//                                                        JSONObject thirthteen = (JSONObject) twelve_object.get("13");
//                                                        JSONArray thirthteen_buckets = (JSONArray) thirthteen.get("buckets");
//                                                        for (Object thirthteen_bucket : thirthteen_buckets) {
//                                                            JSONObject thirthteen_object = (JSONObject) thirthteen_bucket;
//                                                            JSONObject fourthteen = (JSONObject) thirthteen_object.get("14");
//                                                            JSONArray fourthteen_buckets = (JSONArray) fourthteen.get("buckets");
//                                                            for (Object fourthteen_bucket : fourthteen_buckets) {
//                                                                JSONObject fourthteen_object = (JSONObject) fourthteen_bucket;
//                                                                JSONObject fifthteen = (JSONObject) fourthteen_object.get("15");
//                                                                JSONArray fifthteen_buckets = (JSONArray) fifthteen.get("buckets");
//                                                                for (Object fifthteen_bucket : fifthteen_buckets) {
//                                                                    JSONObject fifthteen_object = (JSONObject) fifthteen_bucket;
//                                                                    JSONObject sixthteen = (JSONObject) fifthteen_object.get("16");
//                                                                    JSONArray sixthteen_buckets = (JSONArray) sixthteen.get("buckets");
//                                                                    for (Object sixthteen_bucket : sixthteen_buckets) {
//                                                                        JSONObject sixthteen_object = (JSONObject) sixthteen_bucket;
//                                                                        JSONObject seventhteen = (JSONObject) sixthteen_object.get("17");
//                                                                        JSONArray seventhteen_buckets = (JSONArray) seventhteen.get("buckets");
//                                                                        for (Object seventhteen_bucket : seventhteen_buckets) {
//                                                                            JSONObject seventhteen_object = (JSONObject) seventhteen_bucket;
//                                                                            JSONObject eighteen = (JSONObject) seventhteen_object.get("18");
//                                                                            JSONArray eighteen_buckets = (JSONArray) eighteen.get("buckets");
//                                                                            for(Object eighteen_bucket : eighteen_buckets) {
//                                                                            	JSONObject eighteen_object = (JSONObject) eighteen_bucket;
//                                                                            	
//                                                                            	finObject = new JSONObject();
//
//                                                                                vlm.add(new VisitorLogsModel(
//                                                                                        kusinaStringUtils.getModStr(one_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(two_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(three_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(four_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(five_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(six_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(seven_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(eight_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(nine_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(ten_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(eleven_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(twelve_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(thirthteen_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(fourthteen_object.get("key_as_string")),
//                                                                                        kusinaStringUtils.getModStr(fifthteen_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(sixthteen_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(seventhteen_object.get("key")),
//                                                                                        kusinaStringUtils.getModStr(eighteen_object.get("key"))
//                                                                                        
//                                                                                )
//                                                                                );
//                                                                            }
//                                             
//
//                                                                        }
//
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//                }
//            }
//        }
//        try {
//            String finStr = mapper.writeValueAsString(vlm);
//            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
//            finObject.put("resultset", convertedObj);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return finObject;
//    }
    
    public JSONObject getVisitorLogsV2(JSONObject aggs) {
    	 ObjectMapper mapper = new ObjectMapper();
         JSONObject one = (JSONObject) aggs.get("1");
         JSONArray one_buckets = (JSONArray) one.get("buckets");

         JSONObject finObject = new JSONObject();
         List<VisitorLogsModel> vlm = new ArrayList<>();

         for (Object one_bucket : one_buckets) {
             JSONObject one_object = (JSONObject) one_bucket;
             JSONObject two = (JSONObject) one_object.get("2");
             JSONArray two_buckets = (JSONArray) two.get("buckets");
             for (Object two_bucket : two_buckets) {
                 JSONObject two_object = (JSONObject) two_bucket;
                 JSONObject three = (JSONObject) two_object.get("3");
                 JSONArray three_buckets = (JSONArray) three.get("buckets");
                 for (Object three_bucket : three_buckets) {
                     JSONObject three_object = (JSONObject) three_bucket;
                     JSONObject four = (JSONObject) three_object.get("4");
                     JSONArray four_buckets = (JSONArray) four.get("buckets");
                     for (Object four_bucket : four_buckets) {
                         JSONObject four_object = (JSONObject) four_bucket;
                         JSONObject five = (JSONObject) four_object.get("5");
                         JSONArray five_buckets = (JSONArray) five.get("buckets");
                         for (Object five_bucket : five_buckets) {
                             JSONObject five_object = (JSONObject) five_bucket;
                             JSONObject six = (JSONObject) five_object.get("6");
                             JSONArray six_buckets = (JSONArray) six.get("buckets");
                             for (Object six_bucket : six_buckets) {
                                 JSONObject six_object = (JSONObject) six_bucket;
                                 JSONObject seven = (JSONObject) six_object.get("7");
                                 JSONArray seven_buckets = (JSONArray) seven.get("buckets");
                                 for (Object seven_bucket : seven_buckets) {
                                     JSONObject seven_object = (JSONObject) seven_bucket;
                                     JSONObject eight = (JSONObject) seven_object.get("8");
                                     JSONArray eight_buckets = (JSONArray) eight.get("buckets");
                                     for (Object eight_bucket : eight_buckets) {
                                         JSONObject eight_object = (JSONObject) eight_bucket;
                                         JSONObject nine = (JSONObject) eight_object.get("9");
                                         JSONArray nine_buckets = (JSONArray) nine.get("buckets");
                                         for (Object nine_bucket : nine_buckets) {
                                             JSONObject nine_object = (JSONObject) nine_bucket;
                                             JSONObject ten = (JSONObject) nine_object.get("10");
                                             JSONArray ten_buckets = (JSONArray) ten.get("buckets");
                                             for (Object ten_bucket : ten_buckets) {
                                                 JSONObject ten_object = (JSONObject) ten_bucket;
                                                 JSONObject eleven = (JSONObject) ten_object.get("11");
                                                 JSONArray eleven_buckets = (JSONArray) eleven.get("buckets");
                                                 for (Object eleven_bucket : eleven_buckets) {
                                                     JSONObject eleven_object = (JSONObject) eleven_bucket;
                                                     JSONObject twelve = (JSONObject) eleven_object.get("12");
                                                     JSONArray twelve_buckets = (JSONArray) twelve.get("buckets");
                                                     for (Object twelve_bucket : twelve_buckets) {
                                                         JSONObject twelve_object = (JSONObject) twelve_bucket;
                                                         JSONObject thirthteen = (JSONObject) twelve_object.get("13");
                                                         JSONArray thirthteen_buckets = (JSONArray) thirthteen.get("buckets");
                                                         for (Object thirthteen_bucket : thirthteen_buckets) {
                                                             JSONObject thirthteen_object = (JSONObject) thirthteen_bucket;
                                                             JSONObject fourthteen = (JSONObject) thirthteen_object.get("14");
                                                             JSONArray fourthteen_buckets = (JSONArray) fourthteen.get("buckets");
                                                             for (Object fourthteen_bucket : fourthteen_buckets) {
                                                                 JSONObject fourthteen_object = (JSONObject) fourthteen_bucket;
                                                                 JSONObject fifthteen = (JSONObject) fourthteen_object.get("15");
                                                                 JSONArray fifthteen_buckets = (JSONArray) fifthteen.get("buckets");
                                                                 for (Object fifthteen_bucket : fifthteen_buckets) {
                                                                     JSONObject fifthteen_object = (JSONObject) fifthteen_bucket;
                                                                     JSONObject sixthteen = (JSONObject) fifthteen_object.get("16");
                                                                     JSONArray sixthteen_buckets = (JSONArray) sixthteen.get("buckets");
                                                                     for (Object sixthteen_bucket : sixthteen_buckets) {
                                                                         JSONObject sixthteen_object = (JSONObject) sixthteen_bucket;
                                                                         JSONObject seventhteen = (JSONObject) sixthteen_object.get("17");
                                                                         JSONArray seventhteen_buckets = (JSONArray) seventhteen.get("buckets");
                                                                         for (Object seventhteen_bucket : seventhteen_buckets) {
                                                                             JSONObject seventhteen_object = (JSONObject) seventhteen_bucket;
                                                                             JSONObject eighteen = (JSONObject) seventhteen_object.get("18");
                                                                             JSONArray eighteen_buckets = (JSONArray) eighteen.get("buckets");
                                                                             for(Object eighteen_bucket : eighteen_buckets) {
                                                                             	JSONObject eighteen_object = (JSONObject) eighteen_bucket;
                                                                             	JSONObject ninetheen = (JSONObject) eighteen_object.get("19");
                                                                                JSONArray ninetheen_buckets = (JSONArray) ninetheen.get("buckets");
                                                                                for(Object ninetheen_bucket : eighteen_buckets) {
                                                                                	JSONObject ninetheen_object = (JSONObject) ninetheen_bucket;
                                                                                	finObject = new JSONObject();
                                                                                	
		                                                                            vlm.add(new VisitorLogsModel(
		                                                                                    kusinaStringUtils.getModStr(two_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(three_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(four_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(five_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(six_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(seven_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(eight_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(nine_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(ten_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(eleven_object.get("key")),
		                                                                                    kusinaStringUtils.getModStr(twelve_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(thirthteen_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(fourthteen_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(fifthteen_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(sixthteen_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(seventhteen_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(eighteen_object.get("key_as_string")),
		                                                                                    kusinaStringUtils.getModStr(ninetheen_object.get("key_as_string")), null
		                                                                                    
		                                                                            )
		                                                                            );		
                                                                                }
                                                    
                                                                             }
                                              

                                                                         }

                                                                     }
                                                                 }
                                                             }
                                                         }
                                                     }
                                                 }
                                             }
                                         }
                                     }
                                 }
                             }
                         }

                     }
                 }
             }
         }
         try {
             String finStr = mapper.writeValueAsString(vlm);
             JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
             finObject.put("resultset", convertedObj);
         } catch (JsonProcessingException ex) {
             Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
         }

         return finObject;
    }
    
    public JSONObject customReportEventMetrics(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();

        List<EventsModel> eml = new ArrayList<>();
       

        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            for (Object two_bucket : two_buckets) {
            	JSONObject two_obj = (JSONObject) two_bucket;
            	JSONObject three = (JSONObject) two_obj.get("3");
            	JSONArray three_buckets = (JSONArray) three.get("buckets");
            	for(Object three_bucket : three_buckets ) {
            		 JSONObject three_obj = (JSONObject) three_bucket;
            		
            		eml.add(new EventsModel(
            				one_obj.get("key").toString(),
            				one_obj.get("doc_count").toString(),
            				two_obj.get("key").toString(),
            				two_obj.get("doc_count").toString(),
            				three_obj.get("key").toString(),
            				three_obj.get("doc_count").toString()
            				));
            		 
            	}
            }
            
        }

        try {
            String finStr = mapper.writeValueAsString(eml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    
    public JSONObject getReportFilter(JSONObject o, String filter) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject hits = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hits.get("hits");
              
        JSONObject finObject = new JSONObject();
        List<String> filterObj = new ArrayList<String>();
        String scanFilter = kusinaValidationUtils.modifyPagesFilter(filter);
        
        for(Object obj: innerHits) {
        	JSONObject objHits = (JSONObject) obj;
        	JSONObject objFil = (JSONObject) objHits.get("fields");
        	JSONArray objFilName = (JSONArray) objFil.get(scanFilter);
        	for(Object name: objFilName) {
        		filterObj.add(name.toString());
        	}
        }
       
        try {
            String finStr = mapper.writeValueAsString(filterObj);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
            
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    
    public JSONObject getUsageReportFilterChild(JSONObject o, String filterMetrics) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject hits = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hits.get("hits");
              
        JSONObject finObject = new JSONObject();
        List<String> filterObj = new ArrayList<String>();
        String scanFilter = kusinaValidationUtils.modifyPagesFilter(filterMetrics);
        
        for(Object obj: innerHits) {
        	JSONObject objHits = (JSONObject) obj;
        	JSONObject objFil = (JSONObject) objHits.get("fields");
        	JSONArray objFilName = (JSONArray) objFil.get(scanFilter);
        	for(Object name: objFilName) {
        		filterObj.add(name.toString());
        	}
        }
       
        try {
            String finStr = mapper.writeValueAsString(filterObj);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
           
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    public Object getCustomUsagePagination(JSONObject aggs, JSONObject objParams) {
        String filter = objParams.get("filter").toString();     
    	ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<UserUsageModel> mainList = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat(".##");
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
          
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visits = (JSONObject) one_obj.get("avg_page_visits");	
            
            
            UserUsageModel uum = new UserUsageModel();
            uum.setAvgPagesPerVisit(avg_page_visits.get("value").toString());
            uum.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            //uum.setAvgVisit( df2.format(Double.parseDouble(avg_page_duration.get("value").toString())));
            uum.setVisits(sum_visits.get("value").toString());
            uum.setHits(sum_hits.get("value").toString());
            
            switch(filter) {
            case "user":
            	uum.setUser(one_obj.get("key").toString());
            	break;
            case "careerTracks":
            	uum.setCareerTracks(one_obj.get("key").toString());
            	break;
            case "careerLevelDescription":
            	uum.setCareerLevelDescription(one_obj.get("key").toString());
            	break;
            case "geography":
            	uum.setGeography(one_obj.get("key").toString());
            	break;
            default:
            	break;
            }
            mainList.add(uum);   
        }
               
        
        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
          
            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    
    public Object getCustomUsageChildPagination(JSONObject aggs, JSONObject objParams) {
    	ObjectMapper mapper = new ObjectMapper();
    	String filter = objParams.get("filter").toString();
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<UserUsageModel> mainList = new ArrayList<>();
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
           
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visit = (JSONObject) one_obj.get("avg_page_visit");	
            
            
            UserUsageModel uum = new UserUsageModel();
            uum.setAvgPagesPerVisit(avg_page_visit.get("value").toString());
            uum.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            uum.setVisits(sum_visits.get("value").toString());
            uum.setHits(sum_hits.get("value").toString());
           
            
            if (filter.equalsIgnoreCase("careerLevelDescription")) {
            	uum.setCareerLevel(one_obj.get("key").toString());	
            } else if (filter.equalsIgnoreCase("careerTracks")) {
            	uum.setCareerTrackDescription(one_obj.get("key").toString());
            } else if (filter.equalsIgnoreCase("geography")) {
            	uum.setCountry(one_obj.get("key").toString());
            }

            mainList.add(uum);
            
        }

        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    
    public Object getCustomPagesPagination(JSONObject aggs, JSONObject objParams) {
        String filter = objParams.get("filter").toString();     
    	ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<PagesModel> mainList = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat(".##");
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
          
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("total_sum_visits");
            // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("total_sum_hits");
            // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("total_avg_page_duration");
            // Avg Page Visit
            JSONObject avg_page_visits = (JSONObject) one_obj.get("total_avg_page_visits");	
            
            
            PagesModel pm = new PagesModel();
            pm.setPageURL(one_obj.get("key").toString());
            pm.setAvgPagesPerVisit(avg_page_visits.get("value").toString());
            pm.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            pm.setVisits(sum_visits.get("value").toString());
            pm.setHits(sum_hits.get("value").toString());
            
            mainList.add(pm);   
        }
               
        
        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
          
            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    public Object getCustomPagesChildPagination(JSONObject aggs, JSONObject objParams) {
    	ObjectMapper mapper = new ObjectMapper();
    	String filter = objParams.get("filter").toString();
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<PagesModel> mainList = new ArrayList<>();
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
           
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visit = (JSONObject) one_obj.get("avg_page_visit");	
            
            
            PagesModel pm = new PagesModel();
            pm.setAvgPagesPerVisit(avg_page_visit.get("value").toString());
            pm.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            pm.setVisits(sum_visits.get("value").toString());
            pm.setHits(sum_hits.get("value").toString());
           
            
            if (filter.equalsIgnoreCase("user")) {
            	pm.setUser(one_obj.get("key").toString());	
            } else if (filter.equalsIgnoreCase("careerTracks")) {
            	pm.setCareerTracks(one_obj.get("key").toString());
            }  else if (filter.equalsIgnoreCase("careerLevel")) {
            	pm.setCareerLevel(one_obj.get("key").toString());
            } else if (filter.equalsIgnoreCase("geography")) {
            	pm.setGeography(one_obj.get("key").toString());
            }

            mainList.add(pm);
            
        }

        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    public JSONObject getVisitorLogsV3(JSONObject o) {
    	 ObjectMapper mapper = new ObjectMapper();
         JSONObject hits = (JSONObject) o.get("hits");
         JSONArray innerHits = (JSONArray) hits.get("hits");
               
         JSONObject finObject = new JSONObject();
         List<String> filterObj = new ArrayList<String>();
         
         for(Object obj: innerHits) {
         	JSONObject objHits = (JSONObject) obj;
         	JSONObject objFil = (JSONObject) objHits.get("fields");
         	JSONArray objFilName = (JSONArray) objFil.get("Visits");
         	for(Object name: objFilName) {
         		filterObj.add(name.toString());
         	}
         }
        
         try {
             String finStr = mapper.writeValueAsString(filterObj);
             JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
             System.out.println("VL converted object"+ convertedObj);
             finObject.put("resultset", convertedObj);
         } catch (JsonProcessingException ex) {
             Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         return finObject;
   }
    
    public Object getVisitorLogsV3Pagination(JSONObject aggs, JSONObject objParams) { 
    	ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<VisitorLogsModel> mainList = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat(".##");
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            
            for (Object two_bucket : two_buckets) {
            	JSONObject two_obj = (JSONObject) two_bucket;
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONObject four = (JSONObject) two_obj.get("4");
                JSONObject five = (JSONObject) two_obj.get("5");
                JSONArray three_buckets = (JSONArray) three.get("buckets");
                JSONArray four_buckets = (JSONArray) four.get("buckets");
                JSONArray five_buckets = (JSONArray) five.get("buckets");
                
                for (Object three_bucket : three_buckets) {
                	JSONObject three_obj = (JSONObject) three_bucket;
                	
                	for (Object four_bucket : four_buckets) {
                    	JSONObject four_obj = (JSONObject) four_bucket;
                    	
                    	for (Object five_bucket : five_buckets) {
                        	JSONObject five_obj = (JSONObject) five_bucket;
                	
                        	// Visits
		                    Object Visits = one_obj.get("key");
                        	// Last Action Time
		                    Object LastActionTime = four_obj.get("key_as_string");
		                    // User
		                    Object User = two_obj.get("key");
		                    // Address
		                    Object Address = three_obj.get("key");
		                    // Country
		                    Object Country = five_obj.get("key");
		                    
		                    
		                    VisitorLogsModel uum = new VisitorLogsModel();
		                    
		                    uum.setVisits(Visits.toString());
		                    uum.setLastactiontime(LastActionTime.toString());
		                    uum.setUser(User.toString());
		                    uum.setAddress(Address.toString());
		                    uum.setCountry(Country.toString());
		                    
		                    mainList.add(uum); 
                    	}
                    }
                }
            }  
        }
        
        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
            
            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    public JSONObject getVisitorLogsV3Child(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject hits = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hits.get("hits");
              
        JSONObject finObject = new JSONObject();
        List<String> filterObj = new ArrayList<String>();
        
        for(Object obj: innerHits) {
        	JSONObject objHits = (JSONObject) obj;
        	JSONObject objFil = (JSONObject) objHits.get("fields");
        	JSONArray objFilName = (JSONArray) objFil.get("CleanPageURL.keyword");
        	for(Object name: objFilName) {
        		filterObj.add(name.toString());
        	}
        }
       
        try {
            String finStr = mapper.writeValueAsString(filterObj);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
           
            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    public Object getVisitorLogsV3ChildPagination(JSONObject aggs, JSONObject objParams) { 
    	ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<VisitorLogsModel> mainList = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat(".##");
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            
            for (Object two_bucket : two_buckets) {
            	JSONObject two_obj = (JSONObject) two_bucket;
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONObject four = (JSONObject) two_obj.get("4");
                JSONObject five = (JSONObject) two_obj.get("5");
                JSONObject six = (JSONObject) two_obj.get("6");
                JSONArray three_buckets = (JSONArray) three.get("buckets");
                JSONArray four_buckets = (JSONArray) four.get("buckets");
                JSONArray five_buckets = (JSONArray) five.get("buckets");
                JSONArray six_buckets = (JSONArray) six.get("buckets");
                
                for (Object three_bucket : three_buckets) {
                	JSONObject three_obj = (JSONObject) three_bucket;
                	
                	for (Object four_bucket : four_buckets) {
                    	JSONObject four_obj = (JSONObject) four_bucket;
                    	
                    	for (Object five_bucket : five_buckets) {
                        	JSONObject five_obj = (JSONObject) five_bucket;
                        	
                        	for (Object six_bucket : six_buckets) {
                            	JSONObject six_obj = (JSONObject) six_bucket;
                	
			                	
			                    // Clean Page URL
			                    Object CleanPageURL = two_obj.get("key");
			                    // Page Title
			                    Object PageTitle = three_obj.get("key");
			                    // Browser
			                    Object Browser = four_obj.get("key");
			                    // OS
			                    Object Os = five_obj.get("key");
			                    // Last Action Time
			                    Object LastActionTime = six_obj.get("key_as_string");	
			                    
			                    
			                    VisitorLogsModel uum = new VisitorLogsModel();
			                    
			                    uum.setCleanPageURL(CleanPageURL.toString());
			                    uum.setPagetitle(PageTitle.toString());
			                    uum.setBrowser(Browser.toString());
			                    uum.setOs(Os.toString());
			                    uum.setLastactiontime(LastActionTime.toString());
			                    
			                    mainList.add(uum); 
                        	}
                    	}
                    }
                }
            }  
        }
        
        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
            
            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    public Object getVisitorLogsV3PaginationExport(JSONObject aggs, JSONObject objParams) { 
    	ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<VisitorLogsModel> mainList = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat(".##");
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            
            for (Object two_bucket : two_buckets) {
            	JSONObject two_obj = (JSONObject) two_bucket;
                JSONObject three = (JSONObject) two_obj.get("3");
                JSONObject four = (JSONObject) two_obj.get("4");
                JSONObject five = (JSONObject) two_obj.get("5");
                JSONArray three_buckets = (JSONArray) three.get("buckets");
                JSONArray four_buckets = (JSONArray) four.get("buckets");
                JSONArray five_buckets = (JSONArray) five.get("buckets");
                
                for (Object three_bucket : three_buckets) {
                	JSONObject three_obj = (JSONObject) three_bucket;
                	
                	for (Object four_bucket : four_buckets) {
                    	JSONObject four_obj = (JSONObject) four_bucket;
                    	
                    	for (Object five_bucket : five_buckets) {
                        	JSONObject five_obj = (JSONObject) five_bucket;
                	
                        	// Visits
		                    Object Visits = one_obj.get("key");
                        	// Last Action Time
		                    Object LastActionTime = four_obj.get("key_as_string");
		                    // User
		                    Object User = two_obj.get("key");
		                    // Address
		                    Object Address = three_obj.get("key");
		                    // Country
		                    Object Country = five_obj.get("key");
		                    
		                    
		                    VisitorLogsModel uum = new VisitorLogsModel();
		                    
		                    uum.setLastactiontime(LastActionTime.toString());
		                    uum.setUser(User.toString());
		                    uum.setAddress(Address.toString());
		                    uum.setCountry(Country.toString());
		                    
		                    mainList.add(uum); 
                    	}
                    }
                }
            }  
        }
        
        try {
            String finStr = mapper.writeValueAsString(mainList);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
            
            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    public JSONObject getMyTE(JSONObject o) {
	   	 ObjectMapper mapper = new ObjectMapper();
	        JSONObject hits = (JSONObject) o.get("hits");
	        JSONArray innerHits = (JSONArray) hits.get("hits");
	              
	        JSONObject finObject = new JSONObject();
	        List<String> filterObj = new ArrayList<String>();
	        
	        for(Object obj: innerHits) {
	        	JSONObject objHits = (JSONObject) obj;
	        	JSONObject objFil = (JSONObject) objHits.get("fields");
	        	JSONArray objFilName = (JSONArray) objFil.get("Hits");
	        	for(Object name: objFilName) {
	        		filterObj.add(name.toString());
	        	}
	        }
	       
	        try {
	            String finStr = mapper.writeValueAsString(filterObj);
	            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
	            System.out.println("MyTE converted object"+ convertedObj);
	            finObject.put("resultset", convertedObj);
	        } catch (JsonProcessingException ex) {
	            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return finObject;
	 }
    
    public Object getMyTEPagination(JSONObject aggs, JSONObject objParams) { 
    	
    	ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<MyTEModel> mainList1 = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat("#.##");
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            
            for (Object two_bucket : two_buckets) {
            	JSONObject two_obj = (JSONObject) two_bucket;
            	JSONObject three = (JSONObject) two_obj.get("3");
                JSONArray three_buckets = (JSONArray) three.get("buckets");
                JSONObject four = (JSONObject) two_obj.get("4");
            	JSONArray four_buckets = (JSONArray) four.get("buckets");
                
                for (Object three_bucket : three_buckets) {
                	JSONObject three_obj = (JSONObject) three_bucket;
                	
                	for (Object four_bucket : four_buckets) {
                    	JSONObject four_obj = (JSONObject) four_bucket;
                	
                        	// Page URL
		                    Object PageURL = two_obj.get("key");
		                    // Country
		                    Object Country = three_obj.get("key");
		                    // Average General Time
		                    Object AveGenTime = df2.format(four_obj.get("key"));
		                    
		                    
		                    MyTEModel uum = new MyTEModel();
		                    
		                    uum.setPageUrl(PageURL.toString());
		                    uum.setGeography(Country.toString());
		                    uum.setAvgGenTime(AveGenTime.toString());
		                    
		                    mainList1.add(uum); 
                	}
                }
            }  
        }
        
        try {
            String finStr = mapper.writeValueAsString(mainList1);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
            
            finObject.put("resultset", convertedObj);
            finObject.put("total", mainList1.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;

    }
    
    
    public Object customReportOverviewReferrersAndDownloadMetrics(JSONObject o, JSONObject params) throws MalformedURLException {
    	JSONObject aggs = (JSONObject) o.get("aggregations");  
    	JSONObject reportObj = (JSONObject) params.get("reportObj");
        String reportType = reportObj.get("reportType").toString();   
    	ObjectMapper mapper = new ObjectMapper();

        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<OverviewReferrersMetrics> mainListReferrer = new ArrayList<>();
        List<OverviewDownloadMetrics> mainListDownload = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat(".##");
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
          
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("total_sum_visits");
            // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("total_sum_hits");
            // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("total_avg_page_duration");
            // Avg Page Visit
            JSONObject avg_page_visits = (JSONObject) one_obj.get("total_avg_page_visits");	
            
            if(reportType.equalsIgnoreCase("referrerOverview")) {
            	OverviewReferrersMetrics orm = new OverviewReferrersMetrics();
            	String refererUrl = kusinaStringUtils.getModStr(one_obj.get("key"));
            	int slashIndex = refererUrl.indexOf('/');
            	String urlName=null;
            	if(refererUrl == "" || refererUrl == null) {
            		urlName = refererUrl;
            	}else if(slashIndex > -1){
            		urlName = refererUrl.split("/")[2];
            	}else {
            		urlName = refererUrl;
            	}
                orm.setReferrerName(urlName);
                orm.setAvgPagesPerVisit(kusinaStringUtils.getModStr(avg_page_visits.get("value")));
                orm.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(kusinaStringUtils.getModStr(avg_page_duration.get("value"))));
                orm.setVisits(kusinaStringUtils.getModStr(sum_visits.get("value")));
                orm.setHits(kusinaStringUtils.getModStr(sum_hits.get("value")));
                mainListReferrer.add(orm);
            }else {
            	OverviewDownloadMetrics odm = new OverviewDownloadMetrics();
            	odm.setPageurl(kusinaStringUtils.getModStr(one_obj.get("key")));
            	odm.setAvgPagesPerVisit(kusinaStringUtils.getModStr(avg_page_visits.get("value")));
            	odm.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(kusinaStringUtils.getModStr(avg_page_duration.get("value"))));
            	odm.setVisits(kusinaStringUtils.getModStr(sum_visits.get("value")));
            	odm.setHits(kusinaStringUtils.getModStr(sum_hits.get("value")));
            	mainListDownload.add(odm);
            }  
        }
               
        
        try {
        	String finStr = reportType.equalsIgnoreCase("referrerOverview") ? mapper.writeValueAsString(mainListReferrer) : mapper.writeValueAsString(mainListDownload);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
          
            finObject.put("resultset", convertedObj);
            int size = reportType.equalsIgnoreCase("referrerOverview") ? mainListReferrer.size() : mainListDownload.size();
            finObject.put("total", size);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    public Object customReportOverviewReferrersAndDownloadMetricsChild(JSONObject aggs, JSONObject objParams) {
    	ObjectMapper mapper = new ObjectMapper();
    	JSONObject reportObj = (JSONObject) objParams.get("reportObj");
    	String reportType = reportObj.get("reportType").toString();
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");
        JSONObject finObject = new JSONObject();
        
        List<OverviewReferrersMetrics> mainListReferrer = new ArrayList<>();
        List<OverviewDownloadMetrics> mainListDownload = new ArrayList<>();
        
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
           
            // Unique Visits Count
            JSONObject sum_visits = (JSONObject) one_obj.get("sum_visits");
           // Unique Hits Count
            JSONObject sum_hits = (JSONObject) one_obj.get("sum_hits");
           // Avg Visit Duration
            JSONObject avg_page_duration = (JSONObject) one_obj.get("avg_page_duration");
           // Avg Page Visit
            JSONObject avg_page_visit = (JSONObject) one_obj.get("avg_page_visit");	
            
            
            if(reportType.equalsIgnoreCase("referrerOverviewChild")) {
            	OverviewReferrersMetrics ormc = new OverviewReferrersMetrics();
            	ormc.setAvgPagesPerVisit(avg_page_visit.get("value").toString());
            	ormc.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            	ormc.setVisits(sum_visits.get("value").toString());
            	ormc.setHits(sum_hits.get("value").toString());
            	ormc.setUser(one_obj.get("key").toString());
            	mainListReferrer.add(ormc);
            }else {
            	OverviewDownloadMetrics odmc = new OverviewDownloadMetrics();
            	odmc.setAvgPagesPerVisit(avg_page_visit.get("value").toString());
            	odmc.setAvgVisit(kusinaNumberFormat.convertAndRoudUp(avg_page_duration.get("value").toString()));
            	odmc.setVisits(sum_visits.get("value").toString());
            	odmc.setHits(sum_hits.get("value").toString());
            	odmc.setUser(one_obj.get("key").toString());
            	mainListDownload.add(odmc);
            }
        }

        try {
            String finStr = reportType.equalsIgnoreCase("referrerOverviewChild") ? mapper.writeValueAsString(mainListReferrer) : mapper.writeValueAsString(mainListDownload);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
            int size = reportType.equalsIgnoreCase("referrerOverviewChild") ? mainListReferrer.size() : mainListDownload.size();
            finObject.put("total", size);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    
    public JSONObject customReportEvents(JSONObject o) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject one = (JSONObject) aggs.get("1");
        JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();

        List<EventsModel> eml = new ArrayList<>();
       

        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            for (Object two_bucket : two_buckets) {
            	JSONObject two_obj = (JSONObject) two_bucket;
            	JSONObject three = (JSONObject) two_obj.get("3");
            	JSONArray three_buckets = (JSONArray) three.get("buckets");
            	for(Object three_bucket : three_buckets ) {
            		 JSONObject three_obj = (JSONObject) three_bucket;
            		
//            		eml.add(new EventsModel(
//            				one_obj.get("key").toString(),
//            				one_obj.get("doc_count").toString(),
//            				two_obj.get("key").toString(),
//            				two_obj.get("doc_count").toString(),
//            				three_obj.get("key").toString(),
//            				three_obj.get("doc_count").toString()
//            				));
            		 
            		EventsModel em = new EventsModel();
            		//Getting Event Categories
             		em.setEventCategories(kusinaStringUtils.getModStr(one_obj.get("key")));
            		//Getting Event Actions
             		em.setEventAction(kusinaStringUtils.getModStr(two_obj.get("key")));
             		//Getting Events Total
             		em.setTotalEvents(kusinaStringUtils.getModStr(two_obj.get("doc_count")));
             		
             		eml.add(em);
            	}
            }
            
        }

        try {
            String finStr = mapper.writeValueAsString(eml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
            finObject.put("total", eml.size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
    }
    
    public String getHistoryAnnounceId(JSONObject o) throws ParseException {
    	ObjectMapper mapper = new ObjectMapper();
        JSONObject hitsObj = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");
        String historyId = null;
        
        JSONObject finObject = new JSONObject();

        for (Object obj : innerHits) {
            JSONObject oj = (JSONObject) obj;
            String _id = oj.get("_id").toString();
            
            historyId = _id;
        }
        
        return historyId;
    }
    
    public List<AnnounceModel> getAnnounce(JSONObject o, JSONObject params ) throws ParseException {
    	ObjectMapper mapper = new ObjectMapper();
        JSONObject hitsObj = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        JSONObject finObject = new JSONObject();
        List<AnnounceModel> aml = new ArrayList<>();
        
        AnnounceModel am;
           
        for (Object obj : innerHits) {
            JSONObject oj = (JSONObject) obj;
            String _id = oj.get("_id").toString();
            JSONObject idObj = historyService.getAllReadAnnounce(params, _id);
            JSONObject userReadPerAnnounceId = historyService.getReadAnnouncePerUserPerAnnounce(params, _id);
            JSONObject _sourceObj = (JSONObject) oj.get("_source");
            
            am = new AnnounceModel();
            //Getting unique ID
            am.setId(_id);
            //Getting ID per announcement to get total read count per announcement from histories index.
            am.setTotalReadCount(kusinaStringUtils.getModStr(idObj.get("value")));
            //Getting read status per announcement id per user.
            am.setReadStatus(kusinaStringUtils.getModStr(userReadPerAnnounceId.get("value")));
            //Getting Announcement Type
            am.setType(kusinaStringUtils.getModStr(_sourceObj.get("announcement_type")));
            //Getting Due Date
            //am.setDueDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("announcement_due_date").toString())));
            am.setDueDate(kusinaStringUtils.getModStr(kusinaDateUtils.checkDate(Long.parseLong(_sourceObj.get("announcement_due_date").toString()), params.get("timezone").toString())));
            //Getting Title
            am.setTitle(kusinaStringUtils.getModStr(_sourceObj.get("announcement_title")));
            //Getting Content
            am.setContent(kusinaStringUtils.getModStr(_sourceObj.get("announcement_content")));
            //Getting Status
            am.setStatus(kusinaStringUtils.getModStr(_sourceObj.get("announcement_status")));
            //Getting Created Date
            am.setCreatedDate(kusinaStringUtils.getModStr(kusinaDateUtils.checkDate(Long.parseLong(_sourceObj.get("announcement_created_date").toString()), params.get("timezone").toString())));
            //Getting Last Update Date
            am.setLastUpdateDate(kusinaStringUtils.getModStr(kusinaDateUtils.checkDate(Long.parseLong(_sourceObj.get("announcement_last_updated_date").toString()), params.get("timezone").toString())));
            //Getting Last Update By
            am.setLastUpdateBy(kusinaStringUtils.getModStr(_sourceObj.get("announcement_last_updated_by")));
            
            aml.add(am);
        }
       
        try {
            String finStr = mapper.writeValueAsString(aml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aml;
    }	
    
    
    public List<AnnounceModel> getAnnounceLive(JSONObject o, JSONObject params ) throws ParseException {
    	ObjectMapper mapper = new ObjectMapper();
        JSONObject hitsObj = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        JSONObject finObject = new JSONObject();
        List<AnnounceModel> aml = new ArrayList<>();
        
        AnnounceModel am;
           
        for (Object obj : innerHits) {
            JSONObject oj = (JSONObject) obj;
            String _id = oj.get("_id").toString();
            JSONObject idObj = historyService.getAllReadAnnounce(params, _id);
            JSONObject userReadPerAnnounceId = historyService.getReadAnnouncePerUserPerAnnounce(params, _id);
            JSONObject _sourceObj = (JSONObject) oj.get("_source");
            
            am = new AnnounceModel();
            //Getting unique ID
            am.setId(_id);
            //Getting ID per announcement to get total read count per announcement from histories index.
            am.setTotalReadCount(kusinaStringUtils.getModStr(idObj.get("value")));
            //Getting read status per announcement id per user.
            am.setReadStatus(kusinaStringUtils.getModStr(userReadPerAnnounceId.get("value")));
            //Getting Announcement Type
            am.setType(kusinaStringUtils.getModStr(_sourceObj.get("announcement_type")));
            //Getting Due Date
            am.setDueDate(kusinaStringUtils.getModStr(kusinaDateUtils.checkDate(Long.parseLong(_sourceObj.get("announcement_due_date").toString()), params.get("timezone").toString())));
            //Getting Title
            am.setTitle(kusinaStringUtils.getModStr(_sourceObj.get("announcement_title")));
            //Getting Content
            am.setContent(kusinaStringUtils.getModStr(_sourceObj.get("announcement_content")));
            //Getting Status
            am.setStatus(kusinaStringUtils.getModStr(_sourceObj.get("announcement_status")));
            //Getting Created Date
            am.setCreatedDate(kusinaStringUtils.getModStr(kusinaDateUtils.checkDate(Long.parseLong(_sourceObj.get("announcement_created_date").toString()), params.get("timezone").toString())));
            //Getting Last Update Date
            am.setLastUpdateDate(kusinaStringUtils.getModStr(kusinaDateUtils.checkDate(Long.parseLong(_sourceObj.get("announcement_last_updated_date").toString()), params.get("timezone").toString())));
            //Getting Last Update By
            am.setLastUpdateBy(kusinaStringUtils.getModStr(_sourceObj.get("announcement_last_updated_by")));
            
            aml.add(am);
        }
       
        try {
            String finStr = mapper.writeValueAsString(aml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aml;
    }	
    
    public List<HistoryModel> getHistories(JSONObject o,JSONObject params) throws ParseException {
    	ObjectMapper mapper = new ObjectMapper();
        JSONObject hitsObj = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        JSONObject finObject = new JSONObject();
        List<HistoryModel> hml = new ArrayList<>();
        
        HistoryModel hm;
           
        for (Object obj : innerHits) {
            JSONObject oj = (JSONObject) obj;
            //doc.add(o.get("_source"));
            String _id = (String) oj.get("_id");
            JSONObject _sourceObj = (JSONObject) oj.get("_source");
            
            hm = new HistoryModel();
            //Getting ID
            hm.setId(_id);
            //Getting History Type
            hm.setType(kusinaStringUtils.getModStr(_sourceObj.get("history_type")));
            //Getting Action Date
            //hm.setActionDate(kusinaDateUtils.convertEpochToDate(_sourceObj.get("history_action_date").toString()));
            hm.setActionDate(kusinaStringUtils.getModStr(kusinaDateUtils.checkDate(Long.parseLong(_sourceObj.get("history_action_date").toString()), params.get("timezone").toString())));
            //Getting User Eid
            hm.setUserEid(kusinaStringUtils.getModStr(_sourceObj.get("history_user_eid")));
            //Getting HistoryID
            hm.setDocId(kusinaStringUtils.getModStr(_sourceObj.get("history_doc_id")));
            //Getting Status
            hm.setActionType(kusinaStringUtils.getModStr(_sourceObj.get("history_action_type")));
                  
            hml.add(hm);
        }
       
        try {
            String finStr = mapper.writeValueAsString(hml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hml;
    }	
    
    
    public JSONObject getReadAnnouncePerUser(JSONObject o) {
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject readCount = (JSONObject) aggs.get("readCount");
   	
   	return readCount;
   }
    
    public JSONObject getTotalReadAnnouncePerUser(JSONObject o) {
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject readCount = (JSONObject) aggs.get("readCount");
   	
   	return readCount;
   }
    
    
    public JSONObject getAllReadAnnounce(JSONObject o) {
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject readCount = (JSONObject) aggs.get("readCountAll");
   	
   	return readCount;
   }
    
    
    public JSONObject getAnnounceCountBeforeDueDate(JSONObject o) {
        JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject readCount = (JSONObject) aggs.get("AnnounceCountBeforeDueDate");
       	return readCount;
   }
    
    public List<ProfileModel> getProfile(JSONObject o, JSONObject params ) throws ParseException {
    	
    	ObjectMapper mapper = new ObjectMapper();
        JSONObject hitsObj = (JSONObject) o.get("hits");
        JSONArray innerHits = (JSONArray) hitsObj.get("hits");

        JSONObject finObject = new JSONObject();
        List<ProfileModel> aml = new ArrayList<>();
        
        ProfileModel am;
           
        for (Object obj : innerHits) {
            JSONObject oj = (JSONObject) obj;
            String _id = oj.get("_id").toString();
            JSONObject _sourceObj = (JSONObject) oj.get("_source");
            
            am = new ProfileModel();
            //Getting unique ID
            am.setId(_id);
            //Getting Announcement Type
            am.setAirid(kusinaStringUtils.getModStr(_sourceObj.get("AIRID")));
            //Getting Due Date
            am.setIdSite(kusinaStringUtils.getModStr(_sourceObj.get("ID")));
            //Getting Title
            am.setAppName(kusinaStringUtils.getModStr(_sourceObj.get("APPNAME")));
            //Getting Created Date
            am.setCreatedDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("created_date").toString())));
            //Getting Last Update Date
            am.setLastUpdateDate(kusinaStringUtils.getModStr(kusinaDateUtils.convertEpochToDate(_sourceObj.get("last_update_date").toString())));
            //Getting Last Update By
            am.setLastUpdateBy(kusinaStringUtils.getModStr(_sourceObj.get("last_update_by")));
            
            aml.add(am);
        }
       
        try {
            String finStr = mapper.writeValueAsString(aml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aml;
    }	
    
	public Object getProfileExport2(JSONObject aggs, JSONObject objParams) { 
		ObjectMapper mapper = new ObjectMapper();
	    
	    JSONObject hitsObj = (JSONObject) aggs.get("hits");
	    JSONArray innerHits = (JSONArray) hitsObj.get("hits");
	
	    JSONObject finObject = new JSONObject();
	    List<ProfileModel> aml = new ArrayList<>();
	    
	    ProfileModel am;
	    for (Object obj : innerHits) {
	        JSONObject oj = (JSONObject) obj;
	        String _id = oj.get("_id").toString();
	        JSONObject _sourceObj = (JSONObject) oj.get("_source");
	        
	        am = new ProfileModel();
	        //Getting Announcement Type
	        am.setAirid(kusinaStringUtils.getModStr(_sourceObj.get("AIRID")));
	        //Getting Due Date
	        am.setIdSite(kusinaStringUtils.getModStr(_sourceObj.get("ID")));
	        //Getting Title
	        am.setAppName(kusinaStringUtils.getModStr(_sourceObj.get("APPNAME")));
	       
	        aml.add(am);
	    }
	    
	    try {
	        String finStr = mapper.writeValueAsString(aml);
	        JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);
	        
	        finObject.put("resultset", convertedObj);
	        finObject.put("total", aml.size());
	    } catch (JsonProcessingException ex) {
	        Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return finObject;
	
	}
	
	public List<AirIdModel> getAllAirid(JSONObject o, JSONObject params ) throws ParseException {
    	
    	ObjectMapper mapper = new ObjectMapper();

    	JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject one = (JSONObject) aggs.get("1");
        System.out.println("One: " + one);
        JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();
        List<AirIdModel> aml = new ArrayList<>();
        
        AirIdModel am;
           
        for (Object obj : one_buckets) {
            JSONObject oj = (JSONObject) obj;
            
            Object Airid = oj.get("key");
            
            am = new AirIdModel();
           
            am.setAirid(Airid.toString());
            
            aml.add(am);
        }
       
        try {
            String finStr = mapper.writeValueAsString(aml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aml;
    }
	
	public List<SiteIdModel> getSiteId(JSONObject o, JSONObject params ) throws ParseException {
    	
    	ObjectMapper mapper = new ObjectMapper();

    	JSONObject aggs = (JSONObject) o.get("aggregations");
        JSONObject one = (JSONObject) aggs.get("1");
        System.out.println("One: " + one);
        JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();
        List<SiteIdModel> aml = new ArrayList<>();
        
        SiteIdModel am;
           
        for (Object obj : one_buckets) {
            JSONObject oj = (JSONObject) obj;
            
            Object SiteId = oj.get("key");
            
            am = new SiteIdModel();
           
            am.setSiteId(SiteId.toString());
            
            aml.add(am);
        }
       
        try {
            String finStr = mapper.writeValueAsString(aml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aml;
    }
	
	
	public JSONObject getITFAggregations(JSONObject o) {
	 ObjectMapper mapper = new ObjectMapper();
     JSONObject aggs = (JSONObject) o.get("aggregations");
     JSONObject one = (JSONObject) aggs.get("1");
     JSONArray one_buckets = (JSONArray) one.get("buckets");

        JSONObject finObject = new JSONObject();

        List<ITFModel> itfml = new ArrayList<>();
       
        for (Object one_bucket : one_buckets) {
            JSONObject one_obj = (JSONObject) one_bucket;
            JSONObject two = (JSONObject) one_obj.get("2");
            JSONArray two_buckets = (JSONArray) two.get("buckets");
            for (Object two_bucket : two_buckets) {
            	JSONObject two_obj = (JSONObject) two_bucket;
            	JSONObject three = (JSONObject) two_obj.get("3");
            	JSONArray three_buckets = (JSONArray) three.get("buckets");
            	for(Object three_bucket : three_buckets ) {
            		 JSONObject three_obj = (JSONObject) three_bucket;
            		 JSONObject four = (JSONObject) three_obj.get("4");
                 	 JSONArray four_buckets = (JSONArray) four.get("buckets");
                 	 for(Object four_bucket : four_buckets ) {
	               		 JSONObject four_obj = (JSONObject) four_bucket;
	               		 JSONObject five = (JSONObject) four_obj.get("5");
	                	 JSONArray five_buckets = (JSONArray) five.get("buckets");
	                 	 for(Object five_bucket : five_buckets ) {
		               		 JSONObject five_obj = (JSONObject) five_bucket;
		               		 JSONObject six = (JSONObject) five_obj.get("6");
		                	 JSONArray six_buckets = (JSONArray) six.get("buckets");
		                 	 for(Object six_bucket : six_buckets ) {
			               		 JSONObject six_obj = (JSONObject) six_bucket;
			               		 JSONObject seven = (JSONObject) six_obj.get("7");
			                	 JSONArray seven_buckets = (JSONArray) seven.get("buckets");
			                 	 for(Object seven_bucket : seven_buckets ) {
				               		 JSONObject seven_obj = (JSONObject) seven_bucket;
				               		 JSONObject eight = (JSONObject) seven_obj.get("8");
				               		 JSONObject nine = (JSONObject) seven_obj.get("9");
				               		JSONObject ten = (JSONObject) seven_obj.get("10");
				               		 
				               		 
				               		ITFModel itfm = new ITFModel(
				               				kusinaStringUtils.getModStr(one_obj.get("key")),
				               				kusinaStringUtils.getModStr(two_obj.get("key")),
				               				kusinaStringUtils.getModStr(three_obj.get("key")),
				               				kusinaStringUtils.getModStr(four_obj.get("key")),
				               				kusinaStringUtils.getModStr(five_obj.get("key")),
				               				kusinaStringUtils.getModStr(six_obj.get("key")),
				               				kusinaStringUtils.getModStr(seven_obj.get("key")),
				               				kusinaStringUtils.getModStr(eight.get("value")),
				               				kusinaStringUtils.getModStr(nine.get("value")),
				               				kusinaStringUtils.getModStr(ten.get("value"))				               				
				               				);
				               	 	itfml.add(itfm);	 
				             	} 		                	 
			             	} 
		             	} 
	             	}
            	}
            }
            
        }

        try {
            String finStr = mapper.writeValueAsString(itfml);
            JSONArray convertedObj = new Gson().fromJson(finStr, JSONArray.class);

            finObject.put("resultset", convertedObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KusinaExtractionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finObject;
	}

}
