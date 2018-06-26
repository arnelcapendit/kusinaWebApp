package com.accenture.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"PAGE_URL", "USER", "VISITS", "AVG_VISIT_DURATION", "AVG_PAGES_PER_VISIT", "HITS", "BREAKDOWN"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverviewDownloadMetrics {
    
    private String pageurl;
    private String user;
    private String visits;
    private String avgVisit;
    private String avgPagesPerVisit;
    private String hits;
    
    private List<OverviewDownloadMetrics> DownloadMetricsModel;
    
    public OverviewDownloadMetrics(String pageurl, String user, String visits, String avgVisit, String avgPagesPerVisit, String hits) {
         this.pageurl = pageurl;
         this.user = user;
         this.visits = visits;
         this.avgVisit = avgVisit;
         this.avgPagesPerVisit = avgPagesPerVisit;
         this.hits = hits;
    }

    @JsonGetter("BREAKDOWN")
    public List<OverviewDownloadMetrics> getDownloadMetricsModel() {
        return DownloadMetricsModel;
    }

    public void setDownloadMetricsModel(List<OverviewDownloadMetrics> downloadMetricsModel) {
        DownloadMetricsModel = downloadMetricsModel;
    }

    public OverviewDownloadMetrics() {}

    @JsonGetter("PAGE_URL")
    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
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

}
