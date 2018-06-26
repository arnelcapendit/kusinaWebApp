package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class AirIdModel {
	@JsonPropertyOrder({
		"AIRID"})
	private String airid;
	
	
	@JsonGetter("AIRID")
	public String getAirid() {
		return airid;
	}
	public void setAirid(String airid) {
		this.airid = airid;
	}
}
