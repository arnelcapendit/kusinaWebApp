	package com.accenture.api.service;

import org.json.simple.JSONObject;

public interface VisitorService {
    
    public JSONObject getVisitorLogs(JSONObject object);
    
    public JSONObject getVisitorLogsV2(JSONObject object);
    
    public JSONObject getVisitorLogsV3(JSONObject object);
    
    public JSONObject getVisitorLogTotal(JSONObject object);
    
    public JSONObject getVisitorLogsV3Child(JSONObject object);
    
    public JSONObject getVisitorLogTotalChild(JSONObject object);
    
    public JSONObject getVisitorLogsExport(JSONObject object);
    
}
