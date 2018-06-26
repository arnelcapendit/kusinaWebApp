package com.accenture.api.controller;

import java.util.Arrays;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.api.service.AnnounceService;
import com.accenture.api.service.DashboardService;
import com.accenture.api.service.HistoryService;
import com.accenture.api.service.KusinaService;
import com.accenture.api.utils.KusinaDateUtils;

/**
 *
 * @author marlon.naraja
 */
@RestController

@CrossOrigin(origins = "*")
public class DashboardController {

    public static final String APP_DASHBOARD = "/rest/dashboard";
    public static final String APP_DASHBOARD_ONLOAD = "/rest/dashboard_onload";

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private KusinaService kusinaService;

    @Autowired
    private KusinaDateUtils kusinaDateUtils;
    
    @Autowired
    private AnnounceService announceService;
    
    @Autowired
    private HistoryService historyService;
    
    @RequestMapping(value = APP_DASHBOARD_ONLOAD, method = RequestMethod.GET)
    public JSONObject getDashboardOnload(
            HttpServletRequest request) {

        JSONObject result = new JSONObject();
        JSONObject user = (JSONObject) request.getAttribute("user");
        //JSONObject args = (JSONObject) request.getAttribute("params");
        
        //JSONObject preferences = kusinaService.getAppPreferences();
        JSONObject settings = kusinaService.getAppSettings();

        Long max = (Long) settings.get("max_results_count");
        user.put("max", max);

        JSONObject appname = dashboardService.getAppFilter(user);
        
        //Long max = (Long) settings.get("max_results_count");
        //user.put("max", max);
        
        //user.remove("max");
        //preferences.put("max", max);
        //preferences.put("id", appname.get("id"));
        //preferences.put("gte", kusinaDateUtils.getStartTimeToday());
        //preferences.put("lte", kusinaDateUtils.getEndTimeToday());
        //preferences.put("airid", user.get("user_airid"));
        //preferences.put("user_timezone", new DateTime().getZone());
        //JSONObject visualization = dashboardService.getAllFilters(preferences);

        //Arrays.asList("id", "max", "gte", "lte", "airid", "user_timezone").stream().forEach((s) -> {
        //    preferences.remove(s);
        //});

        //result.put("Visualization", visualization);
        //result.put("Preferences", preferences);
        //result.put("Settings", settings);
        result.put("UserDetails", user);
        result.put("AppNameList", appname.get("AppNameList"));
        //result.put("status","to be decommissioned");
        return result;
    }

    @RequestMapping(value = APP_DASHBOARD, method = RequestMethod.GET)
    public JSONObject getDashboard(
            HttpServletRequest request
    ) {
        
        JSONObject args = (JSONObject) request.getAttribute("params");
        JSONObject result = new JSONObject();
        //JSONObject user = (JSONObject) request.getAttribute("user");
        //JSONObject appname = dashboardService.getAppFilter(user);
        JSONObject preferences = kusinaService.getAppPreferences();
        //JSONObject settings = kusinaService.getAppSettings();
        
        String gte = String.valueOf(args.get("gte"));
        String lte = String.valueOf(args.get("lte"));
        String id = String.valueOf(args.get("id"));
        String airid = String.valueOf(args.get("airid"));
        String zone = String.valueOf(args.get("zone"));
 
        preferences.put("id", id);
        preferences.put("max", (long) 10);
        preferences.put("gte", kusinaDateUtils.convertStrDateToMillis(gte));
        preferences.put("lte", kusinaDateUtils.convertStrDateToMillis(lte));
        preferences.put("airid", airid);
        preferences.put("user_timezone",zone);
        JSONObject visualization = dashboardService.getAllFilters(preferences);

        Arrays.asList("id", "max", "gte", "lte", "airid", "user_timezone").stream().forEach((s) -> {
            preferences.remove(s);
        });

        result.put("Visualization", visualization);

        return result;
    }
}
