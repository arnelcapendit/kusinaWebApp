package com.accenture.api.service;

import org.json.simple.JSONObject;

/**
 *
 * @author marlon.naraja
 */
public interface DashboardService {

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getVisualization(JSONObject object);

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getAllFilters(JSONObject object);

    /**
     *
     * @param object
     * @return
     */
    public JSONObject getAppFilter(JSONObject object);
}
