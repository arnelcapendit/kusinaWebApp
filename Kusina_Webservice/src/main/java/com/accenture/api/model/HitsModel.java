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
@JsonPropertyOrder({"SEGMENT_NAME", "GEOGRAPHY", "INDUSTRY_NAME", "ASSET_TYPE", "HITS"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HitsModel {
    
    private String segmentName;
    private String geography;
    private String industryName;
    private String assetType;
    private String hits;

    
    @JsonGetter("SEGMENT_NAME")
    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    @JsonGetter("GEOGRAPHY")
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @JsonGetter("INDUSTRY_NAME")
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @JsonGetter("ASSET_TYPE")
    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    @JsonGetter("HITS")
    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
    
    
    
    
    
    
}
