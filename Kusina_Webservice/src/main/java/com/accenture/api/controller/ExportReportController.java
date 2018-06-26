/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.api.controller;

import com.accenture.api.service.CustomReportService;
import com.accenture.api.service.DashboardService;
import com.accenture.api.service.KusinaService;
import com.accenture.api.service.MyTEService;
import com.accenture.api.service.ProfileService;
import com.accenture.api.service.VisitorService;
import com.accenture.api.utils.ExportConfigUtils;
import com.accenture.api.utils.KusinaDateUtils;
import com.accenture.api.utils.KusinaStringUtils;
import com.accenture.api.utils.KusinaValidationUtils;
import com.github.opendevl.JFlat;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
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
@RequestMapping("/rest/export")
@CrossOrigin(origins = "*")
public class ExportReportController {

    @Autowired
    private KusinaStringUtils kusinaStringUtils;
    @Autowired
    private KusinaDateUtils kusinaDateUtils;
    @Autowired
    private KusinaValidationUtils kusinaValidationUtils;

    @Autowired
    private CustomReportService customReportService;
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private KusinaService kusinaService;
    
    @Autowired
    private VisitorService visitorService;
    
    @Autowired
    private MyTEService myteService;
    
    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private ExportConfigUtils exportConfigUtils;

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
    private long sizeLimit = 1000000000; 								//1GB
    private int jsonLimit = 1000;   									//separate files more than stated count

    @RequestMapping(value = "/customReports", method = RequestMethod.GET)
    public void exportCustomReportToCsv(
//            @RequestParam(value = "reportType", required = true) String reportType,
//            @RequestParam(value = "airid", required = true) String airid,
//            @RequestParam(value = "id", required = true) String id,
//            @RequestParam(value = "gte", required = true) String gte,
//            @RequestParam(value = "lte", required = true) String lte,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        JSONObject args = (JSONObject)request.getAttribute("params");

//        args.put("reportType", reportType);
//        args.put("airid", airid);
//        args.put("id", id);
//        args.put("gte", gte);
//        args.put("lte", lte);
        
        String reportType = String.valueOf(args.get("reportType"));
        String airid = String.valueOf(args.get("airid"));

        if (kusinaValidationUtils.isValidExportReportType(reportType)) {
            String str = "";
            JSONObject customReport = null;
            if (reportType.equalsIgnoreCase("userMetrics")) {
                customReport = customReportService.getCustomReportsOverviewUserMetrics(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else if (reportType.equalsIgnoreCase("pageMetrics")) {
                customReport = customReportService.getCustomReportsOverviewPageMetrics(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else if (reportType.equalsIgnoreCase("referrersMetrics")) {
                customReport = customReportService.getCustomReportsOverviewReferrersMetrics(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else if (reportType.equalsIgnoreCase("downloadMetrics")) {
                customReport = customReportService.getCustomReportsOverviewDownloadMetrics(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            }else if (reportType.equalsIgnoreCase("userUsage")) {
                customReport = customReportService.getUsageByUser(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else if (reportType.equalsIgnoreCase("visitorLogs")) {
                customReport = (JSONObject) visitorService.getVisitorLogsV2(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else if (kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
                customReport = (JSONObject) customReportService.getCustomReportPages(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else if (reportType.equalsIgnoreCase("events")) {
                customReport = (JSONObject) customReportService.getCustomReportsEventMetrics(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else if (reportType.equalsIgnoreCase("profile")) {
                customReport = (JSONObject) profileService.getProfileExport(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            } else {
                customReport = customReportService.getCustomReports(args);
                str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
            }
            //************************
            StringBuilder sb = new StringBuilder();
            sb.append(airid);
            sb.append("_");
            sb.append(reportType);
            sb.append("_");
            sb.append(sdf.format(new Date()));

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.csv\"", sb.toString()));

            String webInfPath = "";
            String modPath = "";

            try {

                webInfPath = request.getSession().getServletContext().getRealPath("");
                modPath = webInfPath.substring(0, webInfPath.lastIndexOf("kusina"));
                modPath = String.format(modPath + "%s.csv", reportType);
                OutputStream outputStream = response.getOutputStream();

                JFlat flatMe = new JFlat(str);
                //directly write the JSON document to CSV
                flatMe.json2Sheet().headerSeparator().write2csv(modPath);
                outputStream.write(Files.readAllBytes(new File(modPath).toPath()));
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                File file = new File(modPath);
                if (file.exists()) {
                    file.delete();
                }
            }
        }

    }

    @RequestMapping(value = "/myTe", method = RequestMethod.GET)
    public void exportMyTeToCsv(
//            @RequestParam(value = "airid", required = true) String airid,
//            @RequestParam(value = "id", required = true) String id,
//            @RequestParam(value = "pageURL", required = true) String pageURL,
//            @RequestParam(value = "action", required = true) String action,
//            @RequestParam(value = "gte", required = true) String gte,
//            @RequestParam(value = "lte", required = true) String lte,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        JSONObject args = (JSONObject)request.getAttribute("params");

        
//        args.put("airid", airid);
//        args.put("id", id);
        args.put("pageUrlParam", kusinaStringUtils.getPageUrlParam(String.valueOf(args.get("pageURL")), String.valueOf(args.get("action"))));
//        args.put("gte", gte);
//        args.put("lte", lte);

        JSONObject customReport = customReportService.getMyTEUniquePageUrl(args);
        String str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";
        //************************

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(args.get("airid")));
        sb.append("_");
        sb.append("PageVsCountry");
        sb.append("_");
        sb.append(sdf.format(new Date()));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.csv\"", sb.toString()));

        String webInfPath = "";
        String modPath = "";

        try {

            webInfPath = request.getSession().getServletContext().getRealPath("");
            modPath = webInfPath.substring(0, webInfPath.lastIndexOf("kusina"));

            modPath = String.format(modPath + "%s.csv", "myTE");

            OutputStream outputStream = response.getOutputStream();

            JFlat flatMe = new JFlat(str);
            //directly write the JSON document to CSV
            flatMe.json2Sheet().headerSeparator().write2csv(modPath);
            outputStream.write(Files.readAllBytes(new File(modPath).toPath()));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            File file = new File(modPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @RequestMapping(value = "/visualization", method = RequestMethod.GET)
    public void exportVisualizationToCsv(
            @RequestParam(required = true, value = "airid") String airid,
            @RequestParam(required = true, value = "gte") String gte,
            @RequestParam(required = true, value = "lte") String lte,
            @RequestParam(required = true, value = "id") String id,
            @RequestParam(required = true, value = "visualType") String visualType,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {

        JSONObject preferences = kusinaService.getAppPreferences();

        preferences.put("id", id);
        preferences.put("gte", kusinaDateUtils.convertStrDateToMillis(gte));
        preferences.put("lte", kusinaDateUtils.convertStrDateToMillis(lte));
        preferences.put("max", 500);
        preferences.put("airid", airid);
        preferences.put("user_timezone", new DateTime(Long.parseLong(gte) / 1000).getZone());
        preferences.put("payload", visualType);
        JSONObject visualization = dashboardService.getVisualization(preferences);
        String str = visualization != null ? visualization.toJSONString() : "";

        //************************
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.csv\"", visualType));

        String webInfPath = "";
        String modPath = "";

        try {

            webInfPath = request.getSession().getServletContext().getRealPath("");
            modPath = webInfPath.substring(0, webInfPath.lastIndexOf("kusina"));
            modPath = String.format(modPath + "%s.csv", visualType);

            OutputStream outputStream = response.getOutputStream();

            //String str = new String(Files.readAllBytes(Paths.get("C:\\Users\\marlon.naraja\\Documents\\Marlon P. Naraja\\IM Project\\AIBS_copy.json")));
            JFlat flatMe = new JFlat(str);
            //directly write the JSON document to CSV
            flatMe.json2Sheet().headerSeparator().write2csv(modPath);

            outputStream.write(Files.readAllBytes(new File(modPath).toPath()));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            File file = new File(modPath);
            if (file.exists()) {
                file.delete();
            }
        }

    }
    
/** 
*   @author arnel.m.capendit 
*   @updated 3/5/2018
*   @comment This is a new approach for elastic search export for custom report web services...
*	 	  
*/
    @RequestMapping(value="/new/customReports", method = RequestMethod.GET)
    public void exportCustomReportToCsvV2(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    	
    	JSONObject args = (JSONObject)request.getAttribute("params");
    	String reportType = String.valueOf(args.get("reportType"));
    	OutputStream outputStream;
        StringBuilder sb = new StringBuilder();  
        String webInfPath = "";
        String modPath = "";
        String fileName ="";
        String fileType =".csv";
        String str = "";
        int lastIndex =0;
        int total = 0;
        int fileToWrite = 0;
        
        JSONObject customReport = null;
        String customReportItem = null;
        JSONObject customReportTotal = null;
        if (kusinaValidationUtils.isValidExportReportType(reportType)) {
        	if (kusinaValidationUtils.isValidUsageType(String.valueOf(args.get("reportType")))) {
            	customReportItem = "usageReport";  
            	customReportTotal = customReportService.getCustomReportTotal(args);
            } else if ("visitorLogs".equalsIgnoreCase(reportType)) {
            	customReportItem = "visitorLogs";
            	customReportTotal = visitorService.getVisitorLogTotal(args);
            } else if ("myTE".equalsIgnoreCase(reportType)) {
            	customReportItem = "myTE";
            	customReportTotal = myteService.getMyTETotal(args);
            } else if(kusinaValidationUtils.isValidPagesType(String.valueOf(args.get("reportType")))) {
        		customReportItem = "pagesReport";	
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} else if(kusinaValidationUtils.isValidOverviewType(String.valueOf(args.get("reportType")))) {
        		customReportItem = "overviewReport";	
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} else if(kusinaValidationUtils.isValidEventsType(String.valueOf(args.get("reportType")))) {
        		customReportItem = "eventsReport";	
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} else if("users".equalsIgnoreCase(reportType)) {
        		customReportItem = "users";	
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} else if("feedbacks".equalsIgnoreCase(reportType)) {
        		customReportItem = "feedbacks";	
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} else if("profile".equalsIgnoreCase(reportType)) {
        		customReportItem = "profile";	
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} else if(kusinaValidationUtils.isValidITFType(String.valueOf(args.get("reportType")))) {
        		customReportItem = "itfReport";	
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} else {
        		customReportItem = "aibspBviReport";
        		customReportTotal = customReportService.getCustomReportTotal(args);
        	} 
        } 
  
        Long valueTotal = (Long) customReportTotal.get("value");
        System.out.println("TOTAL VALUE: "+ valueTotal);
        fileToWrite = (int) (valueTotal / jsonLimit + ((valueTotal % jsonLimit == 0) ? 0 : 1));  
        System.out.println("FILETOWRITE VALUE: "+ fileToWrite);
        try {     
            webInfPath = request.getSession().getServletContext().getRealPath("");
            modPath = webInfPath + '\\';
           
            for(int i=0;i<fileToWrite;i++){
            	args.replace("from", (i*jsonLimit));
            	args.replace("size", jsonLimit);
            	customReport = exportConfigUtils.getCustomReportItem(args, customReportItem); 
            	System.out.println("customReport: "+ customReport);
            	str = customReport.get("resultset") != null ? customReport.get("resultset").toString() : "";	
                fileName = String.format(modPath + "%s"+fileType, reportType+i);
                System.out.println("FILENAME VALUE: "+ fileName);
                JFlat flatMe = new JFlat(str);
                flatMe.json2Sheet().headerSeparator().write2csv(fileName);
            }
            
            lastIndex = exportConfigUtils.mergeCsvFiles(reportType, fileToWrite, modPath, fileType, sizeLimit);
            if(lastIndex>=0){
            	if(Arrays.asList("users", "feedbacks").contains(reportType)) {
                	sb.append("piwik");
                }else {
                	sb.append(String.valueOf(args.get("airid")));
                }      
                sb.append("_");
                sb.append(reportType);
                sb.append("_");
                sb.append(sdf.format(new Date()));
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s"+fileType+"\"", sb.toString()));
                outputStream = response.getOutputStream();  
                outputStream.write(Files.readAllBytes(new File(modPath+reportType+fileType).toPath()));
                outputStream.flush();
                outputStream.close(); 
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            System.out.println("deleted: " + modPath + reportType+ fileType);
            File file = new File(modPath+reportType+fileType);
            if (file.exists()) {
                file.delete();
                for(int i =0; i<=fileToWrite; i++){
                    System.out.println("deleted: " + modPath + reportType + i + fileType);
                    file = new File(modPath + reportType + i + fileType);
                    file.delete();
                }
            }
        }
    }
}
