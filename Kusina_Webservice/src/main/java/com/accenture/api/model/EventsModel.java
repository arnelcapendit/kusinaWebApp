package com.accenture.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"EVENT_CATEGORIES", "EVENT_ACTION", "TOTAL_EVENTS", "TOTAL_VALUE"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventsModel {

	private String eventCategories;
	private String eventAction;
	private String eventName;
	private String eventCategoriesCount;
	private String eventActionCount;
	private String eventNameCount;
	private String totalEvents;
	private String totalValue;

	
	
	public EventsModel(String categories, String categoriesCount, String action, String actionCount, String name, String nameCount) {
		this.eventCategories = categories;
		this.eventAction = action;
		this.eventName = name;
		this.eventCategoriesCount = categoriesCount;
		this.eventActionCount = actionCount;
		this.eventNameCount = nameCount;
	}

	public EventsModel() {}

	 @JsonGetter("EVENT_CATEGORIES")
	public String getEventCategories() {
		return eventCategories;
	}



	public void setEventCategories(String eventCategories) {
		this.eventCategories = eventCategories;
	}


	@JsonGetter("EVENT_ACTION")
	public String getEventAction() {
		return eventAction;
	}



	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}


	@JsonGetter("EVENT_NAME")
	public String getEventName() {
		return eventName;
	}



	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	
	@JsonGetter("EVENT_CATEGORIES_COUNT")
	public String getEventCategoriesCount() {
		return eventCategoriesCount;
	}



	public void setEventCategoriesCount(String eventCategoriesCount) {
		this.eventCategoriesCount = eventCategoriesCount;
	}


	@JsonGetter("EVENT_ACTION_COUNT")
	public String getEventActionCount() {
		return eventActionCount;
	}



	public void setEventActionCount(String eventActionCount) {
		this.eventActionCount = eventActionCount;
	}


	@JsonGetter("EVENT_EVENT_COUNT")
	public String getEventNameCount() {
		return eventNameCount;
	}



	public void setEventNameCount(String eventNameCount) {
		this.eventNameCount = eventNameCount;
	}
	
	@JsonGetter("TOTAL_EVENTS")
	public String getTotalEvents() {
		return totalEvents;
	}


	public void setTotalEvents(String totalEvent) {
		this.totalEvents = totalEvent;
	}


	@JsonGetter("TOTAL_VALUE")
	public String getTotalValue() {
		return totalValue;
	}


	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

		
	
	
}
