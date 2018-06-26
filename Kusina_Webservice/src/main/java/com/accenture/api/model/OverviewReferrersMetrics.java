package com.accenture.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"REFERRER_URL", "USER", "VISITS", "AVG_VISIT_DURATION", "AVG_PAGES_PER_VISIT", "HITS", "BREAKDOWN"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverviewReferrersMetrics {

    private String referrerName;
    private String user;
    private String visits;
    private String avgVisit;
    private String avgPagesPerVisit;
    private String hits;
    
    private List<OverviewReferrersMetrics> ReferrersMetricsModel;

    @JsonGetter("REFERRER_URL")
    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    @JsonGetter("USER")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
    public List<OverviewReferrersMetrics> getReferrersMetricsModel() {
        return ReferrersMetricsModel;
    }

    public void setReferrersMetricsModel(List<OverviewReferrersMetrics> referrersMetricsModel) {
        ReferrersMetricsModel = referrersMetricsModel;
    }

    public OverviewReferrersMetrics(String referrerName, String user, String visits, String avgVisit, String avgPagesPerVisit, String hits) {
         this.referrerName = referrerName;
         this.user = user;
         this.visits = visits;
         this.avgVisit = avgVisit;
         this.avgPagesPerVisit = avgPagesPerVisit;
         this.hits = hits;
    }
    
    public OverviewReferrersMetrics() {
        // TODO Auto-generated constructor stub
    }
    
    
}