package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
	"LAST_ACTION_TIME",
	"USER", 
	"ADDRESS",
	"COUNTRY", 
	"BROWSER", 
	"OS", 
	"CLEAN_PAGEURL", 
	"REFERRER_URL",
	"PAGE_TITLE",
	"QUICKTIME_PLUGIN", 
	"PDF PLUGIN", 
	"REALPLAYER_PLUGIN",
	"SILVER_PLUGIN",
	"WINDOWSMEDIA_PLUGIN",
	"JAVA_PLUGIN",
	"GEAR_PLUGIN",
	"DIRECTOR_PLUGIN",
	"FLASH_PLUGIN",
	"VISITS"
	})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitorLogsModel {
	private String lastactiontime;
	private String user;
	private String address;
	private String country;
	private String browser;
	
	private String os;
	private String cleanPageURL;
//	private String referrerURL;
	private String pagetitle;
//	private String quickTimePlugin;
//	private String pDFPlugin;
//	private String realPlayerPlugin;
//	private String flashPlugin;
//	private String silverlightPlugin;
//	private String windowsMediaPlugin;
//	private String javaPlugin;
//	private String gearPlugin;
//	private String directorPlugin;	
	private String visits;
	
	public VisitorLogsModel(
			String lastactiontime,
			String user, 
			String address,
			String country, 
			String browser, 
			String os, 
			String cleanPageURL,
			String referrerURL,
			String pagetitle,
			String quickTimePlugin, 
			String pDFPlugin, 
			String realPlayerPlugin, 
			String flashPlugin, 
			String silverlightPlugin, 
			String windowsMediaPlugin, 
			String javaPlugin,
			String gearPlugin, 
			String directorPlugin,
			String visits ){

		this.lastactiontime = lastactiontime;
		this.user = user;
		this.address = address;
		this.country = country;
		this.browser = browser;
		this.os = os;
		this.cleanPageURL = cleanPageURL;
//		this.referrerURL = referrerURL;
		this.pagetitle = pagetitle;
//		this.quickTimePlugin = quickTimePlugin;
//		this.pDFPlugin = pDFPlugin;
//		this.realPlayerPlugin = realPlayerPlugin;
//		this.silverlightPlugin = silverlightPlugin;
//		this.windowsMediaPlugin = windowsMediaPlugin;
//		this.javaPlugin = javaPlugin;
//		this.gearPlugin = gearPlugin;
//		this.directorPlugin = directorPlugin;
//		this.flashPlugin = flashPlugin;	
		this.visits = visits;	
	}
//	
//	public VisitorLogsModel(String user, String country, String browser, String os, String cleanPageURL, 
//			String quickTimePlugin, String pDFPlugin, String realPlayerPlugin, 
//			String flashPlugin, String silverlightPlugin, String windowsMediaPlugin, String javaPlugin,
//			String gearPlugin, String directorPlugin, String referrerURL, String pageURL, String timestamp, String pagetitle){
//		this.user = user;
//		this.country = country;
//		this.browser = browser;
//		this.os = os;
//		this.cleanPageURL = cleanPageURL;
//		this.quickTimePlugin = quickTimePlugin;
//		this.pDFPlugin = pDFPlugin;
//		this.realPlayerPlugin = realPlayerPlugin;
//		this.flashPlugin = flashPlugin;
//		this.silverlightPlugin = silverlightPlugin;
//		this.windowsMediaPlugin = windowsMediaPlugin;
//		this.javaPlugin = javaPlugin;
//		this.gearPlugin = gearPlugin;
//		this.directorPlugin = directorPlugin;
//		this.referrerURL = referrerURL;
//		this.pageURL = pageURL;
//		this.timestamp = timestamp;
//		this.pagetitle = pagetitle;
//	}
	
	
//	public VisitorLogsModel(String user, String country, String browser){
//		this.user = user;
//		this.country = country;
//		this.browser = browser;
//	}

	public VisitorLogsModel() {
		// TODO Auto-generated constructor stub
	}


	@JsonGetter("LAST_ACTION_TIME")
	public String getLastactiontime() {
		return lastactiontime;
	}

	public void setLastactiontime(String lastactiontime) {
		this.lastactiontime = lastactiontime;
	}

	@JsonGetter("ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonGetter("USER")
	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}

	@JsonGetter("COUNTRY")
	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}

	@JsonGetter("BROWSER")
	public String getBrowser() {
		return browser;
	}


	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@JsonGetter("OS")
	public String getOs() {
		return os;
	}

	
	public void setOs(String os) {
		this.os = os;
	}

	@JsonGetter("CLEAN_PAGEURL")
	public String getCleanPageURL() {
		return cleanPageURL;
	}


	public void setCleanPageURL(String cleanPageURL) {
		this.cleanPageURL = cleanPageURL;
	}


	
	@JsonGetter("PAGE_TITLE")
	public String getPagetitle() {
		return pagetitle;
	}

	public void setPagetitle(String pagetitle) {
		this.pagetitle = pagetitle;
	}
	
	@JsonGetter("VISITS")
	public String getVisits() {
		return visits;
	}

	public void setVisits(String visits) {
		this.visits = visits;
	}
	
	
}