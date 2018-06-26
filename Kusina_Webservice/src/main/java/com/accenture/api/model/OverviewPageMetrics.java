package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author: arnel.m.capendit
 * @date: 11/23/2017 8:45 PM
 */
@JsonPropertyOrder({"PAGE_URL", "VISITS", "GEOGRAPHY", "UNIQUE_VISITORS", "PAGEVIEWS"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverviewPageMetrics {

    private String pageurl;
    private String visits;
    private String user;
    private String hits;

    
    @JsonGetter("PAGE_URL")
    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    @JsonGetter("VISITS")
    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

   @JsonGetter("UNIQUE_VISITORS") 
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @JsonGetter("PAGEVIEWS")
    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

}
