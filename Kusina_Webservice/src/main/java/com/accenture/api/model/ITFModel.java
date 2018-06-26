package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
	"USERNAME", 
	"APPLICATION", 
	"USER_ROLE", 
	"GEOGRAPHY", 
	"COUNTRY",
	"CAREER_LEVEL",
	"CAREER_TRACKS",
	"VISITS",
	"HITS",
	"TOTAL_TIME_SPENT"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ITFModel {

	private String username;
	private String application;
	private String role;
	private String geography;
	private String country;
	private String careerLevel;
	private String careerTracks;
	private String visits;
	private String hits;
	private String totalTimeSpent;
	
	public ITFModel() {
		
	}
	
	public ITFModel(String username, String application, String role, String geography, String country,
			String careerLevel, String careerTracks, String visits, String hits, String totalTimeSpent) {
		super();
		this.username = username;
		this.application = application;
		this.role = role;
		this.geography = geography;
		this.country = country;
		this.careerLevel = careerLevel;
		this.careerTracks = careerTracks;
		this.visits = visits;
		this.hits = hits;
		this.totalTimeSpent = totalTimeSpent;
	}

	@JsonGetter("USERNAME")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonGetter("APPLICATION")
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
	@JsonGetter("USER_ROLE")
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@JsonGetter("GEOGRAPHY")
	public String getGeography() {
		return geography;
	}
	public void setGeography(String geography) {
		this.geography = geography;
	}
	
	@JsonGetter("COUNTRY")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@JsonGetter("CAREER_LEVEL")
	public String getCareerLevel() {
		return careerLevel;
	}
	public void setCareerLevel(String careerLevel) {
		this.careerLevel = careerLevel;
	}
	
	@JsonGetter("CAREER_TRACKS")
	public String getCareerTracks() {
		return careerTracks;
	}
	public void setCareerTracks(String careerTracks) {
		this.careerTracks = careerTracks;
	}
	
	@JsonGetter("VISITS")
	public String getVisits() {
		return visits;
	}
	public void setVisits(String visits) {
		this.visits = visits;
	}
	
	@JsonGetter("HITS")
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}

	@JsonGetter("TOTAL_TIME_SPENT")
	public String getTotalTimeSpent() {
		return totalTimeSpent;
	}

	public void setTotalTimeSpent(String totalTimeSpent) {
		this.totalTimeSpent = totalTimeSpent;
	}
	
	
	
	
}
