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
@JsonPropertyOrder({"PAGE_URL", "ACCESS_DATE_TIME", "USER_ID", "BROWSER", "OS", "GEOGRAPHY", "ASSET_NAME", "DEVICE",
    "ASSET_TYPE", "SEGMENT_NAME", "INDUSTRY_NAME", "CAREER_TRACKS", "CAREER_LEVEL"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageCustomInfoModel {

    private String pageUrl;
    private String accessDateTime;
    private String userId;
    private String browser;
    private String os;
    private String device;
    private String geography;
    private String assetName;
    private String assetType;
    private String segmentName;
    private String industryName;
    private String careerTracks;
    private String careerLevel;

    public PageCustomInfoModel(String pageUrl, String accessDateTime, String userId, String browser, String os, String device, String geography, String assetName, String assetType, String segmentName, String industryName, String careerTracks, String careerLevel) {
        this.pageUrl = pageUrl;
        this.accessDateTime = accessDateTime;
        this.userId = userId;
        this.browser = browser;
        this.os = os;
        this.device = device;
        this.geography = geography;
        this.assetName = assetName;
        this.assetType = assetType;
        this.segmentName = segmentName;
        this.industryName = industryName;
        this.careerTracks = careerTracks;
        this.careerLevel = careerLevel;
    }

    public PageCustomInfoModel() {
    }

    @JsonGetter("PAGE_URL")
    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @JsonGetter("ACCESS_DATE_TIME")
    public String getAccessDateTime() {
        return accessDateTime;
    }

    public void setAccessDateTime(String accessDateTime) {
        this.accessDateTime = accessDateTime;
    }

    @JsonGetter("USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @JsonGetter("DEVICE")
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @JsonGetter("GEOGRAPHY")
    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    @JsonGetter("ASSET_NAME")
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    @JsonGetter("ASSET_TYPE")
    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    @JsonGetter("SEGMENT_NAME")
    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    @JsonGetter("INDUSTRY_NAME")
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @JsonGetter("CAREER_TRACKS")
    public String getCareerTracks() {
        return careerTracks;
    }

    public void setCareerTracks(String careerTracks) {
        this.careerTracks = careerTracks;
    }

    @JsonGetter("CAREER_LEVEL")
    public String getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(String careerLevel) {
        this.careerLevel = careerLevel;
    }

}
