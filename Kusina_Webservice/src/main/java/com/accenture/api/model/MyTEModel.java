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
@JsonPropertyOrder({"PAGE_URL", "COUNTRY", "AVG_GENERATION_TIME"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyTEModel {

    private String pageUrl;
    private String geography;
    private String avgGenTime;

    public MyTEModel(String pageUrl, String geography, String avgGenTime) {
        this.pageUrl = pageUrl;
        this.geography = geography;
        this.avgGenTime = avgGenTime;
    }

    public MyTEModel() {
    }

    @JsonGetter("PAGE_URL")
    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @JsonGetter("COUNTRY")
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @JsonGetter("AVG_GENERATION_TIME")
    public String getAvgGenTime() {
        return avgGenTime;
    }

    public void setAvgGenTime(String avgGenTime) {
        this.avgGenTime = avgGenTime;
    }
}
