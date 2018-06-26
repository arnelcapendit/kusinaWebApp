package com.accenture.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaConfigUtils {

    @Value("${host.url}")
    private String appClientHost;

    @Value("${host.port}")
    private String appClientPort;

    @Value("${host.env}")
    private String appClientEnvironment;

    @Value("${app.kusina.index}")
    private String appKusinaPrefix;
    
    @Value("${app.kusina.users.index}")
    private String appKusinaUserPrefix;
    
    @Value("${app.kusina.feedbacks.index}")
    private String appKusinaFeedbacksPrefix;
    
    @Value("${app.kusina.sessions.index}")
    private String appKusinaSessionsPrefix;
    
    @Value("${app.kusina.settings.index}")
    private String appKusinaSettingsPrefix;
    
    @Value("${app.kusina.preferences.index}")
    private String appKusinaPreferencesPrefix;
    
    @Value("${app.kusina.airids.index}")
    private String appKusinaAirIDsPrefix;
    
    @Value("${app.kusina.profile.index}")
    private String appKusinaProfilesPrefix;
        
    @Value("${app.webmetrics.index}")
    private String appWebMetricsPrefix;
    
    @Value("${app.kusina.announce.index}")
    private String appKusinaAnnouncePrefix;
    
    @Value("${app.kusina.history.index}")
    private String appKusinaHistoryPrefix;
    
    public String getAppKusinaHistoryPrefix() {
    	return appKusinaHistoryPrefix;
    }
    
    public String getAppKusinaAnnouncePrefix() {
    	return appKusinaAnnouncePrefix;
    }

    public String getAppClientEnvironment() {
        return appClientEnvironment;
    }

    public String getAppKusinaPrefix() {
        return appKusinaPrefix;
    }

    public String getAppKusinaUserPrefix() {
    	return appKusinaUserPrefix;
    }
    
    public String getAppKusinaFeedbacksPrefix() {
    	return appKusinaFeedbacksPrefix;
    }
    
    public String getAppKusinaSessionsPrefix() {
    	return appKusinaSessionsPrefix;
    }
    
    public String getAppKusinaSettingsPrefix() {
    	return appKusinaSettingsPrefix;
    }
    
    public String getAppKusinaPreferencesPrefix() {
    	return appKusinaPreferencesPrefix;
    }
    
    public String getAppKusinaAirIDsPrefix() {
    	return appKusinaAirIDsPrefix;
    }
    
    public String getAppKusinaProfilesPrefix() {
    	return appKusinaProfilesPrefix;
    }
    
    public String getAppWebMetricsPrefix() {
        return appWebMetricsPrefix;
    }

    public String generateURI(String uri) {

        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(appClientHost);
        sb.append(":");
        sb.append(appClientPort);
        sb.append(uri);

        return sb.toString();
    }

    public String getEnvironmentCredentials() {

        if (appClientEnvironment.equalsIgnoreCase("STG")) {
            return "elastic:Pa55w0rdELKSTG";
        }
        return "elastic:3La5Ti2Sta6K";
    }
}
