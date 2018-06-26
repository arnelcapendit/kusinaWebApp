package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"USERNAME", "CAREER_LEVEL", "CAREER_TRACKS", "GEOGRAPHY", "COUNTRY", "VISITS"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverviewUserMetrics {

    private String user;
    private String careerLevel;
    private String careerTracks;
    private String geography;
    private String country;
    private String visits;

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

    @JsonGetter("COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonGetter("VISITS")
    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

}
