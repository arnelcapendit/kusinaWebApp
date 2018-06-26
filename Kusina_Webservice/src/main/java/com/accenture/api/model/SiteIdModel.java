package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class SiteIdModel {
	@JsonPropertyOrder({
		"SITE_ID"})
	private String site_id;
	
	
	@JsonGetter("SITE_ID")
	public String getSiteId() {
		return site_id;
	}
	public void setSiteId(String site_id) {
		this.site_id = site_id;
	}
}
