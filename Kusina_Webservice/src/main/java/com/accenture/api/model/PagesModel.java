/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 *
 * @author marlon.naraja
 */
@JsonPropertyOrder({"USERNAME", "CAREER_LEVEL", " CAREER_TRACKS", " GEOGRAPHY", "PAGE_URL", "VISITS", "AVG_VISIT_DURATION", "AVG_PAGES_PER_VISIT", "HITS", "BREAKDOWN"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagesModel {

    private String pageURL;
    private String user;
    private String careerLevel;
    private String careerTracks;
    private String geography;
    private String visits;
    private String avgVisit;
    private String avgPagesPerVisit;
    private String hits;
    
    private List<PagesModel> pageList;

    @JsonGetter("PAGE_URL")
    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    @JsonGetter("USERNAME")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    @JsonGetter("GEOGRAPHY")
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @JsonGetter("VISITS")
    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

    @JsonGetter("AVG_VISIT_DURATION")
    public String getAvgVisit() {
        return avgVisit;
    }

    public void setAvgVisit(String avgVisit) {
        this.avgVisit = avgVisit;
    }

    @JsonGetter("AVG_PAGES_PER_VISIT")
    public String getAvgPagesPerVisit() {
        return avgPagesPerVisit;
    }

    public void setAvgPagesPerVisit(String avgPagesPerVisit) {
        this.avgPagesPerVisit = avgPagesPerVisit;
    }

    @JsonGetter("HITS")
    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

     @JsonGetter("BREAKDOWN")
    public List<PagesModel> getPageList() {
        return pageList;
    }

    public void setPageList(List<PagesModel> pageList) {
        this.pageList = pageList;
    }
    
    
    
    
}
