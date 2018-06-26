/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author marlon.naraja
 */
@JsonPropertyOrder({"SEGMENT_NAME", "CAREER_TRACKS", "GEOGRAPHY", "CAREER_LEVEL", "INDUSTRY_NAME", "UNIQUE_VISITORS", "UNIQUE_VISITOR_COUNT", "HITS"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SegmentModel{

    private String segmentName;
    private String careerTracks;
    private String geography;
    private String careerLevel;
    private String industryName;
    private String uniqueVisitorCount;
    private String hits;

    @JsonGetter("SEGMENT_NAME")
    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
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

    @JsonGetter("CAREER_LEVEL")
    public String getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(String careerLevel) {
        this.careerLevel = careerLevel;
    }

    @JsonGetter("INDUSTRY_NAME")
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @JsonGetter("UNIQUE_VISITOR_COUNT")
    public String getUniqueVisitorCount() {
        return uniqueVisitorCount;
    }

    public void setUniqueVisitorCount(String uniqueVisitorCount) {
        this.uniqueVisitorCount = uniqueVisitorCount;
    }

    @JsonGetter("HITS")
    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

}
